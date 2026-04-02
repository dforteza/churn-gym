// frontend/js/config.js
// ─────────────────────────────────────────────────────────────────────────────

// ¡¡¡INTERRUPTOR!!!
const MOCK_MODE = true;

const API_BASE   = 'http://localhost:8080/api';
const MOCK_BASE  = './mock';

// ─── ENDPOINTS ───────────────────────────────────────────────────────────────
const ENDPOINTS = {
  login:        () => MOCK_MODE ? `${MOCK_BASE}/auth.login.json`   : `${API_BASE}/auth/login`,
  modelos:      () => MOCK_MODE ? `${MOCK_BASE}/modelos-existentes.json`       : `${API_BASE}/modelos-existentes`,
  modeloById:   (id) => MOCK_MODE ? `${MOCK_BASE}/modelos-existentes.${id}.json` : `${API_BASE}/modelos-existentes/${id}`,
};

// ─── HELPER fetch con JWT ─────────────────────────────────────────────────────
async function apiFetch(url, options = {}) {
  const token = localStorage.getItem('jwt_token');

  const headers = {
    'Content-Type': 'application/json',
    ...(token ? { 'Authorization': `Bearer ${token}` } : {}),
    ...options.headers,
  };

  const response = await fetch(url, { ...options, headers });

  if (response.status === 401) {
    localStorage.removeItem('jwt_token');
    window.location.href = '/index.html';
    return;
  }

  if (!response.ok) {
    const error = await response.json().catch(() => ({}));
    throw new Error(error.message || `Error ${response.status}`);
  }

  return response.json();
}

export { MOCK_MODE, ENDPOINTS, apiFetch };
