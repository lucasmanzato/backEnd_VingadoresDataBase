package com.example.springboot.models;

import com.example.springboot.repositories.MissoesRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MissaoService {

    @Autowired
    private MissoesRepository missaoRepository;

    public void finalizarMissao(int missaoId) {
        missaoRepository.finalizarMissao(missaoId);
    }

    public List<Object[]> obterDetalhesMissoes() {
        return missaoRepository.obterDetalhesMissoes();
    }
    @Transactional
    public void desativarBaseERealocar() {
        // Desativar a base 'Quartel General da S.H.I.E.L.D.'
        missaoRepository.updateStatusBase("Quartel General da S.H.I.E.L.D.", "Desativada");

        // Obter o ID da nova base para realocação ('Torre dos Vingadores')
        Integer torreId = missaoRepository.findBaseIdByName("Torre dos Vingadores");

        if (torreId == null) {
            throw new RuntimeException("Base 'Torre dos Vingadores' não encontrada");
        }

        // Realocar recursos da base desativada para a 'Torre dos Vingadores'
        missaoRepository.updateAlocacaoRecursosBase("Quartel General da S.H.I.E.L.D.", torreId);

        // Atualizar a localização dos heróis vinculados à base desativada para a nova base
        missaoRepository.updateHeroiLocalizacao("Washington, D.C.", "Nova York");
    }
}
