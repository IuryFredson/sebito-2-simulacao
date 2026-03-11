CREATE TABLE marcos (
                        id BIGSERIAL PRIMARY KEY,
                        nome VARCHAR(150) NOT NULL,
                        obrigatorio BOOLEAN NOT NULL,
                        concluido BOOLEAN NOT NULL DEFAULT FALSE,
                        data_limite DATE NOT NULL,
                        data_conclusao DATE,
                        projeto_id BIGINT NOT NULL,
                        CONSTRAINT fk_marcos_projeto
                            FOREIGN KEY (projeto_id)
                                REFERENCES projetos (id)
                                ON DELETE CASCADE
);