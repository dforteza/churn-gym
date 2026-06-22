# ChurnGym

*Sistema web para la detección temprana de abandono (**churn**) en socios de gimnasio. Analiza frecuencia de visitas, tendencia de actividad y antigüedad para calcular un nivel de riesgo individual y segmentar automáticamente a cada socio en uno de 7 grupos de comportamiento.*

![Demo](docs/Imágenes%20pantallas/pantalla-dashboard-1.png)

## Stack

![Java](https://img.shields.io/badge/Java_21-007396?style=flat&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot_3.5-6DB33F?style=flat&logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL_16-336791?style=flat&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat&logo=docker&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-62B347?style=flat&logo=flyway&logoColor=white)
![Swagger](https://img.shields.io/badge/OpenAPI-85EA2D?style=flat&logo=swagger&logoColor=black)

## Autores

| Nombre | Rol |
|---|---|
| Diego Forteza Benito | Backend — Spring Boot, API REST, base de datos, motor de riesgo |
| Javier Escudero García | Frontend — JavaScript, HTML, CSS |

## Descripción

- **Motor de riesgo** basado en una función sigmoid calibrada con el algoritmo Nelder-Mead (`modelo/calibrar_pesos.py`, script offline con scipy)
- **7 grupos de segmentación** deterministas por comportamiento (CONSOLIDADO_EN_RIESGO, NUEVO_ENGANCHADO, VETERANO_EN_PAUSA…)
- **Dashboard interactivo** con filtros por nivel de riesgo y búsqueda por nombre
- **Autenticación JWT** (HS512) con Spring Security
- **API REST documentada** en Swagger UI y colección Postman incluida
- **Dockerizado al 100 %** — un solo comando para levantar toda la aplicación

## Arquitectura

```
backend/     → API REST (Spring Boot) + motor de riesgo
frontend/    → Interfaz web (nginx en Docker)
docs/        → Diagramas, colección Postman y documentación
modelo/      → Script offline de calibración de pesos (scipy, no es un servicio)
```

**Segmentación actual:** los 7 grupos se calculan con reglas deterministas en `MotorRiesgoServiceImpl` (ver detalle completo en `docs/segmentacion-clientes.md`).

**Punto de extensión (no implementado todavía):** `ClusteringClient` está preparado para delegar la franja `IRREGULAR` a un microservicio de clustering externo (`CLUSTERING_SERVICE_URL`, `POST /cluster`) que no forma parte de este repositorio. Si no está disponible, el sistema cae automáticamente al motor de reglas — por eso el `docker-compose.yml` solo levanta `postgres` + `backend` + `frontend`.

## Instrucciones

**Primera vez o tras borrar volúmenes** (BD limpia desde cero):
```bash
docker compose down -v && docker compose up --build
```

**Reinicios normales** (conserva los datos):
```bash
docker compose up --build
```

| Servicio | URL |
|---|---|
| Frontend | http://localhost:3000 |
| Backend API | http://localhost:8080/api |
| Swagger UI | http://localhost:8080/swagger-ui/index.html |

**Credenciales de prueba:** usuario `diego` / contraseña `admin1234`.

**Endpoints principales:**

| Método | Endpoint | Descripción |
|---|---|---|
| POST | `/api/v1/auth/login` | Obtener token JWT |
| POST | `/api/v1/analisis/lanzar` | Ejecutar análisis de churn |
| GET | `/api/v1/analisis/vigente` | Dashboard — resultados paginados con filtros |
| GET | `/api/v1/analisis/cliente/{id}` | Detalle de un socio |
| GET | `/api/v1/perfil` | Perfil del gimnasio |
| PUT | `/api/v1/perfil` | Actualizar perfil del gimnasio |

## Recursos

- [Anteproyecto](docs/anteproyecto/Anteproyecto_ChurnGym_v1.0.pdf)
- [Segmentación de clientes — detalle de los 7 grupos](docs/segmentacion-clientes.md)
- Diagramas: [Casos de uso](docs/diagramas/casos-de-uso/CasosUso-ChurnGym.png) · [Componentes](docs/diagramas/componentes/Componentes-ChurnGym.png) · [ERD](docs/diagramas/erd/ERD-ChurnGym.png) · [Gantt](docs/diagramas/gantt/Gantt-ChurnGym.png)
- [Guía de la API](docs/postman/guia-api.md) y colección Postman incluida en `docs/postman/`
- [OpenAPI spec](docs/api/openapi.yaml)
- [Guía de workflow de Git](docs/guia-workflow-git.md)

## Licencia

MIT — ver [LICENSE](LICENSE).

## Estado del proyecto

TFG DAM 2025/2026 — versión final.
