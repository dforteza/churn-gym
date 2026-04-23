-- V2__create_clientes_privados.sql

-- Datos sensibles del cliente (entidad ClientePrivado)
CREATE TABLE clientes_privados (
    id          BIGSERIAL    PRIMARY KEY,
    nombre      VARCHAR(100) NOT NULL,
    apellidos   VARCHAR(150) NOT NULL,
    dni         VARCHAR(9)   NOT NULL UNIQUE,
    gmail       VARCHAR(100) NOT NULL UNIQUE
);