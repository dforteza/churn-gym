// frontend/js/config.js
// ─────────────────────────────────────────────────────────────────────────────

// Poner en true para usar datos mock en lugar de la API real
const MOCK_MODE = true;

const API_BASE = 'http://localhost:8080/api';
const APP_ROUTES = {
  login: new URL('../index.html', import.meta.url).href,
  dashboard: new URL('../pages/dashboard.html', import.meta.url).href,
};
const MOCK_BASE = new URL('../mock/', import.meta.url);

// ─── ENDPOINTS ───────────────────────────────────────────────────────────────
const ENDPOINTS = {
  login: () => MOCK_MODE ? new URL('auth.login.json', MOCK_BASE).href : `${API_BASE}/auth/login`,
  modelos: () => MOCK_MODE ? new URL('modelos-existentes.json', MOCK_BASE).href : `${API_BASE}/modelos`,
  modeloById: (id) => MOCK_MODE ? new URL(`modelos-existentes.${id}.json`, MOCK_BASE).href : `${API_BASE}/modelos/${id}`,
};

// ─── HELPER fetch con JWT ─────────────────────────────────────────────────────
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
    localStorage.removeItem('auth_user');
    window.location.href = APP_ROUTES.login;
    return null;
  }

  if (!response.ok) {
    const error = await response.json().catch(() => ({}));
    throw new Error(error.message || `Error ${response.status}`);
  }

  const contentType = response.headers.get('content-type') || '';
  return contentType.includes('application/json') ? response.json() : response.text();
}

export { APP_ROUTES, MOCK_MODE, ENDPOINTS, apiFetch };
