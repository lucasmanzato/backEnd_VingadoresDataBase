package com.example.springboot.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "atribuicao_base")
public class AtribuicaoBase implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int atribuicaoId;

    @ManyToOne
    @JoinColumn(name = "heroi_id", referencedColumnName = "id")
    private Heroi heroi;

    @ManyToOne
    @JoinColumn(name = "base_id", referencedColumnName = "baseId")
    private Bases base;

    public int getAtribuicaoId() {
        return atribuicaoId;
    }

    public void setAtribuicaoId(int atribuicaoId) {
        this.atribuicaoId = atribuicaoId;
    }

    public Heroi getHeroi() {
        return heroi;
    }

    public void setHeroi(Heroi heroi) {
        this.heroi = heroi;
    }

    public Bases getBase() {
        return base;
    }

    public void setBase(Bases base) {
        this.base = base;
    }
}
