-- Tabela: bases
INSERT INTO bases (base_id, nome_base, localizacao_base, proposito_base, capacidade_base, status_base, comandante_base) VALUES
(7, 'Torre dos Vingadores', 'Nova York', 'Operações de Super-heróis', 100, 'Ativa', 'Nick Fury'),
(6, 'Quartel General da S.H.I.E.L.D.', 'Washington, D.C.', 'Inteligência e Defesa', 200, 'Ativa', 'Nick Fury'),
(5, 'Santuário de Kamar-Taj', 'Nepal', 'Treinamento Místico', 50, 'Ativa', 'Doutor Estranho')
ON CONFLICT (base_id) 
DO UPDATE SET 
    nome_base = EXCLUDED.nome_base,
    localizacao_base = EXCLUDED.localizacao_base,
    proposito_base = EXCLUDED.proposito_base,
    capacidade_base = EXCLUDED.capacidade_base,
    status_base = EXCLUDED.status_base,
    comandante_base = EXCLUDED.comandante_base;

-- Tabela: herois
INSERT INTO heroi (id, nome, poder, afiliacao, codinome, localizacao, status) VALUES
(1, 'Tony Stark', 'Tecnologia Avançada', 'Vingadores', 'Homem de Ferro', 'Nova York', 'Ativo'),
(2, 'Steve Rogers', 'Super-Força', 'Vingadores', 'Capitão América', 'Washington, D.C.', 'Ativo'),
(3, 'Natasha Romanoff', 'Habilidades de Espionagem', 'S.H.I.E.L.D.', 'Viúva Negra', 'Nova York', 'Ativo'),
(4, 'Thor', 'Deus do Trovão', 'Asgard', 'Thor', 'Asgard', 'Ativo'),
(5, 'Bruce Banner', 'Força Descomunal', 'Vingadores', 'Hulk', 'Nova York', 'Ativo')
ON CONFLICT (id)
DO UPDATE SET 
    nome = EXCLUDED.nome,
    poder = EXCLUDED.poder,
    afiliacao = EXCLUDED.afiliacao,
    codinome = EXCLUDED.codinome,
    localizacao = EXCLUDED.localizacao,
    status = EXCLUDED.status;

-- Tabela: recursos
INSERT INTO recursos (recurso_id, tipo_recurso, nome_recurso, disponibilidade_recurso, usuario_recurso, localizacao_recurso) VALUES
(1, 'Tecnológico', 'Armadura Mark 50', 'Disponível', 'Tony Stark', 'Nova York'),
(2, 'Arma', 'Escudo de Vibranium', 'Disponível', 'Steve Rogers', 'Washington, D.C.'),
(3, 'Veículo', 'Quinjet', 'Indisponível', 'Natasha Romanoff', 'Nova York'),
(4, 'Místico', 'Mjolnir', 'Disponível', 'Thor', 'Asgard'),
(5, 'Laboratório', 'Laboratório de Pesquisa', 'Disponível', 'Bruce Banner', 'Nova York')
ON CONFLICT (recurso_id)
DO UPDATE SET 
    tipo_recurso = EXCLUDED.tipo_recurso,
    nome_recurso = EXCLUDED.nome_recurso,
    disponibilidade_recurso = EXCLUDED.disponibilidade_recurso,
    usuario_recurso = EXCLUDED.usuario_recurso,
    localizacao_recurso = EXCLUDED.localizacao_recurso;

-- Tabela: eventos_hitoricos
INSERT INTO eventos_hitoricos (eventos_id, nome_eventos, data_eventos, local_eventos, tipo_eventos, participantes_eventos, impacto_eventos) VALUES
(1, 'Invasão Chitauri', '2012-05-04', 'Nova York', 'Batalha', 'Vingadores e Chitauri', 'Alto'),
(2, 'Ataque de Ultron', '2015-05-01', 'Sokovia', 'Batalha', 'Vingadores e Ultron', 'Alto'),
(3, 'Guerra Civil', '2016-04-12', 'Berlim', 'Conflito Interno', 'Vingadores', 'Médio')
ON CONFLICT (eventos_id)
DO UPDATE SET 
    nome_eventos = EXCLUDED.nome_eventos,
    data_eventos = EXCLUDED.data_eventos,
    local_eventos = EXCLUDED.local_eventos,
    tipo_eventos = EXCLUDED.tipo_eventos,
    participantes_eventos = EXCLUDED.participantes_eventos,
    impacto_eventos = EXCLUDED.impacto_eventos;

-- Tabela: viloes
INSERT INTO viloes (viloes_id, nome_viloes, titulos_viloes, poderes_viloes, motivacao_viloes, status_viloes, localizacao_viloes) VALUES
(1, 'Loki', 'Deus da Trapaça', 'Magia', 'Dominação', 'Capturado', 'Asgard'),
(2, 'Ultron', 'Inteligência Artificial', 'Tecnologia Avançada', 'Extinção Humana', 'Destruído', 'Desconhecido'),
(3, 'Thanos', 'Titã Louco', 'Força Incomparável', 'Equilíbrio do Universo', 'Fugitivo', 'Desconhecido')
ON CONFLICT (viloes_id)
DO UPDATE SET 
    nome_viloes = EXCLUDED.nome_viloes,
    titulos_viloes = EXCLUDED.titulos_viloes,
    poderes_viloes = EXCLUDED.poderes_viloes,
    motivacao_viloes = EXCLUDED.motivacao_viloes,
    status_viloes = EXCLUDED.status_viloes,
    localizacao_viloes = EXCLUDED.localizacao_viloes;



------------------------------------------------------------------------------------------------
-- Criação da função AtualizarStatusVilao
DROP FUNCTION atualizarstatusvilao(integer,text)

CREATE OR REPLACE FUNCTION AtualizarStatusVilao(id_missao INT, novo_status TEXT)
RETURNS VOID AS $$
BEGIN
    UPDATE viloes
    SET status_viloes = novo_status
    WHERE viloes.viloes_id IN (
        SELECT vilao_id 
        FROM envolvimento_vilao 
        WHERE missao_id = id_missao
    );
END;
$$ LANGUAGE plpgsql;

------------------------------------------------------------------------------------------------
-- Tabela: missoes
INSERT INTO missoes (missoes_id, nome_missoes, descricao_missoes, data_inicio_missoes, data_termino_missoes, status_missoes, herois_envolvido_missoes, resultado_missoes) VALUES
(1, 'Defesa de Nova York', 'Proteger o planeta da invasão Chitauri', '2012-05-04', '2012-05-04', 'Concluída', 'Tony Stark, Steve Rogers, Thor, Bruce Banner', 'Sucesso'),
(2, 'Confronto com Ultron', 'Derrotar a IA Ultron e salvar Sokovia', '2015-05-01', '2015-05-01', 'Concluída', 'Vingadores', 'Sucesso'),
(3, 'Guerra Civil', 'Resolver conflito entre os Vingadores', '2016-04-12', '2016-04-12', 'Concluída', 'Vingadores', 'Resultado Inconclusivo')
ON CONFLICT (missoes_id)
DO UPDATE SET 
    nome_missoes = EXCLUDED.nome_missoes,
    descricao_missoes = EXCLUDED.descricao_missoes,
    data_inicio_missoes = EXCLUDED.data_inicio_missoes,
    data_termino_missoes = EXCLUDED.data_termino_missoes,
    status_missoes = EXCLUDED.status_missoes,
    herois_envolvido_missoes = EXCLUDED.herois_envolvido_missoes,
    resultado_missoes = EXCLUDED.resultado_missoes;

-- Tabela: alocacao_recursos
INSERT INTO alocacao_recursos (alocacao_id, base_id, recurso_id) VALUES
(1, 7, 1),
(2, 6, 2),
(3, 5, 4)
ON CONFLICT (alocacao_id)
DO UPDATE SET 
    base_id = EXCLUDED.base_id,
    recurso_id = EXCLUDED.recurso_id;

-- Tabela: envolvimento_vilao
INSERT INTO envolvimento_vilao (envolvimento_id, vilao_id, missao_id) VALUES
(1, 1, 1),
(2, 2, 2),
(3, 3, 3)
ON CONFLICT (envolvimento_id)
DO UPDATE SET 
    vilao_id = EXCLUDED.vilao_id,
    missao_id = EXCLUDED.missao_id;

-- Tabela: participacao_eventos
INSERT INTO participacao_eventos (participacao_evento_id, heroi_id, vilao_id, evento_id) VALUES
(1, 1, 1, 1),
(2, 2, 2, 2),
(3, 3, 3, 3)
ON CONFLICT (participacao_evento_id)
DO UPDATE SET 
    heroi_id = EXCLUDED.heroi_id,
    vilao_id = EXCLUDED.vilao_id,
    evento_id = EXCLUDED.evento_id;

-- Tabela: participacao_herois
INSERT INTO participacao_herois (participacao_id, heroi_id, missao_id) VALUES
(1, 1, 1),
(2, 2, 2),
(3, 3, 3)
ON CONFLICT (participacao_id)
DO UPDATE SET 
    heroi_id = EXCLUDED.heroi_id,
    missao_id = EXCLUDED.missao_id;

-- Tabela: utilizacao_recursos
INSERT INTO utilizacao_recursos (utilizacao_id, heroi_id, recurso_id) VALUES
(1, 1, 1),
(2, 2, 2),
(3, 4, 4)
ON CONFLICT (utilizacao_id)
DO UPDATE SET 
    heroi_id = EXCLUDED.heroi_id,
    recurso_id = EXCLUDED.recurso_id;
