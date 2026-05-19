-- V5__insert_admin.sql
-- Hash corresponde a: admin1234 (BCrypt coste 10)

INSERT INTO usuarios (username, email, password, rol, nombre_gimnasio, direccion1, direccion2, telefono, created_at, updated_at)
VALUES (
    'admin',
    'admin@churngym.dev',
    '$2a$10$sjlCpieBIMUkj3ei42oFae4dO5ouRKAZgfUwMvfxH3H8nrhUWLNyC',
    'ADMIN',
    'Churn Gym Madrid Centro',
    'Calle Gran Vía, 45',
    'Madrid, 28013',
    '+34 910 123 456',
    '2024-01-15 09:00:00',
    '2024-01-15 09:00:00'
);