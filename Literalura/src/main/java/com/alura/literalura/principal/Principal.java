package com.alura.literalura.principal;

import com.alura.literalura.service.CatalogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Scanner;

@Component
public class Principal implements CommandLineRunner {

    @Autowired
    private CatalogoService catalogoService;


    public void run(String... args) {

        Scanner scanner = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("""
                    ================================
                    CATÁLOGO DE LIVROS
                    ================================
                    1 - Buscar livro pelo título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em determinado ano
                    5 - Listar livros por idioma
                    0 - Sair
                    """);
            System.out.print("Escolha uma opção: ");
            String entrada = scanner.nextLine();

            try {
                opcao = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas números válidos.");
                opcao = -1;
                continue;
            }

            switch (opcao) {
                case 1:
                    catalogoService.buscarLivrosPorTitulo(scanner);
                    break;
                case 2:
                    catalogoService.listarLivros();
                    break;
                case 3:
                    catalogoService.listarAutores();
                    break;
                case 4:
                    catalogoService.listarAutoresVivos(scanner);
                    break;
                case 5:
                    catalogoService.listarLivrosPorIdioma(scanner);
                    break;
                case 0:
                    System.out.println("Encerrando o programa...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
}
