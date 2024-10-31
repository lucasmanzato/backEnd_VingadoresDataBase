package com.example.springboot.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "envolvimentoEvento")
public class EnvolvimentoEvento implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int envolvimentoEventoId;

    @ManyToOne
    @JoinColumn(name = "vilao_id")
    private Viloes vilao;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    private EventosHistoricos evento;

    public int getEnvolvimentoEventoId() {
        return envolvimentoEventoId;
    }

    public void setEnvolvimentoEventoId(int envolvimentoEventoId) {
        this.envolvimentoEventoId = envolvimentoEventoId;
    }

    public Viloes getVilao() {
        return vilao;
    }

    public void setVilao(Viloes vilao) {
        this.vilao = vilao;
    }

    public EventosHistoricos getEvento() {
        return evento;
    }

    public void setEvento(EventosHistoricos evento) {
        this.evento = evento;
    }
}
