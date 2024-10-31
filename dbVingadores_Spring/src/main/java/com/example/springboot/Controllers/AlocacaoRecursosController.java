package com.example.springboot.Controllers;

import com.example.springboot.dtos.AlocacaoRecursosDto;
import com.example.springboot.models.AlocacaoRecursos;
import com.example.springboot.models.Bases;
import com.example.springboot.models.Recursos;
import com.example.springboot.repositories.AlocacaoRecursosRepository;
import com.example.springboot.repositories.BasesRepository;
import com.example.springboot.repositories.RecursosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/alocacao-recursos")
public class AlocacaoRecursosController {

    @Autowired
    private AlocacaoRecursosRepository alocacaoRecursosRepository;

    @Autowired
    private BasesRepository baseRepository;

    @Autowired
    private RecursosRepository recursoRepository;

    @PostMapping
    public ResponseEntity<Object> alocarRecurso(@RequestBody AlocacaoRecursosDto alocacaoRecursosDto) {
        Optional<Bases> baseOpt = baseRepository.findById(alocacaoRecursosDto.getBaseId());
        Optional<Recursos> recursoOpt = recursoRepository.findById(alocacaoRecursosDto.getRecursoId());

        if (baseOpt.isEmpty() || recursoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Base ou recurso não encontrado.");
        }

        AlocacaoRecursos alocacaoRecursos = new AlocacaoRecursos();
        alocacaoRecursos.setBase(baseOpt.get());
        alocacaoRecursos.setRecurso(recursoOpt.get());
        alocacaoRecursosRepository.save(alocacaoRecursos);

        return ResponseEntity.status(HttpStatus.CREATED).body("Recurso alocado com sucesso.");
    }

    @GetMapping
    public ResponseEntity<Object> listarAlocacoes() {
        return ResponseEntity.status(HttpStatus.OK).body(alocacaoRecursosRepository.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarAlocacao(@PathVariable int id) {
        Optional<AlocacaoRecursos> alocacaoOpt = alocacaoRecursosRepository.findById(id);

        if (alocacaoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alocação não encontrada.");
        }

        alocacaoRecursosRepository.delete(alocacaoOpt.get());
        return ResponseEntity.status(HttpStatus.OK).body("Alocação deletada com sucesso.");
    }
}
