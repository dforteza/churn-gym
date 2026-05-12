// Cambia este valor a false cuando el backend real este listo.
const MOCK_MODE = true;

const API_BASE = 'http://localhost:8080/api';
const MOCK_BASE = new URL('../mock/', import.meta.url);

const APP_ROUTES = {
  login: new URL('../index.html', import.meta.url).href,
};

const ENDPOINTS = {
  // En mock leemos un JSON local; en real apuntamos al endpoint HTTP.
  login: () => MOCK_MODE ? `${MOCK_BASE}mock-auth-login.json` : `${API_BASE}/auth/login`,
};

async function apiFetch(url, options = {}) {
  const token = localStorage.getItem('jwt_token');
  const isFormData = options.body instanceof FormData;

  // Solo enviamos JSON por defecto cuando el body no es FormData.
  const headers = {
    ...(isFormData ? {} : { 'Content-Type': 'application/json' }),
    ...(token ? { Authorization: `Bearer ${token}` } : {}),
    ...options.headers,
  };

  const response = await fetch(url, { ...options, headers });

  // Si el token ha caducado o no es valido, forzamos un logout suave.
  if (response.status === 401) {
    localStorage.removeItem('jwt_token');
    localStorage.removeItem('auth_user');
    window.location.href = APP_ROUTES.login;
    return null;
  }

  if (response.status === 204) {
    return null;
  }

  if (!response.ok) {
    const error = await response.json().catch(() => ({}));
    throw new Error(error.message || `Error ${response.status}`);
  }

  // Permitimos reutilizar este helper con endpoints JSON y endpoints de texto plano.
  const contentType = response.headers.get('content-type') || '';
  return contentType.includes('application/json') ? response.json() : response.text();
}

function apiLogin(username, password) {
  // En mock ignoramos el body porque el origen es un archivo estatico.
  return apiFetch(ENDPOINTS.login(), {
    method: MOCK_MODE ? 'GET' : 'POST',
    body: MOCK_MODE ? undefined : JSON.stringify({ username, password }),
  });
}

export {
  APP_ROUTES,
  ENDPOINTS,
  MOCK_MODE,
  apiFetch,
  apiLogin,
};
