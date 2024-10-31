package com.example.springboot.dtos;

import java.util.UUID;

public class AlocacaoRecursosDto {
    private int baseId;
    private int recursoId;

    public int getBaseId() {
        return baseId;
    }

    public void setBaseId(int baseId) {
        this.baseId = baseId;
    }

    public int getRecursoId() {
        return recursoId;
    }

    public void setRecursoId(int recursoId) {
        this.recursoId = recursoId;
    }
}
