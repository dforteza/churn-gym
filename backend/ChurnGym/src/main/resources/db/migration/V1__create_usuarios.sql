-- V1__create_usuarios.sql
-- Tabla de usuarios del sistema (administradores del gimnasio)

CREATE TABLE usuarios (
    id                BIGSERIAL       PRIMARY KEY,
    username          VARCHAR(50)     NOT NULL UNIQUE,
    email             VARCHAR(100)    NOT NULL UNIQUE,
    password_hash     VARCHAR(255)    NOT NULL,
    rol               VARCHAR(20)     NOT NULL DEFAULT 'USUARIO'
                                      CHECK (rol IN ('ADMIN', 'USUARIO'))
);
