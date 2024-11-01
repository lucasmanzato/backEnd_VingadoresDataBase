package com.example.springboot.Controllers;

import com.example.springboot.dtos.MissoesRecordDto;
import com.example.springboot.models.MissaoService;
import com.example.springboot.models.Missoes;
import com.example.springboot.repositories.MissoesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/missoes")
public class MissoesController {

    @Autowired
    private MissoesRepository missoesRepository;

    // Endpoint para criar uma nova missão
    @PostMapping
    public ResponseEntity<Missoes> createMissao(@RequestBody MissoesRecordDto missoesDto) {
        Missoes missoes = new Missoes();
        missoes.setNomeMissoes(missoesDto.nomeMissoes());
        missoes.setDescricaoMissoes(missoesDto.descricaoMissoes());
        missoes.setDataInicioMissoes(missoesDto.dataInicioMissoes());
        missoes.setDataTerminoMissoes(missoesDto.dataTerminoMissoes());
        missoes.setStatusMissoes(missoesDto.statusMissoes());
        missoes.setHeroisEnvolvidoMissoes(missoesDto.heroisEnvolvidoMissoes());
        missoes.setResultadoMissoes(missoesDto.resultadoMissoes());

        Missoes savedMissao = missoesRepository.save(missoes);
        return new ResponseEntity<>(savedMissao, HttpStatus.CREATED);
    }

    // Endpoint para listar todas as missões
    @GetMapping
    public ResponseEntity<List<Missoes>> getAllMissoes() {
        List<Missoes> missoes = missoesRepository.findAll();
        return new ResponseEntity<>(missoes, HttpStatus.OK);
    }

    // Endpoint para buscar uma missão por ID
    @GetMapping("/{id}")
    public ResponseEntity<Missoes> getMissaoById(@PathVariable int id) {
        Missoes missoes = missoesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Missão não encontrada com id: " + id));
        return new ResponseEntity<>(missoes, HttpStatus.OK);
    }

    // Endpoint para atualizar uma missão
    @PutMapping("/{id}")
    public ResponseEntity<Missoes> updateMissao(@PathVariable int id, @RequestBody MissoesRecordDto missoesDto) {
        Missoes missoes = missoesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Missão não encontrada com id: " + id));

        missoes.setNomeMissoes(missoesDto.nomeMissoes());
        missoes.setDescricaoMissoes(missoesDto.descricaoMissoes());
        missoes.setDataInicioMissoes(missoesDto.dataInicioMissoes());
        missoes.setDataTerminoMissoes(missoesDto.dataTerminoMissoes());
        missoes.setStatusMissoes(missoesDto.statusMissoes());
        missoes.setHeroisEnvolvidoMissoes(missoesDto.heroisEnvolvidoMissoes());
        missoes.setResultadoMissoes(missoesDto.resultadoMissoes());

        Missoes updatedMissao = missoesRepository.save(missoes);
        return new ResponseEntity<>(updatedMissao, HttpStatus.OK);
    }

    // Endpoint para deletar uma missão
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMissao(@PathVariable int id) {
        Missoes missoes = missoesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Missão não encontrada com id: " + id));

        missoesRepository.delete(missoes);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Autowired
    private MissaoService missaoService;

    // Endpoint para finalizar a missão
    @PostMapping("/finalizar")
    public String finalizarMissao(@RequestParam int missaoId) {
        missaoService.finalizarMissao(missaoId);
        return "Missão finalizada e recursos recuperados.";
    }

    // Endpoint para obter detalhes das missões
    @GetMapping("/detalhes")
    public List<Object[]> obterDetalhesMissoes() {
        return missaoService.obterDetalhesMissoes();
    }
    // Novo Endpoint para desativar base e realocar recursos
    @PostMapping("/desativar-e-realocar")
    public String desativarERealocar() {
        try {
            missaoService.desativarBaseERealocar(); // Chama o método de transação no serviço
            return "Base desativada e recursos realocados com sucesso!";
        } catch (Exception e) {
            return "Erro ao desativar a base e realocar recursos: " + e.getMessage();
        }
    }
}
