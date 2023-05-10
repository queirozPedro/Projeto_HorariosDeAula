import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MainHorario {

    public static void main(String[] args) throws IOException, InterruptedException {

        Scanner sc = new Scanner(System.in);
        ArrayList<Professor> professores = new ArrayList<>();
        ArrayList<ComponenteCurricular> disciplinas = new ArrayList<>();
        ArrayList<Turma> turmas = new ArrayList<>();

        Professor auxProf = new Professor();
        ComponenteCurricular auxComp = new ComponenteCurricular();

        String horario1 = null;
        String horario2 = null;
        int vagas = 0;
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
                                        System.out.println(i + 1 + " - " + professores.get(i) + "\n");
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
                                for (int i = 0; i < professores.size(); i++) {
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
                                            System.out.println("\n" + auxProf.toString());
                                            System.out.println("\nRemover professor? ");
                                            System.out.println(" 1 -> Sim");
                                            System.out.println(" 2 -> Não");
                                            System.out.print("  >> ");
                                            op = sc.nextInt();
                                            if (op == 1) {
                                                Professor.ExcluirProfessor(auxString);
                                                System.out.println("\nPressione Enter para continuar!");
                                                sc.nextLine();
                                                sc.nextLine();
                                            } else {
                                                auxProf.limpaProfessor();
                                                System.out
                                                        .println("Operação Cancelada, Pressione Enter para continuar!");
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
                                            if (op == 1)
                                                auxComp.setOptativa(true);

                                            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                            System.out.println("\n" + auxComp.toString());
                                            System.out.println("\nCadastrar Compontente Curricular? ");
                                            System.out.println(" 1 -> Sim");
                                            System.out.println(" 2 -> Não");
                                            System.out.print("  >> ");
                                            op = sc.nextInt();
                                            if (op == 1) {
                                                auxComp.Cadastrar();
                                                System.out.println("Pressione Enter para continuar!");
                                                sc.nextLine();
                                                sc.nextLine();
                                            } else {
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
                                    System.out.println(i + 1 + " - " + disciplinas.get(i));
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

                                        ComponenteCurricular.editaComponente(disciplinas.get(id - 1).getId_componente(),
                                                nome, op);
                                        break;

                                    case 2:
                                        System.out.println("Nova carga horária: ");
                                        int carga_horaria = sc.nextInt();
                                        ComponenteCurricular.editaComponente(disciplinas.get(id - 1).getId_componente(),
                                                carga_horaria, op);
                                        break;

                                    case 3:
                                        System.out.print("Novo semestre: ");
                                        int semestre = sc.nextInt();
                                        ComponenteCurricular.editaComponente(disciplinas.get(id - 1).getId_componente(),
                                                semestre, op);
                                        break;

                                    case 4:
                                        System.out.print("Novo código: ");
                                        String codigo = sc.next();
                                        ComponenteCurricular.editaComponente(disciplinas.get(id - 1).getId_componente(),
                                                codigo, op);
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
                                        } else if (status == 2) {
                                            optativa = false;
                                        }
                                        ComponenteCurricular.editaComponente(disciplinas.get(id - 1).getId_componente(),
                                                optativa, op);
                                        break;

                                    default:
                                        break;
                                }

                                System.out.println("Pressione Enter para continuar!");
                                sc.nextLine();
                                sc.nextLine();

                                break;
                            case 3: // Ver Dados de um Componente Curricular
                                do {
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
                                for (int i = 0; i < disciplinas.size(); i++) {
                                    System.out.print(disciplinas.get(i).toString(i));
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
                                    System.out.print("  >> ");
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
                                            System.out.println("\n" + auxComp.toString());
                                            System.out.println("\nRemover Componente Curricular? ");
                                            System.out.println(" 1 -> Sim");
                                            System.out.println(" 2 -> Não");
                                            System.out.print("  >> ");
                                            op = sc.nextInt();
                                            if (op == 1) {
                                                ComponenteCurricular.ExcluirComponente(auxString);
                                                System.out.println("\nPressione Enter para continuar!");
                                                sc.nextLine();
                                                sc.nextLine();
                                            } else {
                                                auxComp.limpaComponente();
                                                System.out
                                                        .println("Operação Cancelada, Pressione Enter para continuar!");
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
                        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                        Menu.Turma();
                        System.out.print("  >> ");
                        op = sc.nextInt();
                        switch (op) {
                            case 1:
                                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                professores = Professor.listarProfessores();
                                disciplinas = ComponenteCurricular.listarComponentes();
                                int idProfessor;
                                int idComponente;

                                System.out.println("Qual será o professor dessa turma: ");
                                for (int i = 0; i < professores.size(); i++) {
                                    System.out.println(professores.get(i).toString(i));
                                }
                                System.out.print("\n>> ");
                                idProfessor = sc.nextInt();

                                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

                                System.out.println("\nQual será a disciplina dessa turma: \n");
                                for (int i = 0; i < disciplinas.size(); i++) {
                                    System.out.println(disciplinas.get(i).toString(i));
                                }
                                System.out.print("\n>> ");
                                idComponente = sc.nextInt();

                                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

                                if (disciplinas.get(idComponente).getCargaHoraria() == 30) {
                                    System.out.println("Qual o horário dessa turma: ");
                                    horario1 = sc.next();
                                } else if (disciplinas.get(idComponente).getCargaHoraria() == 60) {
                                    System.out.println("Quais são os horários dessa turma: ");
                                    System.out.print("\nHorário 1:");
                                    horario1 = sc.next();
                                    System.out.print("Horário 2:");
                                    horario2 = sc.next();
                                }

                                System.out.println("Qual a quantidade de vagas para essa turma: ");
                                vagas = sc.nextInt();

                                if (horario1 == horario2) {
                                    System.out.println("Horários inválidos!");
                                } else {
                                    Turma turma = new Turma(professores.get(idProfessor - 1).getId_prof(),
                                            disciplinas.get(idComponente - 1).getId_componente(), horario1, horario2,
                                            vagas);

                                    turma.Cadastrar();
                                }

                                System.out.println("\nPressione Enter para continuar!");
                                sc.nextLine();
                                sc.nextLine();

                                break;
                            case 2:

                                do {
                                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                    System.out.println(" => Editar Turma");
                                    System.out.println(" 1 -> Editar dados");
                                    System.out.println(" 2 -> Adicionar Professor");
                                    System.out.println(" 3 -> Excluir Professor");
                                    System.out.println(" 4 -> Voltar");
                                    System.out.print("  >> ");
                                    op = sc.nextInt();

                                    switch (op) {
                                        case 1:

                                            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

                                            turmas = Turma.listarTurmas();
                                            int id_prof;

                                            System.out.println("Qual turma será editada: \n");
                                            for (int i = 0; i < turmas.size(); i++) {
                                                System.out.println(turmas.get(i).toString(i));
                                            }

                                            System.out.print("\n>> ");
                                            int id = sc.nextInt();

                                            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

                                            System.out.println("Qual dado será editado: ");
                                            System.out.println("1 -> Horário 1");
                                            System.out.println("2 -> Horário 2");
                                            System.out.println("3 -> Número da vagas");
                                            System.out.println("4 -> Professor");

                                            System.out.print("\n>> ");
                                            int id_dado = sc.nextInt();

                                            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

                                            sc.nextLine();

                                            switch (id_dado) {
                                                case 1:
                                                    System.out.print("Novo Horário: ");
                                                    horario1 = sc.nextLine();

                                                    Turma.editaTurma(turmas.get(id - 1).getIdTurma(), horario1, id_dado,
                                                            0);

                                                    break;

                                                case 2:
                                                    System.out.print("Novo Horário: ");
                                                    horario2 = sc.nextLine();

                                                    Turma.editaTurma(turmas.get(id - 1).getIdTurma(), horario2, id_dado,
                                                            0);

                                                    break;

                                                case 3:
                                                    System.out.print("Novo número de vagas: ");
                                                    vagas = sc.nextInt();

                                                    Turma.editaTurma(turmas.get(id - 1).getIdTurma(), vagas, id_dado,
                                                            0);

                                                    break;

                                                case 4:
                                                    Turma turma1 = Turma.buscarTurma(turmas.get(id - 1).getIdTurma());
                                                    ArrayList<Integer> idProfessores = turma1.getIdProfessor();
                                                    System.out.println("Qual professor deseja editar: ");
                                                    for (int i = 0; i < idProfessores.size(); i++) {
                                                        System.out
                                                                .println(Professor.buscarProfessor(idProfessores.get(i))
                                                                        .toString(i) + "\n");
                                                    }
                                                    System.out.print(">> ");
                                                    id_prof = sc.nextInt();

                                                    professores = Professor.listarProfessores();
                                                    System.out.println("\nQual o novo professor dessa turma: ");
                                                    for (int i = 0; i < professores.size(); i++) {
                                                        System.out.println(professores.get(i).toString(i) + "\n");
                                                    }
                                                    System.out.print("\n>> ");
                                                    int id_novo_prof = sc.nextInt();

                                                    Turma.editaTurma(turmas.get(id - 1).getIdTurma(),
                                                            professores.get(id_novo_prof - 1).getId_prof(), id_dado,
                                                            idProfessores.get(id_prof - 1));

                                                    break;

                                                default:
                                                    break;
                                            }

                                            System.out.println("\nPressione Enter para continuar!");
                                            sc.nextLine();
                                            sc.nextLine();

                                            break;

                                        case 2:

                                            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

                                            turmas = Turma.listarTurmas();
                                            professores = Professor.listarProfessores();

                                            System.out.println("Qual turma um professor será adicionado: ");
                                            for (int i = 0; i < turmas.size(); i++) {
                                                System.out.println(turmas.get(i).toString(i));
                                            }

                                            System.out.print(">> ");
                                            id = sc.nextInt();

                                            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

                                            System.out.println("Qual professor será adicionado nessa turma: ");
                                            for (int i = 0; i < professores.size(); i++) {
                                                System.out.println(professores.get(i).toString(i));
                                            }

                                            System.out.print("\n>> ");
                                            id_prof = sc.nextInt();

                                            Turma.adicionarProfessor(turmas.get(id - 1).getIdTurma(),
                                                    professores.get(id_prof - 1).getId_prof());

                                            System.out.println("\nPressione Enter para continuar!");
                                            sc.nextLine();
                                            sc.nextLine();

                                            break;

                                        case 3:

                                            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

                                            turmas = Turma.listarTurmas();

                                            System.out.println("Qual turma um professor será excluído: ");
                                            for (int i = 0; i < turmas.size(); i++) {
                                                System.out.println(turmas.get(i).toString(i));
                                            }

                                            System.out.print(">> ");
                                            id = sc.nextInt();

                                            professores = new ArrayList<>();

                                            for (int i = 0; i < turmas.get(id - 1).getIdProfessor().size(); i++) {
                                                professores.add(Professor
                                                        .buscarProfessor(turmas.get(id - 1).getIdProfessor().get(i)));
                                            }

                                            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

                                            if (professores.size() > 1) {
                                                System.out.println("Qual professor será excluído dessa turma: ");
                                                for (int i = 0; i < professores.size(); i++) {
                                                    System.out.println(professores.get(i).toString(i));
                                                }

                                                System.out.print("\n>> ");
                                                id_prof = sc.nextInt();

                                                Turma.ExcluirProfessor(turmas.get(id - 1).getIdTurma(),
                                                        professores.get(id_prof - 1).getId_prof());

                                                System.out.println("\nPressione Enter para continuar!");
                                                sc.nextLine();
                                                sc.nextLine();
                                            } else {
                                                System.out.println("Essa turma só tem um único professor!");

                                                System.out.println("\nPressione Enter para continuar!");
                                                sc.nextLine();
                                                sc.nextLine();
                                            }

                                            break;

                                        case 4:
                                            break;

                                        default:
                                            break;
                                    }
                                } while (op != 4);

                                break;
                            case 3:

                                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                sc.nextLine();
                                System.out.println("Buscando Turma");
                                disciplinas = ComponenteCurricular.listarComponentes();
                                System.out.println("\nQual o componente curricular da turma: \n");
                                for (int i = 0; i < disciplinas.size(); i++) {
                                    System.out.print(disciplinas.get(i).toString(i));
                                }

                                System.out.print(">> ");
                                int id_comp = sc.nextInt();

                                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                System.out.println("Qual o número da turma: ");
                                int codigo = sc.nextInt();

                                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                Turma turma = Turma.buscarTurma(codigo, id_comp);

                                if (turma != null) {
                                    System.out.println(turma);
                                } else {
                                    System.out.println("Turma não encontrada!");
                                }

                                System.out.println("\nPressione Enter para continuar!");
                                sc.nextLine();
                                sc.nextLine();

                                break;
                            case 4:

                                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                                System.out.println("Lista das Turmas");
                                turmas = Turma.listarTurmas();
                                for (int i = 0; i < turmas.size(); i++) {
                                    System.out.print(turmas.get(i).toString(i));
                                }
                                System.out.println("\nPressione Enter para continuar!");
                                sc.nextLine();
                                sc.nextLine();

                                break;
                            case 5: // Listar turmas por semestre

                                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

                                System.out.print("Deseja ver o horário de qual semestre: ");
                                int semestre = sc.nextInt();

                                Turma.imprimirHorarios(Turma.listarTurmasSemestre(semestre));

                                System.out.println("\nPressione Enter para continuar!");
                                sc.nextLine();
                                sc.nextLine();

                                break;
                            case 6: // Listar turmas por professor

                                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

                                professores = Professor.listarProfessores();

                                System.out.print("Deseja ver o horário de qual professor: \n");
                                for (int i = 0; i < professores.size(); i++) {
                                    System.out.println(professores.get(i).toString(i));
                                }

                                System.out.print(">> ");
                                int professor = sc.nextInt();

                                Turma.imprimirHorarios(
                                        Turma.listarTurmasProfessor(professores.get(professor - 1).getId_prof()));

                                System.out.println("\nPressione Enter para continuar!");
                                sc.nextLine();
                                sc.nextLine();

                                break;
                            case 7:

                                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

                                turmas = Turma.listarTurmas();

                                System.out.println("Qual turma deseja excluir: \n");
                                for (int i = 0; i < turmas.size(); i++) {
                                    System.out.println(turmas.get(i).toString(i));
                                }

                                System.out.print(">> ");
                                int id_turma = sc.nextInt();

                                Turma.ExcluirTurma(turmas.get(id_turma - 1).getIdTurma());

                                System.out.println("\nPressione Enter para continuar!");
                                sc.nextLine();
                                sc.nextLine();

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
