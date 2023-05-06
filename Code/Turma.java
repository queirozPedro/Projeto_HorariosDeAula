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

    //Construtor utilizado para adicionar um novo professor no banco de dados
    public Turma(int idProfessor, int idComponenteCurricular, String horario1, String horario2,
            int vagas) {
        this.idProfessor.add(idProfessor);
        this.idComponenteCurricular = idComponenteCurricular;
        this.horario1 = horario1;
        this.horario2 = horario2;
        this.vagas = vagas;
    }

    //Construtor para criar um objeto de um professor já existente no banco de dados
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

    public String getHorario1() {
        return horario1;
    }

    public String getHorario2() {
        return horario2;
    }

    public void Cadastrar(){

        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
    
        try {

            //Vai retornar a quantidade de turmas existentes de um determinado componente curricular
            pstmt = connection.prepareStatement("SELECT count(*) FROM turma WHERE id_comp = ?");
            pstmt.setInt(1, this.idComponenteCurricular);
            rs = pstmt.executeQuery();

            //Caso exista uma x quantidade de turmas ele vai incrementar o número da turma, por exemplo, se existe 3 turmas de um componente curricular, a turma que está sendo cadastrada terá o seu código = 4, que será a turma 4 desse componente.
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

            //Retorna a quantidade de horas-aula por semana que o professor que está sendo cadastrado tem.
            pstmt = connection.prepareStatement("SELECT sum(carga_horaria)/15 FROM componente_curricular as cc INNER JOIN (SELECT id_comp FROM turma NATURAL JOIN turma_professor WHERE id_prof = ?) as d ON cc.id_comp = d.id_comp");
            pstmt.setInt(1, this.idProfessor.get(0));
            rs = pstmt.executeQuery();
            
            //Verifica se há quantidade de horas-aula do professor disponível
            while (rs.next()) {
                if ((rs.getInt(1) + (ComponenteCurricular.buscarComponente(idComponenteCurricular).getCargaHoraria() / 15)) > 20) {
                    System.out.println("Professor atingiu o limite de horas por semana!");
                    return;
                }

            }

            //Insere a turma no banco de dados
            pstmt = connection.prepareStatement("INSERT INTO turma (id_comp, horario1, horario2, vagas, codigo) VALUES (?, ?, ?, ?, ?)");
            pstmt.setInt(1, this.idComponenteCurricular);
            pstmt.setString(2, this.horario1);
            pstmt.setString(3, this.horario2);
            pstmt.setInt(4, this.vagas);
            pstmt.setInt(5, this.codigo);
            pstmt.executeUpdate();

            //Retorna o id da turma que acabou de ser cadastrada
            pstmt = connection.prepareStatement("SELECT id_turma from turma WHERE codigo = ? and id_comp = ?");
            pstmt.setInt(1, this.codigo);
            pstmt.setInt(2, this.idComponenteCurricular);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                //É adicionado o professor dessa turma ao banco de dados
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

            //Retorna o código e o id do componente da turma que está sendo excluída do banco de dados
            pstmt = connection.prepareStatement("SELECT codigo, id_comp FROM turma WHERE id_turma = ?");
            pstmt.setInt(1, id_turma);
            rs = pstmt.executeQuery();

            //Vai atualizar o número do código de todas as turmas que tem o código maior do que o da turma que está sendo excluída, por exemplo, existem 4 turmas e está sendo excluído a turma 2, logo as turmas 3 e 4 vão virar, respectivamente, as turmas 2 e 3
            while (rs.next()) {
                pstmt = connection.prepareStatement("UPDATE turma SET codigo = codigo - 1 WHERE codigo > ? AND id_comp = ?");
                pstmt.setInt(1, rs.getInt(1));
                pstmt.setInt(2, rs.getInt(2));
                pstmt.executeUpdate();
            }

            //Deleta os professores dessa turma
            pstmt = connection.prepareStatement("DELETE FROM turma_professor WHERE id_turma = ?");
            pstmt.setInt(1, id_turma);
            pstmt.executeUpdate();
    
            //Deleta a turma
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

            //Retorna todos os horários da turma que o professor está sendo adicionado
            pstmt = connection.prepareStatement("SELECT horario1, horario2 FROM turma WHERE id_turma = ?");
            pstmt.setInt(1, id_turma);
            rs = pstmt.executeQuery();

            while (rs.next()) {

                //Retorna todos os horários do professor que está sendo adicionado que não estáo disponíveis
                pstmt2 = connection.prepareStatement("SELECT horario1, horario2 FROM turma NATURAL JOIN turma_professor WHERE id_prof = ?");
                pstmt2.setInt(1, id_prof);
                rs2 = pstmt2.executeQuery();

                while (rs2.next()) {
                    String horario1_turma = rs.getString(1);
                    String horario2_turma = rs.getString(2);
                    String horario1_prof = rs2.getString(1);
                    String horario2_prof = rs2.getString(2);

                    //Verifica se algum horário da turma que o professor que está sendo adicionado tem algum conflito com os horários que o professor já tem aulas
                    if (horario1_turma.equals(horario1_prof) || (horario2_turma != null && horario2_turma.equals(horario1_prof)) || horario1_turma.equals(horario2_prof) || (horario2_turma != null && horario2_turma.equals(horario2_prof))) {
                    System.out.println("Horário não está disponível!");
                    return;
                    }
                }

            }

            //Retorna a quantidade de horas-aula por semana que o professor que está sendo cadastrado tem.
            pstmt = connection.prepareStatement("SELECT sum(carga_horaria)/15 FROM componente_curricular as cc INNER JOIN (SELECT id_comp FROM turma NATURAL JOIN turma_professor WHERE id_prof = ?) as d ON cc.id_comp = d.id_comp");
            pstmt.setInt(1, id_prof);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                //Retorna o id do componente da turma que o professor está sendo adicionado
                pstmt2 = connection.prepareStatement("SELECT id_comp FROM turma WHERE id_turma = ?");
                pstmt2.setInt(1, id_turma);
                rs2 = pstmt2.executeQuery();

                while (rs2.next()) {
                    //Verifica se há quantidade de horas-aula do professor disponível
                    if ((rs.getInt(1) + (ComponenteCurricular.buscarComponente(rs2.getInt(1)).getCargaHoraria() / 15)) > 20) {
                    System.out.println("Professor atingiu o limite de horas por semana!");
                    return;
                    }
                }
                

            }
            
            //Insere o professor na turma
            pstmt = connection.prepareStatement("INSERT INTO turma_professor (id_turma, id_prof) VALUES (?, ?)");
            pstmt.setInt(1, id_turma);
            pstmt.setInt(2, id_prof);
            pstmt.executeUpdate();

            System.out.println("Professor adicionado!");
            
        } catch (java.sql.SQLException e) {
            System.out.println("Erro ao adicionar o professor: " + e.getMessage());
        }

    }

    //Busca uma turma pelo seu código e pelo seu id
    public static Turma buscarTurma(int codigo, int id_componente){
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
    
        try{
            //Retorna a turma que corresponde ao código e ao id do componente que foi passado
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

            //Retorna a turma encontrada
            return new Turma(id_turma, professores, id_comp, horario1, horario2, vagas, numero_turma);
    
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
    
        return null;
    }

    //Busca uma turma pelo seu id
    public static Turma buscarTurma(int idTurma){
        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
    
        try{
            //Retorna a turma correspondente ao id passado
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

            //Retorna a turma encontrada
            return new Turma(id_turma, professores, id_comp, horario1, horario2, vagas, numero_turma);
    
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
    
        return null;
    }

    //Lista todas as turmas do banco de dados
    public static ArrayList<Turma> listarTurmas(){

        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        ArrayList<Turma> turmas = new ArrayList<>();
        ResultSet rs = null;
        ResultSet rs2 = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
    
        try{
            //Retorna todas as turmas ordenada pelo seu id
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

                //Retorna o id dos professores correspondente a turma que está no laço no momento
                pstmt2 = connection.prepareStatement("SELECT id_prof FROM turma_professor WHERE id_turma = ? ORDER BY id_prof");
                pstmt2.setInt(1, rs.getInt(1));
                rs2 = pstmt2.executeQuery();

                while (rs2.next()) {
                    professores.add(rs2.getInt(1));
                }

                //Cria um objeto de turma e adiciona a lista de turmas
                Turma turma = new Turma(id_turma, professores, id_comp, horario1, horario2, vagas, numero_turma);
                turmas.add(turma);

            }
    
            //Retorna todas as turmas
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
            //Retorna todas as turmas correspondente ao semestre que foi passado
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

                //Retorna o id dos professores correspondente a turma que está no laço no momento
                pstmt2 = connection.prepareStatement("SELECT id_prof FROM turma_professor WHERE id_turma = ? ORDER BY id_prof");
                pstmt2.setInt(1, rs.getInt(1));
                rs2 = pstmt2.executeQuery();

                while (rs2.next()) {
                    professores.add(rs2.getInt(1));
                }

                //Cria um objeto de turma e adiciona a lista de turmas
                Turma turma = new Turma(id_turma, professores, id_comp, horario1, horario2, vagas, numero_turma);
                turmas.add(turma);

            }
    
            //Retorna todas as turmas
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
            //Retorna todas as turmas que são ministradas pelo professor que foi passado
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

                //Cria um objeto de turma e adiciona a lista de turmas
                Turma turma = new Turma(id_turma, professores, id_comp, horario1, horario2, vagas, numero_turma);
                turmas.add(turma);

            }
    
            //Retorna todas as turmas
            return turmas;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
        return turmas;
    }

    public static void editaTurma(int id_turma, Object dado, int id_dado, int id_prof){

        Connection connection = PostgreSQLConnection.getInstance().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
    
        try {
            //Caso o id do dado passado seja 1 o componente da turma será atualizado
            if (id_dado == 1) {
                pstmt = connection.prepareStatement("UPDATE turma SET id_comp = ? WHERE id_turma = ?");
                int id_comp = (int) dado;
                pstmt.setInt(1, id_comp);
                pstmt.setInt(2, id_turma);
                pstmt.executeUpdate();

                System.out.println("Componente editado com sucesso!");

            //Caso o id do dado seja 2 o horario1 da turma será atualizado 
            } else if (id_dado == 2) {
                
                String horario1 = dado.toString();
                Boolean disponivel = true;

                //Retorna todos os horários que estão indisponíveis no semestre da turma específica
                pstmt = connection.prepareStatement("SELECT horario1, horario2 FROM turma as t INNER JOIN componente_curricular as cc ON t.id_comp = cc.id_comp WHERE semestre in(SELECT semestre FROM turma as t INNER JOIN componente_curricular as cc ON t.id_comp = cc.id_comp WHERE id_turma = ?)");
                pstmt.setInt(1, id_turma);
                rs = pstmt.executeQuery();

                while (rs.next()) {
                    //Verifica se o horário que será atualizado tem algum conflito com algum dos horários indisponíveis no semestre dessa turma
                    if (horario1.equals(rs.getString(1)) || horario1.equals(rs.getString(2))) {
                        System.out.println("Horário não está disponível!");
                        disponivel = false;
                        break;
                    }
                }
                
                if (disponivel) {
                    //Retorna todos os horários indisponíveis do(s) professor(es) dessa turma
                    pstmt = connection.prepareStatement("SELECT horario1, horario2 FROM turma NATURAL JOIN turma_professor WHERE id_prof in(SELECT id_prof FROM turma_professor WHERE id_turma = ?)");
                    pstmt.setInt(1, id_turma);
                    rs = pstmt.executeQuery();

                    //Verifica se o novo horário que está sendo atualizado tem conflito com algum horário de aula dos professores dessa turma
                    while (rs.next()) {
                        if (horario1.equals(rs.getString(1)) || horario1.equals(rs.getString(2))) {
                            System.out.println("Horário não está disponível!");
                            disponivel = false;
                            break;
                        }
                    }
                }

                //Sem tudo estiver ok o horário será atualizado
                if (disponivel) {
                    pstmt = connection.prepareStatement("UPDATE turma SET horario1 = ? WHERE id_turma = ?");
                    pstmt.setString(1, horario1);
                    pstmt.setInt(2, id_turma);
                    pstmt.executeUpdate();

                    System.out.println("Horário editado com sucesso!");
                }

            //Caso o id do dado seja 3 o horario2 da turma será atualizado, é basicamente o mesmo processo do horario1, só muda do horário1 pra horário2
            } else if (id_dado == 3) {

                String horario2 = dado.toString();
                Boolean disponivel = true;

                pstmt = connection.prepareStatement("SELECT horario1, horario2 FROM turma as t INNER JOIN componente_curricular as cc ON t.id_comp = cc.id_comp WHERE semestre in(SELECT semestre FROM turma as t INNER JOIN componente_curricular as cc ON t.id_comp = cc.id_comp WHERE id_turma = ?)");
                pstmt.setInt(1, id_turma);
                rs = pstmt.executeQuery();

                while (rs.next()) {
                    if (horario2.equals(rs.getString(1)) || horario2.equals(rs.getString(2))) {
                        System.out.println("Horário não está disponível!");
                        disponivel = false;
                        break;
                    }
                }
                
                if (disponivel) {
                    pstmt = connection.prepareStatement("SELECT horario1, horario2 FROM turma NATURAL JOIN turma_professor WHERE id_prof in(SELECT id_prof FROM turma_professor WHERE id_turma = ?)");
                    pstmt.setInt(1, id_turma);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        if (horario2.equals(rs.getString(1)) || horario2.equals(rs.getString(2))) {
                            System.out.println("Horário não está disponível!");
                            disponivel = false;
                            break;
                        }
                    }
                }

                if (disponivel) {
                    pstmt = connection.prepareStatement("UPDATE turma SET horario2 = ? WHERE id_turma = ?");
                    pstmt.setString(1, horario2);
                    pstmt.setInt(2, id_turma);
                    pstmt.executeUpdate();

                    System.out.println("Horário editado com sucesso!");
                }
                
            //Caso o id do dado seja 4 o numero de vagas da turma será atualizado
            } else if (id_dado == 4) {
                pstmt = connection.prepareStatement("UPDATE turma SET vagas = ? WHERE id_turma = ?");
                int vagas = (int) dado;
                pstmt.setInt(1, vagas);
                pstmt.setInt(2, id_turma);
                pstmt.executeUpdate();

                System.out.println("Vagas editadas com sucesso!");

            //Caso o id do dado seja 5 o professor da turma será atualizado
            } else if (id_dado == 5) {

                PreparedStatement pstmt2 = null;
                ResultSet rs2 = null;
                
                int id_prof_novo = (int) dado;

                //Retorna todos os horários de aula do novo professor
                pstmt = connection.prepareStatement("SELECT horario1, horario2 FROM turma NATURAL JOIN turma_professor WHERE id_prof = ?");
                pstmt.setInt(1, id_prof_novo);
                rs = pstmt.executeQuery();

                while (rs.next()) {

                    String horario1_prof = rs.getString(1);
                    String horario2_prof = rs.getString(2);

                    //Retorna os horários de aula da turma
                    pstmt2 = connection.prepareStatement("SELECT horario1, horario2 FROM turma WHERE id_turma = ?");
                    pstmt2.setInt(1, id_turma);
                    rs2 = pstmt2.executeQuery();

                    while (rs2.next()) {
                        String horario1_turma = rs2.getString(1);
                        String horario2_turma = rs2.getString(2);

                        //Verifica se há algum conflito entre os horários do novo professor e os horários da turma
                        if (horario1_prof.equals(horario1_turma) || (horario2_prof != null && horario2_prof.equals(horario1_turma)) || horario1_prof.equals(horario2_turma) || (horario2_prof != null && horario2_prof.equals(horario2_turma))) {
                            System.out.println("Horário não está disponível!");
                            return;
                        }
                    }

                }

                //Caso não haja nenhum conflito o professor é atualizado                
                pstmt = connection.prepareStatement("UPDATE turma_professor SET id_prof = ? WHERE id_turma = ? and id_prof = ?");
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
