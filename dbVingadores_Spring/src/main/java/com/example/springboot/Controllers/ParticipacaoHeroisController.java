package com.example.springboot.Controllers;

import com.example.springboot.dtos.ParticipacaoHeroisDto;
import com.example.springboot.models.ParticipacaoHerois;
import com.example.springboot.models.Heroi;
import com.example.springboot.models.Missoes;
import com.example.springboot.repositories.ParticipacaoHeroisRepository;
import com.example.springboot.repositories.HeroiRepository;
import com.example.springboot.repositories.MissoesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/participacao-herois")
public class ParticipacaoHeroisController {

    @Autowired
    private ParticipacaoHeroisRepository participacaoHeroisRepository;

    @Autowired
    private HeroiRepository heroiRepository;

    @Autowired
    private MissoesRepository missaoRepository;

    @PostMapping
    public ResponseEntity<Object> adicionarParticipacao(@RequestBody ParticipacaoHeroisDto participacaoHeroisDto) {
        Optional<Heroi> heroiOpt = heroiRepository.findById(participacaoHeroisDto.getHeroiId());
        Optional<Missoes> missaoOpt = missaoRepository.findById(participacaoHeroisDto.getMissaoId());

        if (heroiOpt.isEmpty() || missaoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Herói ou missão não encontrada.");
        }

        ParticipacaoHerois participacaoHerois = new ParticipacaoHerois();
        participacaoHerois.setHeroi(heroiOpt.get());
        participacaoHerois.setMissao(missaoOpt.get());
        participacaoHeroisRepository.save(participacaoHerois);

        return ResponseEntity.status(HttpStatus.CREATED).body("Participação do herói na missão adicionada com sucesso.");
    }

    @GetMapping
    public ResponseEntity<Object> listarParticipacoes() {
        return ResponseEntity.status(HttpStatus.OK).body(participacaoHeroisRepository.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarParticipacao(@PathVariable UUID id) {
        Optional<ParticipacaoHerois> participacaoOpt = participacaoHeroisRepository.findById(id);

        if (participacaoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Participação não encontrada.");
        }

        participacaoHeroisRepository.delete(participacaoOpt.get());
        return ResponseEntity.status(HttpStatus.OK).body("Participação deletada com sucesso.");
    }
}
