package com.example.springboot.dtos;

import java.util.UUID;

public class ParticipacaoEventosDto {
    private int heroiId;
    private int vilaoId;
    private int eventoId;

    public int getHeroiId() {
        return heroiId;
    }

    public void setHeroiId(int heroiId) {
        this.heroiId = heroiId;
    }

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
