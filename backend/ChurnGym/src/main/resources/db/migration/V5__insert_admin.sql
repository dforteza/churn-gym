-- V5__insert_admin.sql
-- Hash corresponde a: admin1234 (BCrypt coste 10)

INSERT INTO usuarios (username, email, password, rol, nombre_gimnasio, direccion1, direccion2, telefono, created_at, updated_at)
VALUES (
    'paco',
    'paco@churngym.dev',
    '$2a$10$sjlCpieBIMUkj3ei42oFae4dO5ouRKAZgfUwMvfxH3H8nrhUWLNyC',
    'ADMIN',
    'Juan de la Cierva Fitness',
    'Calle de la Caoba 1',
    'Madrid, 28005',
    '+34915064610',
    '2024-01-15 09:00:00',
    '2024-01-15 09:00:00'
);