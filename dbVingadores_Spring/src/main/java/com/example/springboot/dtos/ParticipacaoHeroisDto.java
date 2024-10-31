package com.example.springboot.dtos;

import java.util.UUID;

public class ParticipacaoHeroisDto {
    private UUID heroiId;
    private UUID missaoId;

    public UUID getHeroiId() {
        return heroiId;
    }

    public void setHeroiId(UUID heroiId) {
        this.heroiId = heroiId;
    }

    public UUID getMissaoId() {
        return missaoId;
    }

    public void setMissaoId(UUID missaoId) {
        this.missaoId = missaoId;
    }
}
