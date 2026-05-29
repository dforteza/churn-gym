import { APP_ROUTES } from './config.js';

const SESSION_KEY = 'auth_user';
const TOKEN_KEY = 'jwt_token';

// Guarda el token y los datos visibles del usuario para reutilizarlos
// en otras pantallas sin solicitar de nuevo el inicio de sesión.
function saveSession({ token, username, rol }) {
  localStorage.setItem(TOKEN_KEY, token);
  localStorage.setItem(SESSION_KEY, JSON.stringify({ username, rol }));
}

// Recupera la sesión almacenada y la elimina si no tiene un formato válido.
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

// Elimina los datos locales asociados a la sesión del usuario.
function clearSession() {
  localStorage.removeItem(TOKEN_KEY);
  localStorage.removeItem(SESSION_KEY);
}

// Comprueba si existe un token de autenticación almacenado.
function isAuthenticated() {
  return Boolean(localStorage.getItem(TOKEN_KEY));
}

// Redirige al usuario al dashboard principal.
function redirectToDashboard() {
  window.location.href = APP_ROUTES.dashboard;
}

// Redirige al usuario a la pantalla de inicio de sesión.
function redirectToLogin() {
  window.location.href = APP_ROUTES.login;
}

// Evita que un usuario autenticado vuelva a acceder al formulario de inicio de sesión.
function redirectIfAuthenticated() {
  if (isAuthenticated()) {
    redirectToDashboard();
    return true;
  }

  return false;
}

// Protege las páginas privadas redirigiendo al login si no existe una sesión válida.
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