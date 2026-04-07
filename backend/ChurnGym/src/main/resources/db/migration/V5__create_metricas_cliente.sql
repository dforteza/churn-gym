-- V5__create_metricas_cliente.sql
-- Resultado del análisis de churn por cliente y por importación
-- nivel_riesgo es el output del motor de reglas

CREATE TABLE metricas_clientes (
    id                  BIGSERIAL       PRIMARY KEY,
    cliente_id          BIGINT          NOT NULL REFERENCES clientes(id),
    importacion_id      BIGINT          NOT NULL REFERENCES importaciones(id),
    frecuencia_semanal  NUMERIC(5,2)    NOT NULL,
    semanas_inactivo    INTEGER         NOT NULL,
    tendencia_mensual   NUMERIC(6,2)    NOT NULL,
    nivel_riesgo        VARCHAR(10)     NOT NULL
                                        CHECK (nivel_riesgo IN ('BAJO', 'MEDIO', 'ALTO')),
    calculada_en         TIMESTAMP       NOT NULL DEFAULT NOW(),
    -- Un cliente solo tiene una métrica por importación
    CONSTRAINT uq_metrica_cliente_importacion UNIQUE (cliente_id, importacion_id)
);

CREATE INDEX idx_metricas_cliente     ON metricas_clientes(cliente_id);
CREATE INDEX idx_metricas_importacion ON metricas_clientes(importacion_id);
CREATE INDEX idx_metricas_riesgo      ON metricas_clientes(nivel_riesgo);
