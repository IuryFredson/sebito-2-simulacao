CREATE TABLE projetos (
                          id BIGSERIAL PRIMARY KEY,
                          titulo VARCHAR(200) NOT NULL,
                          coordenador VARCHAR(150) NOT NULL,
                          data_inicio DATE NOT NULL,
                          data_fim DATE NOT NULL,
                          orcamento_previsto NUMERIC(14,2) NOT NULL,
                          orcamento_executado NUMERIC(14,2) NOT NULL DEFAULT 0,
                          status VARCHAR(40) NOT NULL
);