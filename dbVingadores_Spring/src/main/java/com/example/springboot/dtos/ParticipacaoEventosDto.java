package com.example.springboot.dtos;

import java.util.UUID;

public class ParticipacaoEventosDto {
    private UUID heroiId;
    private UUID vilaoId;
    private UUID eventoId;

    public UUID getHeroiId() {
        return heroiId;
    }

    public void setHeroiId(UUID heroiId) {
        this.heroiId = heroiId;
    }

    public UUID getVilaoId() {
        return vilaoId;
    }

    public void setVilaoId(UUID vilaoId) {
        this.vilaoId = vilaoId;
    }

    public UUID getEventoId() {
        return eventoId;
    }

    public void setEventoId(UUID eventoId) {
        this.eventoId = eventoId;
    }
}
