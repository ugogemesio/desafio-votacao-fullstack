CREATE TYPE pauta_status AS ENUM ('CRIADA', 'DEFINIDA');
CREATE TYPE sessao_status AS ENUM ('ABERTA', 'CONTANDO', 'ENCERRADA', 'FINALIZADA');
CREATE TYPE voto_opcao AS ENUM ('NAO', 'SIM');
CREATE TYPE resultado_status AS ENUM ('APROVADO', 'REJEITADO', 'EMPATE');

CREATE TABLE associado (
    id BIGSERIAL PRIMARY KEY,
    cpf VARCHAR(11) NOT NULL,
    nome VARCHAR(255)
);

CREATE TABLE pauta (
    id BIGSERIAL PRIMARY KEY,
    data_criacao TIMESTAMP NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    titulo VARCHAR(255) NOT NULL,
    sim BIGINT,
    nao BIGINT,
    resultado_status resultado_status,
    status pauta_status NOT NULL
);

CREATE TABLE sessao (
    id BIGSERIAL PRIMARY KEY,
    abertura TIMESTAMP NOT NULL,
    duracao_minutos INTEGER NOT NULL,
    fechamento TIMESTAMP NOT NULL,
    status sessao_status NOT NULL,
    pauta_id BIGINT NOT NULL
);

CREATE TABLE voto (
    id BIGSERIAL PRIMARY KEY,
    opcao voto_opcao NOT NULL,
    id_associado BIGINT NOT NULL,
    id_sessao BIGINT NOT NULL
);

ALTER TABLE associado ADD CONSTRAINT uk_associado_cpf UNIQUE (cpf);
ALTER TABLE sessao ADD CONSTRAINT uk_sessao_pauta UNIQUE (pauta_id);
ALTER TABLE voto ADD CONSTRAINT uk_voto_sessao_associado UNIQUE (id_sessao, id_associado);

ALTER TABLE sessao
    ADD CONSTRAINT fk_sessao_pauta 
    FOREIGN KEY (pauta_id) REFERENCES pauta(id);

ALTER TABLE voto 
    ADD CONSTRAINT fk_voto_associado 
    FOREIGN KEY (id_associado) REFERENCES associado(id);

ALTER TABLE voto 
    ADD CONSTRAINT fk_voto_sessao 
    FOREIGN KEY (id_sessao) REFERENCES sessao(id);