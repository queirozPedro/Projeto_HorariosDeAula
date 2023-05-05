import java.sql.*;
import java.util.ArrayList;

public class Turma{
    private int idTurma;
    private ArrayList<Integer> idProfessor = new ArrayList<>();
    private int idComponenteCurricular;
    private String horario1;
    private String horario2 = null;
    private int vagas;
    private int codigo = 1;

    public Turma(int idProfessor, int idComponenteCurricular, String horario1, String horario2,
            int vagas) {
        this.idProfessor.add(idProfessor);
        this.idComponenteCurricular = idComponenteCurricular;
        this.horario1 = horario1;
        this.horario2 = horario2;
        this.vagas = vagas;
    }

    public Turma(int idTurma, ArrayList<Integer> idProfessor, int idComponenteCurricular, String horario1,
            String horario2, int vagas, int codigo) {
        this.idTurma = idTurma;
        this.idProfessor = idProfessor;
        this.idComponenteCurricular = idComponenteCurricular;
        this.horario1 = horario1;
        this.horario2 = horario2;
        this.vagas = vagas;
        this.codigo = codigo;
    }

    public ArrayList<Integer> getIdProfessor() {
        return idProfessor;
    }

    public int getIdTurma() {
        return idTurma;
    }

    public void Cadastrar(){

        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
    
        try {

            pstmt = connection.prepareStatement("SELECT count(*) FROM turma WHERE id_comp = ?");
            pstmt.setInt(1, this.idComponenteCurricular);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                this.codigo += rs.getInt(1);
            }

            //Verifica se os horários que estão sendo cadastrados estão disponíveis no semestre que a turma vai ser inserida
            pstmt = connection.prepareStatement("SELECT horario1, horario2 FROM turma as t INNER JOIN componente_curricular as cc ON t.id_comp = cc.id_comp WHERE semestre in(SELECT semestre FROM componente_curricular WHERE id_comp = ?)");
            pstmt.setInt(1, this.idComponenteCurricular);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                if (this.horario1.equals(rs.getString(1)) || (this.horario2 != null && this.horario2.equals(rs.getString(1))) ||this.horario1.equals(rs.getString(2)) || (this.horario2 != null && this.horario2.equals(rs.getString(2)))) {
                System.out.println("Horário não está disponível!");
                return;
                }

            }

            //Verifica se os horários que estão sendo cadastrados estáo disponíveis para o professor escolhido para ministrar essa turma
            pstmt = connection.prepareStatement("SELECT horario1, horario2 FROM turma NATURAL JOIN turma_professor WHERE id_prof = ?");
            pstmt.setInt(1, this.idProfessor.get(0));
            rs = pstmt.executeQuery();

            while (rs.next()) {
                if (this.horario1.equals(rs.getString(1)) || (this.horario2 != null && this.horario2.equals(rs.getString(1))) ||this.horario1.equals(rs.getString(2)) || (this.horario2 != null && this.horario2.equals(rs.getString(2)))) {
                System.out.println("Horário não está disponível!");
                return;
                }

            }

            pstmt = connection.prepareStatement("INSERT INTO turma (id_comp, horario1, horario2, vagas, codigo) VALUES (?, ?, ?, ?, ?)");
            pstmt.setInt(1, this.idComponenteCurricular);
            pstmt.setString(2, this.horario1);
            pstmt.setString(3, this.horario2);
            pstmt.setInt(4, this.vagas);
            pstmt.setInt(5, this.codigo);
            pstmt.executeUpdate();

            pstmt = connection.prepareStatement("SELECT id_turma from turma WHERE codigo = ? and id_comp = ?");
            pstmt.setInt(1, this.codigo);
            pstmt.setInt(2, this.idComponenteCurricular);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                PreparedStatement pstmt2 = connection.prepareStatement("INSERT INTO turma_professor (id_turma,id_prof) VALUES (?, ?)");
                pstmt2.setInt(1, rs.getInt(1));
                pstmt2.setInt(2, this.idProfessor.get(0));

                pstmt2.executeUpdate();
            }

            System.out.println("Turma cadastrada com sucesso!");
        } catch (java.sql.SQLException e) {
            System.out.println("Erro ao cadastrar turma: " + e.getMessage());
        }

    }

    public static void ExcluirTurma(int id_turma){
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        ResultSet rs = null;
        PreparedStatement pstmt = null;

        try {

            pstmt = connection.prepareStatement("SELECT codigo, id_comp FROM turma WHERE id_turma = ?");
            pstmt.setInt(1, id_turma);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                pstmt = connection.prepareStatement("UPDATE turma SET codigo = codigo - 1 WHERE codigo > ? AND id_comp = ?");
                pstmt.setInt(1, rs.getInt(1));
                pstmt.setInt(2, rs.getInt(2));
                pstmt.executeUpdate();
            }

            pstmt = connection.prepareStatement("DELETE FROM turma_professor WHERE id_turma = ?");
            pstmt.setInt(1, id_turma);
            pstmt.executeUpdate();
    
            pstmt = connection.prepareStatement("DELETE FROM turma WHERE id_turma = ?");
            pstmt.setInt(1, id_turma);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0){
                System.out.println("Turma excluída com sucesso!");
            }
            else {
                System.out.println("Turma não encontrada!");
            }
    
        } catch (SQLException e) {
            System.out.println("Erro ao excluir turma: " + e.getMessage());
        }
    }

    public static void adicionarProfessor(int id_turma, int id_prof){

        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
    
        try {

            pstmt = connection.prepareStatement("SELECT horario1, horario2 FROM turma WHERE id_turma = ?");
            pstmt.setInt(1, id_turma);
            rs = pstmt.executeQuery();

            while (rs.next()) {

                pstmt2 = connection.prepareStatement("SELECT horario1, horario2 FROM turma NATURAL JOIN turma_professor WHERE id_prof = ?");
                pstmt2.setInt(1, id_prof);
                rs2 = pstmt2.executeQuery();

                while (rs2.next()) {
                    String horario1_turma = rs.getString(1);
                    String horario2_turma = rs.getString(2);
                    String horario1_prof = rs2.getString(1);
                    String horario2_prof = rs2.getString(2);

                    if (horario1_turma.equals(horario1_prof) || (horario2_turma != null && horario2_turma.equals(horario1_prof)) || horario1_turma.equals(horario2_prof) || (horario2_turma != null && horario2_turma.equals(horario2_prof))) {
                    System.out.println("Horário não está disponível!");
                    return;
                    }
                }

                

            }

            pstmt = connection.prepareStatement("INSERT INTO turma_professor (id_turma, id_prof) VALUES (?, ?)");
            pstmt.setInt(1, id_turma);
            pstmt.setInt(2, id_prof);
            pstmt.executeUpdate();

            System.out.println("Professor adicionado!");
            
        } catch (java.sql.SQLException e) {
            System.out.println("Erro ao adicionar o professor: " + e.getMessage());
        }

    }

    public static Turma buscarTurma(int codigo, int id_componente){
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
    
        try{
            pstmt = connection.prepareStatement("SELECT * FROM turma NATURAL JOIN turma_professor WHERE turma.codigo = ? AND turma.id_comp = ?");
            pstmt.setInt(1, codigo);
            pstmt.setInt(2, id_componente);
            rs = pstmt.executeQuery();

            int id_turma = 0;
            int id_comp = 0;
            String horario1 = "";
            String horario2 = "";
            int vagas = 0;
            int numero_turma = 0;
            ArrayList<Integer> professores = new ArrayList<>();
    
            while (rs.next()) {
                id_turma = rs.getInt(1);
                id_comp = rs.getInt(2);
                horario1 = rs.getString(3);
                horario2 = rs.getString(4);
                vagas = rs.getInt(5);
                numero_turma = rs.getInt(6);
                professores.add(rs.getInt(7));
                
            }

            return new Turma(id_turma, professores, id_comp, horario1, horario2, vagas, numero_turma);
    
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
    
        return null;
    }

    public static Turma buscarTurma(int idTurma){
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
    
        try{
            pstmt = connection.prepareStatement("SELECT * FROM turma NATURAL JOIN turma_professor WHERE turma.id_turma = ?");
            pstmt.setInt(1, idTurma);
            rs = pstmt.executeQuery();

            int id_turma = 0;
            int id_comp = 0;
            String horario1 = "";
            String horario2 = "";
            int vagas = 0;
            int numero_turma = 0;
            ArrayList<Integer> professores = new ArrayList<>();
    
            while (rs.next()) {
                id_turma = rs.getInt(1);
                id_comp = rs.getInt(2);
                horario1 = rs.getString(3);
                horario2 = rs.getString(4);
                vagas = rs.getInt(5);
                numero_turma = rs.getInt(6);
                professores.add(rs.getInt(7));
                
            }

            return new Turma(id_turma, professores, id_comp, horario1, horario2, vagas, numero_turma);
    
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
    
        return null;
    }

    public static ArrayList<Turma> listarTurmas(){

        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        ArrayList<Turma> turmas = new ArrayList<>();
        ResultSet rs = null;
        ResultSet rs2 = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
    
        try{
            pstmt = connection.prepareStatement("SELECT * FROM turma ORDER BY id_turma");
            rs = pstmt.executeQuery();

            int id_turma = 0;
            int id_comp = 0;
            String horario1 = "";
            String horario2 = "";
            int vagas = 0;
            int numero_turma = 0;
            ArrayList<Integer> professores = new ArrayList<>();
    
            while (rs.next()) {

                id_turma = rs.getInt(1);
                id_comp = rs.getInt(2);
                horario1 = rs.getString(3);
                horario2 = rs.getString(4);
                vagas = rs.getInt(5);
                numero_turma = rs.getInt(6);

                professores = new ArrayList<>();

                pstmt2 = connection.prepareStatement("SELECT id_prof FROM turma_professor WHERE id_turma = ? ORDER BY id_prof");
                pstmt2.setInt(1, rs.getInt(1));
                rs2 = pstmt2.executeQuery();

                while (rs2.next()) {
                    professores.add(rs2.getInt(1));
                }

                Turma turma = new Turma(id_turma, professores, id_comp, horario1, horario2, vagas, numero_turma);
                turmas.add(turma);

            }
    
            return turmas;
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
        return turmas;

    }

    public static ArrayList<Turma> listarTurmasSemestre(int semestre){

        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        ArrayList<Turma> turmas = new ArrayList<>();
        ResultSet rs = null;
        ResultSet rs2 = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
    
        try{
            pstmt = connection.prepareStatement("SELECT t.id_turma, t.id_comp, t.horario1, t.horario2, t.vagas, t.codigo FROM turma as t INNER JOIN componente_curricular as cc on t.id_comp = cc.id_comp WHERE semestre = ? ORDER BY id_turma");
            pstmt.setInt(1, semestre);
            rs = pstmt.executeQuery();

            int id_turma = 0;
            int id_comp = 0;
            String horario1 = "";
            String horario2 = "";
            int vagas = 0;
            int numero_turma = 0;
            ArrayList<Integer> professores = new ArrayList<>();
    
            while (rs.next()) {

                id_turma = rs.getInt(1);
                id_comp = rs.getInt(2);
                horario1 = rs.getString(3);
                horario2 = rs.getString(4);
                vagas = rs.getInt(5);
                numero_turma = rs.getInt(6);

                professores = new ArrayList<>();

                pstmt2 = connection.prepareStatement("SELECT id_prof FROM turma_professor WHERE id_turma = ? ORDER BY id_prof");
                pstmt2.setInt(1, rs.getInt(1));
                rs2 = pstmt2.executeQuery();

                while (rs2.next()) {
                    professores.add(rs2.getInt(1));
                }

                Turma turma = new Turma(id_turma, professores, id_comp, horario1, horario2, vagas, numero_turma);
                turmas.add(turma);

            }
    
            return turmas;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
        return turmas;

    }

    public static ArrayList<Turma> listarTurmasProfessor(int id_prof){

        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        ArrayList<Turma> turmas = new ArrayList<>();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
    
        try{
            pstmt = connection.prepareStatement("SELECT * FROM turma NATURAL JOIN turma_professor WHERE id_prof = ?");
            pstmt.setInt(1, id_prof);
            rs = pstmt.executeQuery();

            int id_turma = 0;
            int id_comp = 0;
            String horario1 = "";
            String horario2 = "";
            int vagas = 0;
            int numero_turma = 0;
            ArrayList<Integer> professores = new ArrayList<>();
    
            while (rs.next()) {

                professores = new ArrayList<>();

                id_turma = rs.getInt(1);
                id_comp = rs.getInt(2);
                horario1 = rs.getString(3);
                horario2 = rs.getString(4);
                vagas = rs.getInt(5);
                numero_turma = rs.getInt(6);
                professores.add(rs.getInt(7));;

                Turma turma = new Turma(id_turma, professores, id_comp, horario1, horario2, vagas, numero_turma);
                turmas.add(turma);

            }
    
            return turmas;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
        return turmas;
    }

    public static void editaTurma(int id_turma, Object dado, int id_dado, int id_prof){

        Connection connection = PostgreSQLConnection.getInstance().getConnection();
    
        try {

            if (id_dado == 1) {
                PreparedStatement pstmt = connection.prepareStatement("UPDATE turma SET id_comp = ? WHERE id_turma = ?");
                int id_comp = (int) dado;
                pstmt.setInt(1, id_comp);
                pstmt.setInt(2, id_turma);
                pstmt.executeUpdate();

                System.out.println("Componente editado com sucesso!");

            } else if (id_dado == 2) {
                PreparedStatement pstmt = connection.prepareStatement("UPDATE turma SET horario1 = ? WHERE id_turma = ?");
                String horario1 = dado.toString();
                pstmt.setString(1, horario1);
                pstmt.setInt(2, id_turma);
                pstmt.executeUpdate();

                System.out.println("Horário editado com sucesso!");

            } else if (id_dado == 3) {
                PreparedStatement pstmt = connection.prepareStatement("UPDATE turma SET horario2 = ? WHERE id_turma = ?");
                String horario2 = dado.toString();
                pstmt.setString(1, horario2);
                pstmt.setInt(2, id_turma);
                pstmt.executeUpdate();

                System.out.println("Horário editado com sucesso!");
                
            } else if (id_dado == 4) {
                PreparedStatement pstmt = connection.prepareStatement("UPDATE turma SET vagas = ? WHERE id_turma = ?");
                int vagas = (int) dado;
                pstmt.setInt(1, vagas);
                pstmt.setInt(2, id_turma);
                pstmt.executeUpdate();

                System.out.println("Vagas editadas com sucesso!");

            } else if (id_dado == 5) {
                PreparedStatement pstmt = connection.prepareStatement("UPDATE turma SET codigo = ? WHERE id_turma = ?");
                int codigo = (int) dado;
                pstmt.setInt(1, codigo);
                pstmt.setInt(2, id_turma);
                pstmt.executeUpdate();

                System.out.println("Código editado com sucesso!");

            } else if (id_dado == 6) {
                
                PreparedStatement pstmt = connection.prepareStatement("UPDATE turma_professor SET id_prof = ? WHERE id_turma = ? and id_prof = ?");
                int id_prof_novo = (int) dado;
                pstmt.setInt(1, id_prof_novo);
                pstmt.setInt(2, id_turma);
                pstmt.setInt(3, id_prof);
                pstmt.executeUpdate();

                System.out.println("Professor editado com sucesso!");
            } else{
                System.out.println("Dado inválido!");
            }


        } catch (java.sql.SQLException e) {
            System.out.println("Erro ao cadastrar professor: " + e.getMessage());
        }

    }

    @Override
    public String toString() {
        return "Turma [idTurma=" + idTurma + ", idProfessor=" + idProfessor + ", idComponenteCurricular="
                + idComponenteCurricular + ", horario1=" + horario1 + ", horario2=" + horario2 + ", vagas=" + vagas
                + ", codigo=" + codigo + "]";
    }

}
