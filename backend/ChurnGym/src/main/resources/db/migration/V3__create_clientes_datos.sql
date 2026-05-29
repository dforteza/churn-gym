-- V3__create_clientes_datos.sql

-- Datos de analisis del cliente (entidad ClienteDatos)
CREATE TABLE clientes_datos (
    id                  BIGSERIAL     PRIMARY KEY,
    cliente_privado_id  BIGINT        NOT NULL UNIQUE REFERENCES clientes_privados(id),
    anyo_nacimiento     INTEGER,
    meses_como_socio    INTEGER,
    franja_horaria      VARCHAR(10)   NOT NULL CHECK (franja_horaria IN ('MANANA', 'TARDE', 'NOCHE')),
    deporte_principal   VARCHAR(20)   NOT NULL CHECK (deporte_principal IN ('MUSCULACION', 'CARDIO', 'CROSSFIT', 'CLASES_COLECTIVAS', 'MIXTO')),
    frecuencia_semanal  DOUBLE PRECISION,
    semanas_inactivo    INTEGER,
    tendencia_mensual   DOUBLE PRECISION
);
