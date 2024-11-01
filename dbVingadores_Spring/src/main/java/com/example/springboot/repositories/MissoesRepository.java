package com.example.springboot.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.springboot.models.Missoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MissoesRepository extends JpaRepository<Missoes, Integer> {

// Método para chamar a função de finalizar a missão
@Query(value = "SELECT finalizar_missao_e_recuperar_recursos(:missaoId)", nativeQuery = true)
void finalizarMissao(@Param("missaoId") int missaoId);

// Método para buscar dados da view VisaoDetalhesMissoes
@Query(value = "SELECT * FROM VisaoDetalhesMissoes", nativeQuery = true)
List<Object[]> obterDetalhesMissoes();
    // Método para desativar uma base
    @Modifying
    @Transactional
    @Query("UPDATE Bases b SET b.statusBase = :status WHERE b.nomeBase = :nomeBase")
    void updateStatusBase(String nomeBase, String status);

    // Método para encontrar o ID de uma base pelo nome
    @Query("SELECT b.baseId FROM Bases b WHERE b.nomeBase = :nomeBase")
    Integer findBaseIdByName(String nomeBase);

    @Transactional
    @Modifying
    @Query("UPDATE AlocacaoRecursos ar SET ar.base.baseId = :novaBaseId WHERE ar.base.baseId IN (SELECT b.baseId FROM Bases b WHERE b.nomeBase = :nomeBaseAntiga)")
    void updateAlocacaoRecursosBase(@Param("nomeBaseAntiga") String nomeBaseAntiga, @Param("novaBaseId") Integer novaBaseId);



    // Método para atualizar a localização dos heróis
    @Modifying
    @Transactional
    @Query("UPDATE Heroi h SET h.localizacao = :novaLocalizacao WHERE h.localizacao = :localizacaoAntiga")
    void updateHeroiLocalizacao(String localizacaoAntiga, String novaLocalizacao);
}

