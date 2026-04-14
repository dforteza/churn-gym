-- V4__create_visitas.sql
-- Registro de cada visita de un cliente al gimnasio
-- Cada visita pertenece a un cliente y a una importación concreta

CREATE TABLE visitas (
    id                BIGSERIAL       PRIMARY KEY,
    cliente_id        BIGINT          NOT NULL REFERENCES clientes(id),
    importacion_id    BIGINT          NOT NULL REFERENCES importaciones(id),
    fecha_visita      DATE            NOT NULL,
    duracion_minutos  INTEGER         NOT NULL
);

CREATE INDEX idx_visitas_cliente     ON visitas(cliente_id);
CREATE INDEX idx_visitas_importacion ON visitas(importacion_id);
CREATE INDEX idx_visitas_fecha       ON visitas(fecha_visita);
