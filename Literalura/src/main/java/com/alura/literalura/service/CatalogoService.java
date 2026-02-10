package com.alura.literalura.service;

import com.alura.literalura.dto.ResultadoDTO;
import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Livro;
import com.alura.literalura.repositorio.AutorRepositorio;
import com.alura.literalura.repositorio.LivroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class CatalogoService {

    private final String ENDERECO_API = "https://gutendex.com/books/?search=";

    @Autowired
    private ConsumoApi consumoApi;

    @Autowired
    private ConverteDados converteDados;

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Autowired
    private LivroRepositorio livroRepositorio;


       // BUSCAR LIVRO PELO TÍTULO
    public void buscarLivrosPorTitulo(Scanner scanner) {

        System.out.print("Digite o título do livro: ");
        String titulo = scanner.nextLine();

        String url = ENDERECO_API + titulo.replace(" ", "%20");
        String json = consumoApi.obterDados(url);

        ResultadoDTO resultado =
                converteDados.obterDados(json, ResultadoDTO.class);

        if (resultado.results().isEmpty()) {
            System.out.println("Nenhum livro encontrado.");
            return;
        }

        resultado.results().stream().limit(1).forEach(livroDTO -> {

            var livroExistente =
                    livroRepositorio.findByTituloIgnoreCase(livroDTO.title());

            if (livroExistente.isPresent()) {
                System.out.println("Livro já cadastrado.");
                imprimirLivro(livroExistente.get());
                return;
            }

            Livro livro = new Livro();
            livro.setTitulo(livroDTO.title());

            List<String> idiomas = livroDTO.languages();
            if (idiomas == null || idiomas.isEmpty()) {
                idiomas = new ArrayList<>();
                idiomas = List.of("desconhecido");
            }
            livro.setIdiomas(idiomas);
            livro.setNumeroDeDownloads(livroDTO.download_count());

            // Autores
            var autores = livroDTO.authors().stream().map(autorDTO ->
                    autorRepositorio.findByNome(autorDTO.name())
                            .orElseGet(() -> {
                                Autor novoAutor = new Autor();
                                novoAutor.setNome(autorDTO.name());
                                novoAutor.setAnoDeNascimento(autorDTO.birth_year());
                                novoAutor.setAnoDeFalecimento(autorDTO.death_year());
                                return autorRepositorio.save(novoAutor);
                            })
            ).toList();

            livro.setAutores(autores);
            livroRepositorio.save(livro);

            imprimirLivro(livro);
        });
    }

    // LISTAR LIVROS
    public void listarLivros() {
        var livros = livroRepositorio.findAll();

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro cadastrado no banco.");
            return;
        }

        livros.forEach(this::imprimirLivro);
    }


       //LISTAR AUTORES
    public void listarAutores() {

        var autores = autorRepositorio.findAll();

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor cadastrado.");
            return;
        }

        autores.forEach(this::imprimirAutor);
    }

    //AUTORES VIVOS EM UM ANO
    public void listarAutoresVivos(Scanner scanner) {

        System.out.print("Digite o ano: ");
        int ano = scanner.nextInt();
        scanner.nextLine();

        var autores = autorRepositorio.buscarAutoresVivosNoAno(ano);

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor vivo encontrado.");
            return;
        }

        autores.forEach(this::imprimirAutor);
    }

    //LIVROS POR IDIOMA
    public void listarLivrosPorIdioma(Scanner scanner) {

        System.out.print("Digite o idioma (ex: pt, en, fr): ");
        String idioma = scanner.nextLine().toLowerCase();

        var livros = livroRepositorio.buscarLivrosPorIdioma(idioma);

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado no idioma: " + idioma);
            return;
        }

        livros.forEach(this::imprimirLivro);
    }

       //IMPRESSÃO DE LIVRO
    private void imprimirLivro(Livro livro) {
        System.out.println("Título: " + livro.getTitulo());
        System.out.println("Idiomas: " + formatarIdiomas(livro.getIdiomas()));
        System.out.println("Número de downloads: " +
                (livro.getNumeroDeDownloads() !=null
                        ? livro.getNumeroDeDownloads()
                        : "não informado"));

        String autores = livro.getAutores().stream()
                .map(Autor::getNome)
                .distinct()
                .reduce((a, b) -> a + ", " + b)
                .orElse("não informado");

        System.out.println("Autor: " + autores);
        System.out.println("-----------------------------");
    }

       //IMPRESSÃO DE AUTOR
    private void imprimirAutor(Autor autor) {

        System.out.println("---------- AUTOR ----------");
        System.out.println("Autor: " + autor.getNome());
        System.out.println("Ano de nascimento: " + autor.getAnoDeNascimento());

        if (autor.getAnoDeFalecimento() != null) {
            System.out.println("Ano de falecimento: " + autor.getAnoDeFalecimento());
        } else {
            System.out.println("Ano de falecimento: vivo");
        }

        System.out.println("---------------------------\n");
    }

       //FORMATAÇÃO DE IDIOMAS
    private String formatarIdiomas(List<String> idiomas) {
        if (idiomas == null || idiomas.isEmpty()) {
            return "não informado";
        }

        return idiomas.stream()
                .map(idioma -> switch (idioma.toLowerCase()) {
                    case "pt" -> "Português";
                    case "en" -> "Inglês";
                    case "fr" -> "Francês";
                    case "es" -> "Espanhol";
                    case "de" -> "Alemão";
                    case "it" -> "Italiano";
                    case "desconhecido" -> "Não informado";
                    default -> idioma;
                })
                .distinct()
                .reduce((a, b) -> a + ", " + b)
                .orElse("não informado");
    }
}
