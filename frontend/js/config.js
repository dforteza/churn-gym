// ─────────────────────────────────────────────────────────────────────────────
// config.js — v2.0
// ─────────────────────────────────────────────────────────────────────────────
// Cambiar MOCK_MODE = false cuando Diego avise que el backend está listo.
//
// Endpoints:
//   POST  /api/auth/login
//   GET   /api/analisis?nivelRiesgo=ALTO&grupo=...
//   POST  /api/analisis/lanzar
//   GET   /api/analisis/cliente/{id}
// ─────────────────────────────────────────────────────────────────────────────

const MOCK_MODE = true;

const API_BASE  = 'http://localhost:8080/api';
const MOCK_BASE = './mock';

const ENDPOINTS = {
  login:           ()   => MOCK_MODE ? `${MOCK_BASE}/auth.login.json`            : `${API_BASE}/auth/login`,
  analisis:        ()   => MOCK_MODE ? `${MOCK_BASE}/analisis.json`               : `${API_BASE}/analisis`,
  analisisLanzar:  ()   => MOCK_MODE ? `${MOCK_BASE}/analisis.lanzar.json`        : `${API_BASE}/analisis/lanzar`,
  analisisCliente: (id) => MOCK_MODE ? `${MOCK_BASE}/analisis.cliente.${id}.json` : `${API_BASE}/analisis/cliente/${id}`,
};

// LOGIN
async function apiLogin(username, password) {
  if (MOCK_MODE) {
    const res = await fetch(ENDPOINTS.login());
    return res.json();
  }
  const res = await fetch(ENDPOINTS.login(), {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ username, password }),
  });
  if (!res.ok) throw new Error('Credenciales incorrectas');
  return res.json();
}

// GET ANÁLISIS VIGENTE
// nivelRiesgo: 'ALTO' | 'MEDIO' | 'BAJO' | null
// grupo:       'Socio consolidado en riesgo' | 'Socio nuevo que no engancha' |
//              'Socio activo estable' | 'Socio irregular' | null
async function apiGetAnalisis(nivelRiesgo = null, grupo = null) {
  if (MOCK_MODE) {
    const res  = await fetch(ENDPOINTS.analisis());
    const data = await res.json();
    let resultados = data.resultados;
    if (nivelRiesgo) resultados = resultados.filter(r => r.nivelRiesgo === nivelRiesgo);
    if (grupo)       resultados = resultados.filter(r => r.grupo === grupo);
    const alto  = resultados.filter(r => r.nivelRiesgo === 'ALTO').length;
    const medio = resultados.filter(r => r.nivelRiesgo === 'MEDIO').length;
    const bajo  = resultados.filter(r => r.nivelRiesgo === 'BAJO').length;
    return { ...data, resultados, totalClientes: resultados.length, alto, medio, bajo };
  }
  const params = new URLSearchParams();
  if (nivelRiesgo) params.append('nivelRiesgo', nivelRiesgo);
  if (grupo)       params.append('grupo', grupo);
  const query = params.toString() ? `?${params.toString()}` : '';
  return apiFetch(`${ENDPOINTS.analisis()}${query}`);
}

// POST LANZAR ANÁLISIS
async function apiLanzarAnalisis() {
  if (MOCK_MODE) {
    await new Promise(r => setTimeout(r, 1200));
    const res = await fetch(ENDPOINTS.analisisLanzar());
    return res.json();
  }
  return apiFetch(ENDPOINTS.analisisLanzar(), { method: 'POST' });
}

// GET DETALLE CLIENTE
async function apiGetDetalleCliente(id) {
  if (MOCK_MODE) {
    const res = await fetch(ENDPOINTS.analisisCliente(id));
    if (!res.ok) throw new Error('Cliente no encontrado');
    return res.json();
  }
  return apiFetch(ENDPOINTS.analisisCliente(id));
}

// HELPER GENÉRICO CON JWT
async function apiFetch(url, options = {}) {
  const token = localStorage.getItem('jwt_token');
  const headers = {
    'Content-Type': 'application/json',
    ...(token ? { Authorization: `Bearer ${token}` } : {}),
    ...options.headers,
  };
  const res = await fetch(url, { ...options, headers });
  if (res.status === 401) {
    localStorage.removeItem('jwt_token');
    window.location.href = '/index.html';
    return;
  }
  if (!res.ok) {
    const err = await res.json().catch(() => ({}));
    throw new Error(err.message || `Error ${res.status}`);
  }
  return res.json();
}

export { apiLogin, apiGetAnalisis, apiLanzarAnalisis, apiGetDetalleCliente };
