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

    public void listaProfessores(){
        if(professores.size()>1){
            for(int i = 0; i < professores.size(); i++){
                System.out.println(professores.get(i).toString());
            }
        }
        else{
            System.out.println(professores.get(0));
        }
    }

    @Override
    public String toString() {
        return "Turma [professores=" + professores + ", horario=" + horario + ", turno=" + turno + "]";
    }
    
}
