public class ComponenteCurricular {
    private int cargaHoraria; 
    private String nome;
    private String semestre;
    private boolean optativa;

    public ComponenteCurricular(int cargaHoraria, String nome, String semestre, boolean optativa) {
        this.cargaHoraria = cargaHoraria;
        this.nome = nome;
        this.semestre = semestre;
        this.optativa = optativa;
    }

    public void editaComponenteCurricular(int cargaHoraria, String nome, String semestre, boolean optativa){
        this.cargaHoraria = cargaHoraria;
        this.nome = nome;
        this.semestre = semestre;
        this.optativa = optativa;
    }

    @Override
    public String toString() {
        return "ComponenteCurricular [cargaHoraria=" + cargaHoraria + ", nome=" + nome + ", semestre=" + semestre
                + ", optativa=" + optativa + "]";
    }
}
