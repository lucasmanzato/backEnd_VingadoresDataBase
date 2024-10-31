package com.example.springboot.Controllers;

import com.example.springboot.dtos.UtilizacaoRecursosDto;
import com.example.springboot.models.UtilizacaoRecursos;
import com.example.springboot.models.Heroi;
import com.example.springboot.models.Recursos;
import com.example.springboot.repositories.UtilizacaoRecursosRepository;
import com.example.springboot.repositories.HeroiRepository;
import com.example.springboot.repositories.RecursosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/utilizacao-recursos")
public class UtilizacaoRecursosController {

    @Autowired
    private UtilizacaoRecursosRepository utilizacaoRecursosRepository;

    @Autowired
    private HeroiRepository heroiRepository;

    @Autowired
    private RecursosRepository recursosRepository;

    @PostMapping
    public ResponseEntity<Object> utilizarRecurso(@RequestBody UtilizacaoRecursosDto utilizacaoRecursosDto) {
        Optional<Heroi> heroiOpt = heroiRepository.findById(utilizacaoRecursosDto.getHeroiId());
        Optional<Recursos> recursoOpt = recursosRepository.findById(utilizacaoRecursosDto.getRecursoId());

        if (heroiOpt.isEmpty() || recursoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Herói ou recurso não encontrado.");
        }

        UtilizacaoRecursos utilizacaoRecursos = new UtilizacaoRecursos();
        utilizacaoRecursos.setHeroi(heroiOpt.get());
        utilizacaoRecursos.setRecurso(recursoOpt.get());
        utilizacaoRecursosRepository.save(utilizacaoRecursos);

        return ResponseEntity.status(HttpStatus.CREATED).body("Recurso utilizado com sucesso.");
    }

    @GetMapping
    public ResponseEntity<Object> listarUtilizacoes() {
        return ResponseEntity.status(HttpStatus.OK).body(utilizacaoRecursosRepository.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarUtilizacao(@PathVariable int id) {
        Optional<UtilizacaoRecursos> utilizacaoOpt = utilizacaoRecursosRepository.findById(id);

        if (utilizacaoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilização não encontrada.");
        }

        utilizacaoRecursosRepository.delete(utilizacaoOpt.get());
        return ResponseEntity.status(HttpStatus.OK).body("Utilização deletada com sucesso.");
    }
}
