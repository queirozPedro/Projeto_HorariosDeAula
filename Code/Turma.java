package Projeto_HorariosDeAula.Code;

import java.util.ArrayList;
import java.util.List;
public class Turma{
    private List<Professor> professores;
    private String horario;
    private String turno;
    private ArrayList<Integer> idProfessores;
    private ArrayList<Integer> idComponenteCurricular;

    // Construtor

    @Override
    public String toString() {
        return "Turma [professores=" + professores + ", horario=" + horario + ", turno=" + turno + "]";
    }
    
}
