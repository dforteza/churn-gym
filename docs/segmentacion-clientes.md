# Segmentación de clientes - Grupos de riesgo

## Los 7 grupos

### G1 - CONSOLIDADO_EN_RIESGO

**Condición:** `mesesComoSocio > 12` y `semanasInactivo >= 3`

El caso más urgente.  Socio que ha demostrado compromiso durante más de un año
y de repente ha desaparecido. La probabilidad de que la baja sea definitiva
aumenta con cada semana extra de ausencia.

**Acción:** contacto directo, oferta de retención personalizada.

---

### G2 - VETERANO_EN_PAUSA

**Condición:** `mesesComoSocio > 12` y `semanasInactivo == 2`

La ventana de intervención temprana. Lleva exactamente 2 semanas sin aparecer:
suficiente para ser una señal real, pero todavía antes del umbral de G1.
Una semana de ausencia no se considera señal — un socio que viene 3 veces por
semana puede perfectamente saltarse una semana sin que signifique nada.

**Acción:** mensaje directo y cercano recordando que el gym está ahí.

---

### G3 - VETERANO_ESPORADICO

**Condición:** `mesesComoSocio > 12`, `semanasInactivo == 0`, `frecuenciaSemanal < 2`

El riesgo silencioso. Lleva años como socio y técnicamente sigue viniendo,
pero su frecuencia es tan baja que no está obteniendo valor real de la cuota.
Estos socios cancelan sin aviso cuando hacen el cálculo de coste-beneficio.

**Acción:** campañas de reactivación, oferta de clases dirigidas.

---

### G4 - NUEVO_SIN_ENGANCHE

**Condición:** `mesesComoSocio <= 3` y `frecuenciaSemanal < 1`

Socio reciente que no ha cogido el hábito. Si la asistencia no se convierte
en rutina durante los primeros 90 días, la probabilidad de baja es muy alta.
No ha invertido suficiente tiempo ni hábito en el gimnasio todavía.

**Acción:** programa de bienvenida, asignación de monitor de iniciación.

---

### G5 - NUEVO_ENGANCHADO

**Condición:** `mesesComoSocio <= 3`, `frecuenciaSemanal >= 1`, `semanasInactivo == 0`

El activo más valioso en este momento. Ha superado la barrera inicial y está
viniendo de forma regular. Si nadie lo acompaña, la motivación inicial decae. Es el momento de consolidar el hábito.

**Acción:** seguimiento de progreso, integración en comunidad.

---

### G6 - ACTIVO_ESTABLE

**Condición:** `frecuenciaSemanal >= 2`, `semanasInactivo == 0`, `mesesComoSocio >= 4`

Patrón de asistencia consolidado. Viene con regularidad y lleva suficiente
tiempo para que el hábito esté asentado. Bajo riesgo a corto plazo.

**Acción:** fidelización, recompensas por permanencia, referidos.

---

### G7 - IRREGULAR

**Condición:** ninguna de las anteriores

Principalmente la franja de 4 a 12 meses, pero también cualquier perfil con
exactamente 1 semana de ausencia (señal insuficiente) o nuevos socios con
patrones mixtos. Son los casos más difíciles de clasificar con reglas manuales
porque sus señales son contradictorias o insuficientes. Esta franja es la
candidata principal a ser resuelta por el modelo sklearn cuando haya datos reales.

**Acción:** seguimiento periódico pasivo, encuesta de satisfacción.

---

## Tabla resumen

| Grupo | Antigüedad | Ausencia | Frecuencia | Urgencia |
|---|---|---|---|---|
| CONSOLIDADO_EN_RIESGO | > 12 m | ≥ 3 sem | - | 🔴 Máxima |
| VETERANO_EN_PAUSA | > 12 m | == 2 sem | — | 🟠 Alta |
| VETERANO_ESPORADICO | > 12 m | 0 sem | < 2/sem | 🟡 Media |
| NUEVO_SIN_ENGANCHE | ≤ 3 m | - | < 1/sem | 🟠 Alta |
| NUEVO_ENGANCHADO | ≤ 3 m | 0 sem | ≥ 1/sem | 🟢 Oportunidad |
| ACTIVO_ESTABLE | ≥ 4 m | 0 sem | ≥ 2/sem | 🟢 Bajo |
| IRREGULAR | 4-12 m | mixto | mixto | ⚪ Pendiente |

---

## Mapa completo de cobertura

Las filas son la antigüedad del socio. Las columnas son las semanas que lleva
sin venir. Dentro de cada celda, la frecuencia histórica determina el grupo
cuando hay más de una opción.

```
                              SEMANAS SIN VENIR
                   ┌──────────┬──────────┬──────────┬────────────┐
                   │  esta    │  1 sem   │  2 sem   │   ≥ 3 sem  │
                   │  semana  │ (ruido)  │          │            │
    ┌──────────────┼──────────┼──────────┼──────────┼────────────┤
    │              │ NUEVO_   │          │          │ NUEVO_SIN_ │
    │  ≤ 3 meses   │ ENGANCHE.│ IRREGULAR│ IRREGULAR│ ENGANCHE   │
    │  (nuevo)     │ frec ≥ 1 │          │          │ frec < 1   │
    │              ├──────────┤          │          │            │
    │              │ NUEVO_   │ NUEVO_   │ NUEVO_   │            │
    │              │ SIN_ENG. │ SIN_ENG. │ SIN_ENG. │            │
    │              │ frec < 1 │ frec < 1 │ frec < 1 │            │
    ├──────────────┼──────────┼──────────┼──────────┼────────────┤
    │  4 - 12 m    │ ACTIVO_  │          │          │            │
    │  (medio)     │ ESTABLE  │ IRREGULAR│ IRREGULAR│ IRREGULAR  │
    │              │ frec ≥ 2 │          │          │            │
    │              ├──────────┤          │          │            │
    │              │IRREGULAR │          │          │            │
    │              │ frec < 2 │          │          │            │
    ├──────────────┼──────────┼──────────┼──────────┼────────────┤
    │              │ ACTIVO_  │          │ VETERANO_│ CONSOLIDADO│
    │  > 12 meses  │ ESTABLE  │ IRREGULAR│ EN_PAUSA │ _EN_RIESGO │
    │  (veterano)  │ frec ≥ 2 │          │          │            │
    │              ├──────────┤          │          │            │
    │              │ VETERANO_│          │          │            │
    │              │ ESPORADI.│          │          │            │
    │              │ frec < 2 │          │          │            │
    └──────────────┴──────────┴──────────┴──────────┴────────────┘
```

La columna "1 sem" es ruido para todos los perfiles — una semana de ausencia
no es señal suficiente independientemente de la antigüedad o la frecuencia.

Las celdas IRREGULAR concentradas en la franja de 4-12 meses son la zona
gris que el modelo sklearn resolverá empíricamente con datos reales.

---