-- Extensión necesaria para búsqueda insensible a acentos (unaccent en queries JPQL vía FUNCTION())
CREATE EXTENSION IF NOT EXISTS unaccent;

-- Corrige la incoherencia entre la migración V4 (grupo nullable) y la entidad Java (nullable = false).
-- La columna siempre se rellena en lanzarAnalisis(); se fuerza el contrato a nivel de BD.
ALTER TABLE resultados_analisis ALTER COLUMN grupo SET NOT NULL;
