package com.example.springboot.dtos;

public record RecursosRecordDto(
        String tipoRecurso,
        String nomeRecurso,
        String disponibilidadeRecurso,
        String usuarioRecurso,
        String localizacaoRecurso
) {}
