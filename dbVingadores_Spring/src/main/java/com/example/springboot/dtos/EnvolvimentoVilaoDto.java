package com.example.springboot.dtos;

import java.util.UUID;

public class EnvolvimentoVilaoDto {
    private UUID vilaoId;
    private UUID missaoId;

    public UUID getVilaoId() {
        return vilaoId;
    }

    public void setVilaoId(UUID vilaoId) {
        this.vilaoId = vilaoId;
    }

    public UUID getMissaoId() {
        return missaoId;
    }

    public void setMissaoId(UUID missaoId) {
        this.missaoId = missaoId;
    }
}
