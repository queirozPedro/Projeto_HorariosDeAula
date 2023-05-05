import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MainHorario {

    public static void main(String[] args) throws IOException, InterruptedException {

        Scanner sc = new Scanner(System.in);
        ArrayList<Professor> professores = new ArrayList<>();
        ArrayList<ComponenteCurricular> disciplinas = new ArrayList<>();
        Professor aux = new Professor(null, null, null, null);

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
                                    System.out.println("  >> ");
                                    op = sc.nextInt();
                                    switch (op) {
                                        case 1:
                                            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                            System.out.println("Cadastrando Novo Professor");
                                            sc.nextLine();
                                            System.out.print("Nome: ");
                                            aux.setNome(sc.nextLine());

                                            System.out.print("Cpf: ");
                                            aux.setCpf(sc.nextLine());

                                            System.out.print("Formação: ");
                                            aux.setFormacao(sc.nextLine());

                                            System.out.print("Email: ");
                                            aux.setEmail(sc.nextLine());

                                            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                            System.out.println("\n" + aux.toString());
                                            System.out.println("\nCadastrar professor? ");
                                            System.out.println(" 1 -> Sim");
                                            System.out.println(" 2 -> Não");
                                            System.out.print("  >> ");
                                            op = sc.nextInt();
                                            if (op == 1) {
                                                aux.Cadastrar();
                                            } else {
                                                aux.limpaProfessor();
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
                                 
                                // Não sei como a funcão edita professor funciona vou fazer o resto e depois olho pra ela
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
                                            auxString = sc.nextLine();
                                            aux = Professor.buscarProfessor(auxString);
                                            System.out.println(aux.toString());
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
                                // Falta terminar
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
                                            aux = Professor.buscarProfessor(auxString);
                                            System.out.println("\n"+ aux.toString());
                                            System.out.println("\nRemover professor? ");
                                            System.out.println(" 1 -> Sim");
                                            System.out.println(" 2 -> Não");
                                            System.out.print("  >> ");
                                            if(op == 1){
                                                Professor.ExcluirProfessor(auxString);
                                            }
                                            else{
                                                aux.limpaProfessor();
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
                                break;
                        }
                    } while (op != 6);
                    op = 0;
                    break;

                // Menu do Componente Curricular
                case 2:
                    do {
                        Menu.ComponenteCurricular();
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
                    } while (op != 8);
                    op = 0;
                    break;

                // Finalizar o programa
                case 4:
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
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
}
