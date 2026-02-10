package com.alura.literalura.service;

import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class ConverteDados {

    private final ObjectMapper mapper = new ObjectMapper();

    public <T> T obterDados(String json, Class<T> classe) {
        try {
            return mapper.readValue(json, classe);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter JSON", e);
        }
    }
}
