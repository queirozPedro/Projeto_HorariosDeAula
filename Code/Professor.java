import java.sql.*;
import java.util.ArrayList;

public class Professor{
    private int id_prof;
    private String nome;
    private String cpf;
    private String formacao;
    private String email;;

    //Dois contrutores sobrecarregados, que servem para receber os dados e indexalos caso necessário 
    public Professor(String nome, String cpf, String formacao, String email) {
        // Tenho que dar um jeito de checar se a formatação está correta...

        this.nome = nome;
        this.cpf = cpf;
        this.formacao = formacao;
        this.email = email;
    }
    public Professor(int id_prof, String nome, String cpf, String formacao, String email) {
        this.id_prof = id_prof;
        this.nome = nome;
        this.cpf = cpf;
        this.formacao = formacao;
        this.email = email;
    }
    public Professor(){}
    
    // Seters que recebem os dados dos professor
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public void setFormacao(String formacao) {
        this.formacao = formacao;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public int getId_prof() {
        return id_prof;
    }

    /**
     * Função que Cadastra o Professor no Banco de Dados. 
     */
    public void Cadastrar(){
        // Cria um Objeto connection que vai servir para conectar ao Banco
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
    
        try {
            if (buscarProfessor(this.cpf) == null) {
                // O PreparedStatement serve como uma especie de Hascode que indexa um dado ou um conjunto de dados
                PreparedStatement pstmt = connection.prepareStatement("INSERT INTO professor(nome, cpf, formacao, email) VALUES (?, ?, ?, ?)");
                
                // Criar uma maneira de receber os dados já formatados!!
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

    /**
     * Metodo que exclui um professor. Recebe o Cpf do professor, busca no Banco e se ele for encontrado será excluido
     * @param cpf
     */
    public static void ExcluirProfessor(String cpf){
        // Conexão com o Banco
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement pstmt = null;

        try {

            pstmt = connection.prepareStatement("DELETE FROM turma_professor WHERE id_prof in(SELECT id_prof FROM professor WHERE cpf = ?)");
            pstmt.setString(1, cpf);
            pstmt.executeUpdate();

            pstmt = connection.prepareStatement("DELETE FROM professor WHERE cpf = ?");
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

    /**
     * Metodo que percorre o banco de dados em busca de um professor usando seu CPF como chave.
     * Retorna o professor caso encontrado e Null caso não encontrado
     * @param cpf
     * @return Professor
     */
    public static Professor buscarProfessor(String cpf){
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
    
        try{
            pstmt = connection.prepareStatement("SELECT * from professor WHERE cpf = ?"); // Seleciona todas as linhas onde o cpf for igual ao consultado
            pstmt.setString(1, cpf);
            rs = pstmt.executeQuery();
    
            if (rs.next()) {
                return new Professor(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
            }
    
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Busca um professor no banco, usa o id do professor para buscar.
     * @param id_prof
     * @return
     */
    public static Professor buscarProfessor(int id_prof){
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
    
        try{
            pstmt = connection.prepareStatement("SELECT * from professor WHERE id_prof = ?"); // Seleciona todas as linhas onde o cpf for igual ao consultado
            pstmt.setInt(1, id_prof);
            rs = pstmt.executeQuery();
    
            if (rs.next()) {
                return new Professor(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
            }
    
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
    
        return null;
    }
    
    /**
     * Retorna uma lista de Professores que estão no Banco de Dados
     * @return ArrayList<Professor>
     */ 
    public static ArrayList<Professor> listarProfessores(){

        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        ArrayList<Professor> professores = new ArrayList<>();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
    
        try{
            pstmt = connection.prepareStatement("SELECT * from professor ORDER BY id_prof");
            rs = pstmt.executeQuery();
    
            while (rs.next()) {
                Professor professor = new Professor(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                professores.add(professor);
            }
    
            return professores;
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
        return professores;
    }

    /**
     * Não entendi como funciona
     * @param id_prof
     * @param dado
     * @param id_dado
     */
    public static void editaProfessor(int id_prof, String dado, int id_dado){
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
    
        try {
            //Caso o id do dado seja 1 o nome do professor será atualizado
            if (id_dado == 1) {
                PreparedStatement pstmt = connection.prepareStatement("UPDATE professor SET nome = ? WHERE id_prof = ?");
                pstmt.setString(1, dado);
                pstmt.setInt(2, id_prof);
                pstmt.executeUpdate();

                System.out.println("Nome editado com sucesso!");

            //Caso o id do dado seja 2 o cpf do professor será atualizado
            } else if (id_dado == 2) {
                PreparedStatement pstmt = connection.prepareStatement("UPDATE professor SET cpf = ? WHERE id_prof = ?");
                pstmt.setString(1, dado);
                pstmt.setInt(2, id_prof);
                pstmt.executeUpdate();

                System.out.println("CPF editado com sucesso!");

            //Caso o id do dado seja 3 a formação do professor será atualizado
            } else if (id_dado == 3) {
                PreparedStatement pstmt = connection.prepareStatement("UPDATE professor SET formacao = ? WHERE id_prof = ?");
                pstmt.setString(1, dado);
                pstmt.setInt(2, id_prof);
                pstmt.executeUpdate();

                System.out.println("Formação editada com sucesso!");
                
            //Caso o id do dado seja 4 o email do professor será atualizado
            } else if (id_dado == 4) {
                PreparedStatement pstmt = connection.prepareStatement("UPDATE professor SET email = ? WHERE id_prof = ?");
                pstmt.setString(1, dado);
                pstmt.setInt(2, id_prof);
                pstmt.executeUpdate();

                System.out.println("Email editado com sucesso!");

            } else{
                System.out.println("Dado inválido!");
            }


        } catch (java.sql.SQLException e) {
            System.out.println("Erro ao cadastrar professor: " + e.getMessage());
        }

    }

    public void limpaProfessor(){
        this.id_prof = 0;
        this.nome = null;
        this.cpf = null;
        this.formacao = null;
        this.email = null;
    }

    @Override
    public String toString() {
        return "\nInformações do Professor\nNome: "+ nome + "\nCpf: "+ cpf +"\nFormação: "+ formacao +"\nEmail: "+ email;
    }

    public String toString(int id){
        return "Professor -> "+ "id" +" "+ nome + " "+ cpf;
    }
}
