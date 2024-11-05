-- Visões
-- Detalhes das Missões com Participação de Heróis e Vilões
CREATE VIEW VisaoDetalhesMissoes AS
SELECT 
    m.nome_missoes,
    m.descricao_missoes,
    m.data_inicio_missoes,
    m.data_termino_missoes,
    m.status_missoes,
    h.nome AS Heroi,
    v.nome_viloes AS Vilao
FROM missoes m
LEFT JOIN participacao_herois ph ON m.missoes_id = ph.missao_id
LEFT JOIN heroi h ON ph.heroi_id = h.id
LEFT JOIN envolvimento_vilao ev ON m.missoes_id = ev.missao_id
LEFT JOIN viloes v ON ev.vilao_id = v.viloes_id;
-----------------------------------------------------------------------------------------------
-- Recursos Utilizados por Heróis em Missões Específicas
CREATE VIEW VisaoRecursosHeroisMissoes AS
SELECT 
    h.nome AS Heroi,
    r.nome_recurso,
    r.tipo_recurso,
    m.nome_missoes,
    m.descricao_missoes,
    m.status_missoes
FROM utilizacao_recursos ur
JOIN heroi h ON ur.heroi_id = h.id
JOIN recursos r ON ur.recurso_id = r.recurso_id
JOIN participacao_herois ph ON h.id = ph.heroi_id
JOIN missoes m ON ph.missao_id = m.missoes_id;
-----------------------------------------------------------------------------------------------
-- Procedure
-- Atualizar Status do Herói Após Conclusão da Missão
CREATE OR REPLACE FUNCTION AtualizarStatusHeroiPostMissao(missao_id INT, resultado_missao TEXT)
RETURNS VOID AS $$
BEGIN
    IF resultado_missao = 'Sucesso' THEN
        UPDATE heroi
        SET status = 'Inativo'
        FROM participacao_herois
        WHERE heroi.id = participacao_herois.heroi_id AND participacao_herois.missao_id = missao_id;
    END IF;
END;
$$ LANGUAGE plpgsql;
-------------------------------------------------------------------------------------------

CREATE TRIGGER trigger_update_hero_status
AFTER UPDATE OF resultado_missoes ON missoes
FOR EACH ROW
WHEN (NEW.resultado_missoes IS NOT NULL AND NEW.status_missoes = 'Concluída')
EXECUTE FUNCTION AtualizarStatusHeroiPostMissao();
-----------------------------------------------------------------------------------------------
-- Ativar Missão
CREATE OR REPLACE PROCEDURE AtivarMissao(
    p_MissaoID INT,
    p_DataInicio DATE,
    p_HeroiIDs INT[]
)
LANGUAGE plpgsql AS $$
BEGIN
    -- Atualiza a missão para o status 'Em andamento'
    UPDATE missoes
    SET data_inicio_missoes = p_DataInicio, status_missoes = 'Em andamento'
    WHERE missoes_id = p_MissaoID;

    -- Adiciona heróis à missão
    FOR i IN array_lower(p_HeroiIDs, 1)..array_upper(p_HeroiIDs, 1)
    LOOP
        INSERT INTO participacao_herois (heroi_id, missao_id)
        VALUES (p_HeroiIDs[i], p_MissaoID)
        ON CONFLICT DO NOTHING;
    END LOOP;
END;
$$;
---------------------------------------------------------------------------------------------
CREATE TRIGGER trigger_activate_mission
BEFORE INSERT OR UPDATE OF data_inicio_missoes ON missoes
FOR EACH ROW
WHEN (NEW.data_inicio_missoes IS NOT NULL AND NEW.status_missoes IS DISTINCT FROM 'Em andamento')
EXECUTE FUNCTION AtivarMissao();
----------------------------------------------------------------------------------------------
-- Finalizar Missão
CREATE OR REPLACE PROCEDURE FinalizarMissao(
    p_MissaoID INT,
    p_DataFim DATE,
    p_Resultado TEXT,
    p_StatusHeroi VARCHAR
)
LANGUAGE plpgsql AS $$
BEGIN
    -- Atualiza a missão para o status 'Concluída' e define o resultado
    UPDATE missoes
    SET data_termino_missoes = p_DataFim, status_missoes = 'Concluída', resultado_missoes = p_Resultado
    WHERE missoes_id = p_MissaoID;

    -- Atualiza o status dos heróis participantes com base no resultado da missão
    UPDATE heroi
    SET status = p_StatusHeroi
    WHERE id IN (SELECT heroi_id FROM participacao_herois WHERE missao_id = p_MissaoID);
END;
$$;
-------------------------------------------------------------------------------------------------
CREATE TRIGGER trigger_finalize_mission
BEFORE UPDATE OF data_termino_missoes ON missoes
FOR EACH ROW
WHEN (NEW.data_termino_missoes IS NOT NULL AND NEW.status_missoes IS DISTINCT FROM 'Concluída')
EXECUTE FUNCTION FinalizarMissao();
------------------------------------------------------------------------------------------------
-- Triggers adicionais
-- Verificar Disponibilidade de Recursos
CREATE OR REPLACE FUNCTION VerificarDisponibilidadeRecurso()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE recursos
    SET disponibilidade_recurso = 'Indisponível'
    WHERE recurso_id = NEW.recurso_id AND disponibilidade_recurso = 'Disponível';
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
---------------------------------------------------------------------------------------------
CREATE TRIGGER TriggerVerificarDisponibilidadeRecurso
AFTER INSERT ON alocacao_recursos
FOR EACH ROW
EXECUTE FUNCTION VerificarDisponibilidadeRecurso();

-- Notificar sobre a ativação de vilões	
CREATE OR REPLACE FUNCTION NotificarAtivacaoVilao() RETURNS TRIGGER AS $$
BEGIN
    RAISE NOTICE 'Atenção: Vilão % com ID % ativado em %', NEW.nome_viloes, NEW.viloes_id, CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
---------------------------------------------------------------------------------------------
CREATE TRIGGER TriggerNotificarAtivacaoVilao
AFTER UPDATE OF status_viloes ON viloes
FOR EACH ROW
WHEN (NEW.status_viloes = 'Ativo')
EXECUTE FUNCTION NotificarAtivacaoVilao();
-------------------------------------------------------------------------------------------------
-- Atualizar o Status do Vilão Após a Missão
CREATE OR REPLACE FUNCTION trigger_update_villain_status() RETURNS TRIGGER AS $$
BEGIN
    IF NEW.resultado_missoes = 'Sucesso' THEN
        PERFORM AtualizarStatusVilao(NEW.missoes_id, 'Capturado');
    ELSE
        PERFORM AtualizarStatusVilao(NEW.missoes_id, 'Fugitivo');
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
-------------------------------------------------------------------------------------------------
CREATE TRIGGER trigger_update_status_villain
AFTER UPDATE OF resultado_missoes ON missoes
FOR EACH ROW
EXECUTE FUNCTION trigger_update_villain_status();
-------------------------------------------------------------------------------------------------
