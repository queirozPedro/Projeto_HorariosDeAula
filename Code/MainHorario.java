import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MainHorario {

    public static void main(String[] args) throws IOException, InterruptedException {

        Scanner sc = new Scanner(System.in);
        ArrayList<Professor> professores = new ArrayList<>();
        ArrayList<ComponenteCurricular> disciplinas = new ArrayList<>();
        String [][] horarios = new String [16][7];
        
        Professor auxProf = new Professor();
        ComponenteCurricular auxComp = new ComponenteCurricular();

        int op = 0;
        String auxString = null;

        do {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            Menu.Principal();
            System.out.print("  >> ");
            op = sc.nextInt();

            switch (op) {

                // Menu do Professor
                case 1:
                    do {
                        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                        Menu.Professor();
                        System.out.print("  >> ");
                        op = sc.nextInt();
                        switch (op) {
                            case 1: // Cadastrar Professor
                                do {
                                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                    System.out.println(" => Cadastro de Professores");
                                    System.out.println(" 1 -> Cadastrar Novo Professor");
                                    System.out.println(" 2 -> Voltar");
                                    System.out.print("  >> ");
                                    op = sc.nextInt();
                                    switch (op) {
                                        case 1:
                                            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                            System.out.println("Cadastrando Novo Professor");
                                            sc.nextLine();
                                            System.out.print("Nome: ");
                                            auxProf.setNome(sc.nextLine());

                                            System.out.print("Cpf: ");
                                            auxProf.setCpf(sc.nextLine());

                                            System.out.print("Formação: ");
                                            auxProf.setFormacao(sc.nextLine());

                                            System.out.print("Email: ");
                                            auxProf.setEmail(sc.nextLine());

                                            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                            System.out.println("\n" + auxProf.toString());
                                            System.out.println("\nCadastrar professor? ");
                                            System.out.println(" 1 -> Sim");
                                            System.out.println(" 2 -> Não");
                                            System.out.print("  >> ");
                                            op = sc.nextInt();
                                            if (op == 1) {
                                                auxProf.Cadastrar();
                                            } else {
                                                auxProf.limpaProfessor();
                                            }
                                            break;
                                        case 2:
                                            break;
                                        default:
                                            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                            System.out.println("Opção não disponível, pressione Enter para continuar!");
                                            sc.nextLine();
                                            sc.nextLine();
                                            break;
                                    }
                                } while (op != 2);
                                break;
                            case 2: // Editar Professor
                                do {
                                    professores = Professor.listarProfessores();
                                    System.out.println("Qual professor será editado: \n");
                                    for (int i = 0; i < professores.size(); i++) {
                                        System.out.println(i+1 + " - " + professores.get(i) + "\n");
                                    }
                                    System.out.print(">> ");
                                    int id = sc.nextInt();

                                    System.out.println("Qual dado será editado: ");
                                    System.out.println(" 1 -> Nome");
                                    System.out.println(" 2 -> CPF");
                                    System.out.println(" 3 -> Formação");
                                    System.out.println(" 4 -> Email");
                                    System.out.print(">> ");
                                    op = sc.nextInt();

                                    System.out.println("\nNovo dado: ");
                                    sc.nextLine();
                                    String dado = sc.nextLine();

                                    Professor.editaProfessor(professores.get(id - 1).getId_prof(), dado, op);
                                } while (op != 4);
                                break;
                            case 3: // Ver dados do Professor
                                do {
                                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                    System.out.println(" => Ver Dados de um Professor");
                                    System.out.println(" 1 -> Ver Professores");
                                    System.out.println(" 2 -> Voltar");
                                    System.out.print("  >> ");
                                    op = sc.nextInt();
                                    switch (op) {
                                        case 1:
                                            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                            sc.nextLine();
                                            System.out.println("Buscando Professor");
                                            System.out.print("Insira o Cpf do Professor: ");
                                            auxProf = Professor.buscarProfessor(sc.nextLine());
                                            System.out.println(auxProf.toString());
                                            System.out.println("\nPressione Enter para continuar!");
                                            sc.nextLine();
                                            break;
                                        case 2:
                                            break;
                                        default:
                                            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                            System.out.println("Opção não disponível, pressione Enter para continuar!");
                                            sc.nextLine();
                                            sc.nextLine();
                                            break;
                                    }
                                } while (op != 2);
                                break;
                            case 4: // Listar Professores 
                                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                System.out.println("Lista dos Professores Ativos");
                                professores = Professor.listarProfessores();
                                for(int i = 0; i < professores.size() ; i++){
                                System.out.println(professores.get(i).toString(i));
                                }
                                System.out.println("\nPressione Enter para continuar!");
                                sc.nextLine();
                                sc.nextLine();
                                break;
                            case 5: // Excluir Profesorres
                                do {
                                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                    System.out.println(" => Remover de Professor");
                                    System.out.println(" 1 -> Remover Professor");
                                    System.out.println(" 2 -> Voltar");
                                    System.out.println("  >> ");
                                    op = sc.nextInt();
                                    switch (op) {
                                        case 1:
                                            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                            System.out.println("Removendo Professor");
                                            System.out.print("Insira o Cpf:");
                                            sc.nextLine();
                                            auxString = sc.nextLine();
                                            auxProf = Professor.buscarProfessor(auxString);
                                            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                            System.out.println("\n"+ auxProf.toString());
                                            System.out.println("\nRemover professor? ");
                                            System.out.println(" 1 -> Sim");
                                            System.out.println(" 2 -> Não");
                                            System.out.print("  >> ");
                                            op = sc.nextInt();
                                            if(op == 1){
                                                Professor.ExcluirProfessor(auxString);
                                                System.out.println("Professor Removido com Sucesso!");
                                                System.out.println("\nPressione Enter para continuar!");
                                                sc.nextLine();
                                                sc.nextLine();
                                            }
                                            else{
                                                auxProf.limpaProfessor();
                                                System.out.println("Operação Cancelada, Pressione Enter para continuar!");
                                                sc.nextLine();
                                                sc.nextLine();
                                            }
                                            break;
                                        case 2:
                                            break;
                                        default:
                                            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                            System.out.println("Opção não disponível, pressione Enter para continuar!");
                                            sc.nextLine();
                                            sc.nextLine();
                                            break;
                                    }
                                } while (op != 2);
                                break;
                            case 6: // Sair
                                break;
                            default:
                                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                System.out.println("Opção não disponível, pressione Enter para continuar!");
                                sc.nextLine();
                                sc.nextLine();
                                break;
                        }
                    } while (op != 6);
                    op = 0;
                    break;

                // Menu do Componente Curricular
                case 2:
                    do {
                        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                        Menu.ComponenteCurricular();
                        System.out.print("  >> ");
                        op = sc.nextInt();
                        switch (op) {
                            case 1: // Cadastrar Componente Curricular 
                            do {
                                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                System.out.println(" => Cadastro de Componentes Curriculares");
                                System.out.println(" 1 -> Cadastrar Novo Componente Curricular");
                                System.out.println(" 2 -> Voltar");
                                System.out.print("  >> ");
                                op = sc.nextInt();
                                switch (op) {
                                    case 1:
                                        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                        System.out.println("Cadastrando Novo Componente Curricular");
                                        sc.nextLine();
                                        System.out.print("Nome: ");
                                        auxComp.setNome(sc.nextLine());

                                        System.out.print("Código: ");
                                        auxComp.setCodigo(sc.nextLine());

                                        System.out.print("Carga Horária: ");
                                        auxComp.setCargaHoraria(sc.nextInt());

                                        System.out.print("Semestre: ");
                                        auxComp.setSemestre(sc.nextInt());

                                        System.out.println("Optativa (1 -> Sim; 2 -> Não):");
                                        op = sc.nextInt();
                                        if(op == 1)
                                            auxComp.setOptativa(true);

                                        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                        System.out.println("\n" + auxComp.toString());
                                        System.out.println("\nCadastrar Compontente Curricular? ");
                                        System.out.println(" 1 -> Sim");
                                        System.out.println(" 2 -> Não");
                                        System.out.print("  >> ");
                                        op = sc.nextInt();
                                        if (op == 1){
                                            auxComp.Cadastrar();
                                            System.out.println("Pressione Enter para continuar!");
                                            sc.nextLine();
                                            sc.nextLine();
                                        }
                                        else{
                                            auxComp.limpaComponente();
                                        }
                                        
                                        break;
                                    case 2:
                                        break;
                                    default:
                                        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                        System.out.println("Opção não disponível, pressione Enter para continuar!");
                                        sc.nextLine();
                                        sc.nextLine();
                                        break;
                                }
                            } while (op != 2);
                                break;
                            case 2: // Editar Componente Curricular

                                disciplinas = ComponenteCurricular.listarComponentes();
                                System.out.println("Qual componente será editado: \n");
                                for (int i = 0; i < disciplinas.size(); i++) {
                                    System.out.println(i+1 + " - " + disciplinas.get(i) + "\n");
                                }
                                System.out.print(">> ");
                                int id = sc.nextInt();

                                System.out.println("Qual dado será editado: ");
                                System.out.println(" 1 -> Nome");
                                System.out.println(" 2 -> Carga horária");
                                System.out.println(" 3 -> Semestre");
                                System.out.println(" 4 -> Código");
                                System.out.println(" 5 -> Status Optativa");
                                System.out.println(">> ");
                                op = sc.nextInt();

                                switch (op) {
                                    case 1:
                                        System.out.print("Novo nome: ");
                                        sc.nextLine();
                                        String nome = sc.nextLine();

                                        ComponenteCurricular.editaComponente(disciplinas.get(id - 1).getId_componente(), nome, op);
                                        break;
                                    
                                    case 2:
                                        System.out.println("Nova carga horária: ");
                                        int carga_horaria = sc.nextInt();
                                        ComponenteCurricular.editaComponente(disciplinas.get(id - 1).getId_componente(), carga_horaria, op);
                                        break;

                                    case 3:
                                        System.out.print("Novo semestre: ");
                                        int semestre = sc.nextInt();
                                        ComponenteCurricular.editaComponente(disciplinas.get(id - 1).getId_componente(), semestre, op);
                                        break;

                                    case 4:
                                        System.out.print("Novo código: ");
                                        String codigo = sc.next();
                                        ComponenteCurricular.editaComponente(disciplinas.get(id - 1).getId_componente(), codigo, op);
                                        break;

                                    case 5:
                                        System.out.println("Novo status: ");
                                        System.out.println("1 - Optativa");
                                        System.out.println("2 - Obrigatório");
                                        System.out.print(">> ");
                                        int status = sc.nextInt();
                                        Boolean optativa = false;
                                        if (status == 1) {
                                            optativa = true;
                                        } else if(status == 2){
                                            optativa = false;
                                        }
                                        ComponenteCurricular.editaComponente(disciplinas.get(id - 1).getId_componente(), optativa, op);
                                        break;
                                
                                    default:
                                        break;
                                }

                                System.out.println("Pressione Enter para continuar!");
                                sc.nextLine();
                                sc.nextLine();

                                break;
                            case 3: // Ver Dados de um Componente Curricular
                                do{    
                                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                    System.out.println(" => Ver Dados de um Componente Curricular");
                                    System.out.println(" 1 -> Ver Componente Curricular");
                                    System.out.println(" 2 -> Voltar");
                                    System.out.print("  >> ");
                                    op = sc.nextInt();
                                
                                    switch (op) {
                                        case 1:
                                            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                            sc.nextLine();
                                            System.out.println("Buscando Componente Curricular");
                                            System.out.print("Insira o Código do Componente: ");
                                            auxComp = ComponenteCurricular.buscarComponente(sc.nextLine());
                                            System.out.println(auxComp.toString());
                                            System.out.println("\nPressione Enter para continuar!");
                                            sc.nextLine();
                                            break;
                                    case 2:
                                        break;
                                    default:
                                        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                        System.out.println("Opção não disponível, pressione Enter para continuar!");
                                        sc.nextLine();
                                        sc.nextLine();
                                        break;
                                    }
                                } while (op != 2);
                                break;
                            case 4: // Listar Componentes Curriculares
                                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                System.out.println("Lista dos Componentes Curriculares");
                                disciplinas = ComponenteCurricular.listarComponentes();
                                for(int i = 0; i < disciplinas.size() ; i++){
                                    System.out.println(disciplinas.get(i).toString(i));
                                }
                                System.out.println("\nPressione Enter para continuar!");
                                sc.nextLine();
                                sc.nextLine();
                                break;
                            case 5: // Excluir Componente Curricular
                            do {
                                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                System.out.println(" => Remover de Componente Curricular");
                                System.out.println(" 1 -> Remover Componente Curricular");
                                System.out.println(" 2 -> Voltar");
                                System.out.println("  >> ");
                                op = sc.nextInt();
                                switch (op) {
                                    case 1:
                                        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                        System.out.println("Removendo Componente Curricular");
                                        System.out.print("Insira o Código do Componente Curricular:");
                                        sc.nextLine();
                                        auxString = sc.nextLine();
                                        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                        auxComp = ComponenteCurricular.buscarComponente(auxString);
                                        System.out.println("\n"+ auxComp.toString());
                                        System.out.println("\nRemover Componente Curricular? ");
                                        System.out.println(" 1 -> Sim");
                                        System.out.println(" 2 -> Não");
                                        System.out.print("  >> ");
                                        op = sc.nextInt();
                                        if(op == 1){
                                            ComponenteCurricular.ExcluirComponente(auxString);
                                            System.out.println("Componente Curricular Removido com Sucesso!");
                                            System.out.println("\nPressione Enter para continuar!");
                                            sc.nextLine();
                                            sc.nextLine();
                                        }
                                        else{
                                            auxComp.limpaComponente();
                                            System.out.println("Operação Cancelada, Pressione Enter para continuar!");
                                            sc.nextLine();
                                            sc.nextLine();
                                        }
                                        break;
                                    case 2:
                                        break;
                                    default:
                                        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                        System.out.println("Opção não disponível, pressione Enter para continuar!");
                                        sc.nextLine();
                                        sc.nextLine();
                                        break;
                                }
                            } while (op != 2);
                                break;
                            case 6:
                                break;
                            default:
                                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                System.out.println("Opção não disponível, pressione Enter para continuar!");
                                sc.nextLine();
                                sc.nextLine();
                                break;
                        }
                    } while (op != 6);
                    op = 0;
                    break;

                // Menu da Turma
                case 3:
                    do {
                        Menu.Turma();
                        System.out.print("  >> ");
                        op = sc.nextInt();
                        switch (op) {
                            case 1:

                                professores = Professor.listarProfessores();
                                disciplinas = ComponenteCurricular.listarComponentes();
                                int idProfessor;
                                int idComponente;

                                System.out.println("Qual será o professor dessa turma: \n" + professores);
                                idProfessor = sc.nextInt();

                                System.out.println("\nQual será a disciplina dessa turma: \n" + disciplinas);
                                idComponente = sc.nextInt();

                                System.out.println("Qual o horário dessa turma: ");
                                String horario1 = sc.next();

                                System.out.println("Qual a quantidade de vagas para essa turma: ");
                                int vagas = sc.nextInt();

                                Turma turma = new Turma(idProfessor, idComponente, horario1, null, vagas);

                                turma.Cadastrar();

                                break;
                            case 2:

                                ArrayList<Turma> turmas = Turma.listarTurmas();
                                int id_prof;

                                System.out.println("Qual turma será editado: \n" + turmas);

                                System.out.println(">> ");
                                int id = sc.nextInt();

                                System.out.println("\nQual dado desse componente será editado: ");
                                System.out.println(ComponenteCurricular.buscarComponente(id));
                                System.out.println(">> ");
                                int id_dado = sc.nextInt();

                                sc.nextLine();

                                if (id_dado == 6) {
                                    Turma turma1 = Turma.buscarTurma(id);
                                    ArrayList<Integer> idProfessores = turma1.getIdProfessor();
                                    System.out.println("Qual professor deseja editar: \n");
                                    for (Integer professor : idProfessores) {
                                        System.out.println(professor);
                                        System.out.println(Professor.buscarProfessor(professor));
                                    }
                                    System.out.println(">> ");
                                    id_prof = sc.nextInt();

                                    professores = Professor.listarProfessores();
                                    System.out.println("\nQual o novo professor dessa turma: " + professores);
                                    int professor11 = sc.nextInt();

                                    System.out.println(id + " " + professores.get(professor11 - 1).getId_prof() + " "
                                            + id_dado + " " + id_prof);

                                    Turma.editaTurma(id, professores.get(professor11 - 1).getId_prof(), id_dado,
                                            id_prof);
                                }

                                break;
                            case 3:

                                Turma.adicionarProfessor(6, 5);

                                break;
                            case 4:

                                turmas = Turma.listarTurmasSemestre(1);
                                int posicaoH = 0;
                                System.out.println(turmas);

                                for (int i = 0; i < turmas.size(); i++) {
                                    for (int z = 0; z < 7; z++) {
                                        int semana = Character.getNumericValue(turmas.get(i).getHorario1().charAt(0));
                                        if (z == semana - 1) {
                                            switch (turmas.get(i).getHorario1().charAt(1)) {
                                                case 'M':
                                                    posicaoH = 0;
                                                    break;

                                                case 'T':
                                                    posicaoH = 6;
                                                    break;
                                                    
                                                case 'N':
                                                    posicaoH = 12;
                                                    break;
                                                
                                                default:
                                                    break;
                                            }

                                            int hora1 = Character.getNumericValue(turmas.get(i).getHorario1().charAt(2)) - 1;
                                            int hora2 = Character.getNumericValue(turmas.get(i).getHorario1().charAt(3)) - 1;

                                            horarios[hora1 + posicaoH][z] = turmas.get(i).getHorario1();
                                            horarios[hora2 + posicaoH][z] = turmas.get(i).getHorario1();
                                        }
                                    }
                                }

                                for (int i = 0; i < 16; i++) {
                                    for (int j = 0; j < 7; j++) {
                                        System.out.print(horarios[i][j] + " ");
                                    }
                                    System.out.println();
                                }

                                break;
                            case 5:

                                break;
                            case 6:

                                break;
                            case 7:

                                turmas = Turma.listarTurmas();

                                System.out.println("Qual turma deseja excluir: \n" + turmas);
                                
                                System.out.println(">> ");
                                int id_turma = sc.nextInt();

                                Turma.ExcluirTurma(turmas.get(id_turma - 1).getIdTurma());

                                break;
                            case 8:
                                break;
                            default:
                                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                System.out.println("Opção não disponível, pressione Enter para continuar!");
                                sc.nextLine();
                                sc.nextLine();
                                break;
                        }
                    } while (op != 8);
                    op = 0;
                    break;

                // Finalizar o programa
                case 4:
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                    op = -1;
                    break;
                default:
                        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                        System.out.println("Opção não disponível, pressione Enter para continuar!");
                        sc.nextLine();
                        sc.nextLine();
                    break;
            }

        } while (op >= 0);

        PostgreSQLConnection.getInstance().closeConnection();
        System.out.println("Finalizando programa...");
        sc.close();
    }
}
