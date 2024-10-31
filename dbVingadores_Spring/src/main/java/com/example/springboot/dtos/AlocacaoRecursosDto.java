package com.example.springboot.dtos;

import java.util.UUID;

public class AlocacaoRecursosDto {
    private UUID baseId;
    private UUID recursoId;

    public UUID getBaseId() {
        return baseId;
    }

    public void setBaseId(UUID baseId) {
        this.baseId = baseId;
    }

    public UUID getRecursoId() {
        return recursoId;
    }

    public void setRecursoId(UUID recursoId) {
        this.recursoId = recursoId;
    }
}
