package com.example.springboot.Controllers;

import com.example.springboot.dtos.EnvolvimentoVilaoDto;
import com.example.springboot.models.EnvolvimentoVilao;
import com.example.springboot.models.Viloes;
import com.example.springboot.models.Missoes;
import com.example.springboot.repositories.EnvolvimentoVilaoRepository;
import com.example.springboot.repositories.ViloesRepository;
import com.example.springboot.repositories.MissoesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/envolvimento-vilao")
public class EnvolvimentoVilaoController {

    @Autowired
    private EnvolvimentoVilaoRepository envolvimentoVilaoRepository;

    @Autowired
    private ViloesRepository vilaoRepository;

    @Autowired
    private MissoesRepository missaoRepository;

    @PostMapping
    public ResponseEntity<Object> adicionarEnvolvimento(@RequestBody EnvolvimentoVilaoDto envolvimentoVilaoDto) {
        Optional<Viloes> vilaoOpt = vilaoRepository.findById(envolvimentoVilaoDto.getVilaoId());
        Optional<Missoes> missaoOpt = missaoRepository.findById(envolvimentoVilaoDto.getMissaoId());

        if (vilaoOpt.isEmpty() || missaoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vilão ou missão não encontrada.");
        }

        EnvolvimentoVilao envolvimentoVilao = new EnvolvimentoVilao();
        envolvimentoVilao.setVilao(vilaoOpt.get());
        envolvimentoVilao.setMissao(missaoOpt.get());
        envolvimentoVilaoRepository.save(envolvimentoVilao);

        return ResponseEntity.status(HttpStatus.CREATED).body("Envolvimento do vilão na missão adicionado com sucesso.");
    }

    @GetMapping
    public ResponseEntity<Object> listarEnvolvimentos() {
        return ResponseEntity.status(HttpStatus.OK).body(envolvimentoVilaoRepository.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarEnvolvimento(@PathVariable int id) {
        Optional<EnvolvimentoVilao> envolvimentoOpt = envolvimentoVilaoRepository.findById(id);

        if (envolvimentoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Envolvimento não encontrado.");
        }

        envolvimentoVilaoRepository.delete(envolvimentoOpt.get());
        return ResponseEntity.status(HttpStatus.OK).body("Envolvimento deletado com sucesso.");
    }
}
