package Projeto_HorariosDeAula;

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

    @Override
    public String toString() {
        return "Professor [nome=" + nome + ", cpf=" + cpf + ", formacao=" + formacao + ", email=" + email + "]";
    }
}
