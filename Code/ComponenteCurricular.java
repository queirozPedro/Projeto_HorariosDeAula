import java.sql.*;
import java.util.ArrayList;

public class ComponenteCurricular {
    private int id_componente;
    private int cargaHoraria; 
    private String nome;
    private int semestre;
    private String codigo;
    private boolean optativa;

    public ComponenteCurricular(int cargaHoraria, String nome, int semestre, String codigo, boolean optativa) {
        this.cargaHoraria = cargaHoraria;
        this.nome = nome;
        this.semestre = semestre;
        this.codigo = codigo;
        this.optativa = optativa;
    }

    public ComponenteCurricular(int id_componente, int cargaHoraria, String nome, int semestre, String codigo,
            boolean optativa) {
        this.id_componente = id_componente;
        this.cargaHoraria = cargaHoraria;
        this.nome = nome;
        this.semestre = semestre;
        this.codigo = codigo;
        this.optativa = optativa;
    }

    public void Cadastrar(){
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
    
        try {
            if (buscarComponente(this.codigo) == null) {
                PreparedStatement pstmt = connection.prepareStatement("insert into componente_curricular(nome, carga_horaria, semestre, codigo, optativa) values (?, ?, ?, ?, ?)");
                pstmt.setString(1, this.nome);
                pstmt.setInt(2, this.cargaHoraria);
                pstmt.setInt(3, this.semestre);
                pstmt.setString(4, this.codigo);
                pstmt.setBoolean(5, this.optativa);
                pstmt.executeUpdate();
                System.out.println("Componente curricular cadastrado com sucesso!");
            } else {
                System.out.println("Componente curricular já cadastrado!");
            }
        } catch (java.sql.SQLException e) {
            System.out.println("Erro ao cadastrar componente curricular: " + e.getMessage());
        }
    }

    public static void ExcluirComponente(String codigo){
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement pstmt = null;

        try {
            pstmt = connection.prepareStatement("DELETE from componente_curricular where codigo = ?");
            pstmt.setString(1, codigo);
            int rowsDeleted = pstmt.executeUpdate();
    
            if (rowsDeleted > 0){
                System.out.println("Componente curricular excluído com sucesso!");
            }
            else {
                System.out.println("Componente curricular não encontrado!");
            }
    
        } catch (SQLException e) {
            System.out.println("Erro ao excluir componente curricular: " + e.getMessage());
        }
    }

    public static ComponenteCurricular buscarComponente(String codigo){
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
    
        try{
            pstmt = connection.prepareStatement("SELECT * from componente_curricular WHERE codigo = ?");
            pstmt.setString(1, codigo);
            rs = pstmt.executeQuery();
    
            if (rs.next()) {
                return new ComponenteCurricular(rs.getInt(3), rs.getString(2), rs.getInt(4), rs.getString(5), rs.getBoolean(6));
            }
    
            return null;
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
    
        return null;
    }

    public static ComponenteCurricular buscarComponente(int id){
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
    
        try{
            pstmt = connection.prepareStatement("SELECT * from componente_curricular WHERE id_comp = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
    
            if (rs.next()) {
                return new ComponenteCurricular(rs.getInt(3), rs.getString(2), rs.getInt(4), rs.getString(5), rs.getBoolean(6));
            }
    
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
    
        return null;
    }

    public static ArrayList<ComponenteCurricular> listarComponentes(){

        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        ArrayList<ComponenteCurricular> componentes = new ArrayList<>();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
    
        try{
            pstmt = connection.prepareStatement("SELECT * from componente_curricular");
            rs = pstmt.executeQuery();
    
            while (rs.next()) {
                ComponenteCurricular componente = new ComponenteCurricular(rs.getInt(1), rs.getInt(3), rs.getString(2), rs.getInt(4), rs.getString(5), rs.getBoolean(6));
                componentes.add(componente);
            }
    
            return componentes;
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
        return componentes;
    }

    @Override
    public String toString() {
        return "ComponenteCurricular [id_componente=" + id_componente + ", cargaHoraria=" + cargaHoraria + ", nome="
                + nome + ", semestre=" + semestre + ", codigo=" + codigo + ", optativa=" + optativa + "]";
    }

    
}
