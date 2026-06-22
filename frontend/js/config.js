// config.js

// Indica si la aplicación utiliza datos simulados o el backend real.
const MOCK_MODE = false;

// URL base de la API. Es una ruta relativa al mismo origen: nginx (en local y en
// producción) se encarga de redirigir todo lo que empieza por /api hacia el
// backend. Así el navegador siempre habla con el mismo dominio, no hay problemas
// de CORS y la URL pública queda limpia y con HTTPS.
const API_BASE = '/api';

// Ruta base de los archivos mock.
const MOCK_BASE = new URL('../mock/', import.meta.url);

// Rutas principales de navegación de la aplicación.
const APP_ROUTES = {
  login: new URL('../index.html', import.meta.url).href,

  dashboard: new URL('../pages/dashboard.html', import.meta.url).href,

  perfil: new URL('../pages/perfil.html', import.meta.url).href,

  // Genera la URL de la pantalla de detalle de cliente incluyendo su identificador.
  clientDetail: (clientId) => {
    const detailUrl = new URL('../pages/cliente-detalle.html', import.meta.url);
    detailUrl.searchParams.set('id', clientId);
    return detailUrl.href;
  },
};

// Endpoints utilizados por la aplicación. Según el modo configurado,
// devuelven rutas a archivos mock o a servicios reales del backend.
function getLoginEndpoint() {
  return MOCK_MODE ? `${MOCK_BASE}mock-auth-login.json` : `${API_BASE}/v1/auth/login`;
}

function getAnalisisEndpoint() {
  return MOCK_MODE ? `${MOCK_BASE}mock-analisis.json` : `${API_BASE}/v1/analisis`;
}

function getAnalisisLanzarEndpoint() {
  return MOCK_MODE ? `${MOCK_BASE}analisis.lanzar.json` : `${API_BASE}/v1/analisis/ejecutar`;
}

function getAnalisisClienteEndpoint(clientId) {
  return MOCK_MODE
    ? `${MOCK_BASE}mock-cliente.json`
    : `${API_BASE}/v1/analisis/${clientId}`;
}

function getPerfilEndpoint() {
  return `${API_BASE}/v1/perfil`;
}

// Realiza peticiones HTTP al backend aplicando configuración común:
// autenticación mediante JWT, cabeceras adecuadas y tratamiento de errores.
async function apiFetch(url, options = {}) {
  const token = localStorage.getItem('jwt_token');
  const isFormData = options.body instanceof FormData;
  const isLoginRequest = String(url).includes('/auth/login') || String(url).includes('mock-auth-login.json');

  // No se define Content-Type cuando se envía FormData, ya que el navegador
  // debe generar automáticamente la cabecera con su boundary correspondiente.
  const headers = {
    ...(isFormData ? {} : { 'Content-Type': 'application/json' }),
    ...(token ? { Authorization: `Bearer ${token}` } : {}),
    ...options.headers,
  };

  let response;

  try {
    response = await fetch(url, { ...options, headers });
  } catch {
    if (isLoginRequest) {
      throw new Error('No se ha podido iniciar sesión. Comprueba la conexión o la URL abierta en localhost.');
    }

    throw new Error('No se ha podido conectar con el servidor.');
  }

  // Si el token no es válido o ha caducado, se elimina la sesión local
  // y se redirige al usuario a la pantalla de inicio de sesión.
  if (response.status === 401) {
    if (isLoginRequest) {
      const error = await response.json().catch(() => ({}));
      throw new Error(error.message || 'Usuario o contraseña incorrectos');
    }

    localStorage.removeItem('jwt_token');
    localStorage.removeItem('auth_user');
    window.location.href = APP_ROUTES.login;
    return null;
  }

  // Algunos endpoints pueden responder correctamente sin contenido.
  if (response.status === 204) {
    return null;
  }

  // Si la respuesta no es correcta, se intenta recuperar un mensaje de error
  // enviado por el backend antes de generar una excepción genérica.
  if (!response.ok) {
    const error = await response.json().catch(() => ({}));
    if (error.errors && typeof error.errors === 'object') {
      throw new Error(Object.values(error.errors).join(' · '));
    }
    throw new Error(error.message || `Error ${response.status}`);
  }

  // La respuesta se procesa como JSON o como texto según la cabecera recibida.
  const contentType = response.headers.get('content-type') || '';
  return contentType.includes('application/json') ? response.json() : response.text();
}

export {
  APP_ROUTES,
  MOCK_MODE,
  apiFetch,
  getAnalisisClienteEndpoint,
  getAnalisisEndpoint,
  getAnalisisLanzarEndpoint,
  getLoginEndpoint,
  getPerfilEndpoint,
};
