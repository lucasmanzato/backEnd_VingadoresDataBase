package com.example.springboot.dtos;

import java.util.UUID;

public record AtribuicaoBaseRecordDto(
        UUID heroiId,
        UUID baseId
) {}
