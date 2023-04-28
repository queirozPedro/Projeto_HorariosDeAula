package Projeto_HorariosDeAula.Code;

import java.util.ArrayList;
import java.util.List;
public class Turma{
    private List<Professor> professores;
    private String horario;
    private String turno;
    private ArrayList<Integer> idProfessores;
    private ArrayList<Integer> idComponenteCurricular;

    public Turma(List<Professor> professores, String horario, String turno, int idProfessores,
            int idComponenteCurricular) {
        this.professores = professores;
        this.horario = horario;
        this.turno = turno;
        this.idProfessores.add = idProfessores;
        this.idComponenteCurricular.add = idComponenteCurricular;
    }

    @Override
    public String toString() {
        return "Turma [professores=" + professores + ", horario=" + horario + ", turno=" + turno + "]";
    }
    
}
