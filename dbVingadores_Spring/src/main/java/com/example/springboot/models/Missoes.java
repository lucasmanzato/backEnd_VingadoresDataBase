package com.example.springboot.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "missoes")
public class Missoes implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID missoesId;
    private String nomeMissoes;
    private String descricaoMissoes;
    private Date dataInicioMissoes;
    private Date dataTerminoMissoes;
    private String statusMissoes;
    private String heroisEnvolvidoMissoes;
    private String resultadoMissoes;

    public UUID getMissoesId() {
        return missoesId;
    }

    public void setMissoesId(UUID missoesId) {
        this.missoesId = missoesId;
    }

    public String getNomeMissoes() {
        return nomeMissoes;
    }

    public void setNomeMissoes(String nomeMissoes) {
        this.nomeMissoes = nomeMissoes;
    }

    public String getDescricaoMissoes() {
        return descricaoMissoes;
    }

    public void setDescricaoMissoes(String descricaoMissoes) {
        this.descricaoMissoes = descricaoMissoes;
    }

    public Date getDataInicioMissoes() {
        return dataInicioMissoes;
    }

    public void setDataInicioMissoes(Date dataInicioMissoes) {
        this.dataInicioMissoes = dataInicioMissoes;
    }

    public Date getDataTerminoMissoes() {
        return dataTerminoMissoes;
    }

    public void setDataTerminoMissoes(Date dataTerminoMissoes) {
        this.dataTerminoMissoes = dataTerminoMissoes;
    }

    public String getStatusMissoes() {
        return statusMissoes;
    }

    public void setStatusMissoes(String statusMissoes) {
        this.statusMissoes = statusMissoes;
    }

    public String getHeroisEnvolvidoMissoes() {
        return heroisEnvolvidoMissoes;
    }

    public void setHeroisEnvolvidoMissoes(String heroisEnvolvidoMissoes) {
        this.heroisEnvolvidoMissoes = heroisEnvolvidoMissoes;
    }

    public String getResultadoMissoes() {
        return resultadoMissoes;
    }

    public void setResultadoMissoes(String resultadoMissoes) {
        this.resultadoMissoes = resultadoMissoes;
    }
}