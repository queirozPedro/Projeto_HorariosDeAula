import java.sql.*;
import java.util.ArrayList;

public class ComponenteCurricular {
    private int id_componente;
    private int cargaHoraria; 
    private String nome;
    private int semestre;
    private String codigo;
    private boolean optativa;

    // Sobrecarga dos construtores, um para as variaveis padrão do código e um para a edição
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

    public int getCargaHoraria() {
        return cargaHoraria;
    }
    
    /**
     * Metodo cadastrar que cadastra o Componente Curricular no Banco.
     */
    public void Cadastrar(){
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
    
        try {
            if (buscarComponente(this.codigo) == null) {
                PreparedStatement pstmt = connection.prepareStatement("INSERT INTO componente_curricular(nome, carga_horaria, semestre, codigo, optativa) VALUES (?, ?, ?, ?, ?)");
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
                return new ComponenteCurricular(rs.getInt(1), rs.getInt(3), rs.getString(2), rs.getInt(4), rs.getString(5), rs.getBoolean(6));
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
                return new ComponenteCurricular(rs.getInt(1), rs.getInt(3), rs.getString(2), rs.getInt(4), rs.getString(5), rs.getBoolean(6));
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

    public static void editaComponente(int id_comp, Object dado, int id_dado){

        Connection connection = PostgreSQLConnection.getInstance().getConnection();
    
        try {

            if (id_dado == 1) {
                PreparedStatement pstmt = connection.prepareStatement("UPDATE componente_curricular SET nome = ? WHERE id_comp = ?");
                String nome = dado.toString();
                pstmt.setString(1, nome);
                pstmt.setInt(2, id_comp);
                pstmt.executeUpdate();

                System.out.println("Nome editado com sucesso!");

            } else if (id_dado == 2) {
                PreparedStatement pstmt = connection.prepareStatement("UPDATE componente_curricular SET carga_horaria = ? WHERE id_comp = ?");
                int carga_horaria = (int) dado;
                pstmt.setInt(1, carga_horaria);
                pstmt.setInt(2, id_comp);
                pstmt.executeUpdate();

                System.out.println("Carga horária editada com sucesso!");

            } else if (id_dado == 3) {
                PreparedStatement pstmt = connection.prepareStatement("UPDATE componente_curricular SET semestre = ? WHERE id_comp = ?");
                int semestre = (int) dado;
                pstmt.setInt(1, semestre);
                pstmt.setInt(2, id_comp);
                pstmt.executeUpdate();

                System.out.println("Semestre editado com sucesso!");
                
            } else if (id_dado == 4) {
                PreparedStatement pstmt = connection.prepareStatement("UPDATE componente_curricular SET codigo = ? WHERE id_comp = ?");
                String codigo = dado.toString();
                pstmt.setString(1, codigo);
                pstmt.setInt(2, id_comp);
                pstmt.executeUpdate();

                System.out.println("Código editado com sucesso!");

            } else if (id_dado == 5) {
                PreparedStatement pstmt = connection.prepareStatement("UPDATE componente_curricular SET optativa = ? WHERE id_comp = ?");
                Boolean optativa = Boolean.valueOf(dado.toString());
                pstmt.setBoolean(1, optativa);
                pstmt.setInt(2, id_comp);
                pstmt.executeUpdate();
                
                System.out.println("Componente editado com sucesso!");

            } else{
                System.out.println("Dado inválido!");
            }


        } catch (java.sql.SQLException e) {
            System.out.println("Erro ao cadastrar professor: " + e.getMessage());
        }

    }

    @Override
    public String toString() {
        return "ComponenteCurricular [id_componente=" + id_componente + ", cargaHoraria=" + cargaHoraria + ", nome="
                + nome + ", semestre=" + semestre + ", codigo=" + codigo + ", optativa=" + optativa + "]";
    }

    
}
