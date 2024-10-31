package com.example.springboot.dtos;

import java.util.UUID;

public class EnvolvimentoVilaoDto {
    private int vilaoId;
    private int missaoId;

    public int getVilaoId() {
        return vilaoId;
    }

    public void setVilaoId(int vilaoId) {
        this.vilaoId = vilaoId;
    }

    public int getMissaoId() {
        return missaoId;
    }

    public void setMissaoId(int missaoId) {
        this.missaoId = missaoId;
    }
}
