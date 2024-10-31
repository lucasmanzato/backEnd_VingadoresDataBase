package com.example.springboot.controllers;

import com.example.springboot.dtos.EnvolvimentoEventoDto;
import com.example.springboot.models.EnvolvimentoEvento;
import com.example.springboot.models.Viloes;
import com.example.springboot.models.EventosHistoricos;
import com.example.springboot.repositories.EnvolvimentoEventoRepository;
import com.example.springboot.repositories.ViloesRepository;
import com.example.springboot.repositories.EventosHistoricosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/envolvimento-evento")
public class EnvolvimentoEventoController {

    @Autowired
    private EnvolvimentoEventoRepository envolvimentoEventoRepository;

    @Autowired
    private ViloesRepository vilaoRepository;

    @Autowired
    private EventosHistoricosRepository eventoHistoricoRepository;

    @PostMapping
    public ResponseEntity<Object> adicionarEnvolvimento(@RequestBody EnvolvimentoEventoDto envolvimentoEventoDto) {
        Optional<Viloes> vilaoOpt = vilaoRepository.findById(envolvimentoEventoDto.getVilaoId());
        Optional<EventosHistoricos> eventoOpt = eventoHistoricoRepository.findById(envolvimentoEventoDto.getEventoId());

        if (vilaoOpt.isEmpty() || eventoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vilão ou evento histórico não encontrado.");
        }

        EnvolvimentoEvento envolvimentoEvento = new EnvolvimentoEvento();
        envolvimentoEvento.setVilao(vilaoOpt.get());
        envolvimentoEvento.setEvento(eventoOpt.get());
        envolvimentoEventoRepository.save(envolvimentoEvento);

        return ResponseEntity.status(HttpStatus.CREATED).body("Envolvimento do vilão no evento histórico adicionado com sucesso.");
    }

    @GetMapping
    public ResponseEntity<Object> listarEnvolvimentos() {
        return ResponseEntity.status(HttpStatus.OK).body(envolvimentoEventoRepository.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarEnvolvimento(@PathVariable int id) {
        Optional<EnvolvimentoEvento> envolvimentoOpt = envolvimentoEventoRepository.findById(id);

        if (envolvimentoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Envolvimento não encontrado.");
        }

        envolvimentoEventoRepository.delete(envolvimentoOpt.get());
        return ResponseEntity.status(HttpStatus.OK).body("Envolvimento deletado com sucesso.");
    }
}
