import java.io.IOException;
import java.util.Scanner;

public class DemoHorario {

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
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
                        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor(); // Apaga o texto do terminal
                        menuComponenteCurricular();
                        System.out.print("  >> ");
                        op = sc.nextInt();
                        switch (op) {
                            case 1:
                            
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
                    } while (op != 6);
                    break;

                // Menu da Turma
                case 3:
                    do {
                        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor(); // Apaga o texto do terminal
                        menuTurma();
                        System.out.print("  >> ");
                        op = sc.nextInt();
                        switch (op) {
                            case 1:
                                
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