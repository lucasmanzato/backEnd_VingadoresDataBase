package com.example.springboot.Controllers;

import com.example.springboot.dtos.ParticipacaoEventosDto;
import com.example.springboot.models.ParticipacaoEventos;
import com.example.springboot.models.Heroi;
import com.example.springboot.models.Viloes;
import com.example.springboot.models.EventosHistoricos;
import com.example.springboot.repositories.ParticipacaoEventosRepository;
import com.example.springboot.repositories.HeroiRepository;
import com.example.springboot.repositories.ViloesRepository;
import com.example.springboot.repositories.EventosHistoricosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/participacao-evento")
public class ParticipacaoEventosController {

    @Autowired
    private ParticipacaoEventosRepository participacaoEventosRepository;

    @Autowired
    private HeroiRepository heroiRepository;

    @Autowired
    private ViloesRepository vilaoRepository;

    @Autowired
    private EventosHistoricosRepository eventoHistoricoRepository;

    @PostMapping
    public ResponseEntity<Object> adicionarParticipacao(@RequestBody ParticipacaoEventosDto participacaoEventosDto) {
        Optional<Heroi> heroiOpt = heroiRepository.findById(participacaoEventosDto.getHeroiId());
        Optional<Viloes> vilaoOpt = vilaoRepository.findById(participacaoEventosDto.getVilaoId());
        Optional<EventosHistoricos> eventoOpt = eventoHistoricoRepository.findById(participacaoEventosDto.getEventoId());

        if (eventoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Evento histórico não encontrado.");
        }

        ParticipacaoEventos participacaoEventos = new ParticipacaoEventos();
        participacaoEventos.setEvento(eventoOpt.get());

        heroiOpt.ifPresent(participacaoEventos::setHeroi);
        vilaoOpt.ifPresent(participacaoEventos::setVilao);

        participacaoEventosRepository.save(participacaoEventos);

        return ResponseEntity.status(HttpStatus.CREATED).body("Participação adicionada com sucesso.");
    }

    @GetMapping
    public ResponseEntity<Object> listarParticipacoes() {
        return ResponseEntity.status(HttpStatus.OK).body(participacaoEventosRepository.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarParticipacao(@PathVariable int id) {
        Optional<ParticipacaoEventos> participacaoOpt = participacaoEventosRepository.findById(id);

        if (participacaoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Participação não encontrada.");
        }

        participacaoEventosRepository.delete(participacaoOpt.get());
        return ResponseEntity.status(HttpStatus.OK).body("Participação deletada com sucesso.");
    }
}
