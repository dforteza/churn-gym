# Guía rápida de flujo Git (main/develop/feature)

## 1) Concepto base

- `main`: rama estable (producción).
- `develop`: rama de integración.
- Regla: no trabajar directamente en `main` ni en `develop`.
- Todo cambio va en ramas temporales `feature/*` (o `fix/*`).

Flujo típico:

```text
develop
  ├── feature/backend-entidades-jpa
  └── feature/frontend-login
```

## 2) Nomenclatura de ramas

Formato: `tipo/descripcion-en-kebab-case`

- `feature/backend-entidades-jpa`
- `feature/backend-csv-import`
- `feature/backend-churn-engine`
- `feature/backend-auth-jwt`
- `feature/frontend-login`
- `feature/frontend-dashboard`
- `feature/frontend-detalle-modelo`
- `fix/nombre-del-bug`

## 3) Flujo paso a paso

### Empezar una tarea nueva

```bash
git checkout develop
git pull origin develop
git checkout -b feature/backend-entidades-jpa
```

### Durante el desarrollo

```bash
git status
git add .
git commit -m "feat: añadir entidad Cliente con campos básicos"
git push origin feature/backend-entidades-jpa
```

### Al terminar la funcionalidad

```bash
git checkout develop
git pull origin develop
git checkout feature/backend-entidades-jpa
git merge develop
git push origin feature/backend-entidades-jpa
```

Después: abrir Pull Request en GitHub con base `develop` y compare `feature/...`.

## 4) Pull Request (plantilla del repo)

Archivo de referencia: `.github/PULL_REQUEST_TEMPLATE.md`

Título recomendado:

```text
feat: entidades JPA completas
```

Qué rellenar en el PR:

- `¿Qué hace este PR?`: resumen funcional corto.
- `Tipo de cambio`: marca la opción adecuada.
- `¿Cómo se ha probado?`: pasos claros de validación.
- `Checklist`: compila, tests al día y sin credenciales.

## 5) Conventional Commits (chuleta)

Formato:

```text
tipo(scope opcional): descripción breve en minúsculas
```

Ejemplo con scope:

```text
feat(backend): añadir endpoint de importación CSV
```

Tipos más usados:

- `feat`: nueva funcionalidad.
- `fix`: corrección de bug.
- `docs`: documentación.
- `refactor`: mejora interna sin cambiar comportamiento.
- `test`: añadir o actualizar tests.
- `chore`: mantenimiento general (config, tareas varias).
- `build`: cambios de build/dependencias.
- `ci`: pipeline/automatización.
- `perf`: mejora de rendimiento.
- `style`: formato/estilo sin cambio lógico.
- `revert`: revierte un commit anterior.

Ejemplos reales adaptados al proyecto:

```bash
git commit -m "feat: añadir entidad ImportacionCsv con relación a Usuario"
git commit -m "feat(backend): endpoint POST /api/modelos con carga de CSV"
git commit -m "fix: corregir parsing de fechas en CsvParserService"
git commit -m "chore: añadir dependencia OpenCSV al pom.xml"
git commit -m "docs: actualizar README con instrucciones de Docker"
git commit -m "test: añadir pruebas de ChurnEngineService"
git commit -m "refactor(frontend): simplificar lógica de login"
git commit -m "ci: ejecutar tests backend en GitHub Actions"
```

## 6) Reglas de oro

- Nunca hacer push directo a `main`.
- Evitar trabajar directamente en `develop`.
- Un PR por funcionalidad.
- Commits pequeños y frecuentes.
- Si se toca un archivo compartido (por ejemplo `docker-compose.yml`), avisar al compañero antes.

## 7) Chuleta ultra-rápida

```text
INICIO
  git checkout develop
  git pull origin develop
  git checkout -b feature/nombre-tarea

DURANTE
  git add .
  git commit -m "feat: descripción breve"
  git push origin feature/nombre-tarea

CIERRE
  abrir PR: base develop <- compare feature/nombre-tarea
```
