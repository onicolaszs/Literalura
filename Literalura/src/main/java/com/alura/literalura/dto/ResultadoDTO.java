package com.alura.literalura.dto;

import java.util.List;

public record ResultadoDTO(
        Integer count,
        List<LivroDTO> results
) {}
