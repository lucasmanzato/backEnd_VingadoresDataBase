package com.example.springboot.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "bases")
public class Bases implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID baseId;
    private String nomeBase;
    private String localizacaoBase;
    private String propositoBase;
    private double capacidadeBase;
    private String statusBase;
    private String comandanteBase;

    public UUID getBaseId() {
        return baseId;
    }

    public void setBaseId(UUID baseId) {
        this.baseId = baseId;
    }

    public String getNomeBase() {
        return nomeBase;
    }

    public void setNomeBase(String nomeBase) {
        this.nomeBase = nomeBase;
    }

    public String getLocalizacaoBase() {
        return localizacaoBase;
    }

    public void setLocalizacaoBase(String localizacaoBase) {
        this.localizacaoBase = localizacaoBase;
    }

    public String getPropositoBase() {
        return propositoBase;
    }

    public void setPropositoBase(String propositoBase) {
        this.propositoBase = propositoBase;
    }

    public double getCapacidadeBase() {
        return capacidadeBase;
    }

    public void setCapacidadeBase(double capacidadeBase) {
        this.capacidadeBase = capacidadeBase;
    }

    public String getStatusBase() {
        return statusBase;
    }

    public void setStatusBase(String statusBase) {
        this.statusBase = statusBase;
    }

    public String getComandanteBase() {
        return comandanteBase;
    }

    public void setComandanteBase(String comandanteBase) {
        this.comandanteBase = comandanteBase;
    }
}