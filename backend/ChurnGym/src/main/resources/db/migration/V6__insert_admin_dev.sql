-- V6__insert_admin_dev.sql
-- Usuario administrador inicial para desarrollo y pruebas
-- IMPORTANTE: cambiar password_hash en producción
-- Hash corresponde a: admin1234 (BCrypt, coste 10)

INSERT INTO usuarios (username, email, password_hash, rol)
VALUES (
    'admin',
    'admin@churngym.dev',
    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
    'ADMIN'
);
