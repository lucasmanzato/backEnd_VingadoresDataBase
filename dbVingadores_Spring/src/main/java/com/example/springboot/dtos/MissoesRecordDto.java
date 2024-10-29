package com.example.springboot.dtos;

import java.util.Date;

public record MissoesRecordDto(
        String nomeMissoes,
        String descricaoMissoes,
        Date dataInicioMissoes,
        Date dataTerminoMissoes,
        String statusMissoes,
        String heroisEnvolvidoMissoes,
        String resultadoMissoes
) {}
