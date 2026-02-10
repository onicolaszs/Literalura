package com.alura.literalura.dto;

import java.util.List;

public record LivroDTO(
        String title,
        List<String> languages,
        Double download_count,
        List<AutorDTO> authors
) {}

