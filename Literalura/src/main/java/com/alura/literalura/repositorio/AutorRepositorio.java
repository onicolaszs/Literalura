package com.alura.literalura.repositorio;

import com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepositorio extends JpaRepository<Autor, Long> {

    Optional<Autor> findByNome(String nome);

    @Query("""
        SELECT a FROM Autor a
        WHERE a.anoDeNascimento <= :ano
          AND (a.anoDeFalecimento IS NULL OR a.anoDeFalecimento >= :ano)
    """)
    List<Autor> buscarAutoresVivosNoAno(@Param("ano") Integer ano);
}
