package com.example.springboot.dtos;

import java.util.UUID;

public class EnvolvimentoEventoDto {
    private int vilaoId;
    private int eventoId;

    public int getVilaoId() {
        return vilaoId;
    }

    public void setVilaoId(int vilaoId) {
        this.vilaoId = vilaoId;
    }

    public int getEventoId() {
        return eventoId;
    }

    public void setEventoId(int eventoId) {
        this.eventoId = eventoId;
    }
}
