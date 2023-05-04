import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

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

    public void Cadastrar(){

        Connection connection = PostgreSQLConnection.getInstance().getConnection();
    
        try {

            PreparedStatement pstmt = connection.prepareStatement("SELECT count(*) FROM turma WHERE id_comp = ?");
            pstmt.setInt(1, this.idComponenteCurricular);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                this.codigo += rs.getInt(1);
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
        PreparedStatement pstmt = null;

        try {
            pstmt = connection.prepareStatement("DELETE FROM turma WHERE id_turma = ?");
            pstmt.setInt(1, id_turma);
            int rowsDeleted = pstmt.executeUpdate();

            pstmt = connection.prepareStatement("DELETE FROM turma_professor WHERE id_turma = ?");
            pstmt.setInt(1, id_turma);
            pstmt.executeUpdate();
    
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
    
        try {
            
            PreparedStatement pstmt = connection.prepareStatement("UPDATE turma SET id_prof = array_append(id_prof, ?) WHERE id_turma = ?");
            pstmt.setInt(1, id_prof);
            pstmt.setInt(2, id_turma);
            pstmt.executeUpdate();

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
    
            if (rs.next()) {
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
    
            if (rs.next()) {
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
        PreparedStatement pstmt = null;
    
        try{
            pstmt = connection.prepareStatement("SELECT * from turma");
            rs = pstmt.executeQuery();

            int id = 0;
    
            while (rs.next()) {
                id_turma = rs.getInt(1);
                id_comp = rs.getInt(2);
                horario1 = rs.getString(3);
                horario2 = rs.getString(4);
                vagas = rs.getInt(5);
                numero_turma = rs.getInt(6);
                professores.add(rs.getInt(7));

                Turma turma = new Turma(rs.getInt(1), professores, rs.getInt(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6));
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
        PreparedStatement pstmt = null;
    
        try{
            pstmt = connection.prepareStatement("SELECT t.id_turma, t.id_comp, t.horario1, t.horario2, t.vagas, t.codigo, t.id_prof FROM turma as t, componente_curricular as cc WHERE cc.semestre = ?");
            pstmt.setInt(1, semestre);
            rs = pstmt.executeQuery();
    
            while (rs.next()) {
                Array array = rs.getArray("id_prof");
                Integer[] intArray = (Integer[]) array.getArray();
                ArrayList<Integer> professores = new ArrayList<>(Arrays.asList(intArray));

                Turma turma = new Turma(rs.getInt(1), professores, rs.getInt(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6));
                turmas.add(turma);
            }
    
            return turmas;
        } catch(SQLException e){
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
            pstmt = connection.prepareStatement("SELECT * FROM turma WHERE ? = ANY(id_prof)");
            pstmt.setInt(1, id_prof);
            rs = pstmt.executeQuery();
    
            while (rs.next()) {
                Array array = rs.getArray("id_prof");
                Integer[] intArray = (Integer[]) array.getArray();
                ArrayList<Integer> professores = new ArrayList<>(Arrays.asList(intArray));

                Turma turma = new Turma(rs.getInt(1), professores, rs.getInt(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6));
                turmas.add(turma);
            }
    
            return turmas;
        } catch(SQLException e){
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
                
                PreparedStatement pstmt = connection.prepareStatement("UPDATE turma SET id_prof[?] = ? WHERE id_turma = ?");
                int id_prof_novo = (int) dado;
                pstmt.setInt(1, id_prof);
                pstmt.setInt(2, id_prof_novo);
                pstmt.setInt(3, id_turma);
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
