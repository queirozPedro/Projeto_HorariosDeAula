package Projeto_HorariosDeAula;

import java.util.List;

public class Turma {
    private List<Professor> professor;
    private String turno;
    private String horario;
    
    public Turma(List<Professor> professor, String turno, String horario) {
        this.professor = professor;
        this.turno = turno;
        this.horario = horario;
    }

    @Override
    public String toString() {
        return "Turma [professor=" + professor + ", turno=" + turno + ", horario=" + horario + "]";
    }
}
