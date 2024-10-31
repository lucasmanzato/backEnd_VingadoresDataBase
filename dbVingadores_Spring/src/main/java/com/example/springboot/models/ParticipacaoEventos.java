package com.example.springboot.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "participacaoEventos")
public class ParticipacaoEventos implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID participacaoEventoId;

    @ManyToOne
    @JoinColumn(name = "heroi_id")
    private Heroi heroi;

    @ManyToOne
    @JoinColumn(name = "vilao_id")
    private Viloes vilao;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    private EventosHistoricos evento;

    public UUID getParticipacaoEventoId() {
        return participacaoEventoId;
    }

    public void setParticipacaoEventoId(UUID participacaoEventoId) {
        this.participacaoEventoId = participacaoEventoId;
    }

    public Heroi getHeroi() {
        return heroi;
    }

    public void setHeroi(Heroi heroi) {
        this.heroi = heroi;
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
