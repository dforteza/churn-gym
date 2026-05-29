# ChurnGym

![Java](https://img.shields.io/badge/Java-21-007396?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5-6DB33F?logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-336791?logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?logo=docker&logoColor=white)

Sistema web para la detección temprana de abandono (**churn**) en socios de gimnasio. Analiza frecuencia de visitas, tendencia de actividad y antigüedad para calcular un nivel de riesgo individual y segmentar automáticamente a cada socio en uno de 7 grupos de comportamiento.

![Demo](docs/demo.gif)

---

## Autores

| Nombre | Rol |
|---|---|
| Diego Forteza Benito | Backend - Spring Boot, API REST, base de datos |
| Javier Escudero García | Frontend - JavaScript, HTML, CSS |

---

## Características

- **Motor de riesgo** basado en función sigmoid calibrada con el algoritmo Nelder-Mead
- **7 grupos de segmentación** deterministas por comportamiento (CONSOLIDADO_EN_RIESGO, NUEVO_ENGANCHADO, VETERANO_EN_PAUSA…)
- **Dashboard interactivo** con filtros por nivel de riesgo y búsqueda por nombre
- **Autenticación JWT** (HS512) con Spring Security
- **API REST documentada** en Swagger UI y colección Postman incluida
- **Dockerizado al 100%** - un solo comando para levantar toda la aplicación

---

## Requisitos

- [Docker Desktop](https://www.docker.com/products/docker-desktop/) instalado y en ejecución

---

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

---

## Credenciales de prueba

| Campo | Valor |
|---|---|
| Usuario | `diego` |
| Contraseña | `admin1234` |

---

## Endpoints principales

| Método | Endpoint | Descripción |
|---|---|---|
| POST | `/api/v1/auth/login` | Obtener token JWT |
| POST | `/api/v1/analisis/lanzar` | Ejecutar análisis de churn |
| GET | `/api/v1/analisis/vigente` | Dashboard - resultados paginados con filtros |
| GET | `/api/v1/analisis/cliente/{id}` | Detalle de un socio |
| GET | `/api/v1/perfil` | Perfil del gimnasio |
| PUT | `/api/v1/perfil` | Actualizar perfil del gimnasio |
Documentación completa en `docs/postman/guia-api.md` y en Swagger UI.

---

## Estructura del repositorio

```
backend/     → API REST (Spring Boot)
frontend/    → Interfaz web (nginx en Docker)
docs/        → Diagramas, colección Postman y documentación
```

---

## Estado del proyecto

TFG DAM 2025/2026 — versión final.
