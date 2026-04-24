import { APP_ROUTES } from './config.js';

const SESSION_KEY = 'auth_user';
const TOKEN_KEY = 'jwt_token';

// Guarda por separado el token y los datos visibles del usuario para
// reutilizarlos en otras pantallas sin tener que volver a loguearse.
function saveSession({ token, username, rol }) {
  localStorage.setItem(TOKEN_KEY, token);
  localStorage.setItem(SESSION_KEY, JSON.stringify({ username, rol }));
}

// Si la sesion esta corrupta, la limpiamos para no dejar la app en un estado inconsistente.
function getSession() {
  const rawSession = localStorage.getItem(SESSION_KEY);

  if (!rawSession) {
    return null;
  }

  try {
    return JSON.parse(rawSession);
  } catch {
    clearSession();
    return null;
  }
}

function clearSession() {
  localStorage.removeItem(TOKEN_KEY);
  localStorage.removeItem(SESSION_KEY);
}

function isAuthenticated() {
  return Boolean(localStorage.getItem(TOKEN_KEY));
}

// Centralizamos las redirecciones para no repetir rutas "hardcodeadas".
function redirectToDashboard() {
  window.location.href = APP_ROUTES.dashboard;
}

function redirectToLogin() {
  window.location.href = APP_ROUTES.login;
}

function redirectIfAuthenticated() {
  if (isAuthenticated()) {
    redirectToDashboard();
    return true;
  }

  return false;
}

// Este guard se usa en paginas privadas para cortar el acceso si no hay token.
function requireAuth() {
  if (!isAuthenticated()) {
    redirectToLogin();
    return false;
  }

  return true;
}

export {
  clearSession,
  getSession,
  isAuthenticated,
  redirectIfAuthenticated,
  redirectToDashboard,
  redirectToLogin,
  requireAuth,
  saveSession,
};
