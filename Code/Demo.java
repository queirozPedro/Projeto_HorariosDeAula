package Projeto_HorariosDeAula.Code;

import java.util.Scanner;

public class Demo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String menu[] = {"Cadastrar Professor", "Editar Professor", "Ver dados de um Professor", "Listar Professores", "Excluir Professor", "Cadastrar Componente Curricular", "Editar Componente Curricular", "Ver dados de um Componente Curricular", "Listar Componentes Curriculares", "Excluir Componente Curricular", "Cadastrar Turma", "Editar Turma", "Ver dados de uma Turma", "Listar todas as Turmas (em formato de tabela)", "Listar Turmas por semestre (em formato de tabela)", "Listar Turmas por Professor (em formato de tabela)", "Excluir Turma"};
        imprimeMenu(menu);
    }

    public static void imprimeMenu(String[] menu){
        for(int i = 0; i < menu.length; i++){
            System.out.println(menu[i]);
        }
    }
}