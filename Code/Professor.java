import java.sql.*;
import java.util.ArrayList;

public class Professor{
    private String nome;
    private String cpf;
    private String formacao;
    private String email;;

    /** 
     * O Construtor vai receber todas as variáveis e enviar para o banco de Dados
     * É um protópipo 
    */
    public Professor(String nome, String cpf, String formacao, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.formacao = formacao;
        this.email = email;
    }
    
    public void Cadastrar(){
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
    
        try {
            if (buscarProfessor(this.cpf) == null) {
                PreparedStatement pstmt = connection.prepareStatement("insert into professor(nome, cpf, formacao, email) values (?, ?, ?, ?)");
                pstmt.setString(1, this.nome);
                pstmt.setString(2, this.cpf);
                pstmt.setString(3, this.formacao);
                pstmt.setString(4, this.email);
                pstmt.executeUpdate();
                System.out.println("Professor cadastrado com sucesso!");
            } else {
                System.out.println("Professor já cadastrado!");
            }
        } catch (java.sql.SQLException e) {
            System.out.println("Erro ao cadastrar professor: " + e.getMessage());
        }
    }

    public static void ExcluirProfessor(String cpf){
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement pstmt = null;

        try {
            pstmt = connection.prepareStatement("DELETE from professor where cpf = ?");
            pstmt.setString(1, cpf);
            int rowsDeleted = pstmt.executeUpdate();
    
            if (rowsDeleted > 0){
                System.out.println("Professor excluído com sucesso!");
            }
            else {
                System.out.println("Professor não encontrado!");
            }
    
        } catch (SQLException e) {
            System.out.println("Erro ao excluir professor: " + e.getMessage());
        }
    }

    public static Professor buscarProfessor(String cpf){
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
    
        try{
            pstmt = connection.prepareStatement("SELECT * from professor WHERE cpf = ?");
            pstmt.setString(1, cpf);
            rs = pstmt.executeQuery();
    
            if (rs.next()) {
                return new Professor(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
            }
    
            return null;
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
    
        return null;
    }

    public static ArrayList<Professor> listarProfessores(){

        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        ArrayList<Professor> professores = new ArrayList<>();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
    
        try{
            pstmt = connection.prepareStatement("SELECT * from professor");
            rs = pstmt.executeQuery();
    
            while (rs.next()) {
                Professor professor = new Professor(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                professores.add(professor);
            }
    
            return professores;
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
        return professores;
    }

    /**
     * Metodo que reedita os dados de um professor
     * @param nome
     * @param cpf
     * @param formacao
     * @param email
     */
    public void editaProfessor(String nome, String cpf, String formacao, String email){
        this.nome = nome;
        this.cpf = cpf;
        this.formacao = formacao;
        this.email = email;
    }



    @Override
    public String toString() {
        return "Professor [nome=" + nome + ", cpf=" + cpf + ", formacao=" + formacao + ", email=" + email + "]";
    }
}
