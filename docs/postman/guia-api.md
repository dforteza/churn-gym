# ChurnGym API — Guía de endpoints

Base URL: `http://localhost:8080`  
Autenticación: Bearer JWT (obtener con POST-Login y usar en el resto)

---

## Auth

| Request | Método | Endpoint | Respuesta |
|---|---|---|---|
| POST-Login | POST | `/api/v1/auth/login` | 200 — devuelve token JWT |
| POST-Login-CredencialesInvalidas | POST | `/api/v1/auth/login` | 401 |

Body del login:
```json
{ "username": "admin", "password": "admin1234" }
```

---

## Análisis — Lanzar

| Request | Método | Endpoint | Respuesta |
|---|---|---|---|
| POST-LanzarAnalisis | POST | `/api/analisis/lanzar` | 200 — calcula y guarda resultados |
| POST-RelanzarAnalisis | POST | `/api/analisis/lanzar` | 200 — borra los anteriores y recalcula |

---

## Análisis — Vigente

Endpoint base: `GET /api/analisis/vigente`

| Request | Parámetros | Respuesta |
|---|---|---|
| GET-AnalisisVigente | — | 200 — página 0, 10 elementos, orden por probabilidad desc |
| GET-AnalisisVigente-Pagina2 | `page=1&size=5` | 200 — segunda página con 5 elementos |
| GET-AnalisisVigente-SizeMaximo | `size=101` | 200 — el servidor clampea a 100 |
| GET-AnalisisVigente-NivelAlto | `nivelRiesgo=ALTO` | 200 |
| GET-AnalisisVigente-NivelMedio | `nivelRiesgo=MEDIO` | 200 |
| GET-AnalisisVigente-NivelBajo | `nivelRiesgo=BAJO` | 200 |
| GET-AnalisisVigente-GrupoConsolidado | `grupo=CONSOLIDADO_EN_RIESGO` | 200 |
| GET-AnalisisVigente-GrupoIrregular | `grupo=IRREGULAR` | 200 |
| GET-AnalisisVigente-FranjaManana | `franjaHoraria=MANANA` | 200 |
| GET-AnalisisVigente-DeporteCardio | `deportePrincipal=CARDIO` | 200 |
| GET-AnalisisVigente-AltoManana | `nivelRiesgo=ALTO&franjaHoraria=MANANA` | 200 |
| GET-AnalisisVigente-AltoConsolidado | `nivelRiesgo=ALTO&grupo=CONSOLIDADO_EN_RIESGO` | 200 |
| GET-AnalisisVigente-TodosFiltros | los 4 filtros a la vez | 200 — puede devolver content vacío |
| GET-AnalisisVigente-OrdenAscendente | `sort=probabilidadAbandono,asc` | 200 |

Valores válidos por filtro:
- `nivelRiesgo`: ALTO · MEDIO · BAJO
- `grupo`: CONSOLIDADO_EN_RIESGO · NUEVO_SIN_ENGANCHE · ACTIVO_ESTABLE · IRREGULAR
- `franjaHoraria`: MANANA · TARDE · NOCHE
- `deportePrincipal`: MUSCULACION · CARDIO · CROSSFIT · CLASES_COLECTIVAS · MIXTO

---

## Análisis — Detalle cliente

| Request | Método | Endpoint | Respuesta |
|---|---|---|---|
| GET-DetalleCliente | GET | `/api/analisis/cliente/{{clienteId}}` | 200 |
| GET-DetalleCliente-NoExiste | GET | `/api/analisis/cliente/999999` | 404 |
| GET-DetalleCliente-SinToken | GET | `/api/analisis/cliente/{{clienteId}}` | 401 |

---

## Orden de ejecución recomendado

1. `POST-Login` — obtiene y guarda el token
2. `POST-LanzarAnalisis` — genera los resultados y guarda el primer clienteId
3. El resto en cualquier orden
