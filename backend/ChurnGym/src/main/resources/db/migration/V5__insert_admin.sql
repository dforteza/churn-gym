-- V5__insert_admin.sql
-- Hash corresponde a: admin1234 (BCrypt coste 10)

INSERT INTO usuarios (username, email, password, rol)
VALUES (
    'admin',
    'admin@churngym.dev',
    '$2a$10$sjlCpieBIMUkj3ei42oFae4dO5ouRKAZgfUwMvfxH3H8nrhUWLNyC',
    'ADMIN'
);