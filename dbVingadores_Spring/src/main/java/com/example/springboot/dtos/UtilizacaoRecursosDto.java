package com.example.springboot.dtos;

import java.util.UUID;

public class UtilizacaoRecursosDto {
    private int heroiId;
    private int recursoId;

    public int getHeroiId() {
        return heroiId;
    }

    public void setHeroiId(int heroiId) {
        this.heroiId = heroiId;
    }

    public int getRecursoId() {
        return recursoId;
    }

    public void setRecursoId(int recursoId) {
        this.recursoId = recursoId;
    }
}
