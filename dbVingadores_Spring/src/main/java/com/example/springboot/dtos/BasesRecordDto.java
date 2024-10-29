package com.example.springboot.dtos;

public record BasesRecordDto(
        String nomeBase,
        String localizacaoBase,
        String propositoBase,
        double capacidadeBase,
        String statusBase,
        String comandanteBase
) {}
