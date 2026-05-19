# Clientes estratégicos — ChurnGym Demo

Siete clientes diseñados para ilustrar cada grupo de riesgo en la demo.
Los datos están calculados para que el motor produzca exactamente el grupo y nivel indicados.

---

## Cómo encontrarlos

En el dashboard, filtra por **Grupo** para aislar cada sección.  
Cada cliente estratégico es el **primero de su grupo** en la lista (ordenada por probabilidad desc.).

---

## 1 · Elena Rivas Montoya — ACTIVO_ESTABLE · Riesgo BAJO

| Campo | Valor |
|---|---|
| ID | 51 |
| Meses como socia | 22 |
| Frecuencia semanal | 5.0 visitas |
| Semanas inactiva | 0 |
| Tendencia mensual | ↑ +15% |
| Probabilidad calculada | ~17% |

**Por qué es estratégica:**  
Ejemplo perfecto del socio que no necesita intervención. Lleva casi dos años, viene 5 días a la semana y su tendencia sube. En la demo sirve para mostrar que el motor *no* alarma donde no hace falta, y para contrastar con los casos de riesgo.

**Argumento de demo:**  
*"Elena es nuestro socio modelo. El motor le asigna un 17% de probabilidad de abandono — básicamente ruido estadístico. No lanzaríamos ninguna campaña hacia ella; el presupuesto va a quien de verdad lo necesita."*

---

## 2 · Sofía Mora Leal — IRREGULAR · Riesgo MEDIO

| Campo | Valor |
|---|---|
| ID | 111 |
| Meses como socia | 8 |
| Frecuencia semanal | 1.5 visitas |
| Semanas inactiva | 1 |
| Tendencia mensual | ↓ -25% |
| Probabilidad calculada | ~38% |

**Por qué es estratégica:**  
Representa la zona gris: lleva 8 meses, viene poco y su tendencia baja. No encaja en ningún patrón claro todavía. Es el caso más difícil de explicar, y precisamente por eso es interesante para la demo.

**Argumento de demo:**  
*"Sofía está en el grupo Irregular. El motor la pone al 38% — no es una alarma, pero tampoco es tranquilizador. Aquí la recomendación sería monitorización activa: si en dos meses no mejora la tendencia, pasará a intervención."*

---

## 3 · Marc Puig Casals — NUEVO_ENGANCHADO · Riesgo BAJO

| Campo | Valor |
|---|---|
| ID | 151 |
| Meses como socio | 2 |
| Frecuencia semanal | 4.0 visitas |
| Semanas inactivo | 0 |
| Tendencia mensual | ↑ +12% |
| Probabilidad calculada | ~24% |

**Por qué es estratégico:**  
Nuevo que ya tiene hábito. Solo lleva 2 meses pero viene 4 días a la semana con tendencia positiva. El riesgo es bajo, pero el grupo importa: hay que consolidar ese hábito antes de que decaiga.

**Argumento de demo:**  
*"Marc acaba de llegar y ya está enganchado. El 24% de riesgo no nos preocupa hoy, pero sí nos dice que hay que cuidarlo ahora — una bienvenida personalizada, un seguimiento al tercer mes. Es el momento de fidelizar, no cuando ya esté a punto de irse."*

---

## 4 · Roberto Solís Vega — CONSOLIDADO_EN_RIESGO · Riesgo ALTO

| Campo | Valor |
|---|---|
| ID | 181 |
| Meses como socio | 36 |
| Frecuencia semanal | 0.5 visitas |
| Semanas inactivo | 9 |
| Tendencia mensual | ↓ -75% |
| Probabilidad calculada | ~92% |

**Por qué es estratégico:**  
El caso de alarma máxima. Tres años de socio, nueve semanas sin aparecer, tendencia en caída libre. El motor lo coloca en el grupo de máxima prioridad de retención con un 92% de probabilidad de abandono.

**Argumento de demo:**  
*"Roberto lleva 3 años con nosotros y hace 9 semanas que no aparece. El motor le da un 92% de abandono. Este es exactamente el perfil para una llamada directa del equipo, no un email masivo. Si se va, perdemos a alguien que ya demostró que valora el gimnasio."*

**Cálculo visible para la demo:**

```
semanas_norm  = 9/12  = 0.75
frec_norm     = 0.5/7 = 0.071
tendencia_norm = (-(-75) + 100) / 200 = 0.875
meses_norm    = 36/60 = 0.60

z = -1.95 + 4.61×0.75 + 0.34×(1-0.071) + 1.53×0.875 + (-1.13)×0.60
z = -1.95 + 3.46 + 0.32 + 1.34 - 0.68 = 2.49
prob = 1 / (1 + e^-2.49) ≈ 0.92  →  ALTO ✓
```

---

## 5 · Carmen Vidal Ruiz — VETERANO_EN_PAUSA · Riesgo MEDIO

| Campo | Valor |
|---|---|
| ID | 206 |
| Meses como socia | 28 |
| Frecuencia semanal | 1.8 visitas |
| Semanas inactiva | 2 |
| Tendencia mensual | ↓ -35% |
| Probabilidad calculada | ~40% |

**Por qué es estratégica:**  
El caso de intervención temprana. Dos semanas de ausencia no parecen mucho, pero el motor detecta que en una veterana de 28 meses es una señal de alerta. Si no se actúa ahora, en dos semanas más será CONSOLIDADO_EN_RIESGO.

**Argumento de demo:**  
*"Carmen lleva más de dos años y solo falta dos semanas — parece poco, pero es justo el umbral donde intervenir es más barato que recuperar. Un mensaje de 'te echamos de menos' aquí cuesta cero; recuperarla después de tres meses puede ser imposible."*

---

## 6 · Nadia Flores Romero — NUEVO_SIN_ENGANCHE · Riesgo ALTO

| Campo | Valor |
|---|---|
| ID | 226 |
| Meses como socia | 1 |
| Frecuencia semanal | 0.3 visitas |
| Semanas inactiva | 6 |
| Tendencia mensual | ↓ -65% |
| Probabilidad calculada | ~87% |

**Por qué es estratégica:**  
Perdida desde el primer mes. Un mes de alta, 6 semanas sin venir y frecuencia casi nula. El motor la detecta con un 87% de abandono — básicamente ya se fue, aunque siga pagando la cuota.

**Argumento de demo:**  
*"Nadia se dio de alta hace un mes y ya lleva 6 semanas sin aparecer. Esto pasa mucho en enero. El motor la detecta al 87% — si no actuamos en los próximos días, la perderemos antes de que cumpla dos meses. Un programa de iniciación personal en la primera semana hubiera evitado esto."*

---

## 7 · Tomás Herrera Blanco — VETERANO_ESPORADICO · Riesgo BAJO

| Campo | Valor |
|---|---|
| ID | 241 |
| Meses como socio | 42 |
| Frecuencia semanal | 0.9 visitas |
| Semanas inactivo | 0 |
| Tendencia mensual | ↓ -10% |
| Probabilidad calculada | ~17% |

**Por qué es estratégico:**  
El riesgo silencioso. Tomás lleva 42 meses y sigue viniendo, pero menos de una vez a la semana. No es urgente, pero es el perfil que un día deja de venir sin que nadie lo note. Aquí el motor separa "viene poco" de "está a punto de irse".

**Argumento de demo:**  
*"Tomás lleva 3 años y medio. Solo viene una vez a la semana, pero viene. El motor le da un 17% de riesgo — bajo. No es una alarma, pero sí un perfil que merece una oferta de servicio diferente: clases dirigidas, actividades de fin de semana. No queremos perder a alguien que lleva tanto tiempo."*

---

## Resumen rápido

| # | Nombre | Grupo | Nivel | Prob. | Narrativa demo |
|---|---|---|---|---|---|
| 51 | Elena Rivas | ACTIVO_ESTABLE | BAJO | 17% | Socio modelo, no intervenir |
| 111 | Sofía Mora | IRREGULAR | MEDIO | 38% | Zona gris, monitorizar |
| 151 | Marc Puig | NUEVO_ENGANCHADO | BAJO | 24% | Nuevo prometedor, consolidar |
| 181 | Roberto Solís | CONSOLIDADO_EN_RIESGO | ALTO | 92% | Alarma máxima, llamada directa |
| 206 | Carmen Vidal | VETERANO_EN_PAUSA | MEDIO | 40% | Intervención temprana |
| 226 | Nadia Flores | NUEVO_SIN_ENGANCHE | ALTO | 87% | Perdida desde el día 1 |
| 241 | Tomás Herrera | VETERANO_ESPORADICO | BAJO | 17% | Riesgo silencioso |
