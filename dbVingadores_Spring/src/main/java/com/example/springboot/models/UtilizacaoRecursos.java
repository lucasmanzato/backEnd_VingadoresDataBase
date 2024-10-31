package com.example.springboot.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "utilizacaoRecursos")
public class UtilizacaoRecursos implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int utilizacaoId;

    @ManyToOne
    @JoinColumn(name = "heroi_id")
    private Heroi heroi;

    @ManyToOne
    @JoinColumn(name = "recurso_id")
    private Recursos recurso;

    public int getUtilizacaoId() {
        return utilizacaoId;
    }

    public void setUtilizacaoId(int utilizacaoId) {
        this.utilizacaoId = utilizacaoId;
    }

    public Heroi getHeroi() {
        return heroi;
    }

    public void setHeroi(Heroi heroi) {
        this.heroi = heroi;
    }

    public Recursos getRecurso() {
        return recurso;
    }

    public void setRecurso(Recursos recurso) {
        this.recurso = recurso;
    }
}
