// ─────────────────────────────────────────────────────────────────────────────
// config.js
// ─────────────────────────────────────────────────────────────────────────────
// ÚNICO fichero que hay que tocar al pasar de mock a backend real.
// MOCK_MODE = true hasta despliegue
//
// Endpoints disponibles:
//   POST   /auth/login
//   GET    /modelos
//   GET    /modelos/{id}
//   POST   /modelos          (multipart/form-data)
//   DELETE /modelos/{id}     (devuelve 204, sin body)
// ─────────────────────────────────────────────────────────────────────────────

// Poner en true para usar datos mock en lugar de la API real
const MOCK_MODE = true;

const API_BASE = 'http://localhost:8080/api';
const APP_ROUTES = {
  login: new URL('../index.html', import.meta.url).href,
  dashboard: new URL('../pages/dashboard.html', import.meta.url).href,
};
const MOCK_BASE = new URL('../mock/', import.meta.url);

// ─── MAPA DE ENDPOINTS ────────────────────────────────────────────────────────
const ENDPOINTS = {
  login:        ()   => MOCK_MODE ? `${MOCK_BASE}/auth.login.json`      : `${API_BASE}/auth/login`,
  modelos:      ()   => MOCK_MODE ? `${MOCK_BASE}/modelos.json`          : `${API_BASE}/modelos`,
  modeloById:   (id) => MOCK_MODE ? `${MOCK_BASE}/modelos.${id}.json`    : `${API_BASE}/modelos/${id}`,
  modeloNuevo:  ()   => MOCK_MODE ? `${MOCK_BASE}/modelos.nuevo.json`    : `${API_BASE}/modelos`,
  modeloDelete: (id) => MOCK_MODE ? null : `${API_BASE}/modelos/${id}`,
};

// ─── LOGIN ────────────────────────────────────────────────────────────────────
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

// ─── GET MODELOS ──────────────────────────────────────────────────────────────
async function apiGetModelos() {
  if (MOCK_MODE) {
    const res = await fetch(ENDPOINTS.modelos());
    return res.json();
  }
  return apiFetch(ENDPOINTS.modelos());
}

// ─── GET MODELO BY ID ─────────────────────────────────────────────────────────
async function apiGetModeloById(id) {
  if (MOCK_MODE) {
    const res = await fetch(ENDPOINTS.modeloById(id));
    if (!res.ok) throw new Error('Modelo no encontrado');
    return res.json();
  }
  return apiFetch(ENDPOINTS.modeloById(id));
}

// ─── POST IMPORTAR MODELO ────────────────────────────────────────────────────
async function apiImportarModelo(nombre, csvClientes, csvVisitas) {
  if (MOCK_MODE) {
    await new Promise(r => setTimeout(r, 800)); // simula tiempo de proceso
    const res = await fetch(ENDPOINTS.modeloNuevo());
    return res.json();
  }
  const token = localStorage.getItem('jwt_token');
  const formData = new FormData();
  formData.append('nombre', nombre);
  formData.append('csvClientes', csvClientes);
  formData.append('csvVisitas', csvVisitas);
  const res = await fetch(ENDPOINTS.modeloNuevo(), {
    method: 'POST',
    headers: { Authorization: `Bearer ${token}` },
    body: formData,
  });
  if (!res.ok) {
    const err = await res.json().catch(() => ({}));
    throw new Error(err.message || `Error ${res.status}`);
  }
  return res.json();
}

// ─── DELETE MODELO ────────────────────────────────────────────────────────────
async function apiDeleteModelo(id) {
  if (MOCK_MODE) {
    await new Promise(r => setTimeout(r, 300)); // simula latencia
    return null; // 204 sin body
  }
  return apiFetch(ENDPOINTS.modeloDelete(id), { method: 'DELETE' });
}

// ─── HELPER GENÉRICO CON JWT ──────────────────────────────────────────────────
async function apiFetch(url, options = {}) {
  const token = localStorage.getItem('jwt_token');
  const isFormData = options.body instanceof FormData;

  const headers = {
    ...(isFormData ? {} : { 'Content-Type': 'application/json' }),
    ...(token ? { Authorization: `Bearer ${token}` } : {}),
    ...options.headers,
  };

  const response = await fetch(url, { ...options, headers });

  if (response.status === 401) {
    localStorage.removeItem('jwt_token');
    window.location.href = '/index.html';
    return;
  }
  if (res.status === 204) return null;
  if (!res.ok) {
    const err = await res.json().catch(() => ({}));
    throw new Error(err.message || `Error ${res.status}`);
  }

  const contentType = response.headers.get('content-type') || '';
  return contentType.includes('application/json') ? response.json() : response.text();
}

export { apiLogin, apiGetModelos, apiGetModeloById, apiImportarModelo, apiDeleteModelo };
