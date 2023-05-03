import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DemoHorario {

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        ArrayList<Professor> professores = new ArrayList<>();
        ArrayList<ComponenteCurricular> disciplinas = new ArrayList<>();
        Professor aux = new Professor(0, null, null, null, null);
        int op = 0;
        do {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor(); // Apaga o texto do terminal
            menuPrincipal();
            System.out.print("  >> ");
            op = sc.nextInt();
            
            // Esse trambolho so vai ficar aqui por um tempo, logo logo a gente tira ele, confia...
            switch (op) {

                // Menu do Professor
                case 1:
                    do {
                        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor(); // Apaga o texto do terminal
                        menuProfessor();
                        System.out.print("  >> ");
                        op = sc.nextInt();
                        switch (op) {
                            case 1:
                                System.out.println("Insira as Informações do Professor:");
                                System.out.print("Nome: ");
                                aux.setNome(sc.nextLine());
                                sc.nextLine();
                                System.out.print("Cpf: ");
                                aux.setCpf(sc.nextLine());
                                sc.nextLine();
                                System.out.print("Formação: ");
                                aux.setFormacao(sc.nextLine());
                                sc.nextLine();
                                System.out.print("Email: ");
                                aux.setEmail(sc.nextLine());
                                sc.nextLine();
                                aux.Cadastrar();
                                break;
                            case 2:
                                
                                break;
                            case 3:

                                break;
                            case 4:

                                break;
                            case 5:

                                break;
                            case 6: 
                                break;
                            default:
                                break;
                        }
                    } while(op != 6);
                    break;

                // Menu do Componente Curricular
                case 2:
                    do {
                        menuComponenteCurricular();
                        System.out.print("  >> ");
                        op = sc.nextInt();
                        switch (op) {
                            case 1:
                            
                                break;
                            case 2:

                                disciplinas = ComponenteCurricular.listarComponentes();

                                System.out.println("Qual componente curricular será editado: \n" + disciplinas);

                                System.out.println(">> ");
                                int id = sc.nextInt();

                                System.out.println("\nQual dado desse componente será editado: ");
                                System.out.println(ComponenteCurricular.buscarComponente(id));
                                System.out.println(">> ");
                                int id_dado = sc.nextInt();

                                sc.nextLine();

                                System.out.println("\nDigite o novo XXXX desse componente: ");
                                Boolean dado = sc.nextBoolean();

                                ComponenteCurricular.editaComponente(id, dado, id_dado);
                
                                break;
                            case 3:

                                break;
                            case 4:

                                break;
                            case 5:

                                break;
                            case 6: 
                               
                                break;
                            default:
                                break;
                        }
                    } while (op != 6);
                    break;

                // Menu da Turma
                case 3:
                    do {
                        menuTurma();
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

                                    System.out.println(id+ " " + professores.get(professor11 - 1).getId_prof() + " " + id_dado + " " + id_prof);
                                    
                                    Turma.editaTurma(id, professores.get(professor11 - 1).getId_prof(), id_dado, id_prof); 
                                }

                                
                    
                                break;
                            case 3:

                                break;
                            case 4:

                                System.out.println(Turma.listarTurmas());

                                break;
                            case 5:

                                break;
                            case 6: 

                                break;
                            case 7:

                                break;
                            case 8:
                                break;
                            default:
                                break;
                        }
                    } while(op != 8);
                    break;

                // Finalizar o programa
                case 4:
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor(); // Apaga o texto do terminal
                    op = -1;
                    break;
                default:
                    break;
            }

        } while (op >= 0);

        PostgreSQLConnection.getInstance().closeConnection();
        System.out.println("Finalizando programa...");
        sc.close();
    }

    public static void menuPrincipal(){
        System.out.println(" == Menu ==");
        System.out.println(" 1 -> Menu do Professor");
        System.out.println(" 2 -> Menu do Componente Curricular");
        System.out.println(" 3 -> Menu da Turma");
        System.out.println(" 4 -> Finalizar Programa");
    }

    public static void menuProfessor(){
        System.out.println(" == Opções do Professor ==");
        System.out.println(" 1 -> Cadastrar Professor");
        System.out.println(" 2 -> Editar Professor");
        System.out.println(" 3 -> Ver dados de um Professor");
        System.out.println(" 4 -> Listar Professores");
        System.out.println(" 5 -> Excluir Professor");
        System.out.println(" 6 -> Voltar");
    }

    public static void menuComponenteCurricular(){
        System.out.println(" == Opções do Componente Curricular ==");
        System.out.println("1 -> Cadastrar Componente Curricular");
        System.out.println("2 -> Editar Componente Curricular");
        System.out.println("3 -> Ver dados de um Componente Curricular");
        System.out.println("4 -> Listar Componentes Curriculares");
        System.out.println("5 -> Excluir Componente Curricular");
        System.out.println("6 -> Voltar");
    }

    public static void menuTurma(){
        System.out.println(" = Opções da Turma ==");
        System.out.println("1 -> Cadastrar Turma");
        System.out.println("2 -> Editar Turma");
        System.out.println("3 -> Ver dados de uma Turma");
        System.out.println("4 -> Listar todas as Turmas (em formato de tabela)");
        System.out.println("5 -> Listar Turmas por semestre (em formato de tabela)");
        System.out.println("6 -> Listar Turmas por Professor (em formato de tabela)");
        System.out.println("7 -> Excluir Turma");
        System.out.println("8 -> Voltar");
    }
}


// Rascunhos
//String menu[] = {"Menu do Professor","Componente Curricular", "Menu da Turma"};
//String menuDoProfessor[] = {"Cadastrar Professor", "Editar Professor", "Ver dados de um Professor", "Listar Professores", "Excluir Professor"};
//String menuDoComponenteCurricular[] = {"Cadastrar Componente Curricular", "Editar Componente Curricular", "Ver dados de um Componente Curricular", "Listar Componentes Curriculares", "Excluir Componente Curricular"};
//String menuDaTurma[] = {"Cadastrar Turma", "Editar Turma", "Ver dados de uma Turma", "Listar todas as Turmas (em formato de tabela)", "Listar Turmas por semestre (em formato de tabela)", "Listar Turmas por Professor (em formato de tabela)", "Excluir Turma"};