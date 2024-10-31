package com.example.springboot.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "recursos")
public class Recursos implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int recursoId;
    private String tipoRecurso;
    private String nomeRecurso;
    private String disponibilidadeRecurso;
    private String usuarioRecurso;
    private String localizacaoRecurso;

    public int getRecursoId() {
        return recursoId;
    }

    public void setRecursoId(int recursoId) {
        this.recursoId = recursoId;
    }

    public String getTipoRecurso() {
        return tipoRecurso;
    }

    public void setTipoRecurso(String tipoRecurso) {
        this.tipoRecurso = tipoRecurso;
    }

    public String getNomeRecurso() {
        return nomeRecurso;
    }

    public void setNomeRecurso(String nomeRecurso) {
        this.nomeRecurso = nomeRecurso;
    }

    public String getDisponibilidadeRecurso() {
        return disponibilidadeRecurso;
    }

    public void setDisponibilidadeRecurso(String disponibilidadeRecurso) {
        this.disponibilidadeRecurso = disponibilidadeRecurso;
    }

    public String getUsuarioRecurso() {
        return usuarioRecurso;
    }

    public void setUsuarioRecurso(String usuarioRecurso) {
        this.usuarioRecurso = usuarioRecurso;
    }

    public String getLocalizacaoRecurso() {
        return localizacaoRecurso;
    }

    public void setLocalizacaoRecurso(String localizacaoRecurso) {
        this.localizacaoRecurso = localizacaoRecurso;
    }
}