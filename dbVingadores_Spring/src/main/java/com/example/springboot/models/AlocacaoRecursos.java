package com.example.springboot.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "alocacaoRecursos")
public class AlocacaoRecursos implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID alocacaoId;

    @ManyToOne
    @JoinColumn(name = "base_id")
    private Bases base;

    @ManyToOne
    @JoinColumn(name = "recurso_id")
    private Recursos recurso;

    public UUID getAlocacaoId() {
        return alocacaoId;
    }

    public void setAlocacaoId(UUID alocacaoId) {
        this.alocacaoId = alocacaoId;
    }

    public Bases getBase() {
        return base;
    }

    public void setBase(Bases base) {
        this.base = base;
    }

    public Recursos getRecurso() {
        return recurso;
    }

    public void setRecurso(Recursos recurso) {
        this.recurso = recurso;
    }
}
