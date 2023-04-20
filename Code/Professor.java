package Projeto_HorariosDeAula.Code;

public class Professor{
    private String nome;
    private String cpf;
    private String formacao;
    private String email;
    //private List<ComponenteCurricular> disciplinas;
    
    public Professor(String nome, String cpf, String formacao, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.formacao = formacao;
        this.email = email;
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

    /**
     * Metodo que edita o Nome do professor
     * @param nome
     */
    public void editaProfessorNome(String nome){
        this.nome = nome;
    }

    /**
     * Metodo que edita o Cpf do professor
     * @param cpf
     */
    public void editaProfessorCpf(String cpf){
        this.cpf = cpf;
    }

    /**
     * Metodo que edita a Formacao do professor
     * @param formacao
     */
    public void editaProfessorFormacao(String formacao){
        this.formacao = formacao;
    }

    /**
     * Metodo que edita o Email do professor
     * @param email
     */
    public void editaProfessorEmail(String email){
        this.email = email;
    }

    @Override
    public String toString() {
        return "Professor [nome=" + nome + ", cpf=" + cpf + ", formacao=" + formacao + ", email=" + email + "]";
    }
}
