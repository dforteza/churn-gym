-- V1__create_usuarios.sql

CREATE EXTENSION IF NOT EXISTS unaccent;

CREATE TABLE usuarios (
    id               BIGSERIAL     PRIMARY KEY,
    username         VARCHAR(50)   NOT NULL UNIQUE,
    email            VARCHAR(100)  NOT NULL UNIQUE,
    password         VARCHAR(255)  NOT NULL,
    rol              VARCHAR(20)   NOT NULL CHECK (rol IN ('ADMIN', 'USUARIO')),
    nombre_gimnasio  VARCHAR(150),
    direccion1       VARCHAR(200),
    direccion2       VARCHAR(100),
    telefono         VARCHAR(20),
    created_at       TIMESTAMP     DEFAULT NOW(),
    updated_at       TIMESTAMP     DEFAULT NOW()
);
