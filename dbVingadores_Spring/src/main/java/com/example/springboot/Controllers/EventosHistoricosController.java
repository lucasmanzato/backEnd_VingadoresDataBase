package com.example.springboot.Controllers;

import com.example.springboot.dtos.EventosHistoricosRecordDto;
import com.example.springboot.models.EventosHistoricos;
import com.example.springboot.repositories.EventosHistoricosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/eventosHistoricos")
public class EventosHistoricosController {

    @Autowired
    private EventosHistoricosRepository eventosHistoricosRepository;

    // Endpoint para criar um novo evento histórico
    @PostMapping
    public ResponseEntity<EventosHistoricos> createEvento(@RequestBody EventosHistoricosRecordDto eventosDto) {
        EventosHistoricos evento = new EventosHistoricos();
        evento.setEventosId(UUID.randomUUID());
        evento.setNomeEventos(eventosDto.nomeEventos());
        evento.setDataEventos(eventosDto.dataEventos());
        evento.setLocalEventos(eventosDto.localEventos());
        evento.setTipoEventos(eventosDto.tipoEventos());
        evento.setParticipantesEventos(eventosDto.participantesEventos());
        evento.setImpactoEventos(eventosDto.impactoEventos());

        EventosHistoricos savedEvento = eventosHistoricosRepository.save(evento);
        return new ResponseEntity<>(savedEvento, HttpStatus.CREATED);
    }

    // Endpoint para listar todos os eventos históricos
    @GetMapping
    public ResponseEntity<List<EventosHistoricos>> getAllEventos() {
        List<EventosHistoricos> eventos = eventosHistoricosRepository.findAll();
        return new ResponseEntity<>(eventos, HttpStatus.OK);
    }

    // Endpoint para buscar um evento histórico por ID
    @GetMapping("/{id}")
    public ResponseEntity<EventosHistoricos> getEventoById(@PathVariable UUID id) {
        EventosHistoricos evento = eventosHistoricosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento histórico não encontrado com id: " + id));
        return new ResponseEntity<>(evento, HttpStatus.OK);
    }

    // Endpoint para atualizar um evento histórico
    @PutMapping("/{id}")
    public ResponseEntity<EventosHistoricos> updateEvento(@PathVariable UUID id, @RequestBody EventosHistoricosRecordDto eventosDto) {
        EventosHistoricos evento = eventosHistoricosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento histórico não encontrado com id: " + id));

        evento.setNomeEventos(eventosDto.nomeEventos());
        evento.setDataEventos(eventosDto.dataEventos());
        evento.setLocalEventos(eventosDto.localEventos());
        evento.setTipoEventos(eventosDto.tipoEventos());
        evento.setParticipantesEventos(eventosDto.participantesEventos());
        evento.setImpactoEventos(eventosDto.impactoEventos());

        EventosHistoricos updatedEvento = eventosHistoricosRepository.save(evento);
        return new ResponseEntity<>(updatedEvento, HttpStatus.OK);
    }

    // Endpoint para deletar um evento histórico
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvento(@PathVariable UUID id) {
        EventosHistoricos evento = eventosHistoricosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento histórico não encontrado com id: " + id));

        eventosHistoricosRepository.delete(evento);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
