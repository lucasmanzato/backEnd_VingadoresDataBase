package com.example.springboot.dtos;

import java.util.UUID;

public class UtilizacaoRecursosDto {
    private UUID heroiId;
    private UUID recursoId;

    public UUID getHeroiId() {
        return heroiId;
    }

    public void setHeroiId(UUID heroiId) {
        this.heroiId = heroiId;
    }

    public UUID getRecursoId() {
        return recursoId;
    }

    public void setRecursoId(UUID recursoId) {
        this.recursoId = recursoId;
    }
}
