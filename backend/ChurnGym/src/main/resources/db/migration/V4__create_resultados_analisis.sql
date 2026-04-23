-- V4__create_resultados_analisis.sql

-- Resultado vigente del ultimo analisis (entidad ResultadoAnalisis)
-- 1:1 con clientes_datos mediante cliente_datos_id
CREATE TABLE resultados_analisis (
    id                    BIGSERIAL    PRIMARY KEY,
    cliente_datos_id      BIGINT       NOT NULL UNIQUE REFERENCES clientes_datos(id),
    nivel_riesgo          VARCHAR(10)  NOT NULL CHECK (nivel_riesgo IN ('BAJO', 'MEDIO', 'ALTO')),
    probabilidad_abandono NUMERIC(5,2) NOT NULL,
    grupo                 VARCHAR(50),
    calculado_en          TIMESTAMP    NOT NULL
);

CREATE INDEX idx_resultados_analisis_nivel_riesgo ON resultados_analisis(nivel_riesgo);