# Churn Gym Detector

Sistema web para la detección temprana de abandono (churn) en clientes de gimnasios mediante análisis de actividad y segmentación automática por grupos de riesgo.

## Autores

| Nombre | Rol |
|---|---|
| Diego Forteza Benito | Backend — Spring Boot, API REST, BD |
| Javier Escudero García | Frontend — Vanilla JS / HTML / CSS |

## Stack tecnológico

- **Backend**: Java 21 + Spring Boot 3.5 + PostgreSQL 16
- **Frontend**: Vanilla JS / HTML5 / CSS3 — servido por nginx
- **Infraestructura**: Docker + Docker Compose
- **Seguridad**: JWT (HS512) + Spring Security
- **ORM / Migraciones**: Spring Data JPA + Flyway
- **Mapeo**: MapStruct · Lombok

## Arrancar el proyecto

**Primera vez o tras borrar volúmenes** (BD limpia desde cero):
```bash
docker compose down -v && docker compose up --build
```

**Reinicios normales** (conserva los datos):
```bash
docker compose up --build
```

Una vez arrancado:

| Servicio | URL |
|---|---|
| Frontend | http://localhost:3000 |
| Backend API | http://localhost:8080/api |
| Swagger UI | http://localhost:8080/swagger-ui/index.html |
| Health check | http://localhost:8080/actuator/health |

## Credenciales de prueba

| Campo | Valor |
|---|---|
| Usuario | `diego` |
| Contraseña | `admin1234` |

## Endpoints principales

| Método | Endpoint | Descripción |
|---|---|---|
| POST | `/api/v1/auth/login` | Obtener token JWT |
| POST | `/api/analisis/lanzar` | Ejecutar análisis de churn |
| GET | `/api/analisis/vigente` | Dashboard — resultados paginados con filtros |
| GET | `/api/analisis/cliente/{id}` | Detalle de un cliente |
| GET | `/api/v1/perfil` | Perfil del gimnasio |
| PUT | `/api/v1/perfil` | Actualizar perfil del gimnasio |
| GET | `/actuator/health` | Estado del servicio |

Documentación completa en `docs/postman/guia-api.md` y en Swagger UI.

## Estructura del repositorio

```
backend/     → API REST Spring Boot
frontend/    → Interfaz web (nginx en Docker)
docs/        → Diagramas, guías y documentación
```

## Estado del proyecto

En desarrollo — TFG DAM 2025/2026
