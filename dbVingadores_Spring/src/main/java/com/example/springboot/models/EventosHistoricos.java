package com.example.springboot.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "eventosHitoricos")
public class EventosHistoricos implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID eventosId;
    private String nomeEventos;
    private Date dataEventos;
    private String localEventos;
    private String tipoEventos;
    private String participantesEventos;
    private String impactoEventos;

    public UUID getEventosId() {
        return eventosId;
    }

    public void setEventosId(UUID eventosId) {
        this.eventosId = eventosId;
    }

    public String getNomeEventos() {
        return nomeEventos;
    }

    public void setNomeEventos(String nomeEventos) {
        this.nomeEventos = nomeEventos;
    }

    public Date getDataEventos() {
        return dataEventos;
    }

    public void setDataEventos(Date dataEventos) {
        this.dataEventos = dataEventos;
    }

    public String getLocalEventos() {
        return localEventos;
    }

    public void setLocalEventos(String localEventos) {
        this.localEventos = localEventos;
    }

    public String getTipoEventos() {
        return tipoEventos;
    }

    public void setTipoEventos(String tipoEventos) {
        this.tipoEventos = tipoEventos;
    }

    public String getParticipantesEventos() {
        return participantesEventos;
    }

    public void setParticipantesEventos(String participantesEventos) {
        this.participantesEventos = participantesEventos;
    }

    public String getImpactoEventos() {
        return impactoEventos;
    }

    public void setImpactoEventos(String impactoEventos) {
        this.impactoEventos = impactoEventos;
    }
}