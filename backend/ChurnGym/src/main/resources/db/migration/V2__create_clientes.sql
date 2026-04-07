-- V2__create_clientes.sql
-- Tabla de clientes del gimnasio
-- Un cliente puede aparecer en múltiples modelos/importaciones

CREATE TABLE clientes (
    id                BIGSERIAL       PRIMARY KEY,
    nombre            VARCHAR(100)    NOT NULL,
    apellidos         VARCHAR(150)    NOT NULL,
    email             VARCHAR(100)    NOT NULL UNIQUE,
    fecha_nacimiento  DATE,
    fecha_alta        DATE            NOT NULL,
    activo            BOOLEAN         NOT NULL DEFAULT TRUE
);
