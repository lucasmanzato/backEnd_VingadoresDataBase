package com.example.springboot.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "viloes")
public class Viloes implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID viloesId;
    private String nomeViloes;
    private String titulosViloes;
    private String poderesViloes;
    private String motivacaoViloes;
    private String statusViloes;
    private String localizacaoViloes;

    public UUID getViloesId() {
        return viloesId;
    }

    public void setViloesId(UUID viloesId) {
        this.viloesId = viloesId;
    }

    public String getNomeViloes() {
        return nomeViloes;
    }

    public void setNomeViloes(String nomeViloes) {
        this.nomeViloes = nomeViloes;
    }

    public String getTitulosViloes() {
        return titulosViloes;
    }

    public void setTitulosViloes(String titulosViloes) {
        this.titulosViloes = titulosViloes;
    }

    public String getPoderesViloes() {
        return poderesViloes;
    }

    public void setPoderesViloes(String poderesViloes) {
        this.poderesViloes = poderesViloes;
    }

    public String getMotivacaoViloes() {
        return motivacaoViloes;
    }

    public void setMotivacaoViloes(String motivacaoViloes) {
        this.motivacaoViloes = motivacaoViloes;
    }

    public String getStatusViloes() {
        return statusViloes;
    }

    public void setStatusViloes(String statusViloes) {
        this.statusViloes = statusViloes;
    }

    public String getLocalizacaoViloes() {
        return localizacaoViloes;
    }

    public void setLocalizacaoViloes(String localizacaoViloes) {
        this.localizacaoViloes = localizacaoViloes;
    }
}