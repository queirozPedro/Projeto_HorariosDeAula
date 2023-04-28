package Projeto_HorariosDeAula.Code;

import java.util.List;
public class Turma extends ComponenteCurricular{
    private List<Professor> professores;
    private String horario;
    private String turno;

    public Turma(int cargaHoraria, String nome, String semestre, boolean optativa, List<Professor> professores, String horario, String turno) {
        super(cargaHoraria, nome, semestre, optativa);
        this.professores = professores;
        this.horario = horario;
        this.turno = turno;
    }

    @Override
    public String toString() {
        return "Turma [professores=" + professores + ", horario=" + horario + ", turno=" + turno + "]";
    }
    
}
