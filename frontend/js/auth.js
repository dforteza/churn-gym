import { APP_ROUTES } from './config.js';

const SESSION_KEY = 'auth_user';
const TOKEN_KEY = 'jwt_token';

function saveSession({ token, username, rol }) {
  localStorage.setItem(TOKEN_KEY, token);
  localStorage.setItem(SESSION_KEY, JSON.stringify({ username, rol }));
}

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

function redirectToDashboard() {
  window.location.href = APP_ROUTES.dashboard;
}

function redirectToLogin() {
  window.location.href = APP_ROUTES.login;
}

function redirectIfAuthenticated() {
  if (isAuthenticated()) {
    redirectToDashboard();
  }
}

function requireAuth() {
  if (!isAuthenticated()) {
    redirectToLogin();
  }
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
