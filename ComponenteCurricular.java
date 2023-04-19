package Projeto_HorariosDeAula;

public class ComponenteCurricular {
    private Professor professor;
    private int cargaHoraria; 
    private String nome;
    private String semestre;
    private boolean optativa;

    public ComponenteCurricular(Professor professor, int cargaHoraria, String nome, String semestre, boolean optativa) {
        this.professor = professor;
        this.cargaHoraria = cargaHoraria;
        this.nome = nome;
        this.semestre = semestre;
        this.optativa = optativa;
    }

    @Override
    public String toString() {
        return "ComponenteCurricular [professor=" + professor + ", cargaHoraria=" + cargaHoraria + ", nome=" + nome
                + ", semestre=" + semestre + ", optativa=" + optativa + "]";
    }
}
