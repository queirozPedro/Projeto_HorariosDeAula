package Projeto_HorariosDeAula.Code;

import java.util.Scanner;

public class Demo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cont = 0;
        String menu[] = {"Menu do Professor","Componente Curricular", "Menu da Turma"};
        // String menuDoProfessor[] = {"Cadastrar Professor", "Editar Professor", "Ver dados de um Professor", "Listar Professores", "Excluir Professor"};
        // String menuDoComponenteCurricular[] = {"Cadastrar Componente Curricular", "Editar Componente Curricular", "Ver dados de um Componente Curricular", "Listar Componentes Curriculares", "Excluir Componente Curricular"};
        // String menuDaTurma[] = {"Cadastrar Turma", "Editar Turma", "Ver dados de uma Turma", "Listar todas as Turmas (em formato de tabela)", "Listar Turmas por semestre (em formato de tabela)", "Listar Turmas por Professor (em formato de tabela)", "Excluir Turma"};
        
        
        sc.close();
    }

    public static void imprimeMenu(String[] menu, int cont){

        for(int i = 0; i < menu.length; i++){
            System.out.println(menu[i]);
        }
    }
}