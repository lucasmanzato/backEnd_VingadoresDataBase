package com.example.springboot.dtos;

import java.util.Date;

public record EventosHistoricosRecordDto(
        String nomeEventos,
        Date dataEventos,
        String localEventos,
        String tipoEventos,
        String participantesEventos,
        String impactoEventos
) {}
