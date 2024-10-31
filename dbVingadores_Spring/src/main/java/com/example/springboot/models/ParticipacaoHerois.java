package com.example.springboot.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "participacaoHerois")
public class ParticipacaoHerois implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID participacaoId;

    @ManyToOne
    @JoinColumn(name = "heroi_id")
    private Heroi heroi;

    @ManyToOne
    @JoinColumn(name = "missao_id")
    private Missoes missao;

    public UUID getParticipacaoId() {
        return participacaoId;
    }

    public void setParticipacaoId(UUID participacaoId) {
        this.participacaoId = participacaoId;
    }

    public Heroi getHeroi() {
        return heroi;
    }

    public void setHeroi(Heroi heroi) {
        this.heroi = heroi;
    }

    public Missoes getMissao() {
        return missao;
    }

    public void setMissao(Missoes missao) {
        this.missao = missao;
    }
}
