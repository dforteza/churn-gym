# ChurnGym — Guión completo de presentación TFG
**Duración:** 15 minutos | **Simulacro:** viernes 30/05/2026  
**Formato:** 13 slides + vídeo demo + demo en vivo tras la presentación

---

## TABLA DE TIEMPOS

| # | Slide | Tiempo |
|---|-------|--------|
| 1 | Portada | 00:30 |
| 2 | El problema | 01:00 |
| 3 | La solución | 01:00 |
| 4 | Vídeo demo | 01:30 |
| 5 | Stack completo | 00:30 |
| 6 | Motor de Riesgo — El Perceptrón | 01:30 |
| 7 | Motor de Riesgo — Nelder-Mead | 01:00 |
| 8 | Segmentación: los 7 grupos | 02:00 |
| 9 | Arquitectura — Diagrama Docker | 00:45 |
| 10 | Arquitectura — Backend en capas | 00:45 |
| 11 | Arquitectura — Swagger 7 endpoints | 00:30 |
| 12 | Arquitectura — Frontend (compañero) | 00:30 |
| 13 | V2 — Keras + sklearn KMeans | 01:30 |
| 14 | Conclusiones y cierre | 01:00 |
| **Total** | | **~14:00** |

---

## SLIDE 1 — Portada
**Tiempo: 00:00 – 00:30**

### Contenido visual
- Título: *ChurnGym: sistema web de detección y segmentación del riesgo de abandono en gimnasios*
- Nombres: Diego Forteza Benito + nombre compañero
- Instituto + Curso 2025/2026 + Fecha

### Qué dices
Solo presentarte. Sin leer la slide.
> *"Buenos días. Soy Diego Forteza y este es ChurnGym — un sistema para que los gimnasios dejen de perder socios sin saberlo."*

---

## SLIDE 2 — El problema
**Tiempo: 00:30 – 01:30**

### Contenido visual
**Titular grande:**
> *"La mayoría de gimnasios pierden un tercio de sus socios al año sin saber quién, ni cuándo, ni por qué"*

**3 bullets:**
- No existe ninguna señal de alarma antes de que se vayan
- Cuando se detecta el problema, ya es demasiado tarde para actuar
- Las herramientas existentes son genéricas, no están pensadas para este sector

### Qué dices
> *"Un gestor de gimnasio sabe cuántos socios tiene hoy. Pero no sabe cuáles van a darse de baja el mes que viene. Para cuando lo sabe, ya se han ido. Y captarlo de nuevo cuesta entre 5 y 7 veces más que haberlo retenido."*

**Cierre:**
> *"La pregunta que nos hicimos fue: ¿y si pudieras actuar antes de que se vayan?"*

---

## SLIDE 3 — La solución
**Tiempo: 01:30 – 02:30**

### Contenido visual
**Frase central:**
> *"ChurnGym calcula el riesgo de abandono de cada socio y le dice al gestor exactamente qué hacer"*

**3 bloques con icono:**
- 📊 **Detección** — score de riesgo individual por socio (0% a 100%)
- 🎯 **Segmentación** — 7 grupos de comportamiento con acción recomendada
- 📧 **Acción** — lanzar campaña de retención desde la propia aplicación

### Qué dices
> *"ChurnGym hace tres cosas: calcula un score de riesgo para cada socio, los agrupa por perfil de comportamiento, y le da al gestor una acción concreta. Todo desde una interfaz web, sin formación técnica requerida."*

---

## SLIDE 4 — Vídeo demo
**Tiempo: 02:30 – 04:00**

### Contenido visual
**El vídeo.** Flujo a grabar (sin audio — narras encima):
1. Login → entrar con credenciales
2. Dashboard: tabla de socios con colores ALTO (rojo) / MEDIO (naranja) / BAJO (verde)
3. Filtrar por riesgo ALTO
4. Buscar un socio por nombre
5. Detalle: score numérico, grupo, historial de visitas, botón campaña email

### Qué dices
> *"Esto es lo que ve el gestor cada mañana. De un vistazo: quién está en riesgo alto, quién está estable."*

> *"Entramos al detalle: este socio tiene un 78% de probabilidad de abandonar. El sistema lo clasifica como CONSOLIDADO_EN_RIESGO — lleva más de un año y lleva 3 semanas sin aparecer. Desde aquí el gestor lanza la campaña de retención sin salir de la aplicación."*

---

## SLIDE 5 — Stack completo
**Tiempo: 04:00 – 04:30**

### Contenido visual
| Capa | Tecnologías |
|------|-------------|
| **Backend** | Java 21 · Spring Boot 3.5 · Spring Security + JWT · MapStruct · Flyway |
| **Frontend** | HTML · CSS · JavaScript vanilla · Nginx |
| **Base de datos** | PostgreSQL 15 |
| **Infraestructura** | Docker · Docker Compose |
| **Calidad** | JUnit 5 · Mockito · JaCoCo |
| **API / Docs** | Swagger UI · OpenAPI 3 · Postman |
| **ML (v2)** | Python · Keras · scikit-learn · Nelder-Mead |

### Qué dices
> *"El sistema está construido sobre este stack. Destacar tres cosas: Swagger para la documentación viva de la API, JaCoCo para la cobertura de tests, y Python con Keras y scikit-learn — la base de la v2 de la que hablaremos al final."*

---

## SLIDE 6 — Motor de Riesgo: El Perceptrón
**Tiempo: 04:30 – 06:00**

### Contenido visual

**Bloque 0 — ¿Qué sabe el sistema de cada socio?**
| Campo | Qué mide |
|-------|----------|
| `semanasInactivo` | Semanas consecutivas sin venir al gimnasio |
| `frecuenciaMediaSemanal` | Visitas por semana de media |
| `tendencia` | Evolución reciente: bajista / estable / alcista |
| `mesesComoSocio` | Antigüedad en el gimnasio |

> Estos 4 campos se almacenan por socio en la base de datos. El endpoint `/analisis/ejecutar` los lee y calcula el score de riesgo.

**Bloque 1 — Diagrama visual de perceptrón:**
```
   semanas_norm  ──(w₁=+4.61)──┐
   frecuencia_norm ─(w₂=+0.34)──┤
                                ├──[ Σ + bias(-1.95) ]──[ sigmoid ]──▶ P(abandono)
   tendencia_norm ─(w₃=+1.53)──┤
   meses_norm ────(w₄=−1.13)──┘
```

**Bloque 2 — Las fórmulas:**
```
z = −1.95 + 4.61·x₁ + 0.34·x₂ + 1.53·x₃ − 1.13·x₄

P = sigmoid(z) = 1 / (1 + e⁻ᶻ)
```

**Bloque 3 — Ejemplo con números reales:**
| Variable | Valor raw | Normalizado |
|----------|-----------|-------------|
| Semanas inactivo | 4 sem | 4/12 = 0.33 |
| Frecuencia semanal | 1 vez | 1−(1/7) = 0.86 |
| Tendencia | bajista | 0.8 |
| Meses como socio | 8 meses | 8/60 = 0.13 |

```
z = −1.95 + 4.61·(0.33) + 0.34·(0.86) + 1.53·(0.8) − 1.13·(0.13)
z = −1.95 + 1.52 + 0.29 + 1.22 − 0.15 = 0.93
P = sigmoid(0.93) = 0.72  →  72% de riesgo 🔴 ALTO
```

### Qué dices
> *"Antes de calcular nada, el sistema necesita saber cuatro cosas de cada socio: cuántas semanas lleva sin venir, con qué frecuencia venía, si su asistencia tiene tendencia bajista o no, y cuánto tiempo lleva como socio. Esos cuatro campos viven en la base de datos. Cuando el gestor pulsa 'ejecutar análisis', el backend los lee para cada uno de los 200 socios y aplica esto."*

> *"El motor de riesgo es un perceptrón — la unidad mínima de una red neuronal. Cuatro entradas normalizadas, cuatro pesos, un bias, y una función de activación sigmoid que convierte cualquier número real en una probabilidad entre 0 y 1."*

> *"Cada peso tiene un significado de negocio: el tiempo de inactividad tiene el peso más alto porque es el predictor más potente del abandono. La antigüedad tiene peso negativo — un veterano de cinco años que desaparece dos semanas no es lo mismo que un socio nuevo."*

> *"Con este socio concreto: cuatro semanas sin venir, viene poco, tendencia bajista. El resultado es un 72% de riesgo — ALTO."*

---

## SLIDE 7 — Motor de Riesgo: Nelder-Mead
**Tiempo: 06:00 – 07:00**

### Contenido visual

**Bloque 1 — El problema:**
> *"¿Cómo encontramos w₁=4.61, w₂=0.34, w₃=1.53, w₄=−1.13?"*

**Bloque 2 — Diagrama visual del simplex (triángulo buscando el mínimo):**
Gráfico simple de una curva de coste con un punto moviéndose hacia el mínimo.
O: imagen de triángulo (simplex en 2D) contrayéndose hacia el mínimo.

**Bloque 3 — Qué hace Nelder-Mead en una línea:**
- Prueba combinaciones de pesos
- Evalúa cuántos socios clasifica bien con esos pesos
- Contrae el espacio de búsqueda hacia los pesos que maximizan la separación
- Sin gradientes — funciona aunque la función de coste no sea diferenciable

**V1 vs V2 (pequeño, al pie):**
- V1: Nelder-Mead sobre datos sintéticos → pesos heurísticos
- V2: red neuronal (Keras) entrenada con datos reales → pesos aprendidos

### Qué dices
> *"Los pesos no se eligieron a mano. Se optimizaron con Nelder-Mead: un algoritmo que prueba combinaciones de pesos y se va contrayendo hacia los que mejor separan los socios que abandonan de los que no. Sin gradientes, sin backpropagation — una búsqueda numérica directa."*

> *"La diferencia con la v2 es exactamente esta: en lugar de Nelder-Mead sobre datos sintéticos, usaremos una red neuronal con Keras entrenada sobre datos reales. Mismo concepto, aprendizaje real."*

---

## SLIDE 8 — Segmentación: los 7 grupos
**Tiempo: 07:00 – 09:00**

### Contenido visual

**Titular:**
> *"El score te dice cuánto preocuparte. El grupo te dice qué hacer."*

**Tabla:**
| Grupo | Perfil | Urgencia | Acción |
|-------|--------|----------|--------|
| CONSOLIDADO_EN_RIESGO | Veterano, ≥3 sem sin venir | 🔴 Máxima | Contacto directo + oferta |
| VETERANO_EN_PAUSA | Veterano, 2 sem sin venir | 🟠 Alta | Mensaje cercano |
| VETERANO_ESPORÁDICO | Veterano, viene poco | 🟡 Media | Reactivación, clases |
| NUEVO_SIN_ENGANCHE | Nuevo, frecuencia < 1/sem | 🟠 Alta | Monitor de iniciación |
| NUEVO_ENGANCHADO | Nuevo, viene regular | 🟢 Oportunidad | Consolidar hábito |
| ACTIVO_ESTABLE | ≥4 meses, viene regular | 🟢 Bajo | Fidelización |
| IRREGULAR | Señales mixtas (4-12 meses) | ⚪ Pendiente | Encuesta satisfacción |

### Qué dices
> *"Un score solo no basta. Imagina dos socios con un 70% de riesgo: uno lleva tres años y lleva tres semanas sin aparecer — CONSOLIDADO_EN_RIESGO, hay que llamarle hoy. El otro lleva dos meses y viene una vez por semana — NUEVO_SIN_ENGANCHE, necesita un programa de bienvenida. Misma urgencia, acción completamente distinta."*

> *"Los grupos tienen nombres de negocio intencionalmente — para que el gestor los entienda sin formación técnica."*

> *"El grupo IRREGULAR merece mención especial: son los perfiles con señales contradictorias, principalmente la franja de 4 a 12 meses. Son los más difíciles de clasificar con reglas. Y son exactamente los candidatos a ser resueltos por KMeans en la v2."*

---

## SLIDE 9 — Arquitectura: Diagrama Docker
**Tiempo: 09:00 – 09:45**

### Contenido visual
```
┌─────────────────────────────────────────────────┐
│                  Docker Compose                  │
│                                                  │
│  ┌──────────────┐    ┌────────────────────────┐  │
│  │   Frontend   │───▶│  Backend (API REST)    │  │
│  │    Nginx     │    │  Spring Boot 3.5       │  │
│  │   :3000      │    │  Java 21  ·  :8080     │  │
│  └──────────────┘    └───────────┬────────────┘  │
│                                  │               │
│                      ┌───────────▼────────────┐  │
│                      │     PostgreSQL 15       │  │
│                      │  Flyway migrations      │  │
│                      └────────────────────────┘  │
└─────────────────────────────────────────────────┘
```

### Qué dices
> *"El sistema tiene tres servicios orquestados con Docker Compose. Un solo comando levanta todo el entorno. El frontend está servido por Nginx, el backend expone la API en el puerto 8080, y PostgreSQL gestiona la persistencia con migraciones versionadas por Flyway."*

---

## SLIDE 10 — Arquitectura: Backend en capas
**Tiempo: 09:45 – 10:30**

### Contenido visual

```
com.juandelacierva.ChurnGym/
│
├── controller/          ← recibe las peticiones HTTP, valida entrada
│   ├── AnalisisServiceController.java
│   └── AuthController.java
│
├── service/             ← lógica de negocio
│   ├── AnalisisServiceImpl.java
│   ├── MotorRiesgoServiceImpl.java   ← perceptrón aquí
│   ├── ClusteringClient.java         ← puente a v2
│   └── interfaces/
│
├── repository/          ← acceso a BD (Spring Data JPA)
│
├── domain/              ← entidades JPA + enums
│   └── enums/           (GrupoRiesgo, NivelRiesgo, ...)
│
├── dto/                 ← objetos de transferencia (entrada/salida API)
├── mapper/              ← MapStruct (conversión automática)
│
├── security/            ← JWT, filtros, Spring Security
│   ├── config/
│   └── jwt/
│
└── exception/           ← manejo global de errores
```

### Qué dices
> *"El backend sigue arquitectura en capas estricta. Los controllers reciben y validan. Los services contienen toda la lógica — incluido el motor de riesgo y el ClusteringClient que conecta con la v2. Los repositories hablan con la base de datos. Los DTOs y MapStruct garantizan que nunca se expone el dominio interno directamente en la API."*

---

## SLIDE 11 — Arquitectura: Swagger — 7 endpoints
**Tiempo: 10:30 – 11:00**

### Contenido visual
**Captura de Swagger UI** con los 7 endpoints visibles:

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | /api/v1/auth/login | Autenticación, devuelve JWT |
| GET | /api/v1/analisis | Listado paginado con filtros |
| POST | /api/v1/analisis/ejecutar | Lanza el análisis de todos los socios |
| GET | /api/v1/analisis/{id} | Detalle de un socio |
| GET | /api/v1/perfil | Datos del gimnasio |
| PUT | /api/v1/perfil | Actualizar datos del gimnasio |
| GET | /actuator/health | Estado del sistema |

### Qué dices
> *"La API tiene 7 endpoints documentados con OpenAPI y accesibles desde Swagger UI. Autenticación con JWT, operaciones de análisis, y gestión del perfil del gimnasio. La colección completa también está exportada en Postman."*

---

## SLIDE 12 — Arquitectura: Frontend (compañero)
**Tiempo: 11:00 – 11:30**

### Contenido visual
```
frontend/
├── index.html              ← login
├── pages/
│   ├── dashboard.html
│   └── cliente-detalle.html
│
├── js/
│   ├── api/                ← llamadas al backend (analisis-api.js)
│   ├── pages/              ← lógica por página
│   ├── dashboard/          ← data · elements · render (separación MVC)
│   ├── services/           ← campaign-service · export-service
│   └── utils/              ← formatters · escape · download
│
└── css/                    ← estilos por página
```

### Qué dices
**Esta slide la explica el compañero.**
Que comente: la separación entre data/elements/render en el dashboard, cómo organiza las llamadas a la API, y la decisión de no usar framework (vanilla JS).

---

## SLIDE 13 — V2: Keras + sklearn KMeans
**Tiempo: 11:30 – 13:00**

### Contenido visual

**Dos columnas — V1 vs V2:**

| | V1 (hoy) | V2 (arquitectada) |
|-|----------|-------------------|
| **Probabilidad** | Perceptrón + Nelder-Mead | Red neuronal Keras entrenada con datos reales |
| **Segmentación** | 7 grupos por reglas de negocio | KMeans (sklearn) — clusters empíricos |
| **Pesos** | Heurísticos, calibrados a mano | Aprendidos del comportamiento real |
| **Grupo IRREGULAR** | "No sé clasificarte" | Cluster propio basado en patrones |

**Bloque de código real (ClusteringClient.java):**
```java
// Si el microservicio Python responde → usa KMeans
// Si no responde → cae a las reglas de negocio de v1
Map<Long, String> grupos = clusteringClient.obtenerGrupos(socios)
    .orElse(calcularGruposConReglas(socios));
```

**Frase debajo:** *"Mismo contrato de API. Cero cambios en el resto del sistema."*

### Qué dices
> *"La v1 funciona con un perceptrón cuyos pesos se optimizaron con Nelder-Mead. La limitación honesta: esos pesos son heurísticos, no aprendidos de datos reales de un gimnasio."*

> *"La v2 sustituye eso por una red neuronal con Keras entrenada sobre el histórico real de asistencia. Y sustituye las reglas de segmentación por KMeans de scikit-learn — que clusterizará los socios empíricamente en lugar de por condiciones fijas."*

> *"Lo importante es este fragmento: el sistema ya tiene implementada la llamada al microservicio Python. Si responde, lo usa. Si no, cae automáticamente a la v1. El resto del sistema no se toca. Esto no es un plan — es código que ya existe en el repositorio."*

---

## SLIDE 14 — Conclusiones y cierre
**Tiempo: 13:00 – 14:00**

### Contenido visual

**✅ Qué se ha conseguido**
- Sistema funcional y desplegable desde el día uno
- Motor de riesgo propio — no una librería, un perceptrón calibrado
- Segmentación accionable de 200 socios con acción recomendada por grupo
- API documentada (Swagger + Postman) · Tests con JaCoCo · Docker listo para Render

**📚 Qué hemos aprendido**
- Que la ingeniería de software y el análisis de datos se integran desde el diseño
- Diseño de sistemas reales en capas — no de tutorial
- Gestión de proyecto con Git, ramas y pull requests

**🚀 Próximos pasos**
- Despliegue en Render
- Microservicio Python con Keras + KMeans
- Panel de métricas de retención real (tasa de churn mensual)

### Qué dices
> *"ChurnGym es una v1 funcional. No un prototipo — un sistema que se puede desplegar hoy y usar mañana. Con la v2 ya arquitectada para crecer."*

> *"Lo que más me llevo de este proyecto es haber entendido que un problema de negocio real necesita una solución de ingeniería real. Y que esa solución tiene que ser explicable, mantenible, y con margen para crecer."*

> *"Muchas gracias. Abrimos turno de preguntas."*

---

## NOTAS GENERALES

### Sobre el dato del churn
No usar porcentaje concreto sin fuente. Formulación segura: *"La mayoría de gimnasios pierden un tercio de sus socios cada año"*. Si el anteproyecto o la memoria citan una fuente, usar ese dato con referencia.

### Demo en vivo (tras los 15 min)
- Tener Docker corriendo antes de entrar: `docker compose up --build`
- Credenciales: `diego` / `admin1234` en `localhost:3000`
- Flujo recomendado: dashboard → filtro ALTO → detalle cliente con score alto → botón campaña email → perfil del gimnasio
- Tener capturas estáticas de fallback por si Docker no arranca

### Posibles preguntas del jurado
- *"¿De dónde vienen los datos de cada socio?"* → Están almacenados en la BD (4 campos por socio: semanasInactivo, frecuenciaMedia, tendencia, mesesComoSocio). En producción se alimentarían del sistema de control de acceso del gimnasio; en la demo provienen del seed de Flyway con 200 socios sintéticos de distribución realista.
- *"¿Cómo calibraste los pesos?"* → Nelder-Mead, script Python, maximizando separación entre grupos
- *"¿Por qué sigmoid y no otra función?"* → Sigmoid es la función de activación natural para probabilidades binarias (abandona / no abandona)
- *"¿Qué pasa si el microservicio Python no está?"* → Fallback en ClusteringClient, el sistema sigue con reglas v1
- *"¿Habéis probado con datos reales?"* → Los 200 socios son sintéticos con distribución realista; v2 necesita datos reales de un gimnasio real

---

*Documento actualizado: 2026-05-25 — simulacro viernes 30/05/2026*
