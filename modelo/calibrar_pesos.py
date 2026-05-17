"""
Calibrador de pesos — Motor de Riesgo ChurnGym
===============================================
Ejecuta este script para saber si los pesos de MotorRiesgoServiceImpl
son los mejores posibles, y obtener los valores recomendados.

    python calibrar_pesos.py

Requiere:  pip install scipy
"""

from math import exp
from scipy.optimize import minimize


# ==========================================================
#  PESOS ESTIMADOS DE LITERATURA
# ==========================================================

PESOS_ACTUALES = [
    -1.5,   # intercepto      
     3.5,   # semanas inactivo 
     2.5,   # baja frecuencia  
     1.5,   # tendencia mala  
    -1.0,   # antigüedad       
]


# ==========================================================
#  PERFILES DE PRUEBA
# ==========================================================
#
#  Formato de cada fila:
#  "nombre": (semanas_sin_venir, visitas_por_semana, tendencia_%,
#              meses_de_socio, prob_minima_esperada, prob_maxima_esperada)

PERFILES = {
    "va todos los días, lleva años":        (0,  5.0, +80, 48,  0.02, 0.12),
    "socio nuevo muy activo":               (0,  4.0, +50,  6,  0.05, 0.18),
    "veterano tranquilo, va 3 veces/sem":   (1,  3.0, +10, 60,  0.05, 0.20),
    "va poco pero sin caída clara":         (1,  3.0,   0, 18,  0.10, 0.28),
    "se ausentó un mes y ha vuelto":        (2,  2.5, +30, 24,  0.12, 0.32),
    "socio irregular, lleva 2.5 años":        (3,  1.5, -10, 30,  0.30, 0.52),
    "nuevo que apenas aparece":             (2,  0.8, -20,  3,  0.42, 0.65),
    "lleva meses yendo cada vez menos":     (2,  1.5, -35, 12,  0.38, 0.60),
    "6 semanas sin venir, tendencia mala":  (6,  1.0, -50,  8,  0.68, 0.88),
    "lleva 3 años y ha desaparecido":       (5,  0.5, -60, 36,  0.65, 0.85),
    "veterano con señales activas graves":  (4,  1.2, -55, 45,  0.55, 0.75),
    "8 semanas sin entrar, frecuencia 0":   (8,  0.0, -80, 10,  0.88, 0.97),
    "caso extremo — baja segura":           (12, 0.0,-100,  0,  0.95, 0.99),
}


# ==========================================================
#  MODELO JAVA
# ==========================================================

def probabilidad(pesos, semanas, frecuencia, tendencia, meses):
    w0, w1, w2, w3, w4 = pesos

    s = min(semanas,   12) / 12.0
    f = min(frecuencia, 7) /  7.0
    t = max(0.0, min(1.0, (-tendencia + 100.0) / 200.0))
    m = min(meses, 60) / 60.0

    z = w0 + w1*s + w2*(1-f) + w3*t + w4*m

    sig = 1.0 / (1.0 + exp(-z))

    return (sig)


def verificar_pesos(pesos):
    """Verifica los pesos contra todos los perfiles y retorna el número de fallos."""
    fallos = 0
    for nombre, (sem, frec, tend, mes, p_min, p_max) in PERFILES.items():
        p = probabilidad(pesos, sem, frec, tend, mes)
        if p >= p_min and p <= p_max:
            estado = "✓"
        else:
            estado = "X"
            fallos += 1
        print(f"  {estado}  {nombre:<40s}  {p:.2f}  [{p_min:.2f}–{p_max:.2f}]")
    return fallos


def error_total(pesos):
    """Penaliza cada perfil que esté fuera de su rango esperado."""
    total = 0.0
    for nombre, (sem, frec, tend, mes, p_min, p_max) in PERFILES.items():
        p = probabilidad(pesos, sem, frec, tend, mes)
        if   p < p_min:
            total += (p_min - p) ** 2
        elif p > p_max:
            total += (p - p_max) ** 2
    return (total)


# ==========================================================
#  EJECUCIÓN
# ==========================================================

def main():
    separador = "=" * 55

    print("\n" + separador)
    print("  Calibrador de pesos — Motor de Riesgo ChurnGym")
    print(separador)

    # == 1. Cómo van los pesos actuales ======================
    print("\n Comprobando pesos actuales\n")
    
    # VERIFICAR PESOS ACTUALES
    fallos_actuales = verificar_pesos(PESOS_ACTUALES)

    print(f"\n  Resultado: {len(PERFILES) - fallos_actuales}/{len(PERFILES)} perfiles dentro del rango esperado")

    # == 2. Buscar mejores pesos ==============================
    print(f"\n{separador}")
    print("\n Buscando pesos mejorados\n")

    # CALCULAR NUEVOS PESOS OPTIMIZADOS
    resultado = minimize(
        error_total,
        x0=PESOS_ACTUALES,
        method="Nelder-Mead",
        options={"xatol": 1e-7, "fatol": 1e-9, "maxiter": 100_000}
    )
    # x contiene el vector de pesos optimizados 
    pesos_nuevos = resultado.x.tolist()

    # VERIFICAR NUEVOS PESOS
    fallos_nuevos = verificar_pesos(pesos_nuevos)

    print(f"\n  Resultado: {len(PERFILES) - fallos_nuevos}/{len(PERFILES)} perfiles dentro del rango esperado")

    # == 3. Comparativa de pesos ==============================
    print(f"\n{separador}")
    print("\n Pesos que necesitan ajuste\n")

    nombres = ["intercepto", "semanas inactivo", "baja frecuencia",
               "tendencia mala", "antigüedad"]

    print(f"  {'Peso':<20}  {'Actual':>8}  {'Nuevo':>8}")
    print(f"  {'='*20}  {'='*8}  {'='*8}")

    pesos_a_cambiar = []
    for i, nombre in enumerate(nombres):
        actual = PESOS_ACTUALES[i]
        nuevo  = round(pesos_nuevos[i], 2)
        if abs(actual - nuevo) >= 0.01:
            print(f"  {nombre:<20}  {actual:>8}  {nuevo:>8}")
            pesos_a_cambiar.append((nombre, actual, nuevo))

    if not pesos_a_cambiar:
        print("  Ninguno — los pesos actuales ya son óptimos.")




main()
