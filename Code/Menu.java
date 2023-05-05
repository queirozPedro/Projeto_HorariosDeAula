public class Menu {
    public static void Principal(){
        System.out.println(" ====  Menu Principal  ====");
        System.out.println(" 1 -> Menu do Professor");
        System.out.println(" 2 -> Menu do Componente Curricular");
        System.out.println(" 3 -> Menu da Turma");
        System.out.println(" 4 -> Finalizar Programa");
    }

    public static void Professor(){
        System.out.println(" == Opções do Professor ==");
        System.out.println(" 1 -> Cadastrar Professor");
        System.out.println(" 2 -> Editar Professor");
        System.out.println(" 3 -> Ver dados de um Professor");
        System.out.println(" 4 -> Listar Professores");
        System.out.println(" 5 -> Excluir Professor");
        System.out.println(" 6 -> Voltar");
    }

    public static void ComponenteCurricular(){
        System.out.println(" == Opções do Componente Curricular ==");
        System.out.println("1 -> Cadastrar Componente Curricular");
        System.out.println("2 -> Editar Componente Curricular");
        System.out.println("3 -> Ver dados de um Componente Curricular");
        System.out.println("4 -> Listar Componentes Curriculares");
        System.out.println("5 -> Excluir Componente Curricular");
        System.out.println("6 -> Voltar");
    }

    public static void Turma(){
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