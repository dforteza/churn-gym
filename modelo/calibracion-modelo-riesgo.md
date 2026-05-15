# Modelo de Riesgo — Base teórica

## El modelo

Regresión logística manual. Calcula la probabilidad de abandono de un socio
combinando 4 variables con pesos fijos y pasando el resultado por una sigmoide:

```
z    =  w0 + w1·semanas + w2·(1−frecuencia) + w3·tendencia + w4·meses
prob =  1 / (1 + e^−z)
```

La sigmoide convierte cualquier valor real z en un número entre 0 y 1.
Los pesos determinan cuánto influye cada variable en el resultado.

---

## Variables y normalización

Todas las variables se llevan al rango [0, 1] antes de entrar al modelo:

| Variable | Normalización | Efecto sobre el riesgo |
|---|---|---|
| `semanasInactivo` | `min(x, 12) / 12` | + (más semanas → más riesgo) |
| `frecuenciaSemanal` | `min(x, 7) / 7` | − (se usa `1−f`, menos frecuencia → más riesgo) |
| `tendenciaMensual` | `(−x + 100) / 200` | + (tendencia negativa → más riesgo) |
| `mesesComoSocio` | `min(x, 60) / 60` | − (más antigüedad → menos riesgo) |

---

## Calibración por casos experto

Sin datos históricos etiquetados no es posible entrenar el modelo estadísticamente.
La alternativa es definir perfiles sintéticos con probabilidad esperada conocida
y optimizar los pesos para que el modelo los satisfaga.

**Función de pérdida:**
Para cada perfil se define un rango `[p_min, p_max]`. La pérdida es cero si la
probabilidad calculada cae dentro del rango, y el cuadrado de la distancia al
extremo más cercano si cae fuera:

```
pérdida(perfil) = 0                    si p_min ≤ prob ≤ p_max
                = (p_min − prob)²      si prob < p_min
                = (prob − p_max)²      si prob > p_max

pérdida_total = Σ pérdida(perfil)
```

El objetivo es encontrar el vector de pesos `w` que minimiza `pérdida_total`.

---

## Algoritmo de optimización: Nelder-Mead

Se usa `scipy.optimize.minimize` con el método **Nelder-Mead**.

`minimize` es la función de SciPy que busca el mínimo de una función dada.
Recibe la función a minimizar, un punto de partida y el método numérico a usar.

**Nelder-Mead** es un método de optimización sin gradiente. Funciona con un
simplex: una figura geométrica de N+1 vértices en el espacio de pesos (aquí, 5
pesos → simplex de 6 puntos). En cada iteración el simplex se deforma —
expandiéndose, contrayéndose o reflejándose— para moverse hacia la zona de
menor pérdida. No necesita calcular derivadas, lo que lo hace adecuado para
funciones de pérdida no diferenciables como la usada aquí (basada en rangos).

```python
resultado = minimize(
    error_total,          # función a minimizar
    x0=PESOS_ACTUALES,    # punto de partida (pesos actuales)
    method="Nelder-Mead"
)
pesos_optimos = resultado.x
```

**Limitación:** Nelder-Mead puede quedarse en un mínimo local si el punto de
partida está lejos de la solución óptima. Para este problema es suficiente
porque los pesos de partida ya son razonables y el espacio de búsqueda es pequeño.

---

## Transición a ML real

Cuando haya al menos 200 socios con resultado conocido (baja sí/no), los pesos
se pueden calcular estadísticamente con regresión logística real:

```python
from sklearn.linear_model import LogisticRegression
model = LogisticRegression()
model.fit(X_train, y_train)  # X = features normalizadas, y = 1 baja / 0 no baja
# model.intercept_ → w0,  model.coef_ → [w1, w2, w3, w4]
```
