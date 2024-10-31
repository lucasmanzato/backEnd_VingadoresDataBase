package com.example.springboot.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "envolvimentoVilao")
public class EnvolvimentoVilao implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID envolvimentoId;

    @ManyToOne
    @JoinColumn(name = "vilao_id")
    private Viloes vilao;

    @ManyToOne
    @JoinColumn(name = "missao_id")
    private Missoes missao;

    public UUID getEnvolvimentoId() {
        return envolvimentoId;
    }

    public void setEnvolvimentoId(UUID envolvimentoId) {
        this.envolvimentoId = envolvimentoId;
    }

    public Viloes getVilao() {
        return vilao;
    }

    public void setVilao(Viloes vilao) {
        this.vilao = vilao;
    }

    public Missoes getMissao() {
        return missao;
    }

    public void setMissao(Missoes missao) {
        this.missao = missao;
    }
}
