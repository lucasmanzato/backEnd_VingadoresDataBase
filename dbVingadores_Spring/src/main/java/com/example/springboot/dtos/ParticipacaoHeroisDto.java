package com.example.springboot.dtos;

import java.util.UUID;

public class ParticipacaoHeroisDto {
    private int heroiId;
    private int missaoId;

    public int getHeroiId() {
        return heroiId;
    }

    public void setHeroiId(int heroiId) {
        this.heroiId = heroiId;
    }

    public int getMissaoId() {
        return missaoId;
    }

    public void setMissaoId(int missaoId) {
        this.missaoId = missaoId;
    }
}
