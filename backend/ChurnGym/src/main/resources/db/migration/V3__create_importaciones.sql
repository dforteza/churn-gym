-- V3__create_importaciones_csv.sql
-- Registro de cada modelo importado por un usuario
-- Agrupa las visitas y métricas generadas en esa importación

CREATE TABLE importaciones (
    id                  BIGSERIAL       PRIMARY KEY,
    usuario_id          BIGINT          NOT NULL REFERENCES usuarios(id),
    nombre_descriptivo  VARCHAR(100)    NOT NULL,
    fecha_importacion   TIMESTAMP       NOT NULL DEFAULT NOW(),
    total_registros     INTEGER         NOT NULL DEFAULT 0,
    registros_ok        INTEGER         NOT NULL DEFAULT 0,
    registros_error     INTEGER         NOT NULL DEFAULT 0,
    estado              VARCHAR(20)     NOT NULL DEFAULT 'PROCESANDO'
                                        CHECK (estado IN ('PROCESANDO', 'COMPLETADA', 'ERROR'))
);

CREATE INDEX idx_importaciones_usuario ON importaciones(usuario_id);
