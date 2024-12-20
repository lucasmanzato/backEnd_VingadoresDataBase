PGDMP  3    1    
        
    |            VingadoresDB    16.2    16.2 q    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    41342    VingadoresDB    DATABASE     �   CREATE DATABASE "VingadoresDB" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Portuguese_Brazil.1252';
    DROP DATABASE "VingadoresDB";
                postgres    false            �            1255    41904 &   ativarmissao(integer, date, integer[]) 	   PROCEDURE     Z  CREATE PROCEDURE public.ativarmissao(IN p_missaoid integer, IN p_datainicio date, IN p_heroiids integer[])
    LANGUAGE plpgsql
    AS $$
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
 j   DROP PROCEDURE public.ativarmissao(IN p_missaoid integer, IN p_datainicio date, IN p_heroiids integer[]);
       public          postgres    false            �            1255    41903 -   atualizarstatusheroipostmissao(integer, text)    FUNCTION     �  CREATE FUNCTION public.atualizarstatusheroipostmissao(missao_id integer, resultado_missao text) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
    IF resultado_missao = 'Sucesso' THEN
        UPDATE heroi
        SET status = 'Inativo'
        FROM participacao_herois
        WHERE heroi.id = participacao_herois.heroi_id AND participacao_herois.missao_id = missao_id;
    END IF;
END;
$$;
 _   DROP FUNCTION public.atualizarstatusheroipostmissao(missao_id integer, resultado_missao text);
       public          postgres    false            �            1255    41918 #   atualizarstatusvilao(integer, text)    FUNCTION     G  CREATE FUNCTION public.atualizarstatusvilao(id_missao integer, novo_status text) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
    UPDATE viloes
    SET status_viloes = novo_status
    WHERE viloes.viloes_id IN (
        SELECT vilao_id 
        FROM envolvimento_vilao 
        WHERE missao_id = id_missao
    );
END;
$$;
 P   DROP FUNCTION public.atualizarstatusvilao(id_missao integer, novo_status text);
       public          postgres    false            �            1255    41906 7   finalizarmissao(integer, date, text, character varying) 	   PROCEDURE     �  CREATE PROCEDURE public.finalizarmissao(IN p_missaoid integer, IN p_datafim date, IN p_resultado text, IN p_statusheroi character varying)
    LANGUAGE plpgsql
    AS $$
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
 �   DROP PROCEDURE public.finalizarmissao(IN p_missaoid integer, IN p_datafim date, IN p_resultado text, IN p_statusheroi character varying);
       public          postgres    false            �            1255    41909    notificarativacaovilao()    FUNCTION     �   CREATE FUNCTION public.notificarativacaovilao() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    RAISE NOTICE 'Atenção: Vilão % com ID % ativado em %', NEW.nome_viloes, NEW.viloes_id, CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$;
 /   DROP FUNCTION public.notificarativacaovilao();
       public          postgres    false            �            1255    41911    trigger_update_villain_status()    FUNCTION     M  CREATE FUNCTION public.trigger_update_villain_status() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    IF NEW.resultado_missoes = 'Sucesso' THEN
        PERFORM AtualizarStatusVilao(NEW.missoes_id, 'Capturado');
    ELSE
        PERFORM AtualizarStatusVilao(NEW.missoes_id, 'Fugitivo');
    END IF;
    RETURN NEW;
END;
$$;
 6   DROP FUNCTION public.trigger_update_villain_status();
       public          postgres    false            �            1255    41907 !   verificardisponibilidaderecurso()    FUNCTION        CREATE FUNCTION public.verificardisponibilidaderecurso() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    UPDATE recursos
    SET disponibilidade_recurso = 'Indisponível'
    WHERE recurso_id = NEW.recurso_id AND disponibilidade_recurso = 'Disponível';
    RETURN NEW;
END;
$$;
 8   DROP FUNCTION public.verificardisponibilidaderecurso();
       public          postgres    false            �            1259    41709    alocacao_recursos    TABLE     y   CREATE TABLE public.alocacao_recursos (
    alocacao_id integer NOT NULL,
    base_id integer,
    recurso_id integer
);
 %   DROP TABLE public.alocacao_recursos;
       public         heap    postgres    false            �            1259    41695    alocacao_recursos_seq    SEQUENCE        CREATE SEQUENCE public.alocacao_recursos_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE public.alocacao_recursos_seq;
       public          postgres    false            �            1259    41714    atribuicao_base    TABLE     w   CREATE TABLE public.atribuicao_base (
    atribuicao_id integer NOT NULL,
    base_id integer,
    heroi_id integer
);
 #   DROP TABLE public.atribuicao_base;
       public         heap    postgres    false            �            1259    41696    atribuicao_base_seq    SEQUENCE     }   CREATE SEQUENCE public.atribuicao_base_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE public.atribuicao_base_seq;
       public          postgres    false            �            1259    41719    bases    TABLE     =  CREATE TABLE public.bases (
    base_id integer NOT NULL,
    capacidade_base double precision NOT NULL,
    comandante_base character varying(255),
    localizacao_base character varying(255),
    nome_base character varying(255),
    proposito_base character varying(255),
    status_base character varying(255)
);
    DROP TABLE public.bases;
       public         heap    postgres    false            �            1259    41697 	   bases_seq    SEQUENCE     s   CREATE SEQUENCE public.bases_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
     DROP SEQUENCE public.bases_seq;
       public          postgres    false            �            1259    41726    envolvimento_evento    TABLE     �   CREATE TABLE public.envolvimento_evento (
    envolvimento_evento_id integer NOT NULL,
    evento_id integer,
    vilao_id integer
);
 '   DROP TABLE public.envolvimento_evento;
       public         heap    postgres    false            �            1259    41698    envolvimento_evento_seq    SEQUENCE     �   CREATE SEQUENCE public.envolvimento_evento_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 .   DROP SEQUENCE public.envolvimento_evento_seq;
       public          postgres    false            �            1259    41731    envolvimento_vilao    TABLE     ~   CREATE TABLE public.envolvimento_vilao (
    envolvimento_id integer NOT NULL,
    missao_id integer,
    vilao_id integer
);
 &   DROP TABLE public.envolvimento_vilao;
       public         heap    postgres    false            �            1259    41699    envolvimento_vilao_seq    SEQUENCE     �   CREATE SEQUENCE public.envolvimento_vilao_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public.envolvimento_vilao_seq;
       public          postgres    false            �            1259    41736    eventos_hitoricos    TABLE     V  CREATE TABLE public.eventos_hitoricos (
    eventos_id integer NOT NULL,
    data_eventos timestamp(6) without time zone,
    impacto_eventos character varying(255),
    local_eventos character varying(255),
    nome_eventos character varying(255),
    participantes_eventos character varying(255),
    tipo_eventos character varying(255)
);
 %   DROP TABLE public.eventos_hitoricos;
       public         heap    postgres    false            �            1259    41700    eventos_hitoricos_seq    SEQUENCE        CREATE SEQUENCE public.eventos_hitoricos_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE public.eventos_hitoricos_seq;
       public          postgres    false            �            1259    41932 
   foto_heroi    TABLE     j   CREATE TABLE public.foto_heroi (
    id bigint NOT NULL,
    imagem oid NOT NULL,
    heroi_id integer
);
    DROP TABLE public.foto_heroi;
       public         heap    postgres    false            �            1259    41931    foto_heroi_id_seq    SEQUENCE     �   ALTER TABLE public.foto_heroi ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.foto_heroi_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    247            �            1259    41743    heroi    TABLE       CREATE TABLE public.heroi (
    id integer NOT NULL,
    afiliacao character varying(255),
    codinome character varying(255),
    localizacao character varying(255),
    nome character varying(255),
    poder character varying(255),
    status character varying(255)
);
    DROP TABLE public.heroi;
       public         heap    postgres    false            �            1259    41701 	   heroi_seq    SEQUENCE     s   CREATE SEQUENCE public.heroi_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
     DROP SEQUENCE public.heroi_seq;
       public          postgres    false            �            1259    41750    missoes    TABLE     �  CREATE TABLE public.missoes (
    missoes_id integer NOT NULL,
    data_inicio_missoes timestamp(6) without time zone,
    data_termino_missoes timestamp(6) without time zone,
    descricao_missoes character varying(255),
    herois_envolvido_missoes character varying(255),
    nome_missoes character varying(255),
    resultado_missoes character varying(255),
    status_missoes character varying(255)
);
    DROP TABLE public.missoes;
       public         heap    postgres    false            �            1259    41702    missoes_seq    SEQUENCE     u   CREATE SEQUENCE public.missoes_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 "   DROP SEQUENCE public.missoes_seq;
       public          postgres    false            �            1259    41757    participacao_eventos    TABLE     �   CREATE TABLE public.participacao_eventos (
    evento_id integer,
    heroi_id integer,
    participacao_evento_id integer NOT NULL,
    vilao_id integer
);
 (   DROP TABLE public.participacao_eventos;
       public         heap    postgres    false            �            1259    41703    participacao_eventos_seq    SEQUENCE     �   CREATE SEQUENCE public.participacao_eventos_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 /   DROP SEQUENCE public.participacao_eventos_seq;
       public          postgres    false            �            1259    41762    participacao_herois    TABLE        CREATE TABLE public.participacao_herois (
    heroi_id integer,
    missao_id integer,
    participacao_id integer NOT NULL
);
 '   DROP TABLE public.participacao_herois;
       public         heap    postgres    false            �            1259    41704    participacao_herois_seq    SEQUENCE     �   CREATE SEQUENCE public.participacao_herois_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 .   DROP SEQUENCE public.participacao_herois_seq;
       public          postgres    false            �            1259    41767    recursos    TABLE     $  CREATE TABLE public.recursos (
    recurso_id integer NOT NULL,
    disponibilidade_recurso character varying(255),
    localizacao_recurso character varying(255),
    nome_recurso character varying(255),
    tipo_recurso character varying(255),
    usuario_recurso character varying(255)
);
    DROP TABLE public.recursos;
       public         heap    postgres    false            �            1259    41705    recursos_seq    SEQUENCE     v   CREATE SEQUENCE public.recursos_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.recursos_seq;
       public          postgres    false            �            1259    41921    usuarios    TABLE     �   CREATE TABLE public.usuarios (
    id integer NOT NULL,
    username character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    role character varying(255)
);
    DROP TABLE public.usuarios;
       public         heap    postgres    false            �            1259    41920    usuarios_id_seq    SEQUENCE     �   CREATE SEQUENCE public.usuarios_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.usuarios_id_seq;
       public          postgres    false    245            �           0    0    usuarios_id_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public.usuarios_id_seq OWNED BY public.usuarios.id;
          public          postgres    false    244            �            1259    41706    usuarios_seq    SEQUENCE     v   CREATE SEQUENCE public.usuarios_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.usuarios_seq;
       public          postgres    false            �            1259    41783    utilizacao_recursos    TABLE     ~   CREATE TABLE public.utilizacao_recursos (
    heroi_id integer,
    recurso_id integer,
    utilizacao_id integer NOT NULL
);
 '   DROP TABLE public.utilizacao_recursos;
       public         heap    postgres    false            �            1259    41707    utilizacao_recursos_seq    SEQUENCE     �   CREATE SEQUENCE public.utilizacao_recursos_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 .   DROP SEQUENCE public.utilizacao_recursos_seq;
       public          postgres    false            �            1259    41788    viloes    TABLE     C  CREATE TABLE public.viloes (
    viloes_id integer NOT NULL,
    localizacao_viloes character varying(255),
    motivacao_viloes character varying(255),
    nome_viloes character varying(255),
    poderes_viloes character varying(255),
    status_viloes character varying(255),
    titulos_viloes character varying(255)
);
    DROP TABLE public.viloes;
       public         heap    postgres    false            �            1259    41708 
   viloes_seq    SEQUENCE     t   CREATE SEQUENCE public.viloes_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 !   DROP SEQUENCE public.viloes_seq;
       public          postgres    false            �            1259    41892    visaodetalhesmissoes    VIEW       CREATE VIEW public.visaodetalhesmissoes AS
 SELECT m.nome_missoes,
    m.descricao_missoes,
    m.data_inicio_missoes,
    m.data_termino_missoes,
    m.status_missoes,
    h.nome AS heroi,
    v.nome_viloes AS vilao
   FROM ((((public.missoes m
     LEFT JOIN public.participacao_herois ph ON ((m.missoes_id = ph.missao_id)))
     LEFT JOIN public.heroi h ON ((ph.heroi_id = h.id)))
     LEFT JOIN public.envolvimento_vilao ev ON ((m.missoes_id = ev.missao_id)))
     LEFT JOIN public.viloes v ON ((ev.vilao_id = v.viloes_id)));
 '   DROP VIEW public.visaodetalhesmissoes;
       public          postgres    false    235    236    236    241    238    236    238    236    236    236    233    233    235    241            �            1259    41897    visaorecursosheroismissoes    VIEW     �  CREATE VIEW public.visaorecursosheroismissoes AS
 SELECT h.nome AS heroi,
    r.nome_recurso,
    r.tipo_recurso,
    m.nome_missoes,
    m.descricao_missoes,
    m.status_missoes
   FROM ((((public.utilizacao_recursos ur
     JOIN public.heroi h ON ((ur.heroi_id = h.id)))
     JOIN public.recursos r ON ((ur.recurso_id = r.recurso_id)))
     JOIN public.participacao_herois ph ON ((h.id = ph.heroi_id)))
     JOIN public.missoes m ON ((ph.missao_id = m.missoes_id)));
 -   DROP VIEW public.visaorecursosheroismissoes;
       public          postgres    false    238    238    239    236    236    239    239    240    240    236    236    235    235            �           2604    41924    usuarios id    DEFAULT     j   ALTER TABLE ONLY public.usuarios ALTER COLUMN id SET DEFAULT nextval('public.usuarios_id_seq'::regclass);
 :   ALTER TABLE public.usuarios ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    245    244    245            {          0    41709    alocacao_recursos 
   TABLE DATA           M   COPY public.alocacao_recursos (alocacao_id, base_id, recurso_id) FROM stdin;
    public          postgres    false    229   �       |          0    41714    atribuicao_base 
   TABLE DATA           K   COPY public.atribuicao_base (atribuicao_id, base_id, heroi_id) FROM stdin;
    public          postgres    false    230   A�       }          0    41719    bases 
   TABLE DATA           �   COPY public.bases (base_id, capacidade_base, comandante_base, localizacao_base, nome_base, proposito_base, status_base) FROM stdin;
    public          postgres    false    231   ^�       ~          0    41726    envolvimento_evento 
   TABLE DATA           Z   COPY public.envolvimento_evento (envolvimento_evento_id, evento_id, vilao_id) FROM stdin;
    public          postgres    false    232   N�                 0    41731    envolvimento_vilao 
   TABLE DATA           R   COPY public.envolvimento_vilao (envolvimento_id, missao_id, vilao_id) FROM stdin;
    public          postgres    false    233   k�       �          0    41736    eventos_hitoricos 
   TABLE DATA           �   COPY public.eventos_hitoricos (eventos_id, data_eventos, impacto_eventos, local_eventos, nome_eventos, participantes_eventos, tipo_eventos) FROM stdin;
    public          postgres    false    234   ��       �          0    41932 
   foto_heroi 
   TABLE DATA           :   COPY public.foto_heroi (id, imagem, heroi_id) FROM stdin;
    public          postgres    false    247   U�       �          0    41743    heroi 
   TABLE DATA           Z   COPY public.heroi (id, afiliacao, codinome, localizacao, nome, poder, status) FROM stdin;
    public          postgres    false    235   r�       �          0    41750    missoes 
   TABLE DATA           �   COPY public.missoes (missoes_id, data_inicio_missoes, data_termino_missoes, descricao_missoes, herois_envolvido_missoes, nome_missoes, resultado_missoes, status_missoes) FROM stdin;
    public          postgres    false    236   a�       �          0    41757    participacao_eventos 
   TABLE DATA           e   COPY public.participacao_eventos (evento_id, heroi_id, participacao_evento_id, vilao_id) FROM stdin;
    public          postgres    false    237   x�       �          0    41762    participacao_herois 
   TABLE DATA           S   COPY public.participacao_herois (heroi_id, missao_id, participacao_id) FROM stdin;
    public          postgres    false    238   ��       �          0    41767    recursos 
   TABLE DATA           �   COPY public.recursos (recurso_id, disponibilidade_recurso, localizacao_recurso, nome_recurso, tipo_recurso, usuario_recurso) FROM stdin;
    public          postgres    false    239   ܞ       �          0    41921    usuarios 
   TABLE DATA           @   COPY public.usuarios (id, username, password, role) FROM stdin;
    public          postgres    false    245   Ɵ       �          0    41783    utilizacao_recursos 
   TABLE DATA           R   COPY public.utilizacao_recursos (heroi_id, recurso_id, utilizacao_id) FROM stdin;
    public          postgres    false    240   �       �          0    41788    viloes 
   TABLE DATA           �   COPY public.viloes (viloes_id, localizacao_viloes, motivacao_viloes, nome_viloes, poderes_viloes, status_viloes, titulos_viloes) FROM stdin;
    public          postgres    false    241   8�       �           0    0    alocacao_recursos_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public.alocacao_recursos_seq', 1, false);
          public          postgres    false    215            �           0    0    atribuicao_base_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.atribuicao_base_seq', 1, false);
          public          postgres    false    216            �           0    0 	   bases_seq    SEQUENCE SET     7   SELECT pg_catalog.setval('public.bases_seq', 1, true);
          public          postgres    false    217            �           0    0    envolvimento_evento_seq    SEQUENCE SET     F   SELECT pg_catalog.setval('public.envolvimento_evento_seq', 1, false);
          public          postgres    false    218            �           0    0    envolvimento_vilao_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.envolvimento_vilao_seq', 1, false);
          public          postgres    false    219            �           0    0    eventos_hitoricos_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public.eventos_hitoricos_seq', 1, false);
          public          postgres    false    220            �           0    0    foto_heroi_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.foto_heroi_id_seq', 1, false);
          public          postgres    false    246            �           0    0 	   heroi_seq    SEQUENCE SET     9   SELECT pg_catalog.setval('public.heroi_seq', 251, true);
          public          postgres    false    221            �           0    0    missoes_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.missoes_seq', 1, false);
          public          postgres    false    222            �           0    0    participacao_eventos_seq    SEQUENCE SET     G   SELECT pg_catalog.setval('public.participacao_eventos_seq', 1, false);
          public          postgres    false    223            �           0    0    participacao_herois_seq    SEQUENCE SET     F   SELECT pg_catalog.setval('public.participacao_herois_seq', 1, false);
          public          postgres    false    224            �           0    0    recursos_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.recursos_seq', 1, false);
          public          postgres    false    225            �           0    0    usuarios_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.usuarios_id_seq', 11, true);
          public          postgres    false    244            �           0    0    usuarios_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.usuarios_seq', 1, false);
          public          postgres    false    226            �           0    0    utilizacao_recursos_seq    SEQUENCE SET     F   SELECT pg_catalog.setval('public.utilizacao_recursos_seq', 1, false);
          public          postgres    false    227            �           0    0 
   viloes_seq    SEQUENCE SET     9   SELECT pg_catalog.setval('public.viloes_seq', 1, false);
          public          postgres    false    228            �           2606    41713 (   alocacao_recursos alocacao_recursos_pkey 
   CONSTRAINT     o   ALTER TABLE ONLY public.alocacao_recursos
    ADD CONSTRAINT alocacao_recursos_pkey PRIMARY KEY (alocacao_id);
 R   ALTER TABLE ONLY public.alocacao_recursos DROP CONSTRAINT alocacao_recursos_pkey;
       public            postgres    false    229            �           2606    41718 $   atribuicao_base atribuicao_base_pkey 
   CONSTRAINT     m   ALTER TABLE ONLY public.atribuicao_base
    ADD CONSTRAINT atribuicao_base_pkey PRIMARY KEY (atribuicao_id);
 N   ALTER TABLE ONLY public.atribuicao_base DROP CONSTRAINT atribuicao_base_pkey;
       public            postgres    false    230            �           2606    41725    bases bases_pkey 
   CONSTRAINT     S   ALTER TABLE ONLY public.bases
    ADD CONSTRAINT bases_pkey PRIMARY KEY (base_id);
 :   ALTER TABLE ONLY public.bases DROP CONSTRAINT bases_pkey;
       public            postgres    false    231            �           2606    41730 ,   envolvimento_evento envolvimento_evento_pkey 
   CONSTRAINT     ~   ALTER TABLE ONLY public.envolvimento_evento
    ADD CONSTRAINT envolvimento_evento_pkey PRIMARY KEY (envolvimento_evento_id);
 V   ALTER TABLE ONLY public.envolvimento_evento DROP CONSTRAINT envolvimento_evento_pkey;
       public            postgres    false    232            �           2606    41735 *   envolvimento_vilao envolvimento_vilao_pkey 
   CONSTRAINT     u   ALTER TABLE ONLY public.envolvimento_vilao
    ADD CONSTRAINT envolvimento_vilao_pkey PRIMARY KEY (envolvimento_id);
 T   ALTER TABLE ONLY public.envolvimento_vilao DROP CONSTRAINT envolvimento_vilao_pkey;
       public            postgres    false    233            �           2606    41742 (   eventos_hitoricos eventos_hitoricos_pkey 
   CONSTRAINT     n   ALTER TABLE ONLY public.eventos_hitoricos
    ADD CONSTRAINT eventos_hitoricos_pkey PRIMARY KEY (eventos_id);
 R   ALTER TABLE ONLY public.eventos_hitoricos DROP CONSTRAINT eventos_hitoricos_pkey;
       public            postgres    false    234            �           2606    41936    foto_heroi foto_heroi_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.foto_heroi
    ADD CONSTRAINT foto_heroi_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.foto_heroi DROP CONSTRAINT foto_heroi_pkey;
       public            postgres    false    247            �           2606    41749    heroi heroi_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.heroi
    ADD CONSTRAINT heroi_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.heroi DROP CONSTRAINT heroi_pkey;
       public            postgres    false    235            �           2606    41756    missoes missoes_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.missoes
    ADD CONSTRAINT missoes_pkey PRIMARY KEY (missoes_id);
 >   ALTER TABLE ONLY public.missoes DROP CONSTRAINT missoes_pkey;
       public            postgres    false    236            �           2606    41761 .   participacao_eventos participacao_eventos_pkey 
   CONSTRAINT     �   ALTER TABLE ONLY public.participacao_eventos
    ADD CONSTRAINT participacao_eventos_pkey PRIMARY KEY (participacao_evento_id);
 X   ALTER TABLE ONLY public.participacao_eventos DROP CONSTRAINT participacao_eventos_pkey;
       public            postgres    false    237            �           2606    41766 ,   participacao_herois participacao_herois_pkey 
   CONSTRAINT     w   ALTER TABLE ONLY public.participacao_herois
    ADD CONSTRAINT participacao_herois_pkey PRIMARY KEY (participacao_id);
 V   ALTER TABLE ONLY public.participacao_herois DROP CONSTRAINT participacao_herois_pkey;
       public            postgres    false    238            �           2606    41773    recursos recursos_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.recursos
    ADD CONSTRAINT recursos_pkey PRIMARY KEY (recurso_id);
 @   ALTER TABLE ONLY public.recursos DROP CONSTRAINT recursos_pkey;
       public            postgres    false    239            �           2606    41938 %   foto_heroi uklnyopave46d7fc8s5motc5jr 
   CONSTRAINT     d   ALTER TABLE ONLY public.foto_heroi
    ADD CONSTRAINT uklnyopave46d7fc8s5motc5jr UNIQUE (heroi_id);
 O   ALTER TABLE ONLY public.foto_heroi DROP CONSTRAINT uklnyopave46d7fc8s5motc5jr;
       public            postgres    false    247            �           2606    41928    usuarios usuarios_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.usuarios
    ADD CONSTRAINT usuarios_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.usuarios DROP CONSTRAINT usuarios_pkey;
       public            postgres    false    245            �           2606    41930    usuarios usuarios_username_key 
   CONSTRAINT     ]   ALTER TABLE ONLY public.usuarios
    ADD CONSTRAINT usuarios_username_key UNIQUE (username);
 H   ALTER TABLE ONLY public.usuarios DROP CONSTRAINT usuarios_username_key;
       public            postgres    false    245            �           2606    41787 ,   utilizacao_recursos utilizacao_recursos_pkey 
   CONSTRAINT     u   ALTER TABLE ONLY public.utilizacao_recursos
    ADD CONSTRAINT utilizacao_recursos_pkey PRIMARY KEY (utilizacao_id);
 V   ALTER TABLE ONLY public.utilizacao_recursos DROP CONSTRAINT utilizacao_recursos_pkey;
       public            postgres    false    240            �           2606    41794    viloes viloes_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.viloes
    ADD CONSTRAINT viloes_pkey PRIMARY KEY (viloes_id);
 <   ALTER TABLE ONLY public.viloes DROP CONSTRAINT viloes_pkey;
       public            postgres    false    241            �           2620    41912 %   missoes trigger_update_status_villain    TRIGGER     �   CREATE TRIGGER trigger_update_status_villain AFTER UPDATE OF resultado_missoes ON public.missoes FOR EACH ROW EXECUTE FUNCTION public.trigger_update_villain_status();
 >   DROP TRIGGER trigger_update_status_villain ON public.missoes;
       public          postgres    false    236    236    254            �           2620    41910 $   viloes triggernotificarativacaovilao    TRIGGER     �   CREATE TRIGGER triggernotificarativacaovilao AFTER UPDATE OF status_viloes ON public.viloes FOR EACH ROW WHEN (((new.status_viloes)::text = 'Ativo'::text)) EXECUTE FUNCTION public.notificarativacaovilao();
 =   DROP TRIGGER triggernotificarativacaovilao ON public.viloes;
       public          postgres    false    253    241    241    241            �           2620    41908 8   alocacao_recursos triggerverificardisponibilidaderecurso    TRIGGER     �   CREATE TRIGGER triggerverificardisponibilidaderecurso AFTER INSERT ON public.alocacao_recursos FOR EACH ROW EXECUTE FUNCTION public.verificardisponibilidaderecurso();
 Q   DROP TRIGGER triggerverificardisponibilidaderecurso ON public.alocacao_recursos;
       public          postgres    false    252    229            �           2606    41815 /   envolvimento_evento fk23sy09fct09kx22ky89r9ljmg    FK CONSTRAINT     �   ALTER TABLE ONLY public.envolvimento_evento
    ADD CONSTRAINT fk23sy09fct09kx22ky89r9ljmg FOREIGN KEY (evento_id) REFERENCES public.eventos_hitoricos(eventos_id);
 Y   ALTER TABLE ONLY public.envolvimento_evento DROP CONSTRAINT fk23sy09fct09kx22ky89r9ljmg;
       public          postgres    false    232    234    4786            �           2606    41860 /   utilizacao_recursos fk2bk9joryybeis1851tqm8ursi    FK CONSTRAINT     �   ALTER TABLE ONLY public.utilizacao_recursos
    ADD CONSTRAINT fk2bk9joryybeis1851tqm8ursi FOREIGN KEY (heroi_id) REFERENCES public.heroi(id);
 Y   ALTER TABLE ONLY public.utilizacao_recursos DROP CONSTRAINT fk2bk9joryybeis1851tqm8ursi;
       public          postgres    false    240    235    4788            �           2606    41830 .   envolvimento_vilao fkbkp7628qpgnglv8crau2vwnt6    FK CONSTRAINT     �   ALTER TABLE ONLY public.envolvimento_vilao
    ADD CONSTRAINT fkbkp7628qpgnglv8crau2vwnt6 FOREIGN KEY (vilao_id) REFERENCES public.viloes(viloes_id);
 X   ALTER TABLE ONLY public.envolvimento_vilao DROP CONSTRAINT fkbkp7628qpgnglv8crau2vwnt6;
       public          postgres    false    4800    233    241            �           2606    41800 -   alocacao_recursos fkcd7au5rqipjiwcscblu435j1q    FK CONSTRAINT     �   ALTER TABLE ONLY public.alocacao_recursos
    ADD CONSTRAINT fkcd7au5rqipjiwcscblu435j1q FOREIGN KEY (recurso_id) REFERENCES public.recursos(recurso_id);
 W   ALTER TABLE ONLY public.alocacao_recursos DROP CONSTRAINT fkcd7au5rqipjiwcscblu435j1q;
       public          postgres    false    4796    229    239            �           2606    41840 0   participacao_eventos fkcu4t1ugsy7v9f2sqtdw067dxu    FK CONSTRAINT     �   ALTER TABLE ONLY public.participacao_eventos
    ADD CONSTRAINT fkcu4t1ugsy7v9f2sqtdw067dxu FOREIGN KEY (heroi_id) REFERENCES public.heroi(id);
 Z   ALTER TABLE ONLY public.participacao_eventos DROP CONSTRAINT fkcu4t1ugsy7v9f2sqtdw067dxu;
       public          postgres    false    235    4788    237            �           2606    41825 .   envolvimento_vilao fkdn82gwe7nm6egegal2v0kkyhh    FK CONSTRAINT     �   ALTER TABLE ONLY public.envolvimento_vilao
    ADD CONSTRAINT fkdn82gwe7nm6egegal2v0kkyhh FOREIGN KEY (missao_id) REFERENCES public.missoes(missoes_id);
 X   ALTER TABLE ONLY public.envolvimento_vilao DROP CONSTRAINT fkdn82gwe7nm6egegal2v0kkyhh;
       public          postgres    false    236    4790    233            �           2606    41939 &   foto_heroi fke9825fe9sfs0tulcrg2emxgod    FK CONSTRAINT     �   ALTER TABLE ONLY public.foto_heroi
    ADD CONSTRAINT fke9825fe9sfs0tulcrg2emxgod FOREIGN KEY (heroi_id) REFERENCES public.heroi(id);
 P   ALTER TABLE ONLY public.foto_heroi DROP CONSTRAINT fke9825fe9sfs0tulcrg2emxgod;
       public          postgres    false    235    247    4788            �           2606    41810 +   atribuicao_base fkexmygfvbq6kctusojhkks1p65    FK CONSTRAINT     �   ALTER TABLE ONLY public.atribuicao_base
    ADD CONSTRAINT fkexmygfvbq6kctusojhkks1p65 FOREIGN KEY (heroi_id) REFERENCES public.heroi(id);
 U   ALTER TABLE ONLY public.atribuicao_base DROP CONSTRAINT fkexmygfvbq6kctusojhkks1p65;
       public          postgres    false    4788    230    235            �           2606    41845 0   participacao_eventos fkgqilyj1tmbeg8t4vy34aycav1    FK CONSTRAINT     �   ALTER TABLE ONLY public.participacao_eventos
    ADD CONSTRAINT fkgqilyj1tmbeg8t4vy34aycav1 FOREIGN KEY (vilao_id) REFERENCES public.viloes(viloes_id);
 Z   ALTER TABLE ONLY public.participacao_eventos DROP CONSTRAINT fkgqilyj1tmbeg8t4vy34aycav1;
       public          postgres    false    4800    241    237            �           2606    41855 /   participacao_herois fkgtx060ufpl9jv01m54uyy7ovb    FK CONSTRAINT     �   ALTER TABLE ONLY public.participacao_herois
    ADD CONSTRAINT fkgtx060ufpl9jv01m54uyy7ovb FOREIGN KEY (missao_id) REFERENCES public.missoes(missoes_id);
 Y   ALTER TABLE ONLY public.participacao_herois DROP CONSTRAINT fkgtx060ufpl9jv01m54uyy7ovb;
       public          postgres    false    236    4790    238            �           2606    41820 /   envolvimento_evento fkjc24knvcq8gt6r8ookaip8arg    FK CONSTRAINT     �   ALTER TABLE ONLY public.envolvimento_evento
    ADD CONSTRAINT fkjc24knvcq8gt6r8ookaip8arg FOREIGN KEY (vilao_id) REFERENCES public.viloes(viloes_id);
 Y   ALTER TABLE ONLY public.envolvimento_evento DROP CONSTRAINT fkjc24knvcq8gt6r8ookaip8arg;
       public          postgres    false    241    4800    232            �           2606    41865 /   utilizacao_recursos fkko2pc5qnux5l484mqm9etuohd    FK CONSTRAINT     �   ALTER TABLE ONLY public.utilizacao_recursos
    ADD CONSTRAINT fkko2pc5qnux5l484mqm9etuohd FOREIGN KEY (recurso_id) REFERENCES public.recursos(recurso_id);
 Y   ALTER TABLE ONLY public.utilizacao_recursos DROP CONSTRAINT fkko2pc5qnux5l484mqm9etuohd;
       public          postgres    false    4796    239    240            �           2606    41795 -   alocacao_recursos fkpk047srnrpqhxy6xq6ogabbye    FK CONSTRAINT     �   ALTER TABLE ONLY public.alocacao_recursos
    ADD CONSTRAINT fkpk047srnrpqhxy6xq6ogabbye FOREIGN KEY (base_id) REFERENCES public.bases(base_id);
 W   ALTER TABLE ONLY public.alocacao_recursos DROP CONSTRAINT fkpk047srnrpqhxy6xq6ogabbye;
       public          postgres    false    229    4780    231            �           2606    41835 0   participacao_eventos fkrk31x2snp4mn6c9lbhlcny6nq    FK CONSTRAINT     �   ALTER TABLE ONLY public.participacao_eventos
    ADD CONSTRAINT fkrk31x2snp4mn6c9lbhlcny6nq FOREIGN KEY (evento_id) REFERENCES public.eventos_hitoricos(eventos_id);
 Z   ALTER TABLE ONLY public.participacao_eventos DROP CONSTRAINT fkrk31x2snp4mn6c9lbhlcny6nq;
       public          postgres    false    4786    237    234            �           2606    41850 /   participacao_herois fks12j4c7hjgarulg54uqxf1ds2    FK CONSTRAINT     �   ALTER TABLE ONLY public.participacao_herois
    ADD CONSTRAINT fks12j4c7hjgarulg54uqxf1ds2 FOREIGN KEY (heroi_id) REFERENCES public.heroi(id);
 Y   ALTER TABLE ONLY public.participacao_herois DROP CONSTRAINT fks12j4c7hjgarulg54uqxf1ds2;
       public          postgres    false    238    235    4788            �           2606    41805 +   atribuicao_base fks5wqkydspssjh4bwx82qsw1d4    FK CONSTRAINT     �   ALTER TABLE ONLY public.atribuicao_base
    ADD CONSTRAINT fks5wqkydspssjh4bwx82qsw1d4 FOREIGN KEY (base_id) REFERENCES public.bases(base_id);
 U   ALTER TABLE ONLY public.atribuicao_base DROP CONSTRAINT fks5wqkydspssjh4bwx82qsw1d4;
       public          postgres    false    230    231    4780            {      x�3�4�4�2�4�4�2�4�4����� "v�      |      x������ � �      }   �   x���AJAE�5���f��kqb�̠�)f*�6��P��q�W�	�b�������7�q�Ci�5�E���κUw���5�I�8e�J=����7331��00s�kW��5����%{��`w�]��?z);¹�jQel��렣V�=<oE�H?�e��Gk���H�:i�%\�PH�8�A�u%o���\��V��hC:��je�h�.>�/l#G��dY�~�v^      ~      x������ � �            x�3�4�4�2�B.cN ����� !��      �   �   x�]�1�0����� �Tqpà�����%Ty���R�����b
Ѡ&��~�b�*֑J"��J��AjÖ;�G�5�æ���+
�z����M#�T3h+z@������B��Y���������o�z�z�6��$��xKWX��{�9ud�0�ٝ-���;���	�kK�      �      x������ � �      �   �   x�U�Aj�0���:@"h��8���EmZ
�L��"i�H�<Y�^�[_�
x��>������`ѰP��=yeHmI������/�'�E�>�u��a�F�P'��z���8�4]Y�~��Gx�x,��a����'ʤ^ؒD�/#�r�R��[A�[��7z�]����
�u�0��1�� -~��3h(�~���q@K~f�����pd����V�p.���]U�dyfu      �     x�m�Aj�0E��)� v�ݦ��J6��i���`OaUSF���麧��:]�$ �����c*S�U]������|8��ڋp�=	0|9�z���Cs�G�f��ڈ2�zQ"ذ�B�KK;�%zObV���AϜ�Y��<6�΍���z"Z���其"QN@X?«����b�'����=�,�������!�Nu�����gچ����4�YM&�����=�F�Fhl�nr�.�־;��f$7Y�����.      �      x�3�4A.#N0�2�C�=... 4#p      �   )   x�3�4�4�2�4�4�2�4�4�2��E��"�\1z\\\ g$�      �   �   x�u�AN�0E��S� U���,�H�*Bb3u���d�v$�U���e	�_���oa�×p�;j`#��,��*)��b7�#���k��N��n���	���s�gv]<�\��Vd����>�#��:�oR�3�'���xM��aO�w��`6ph��r<����2Ԩ�gi�+��q4>���?g�� �1_Տ�/��8	`�ɑ]!3��,�1��5s�      �   5   x�3�LL�����FƜ�.��~\���ũE� ��`� .CC��B$F��� ���      �      x�3�4�4�2�B.NNc�=... !��      �   �   x�]�1n1E��)|�$'XAP��[�4�m�Q�3����:�	�S�bq��t_���^�K�����Xo�*��/����)E/�
%Y��W���U�	������3��o?ʈ���Y���%J��nƆ����p��zg�Kh�#���?�w�X%�^�i���$X�6�ݰ�qB��9DX��2��\�v+ŉ�\c~ �]�     