package com.alura.literalura.repositorio;

import com.alura.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LivroRepositorio extends JpaRepository<Livro, Long> {

    Optional<Livro> findByTituloIgnoreCase(String titulo);

    @Query("""
        SELECT l FROM Livro l
        WHERE :idioma MEMBER OF l.idiomas
    """)
    List<Livro> buscarLivrosPorIdioma(@Param("idioma") String idioma);
}
