import { MOCK_MODE } from '../config.js';
import { apiLogin } from '../api/auth-api.js';
import { redirectIfAuthenticated, redirectToDashboard, saveSession } from '../auth.js';

const MOCK_CREDENTIALS = {
  username: 'admin',
  password: 'admin1234',
};

const MOCK_LOGIN_RESPONSE = {
  token: 'mock-jwt-token',
  username: 'admin',
  rol: 'ADMIN',
};

const form = document.querySelector('[data-login-form]');
const submitButton = document.querySelector('[data-submit-button]');
const feedback = document.querySelector('[data-feedback]');

// Evita mostrar el formulario de inicio de sesión si el usuario ya está autenticado.
redirectIfAuthenticated();

// Muestra mensajes informativos o de error en el formulario.
function setFeedback(message, type = 'error') {
  feedback.textContent = message;
  feedback.dataset.state = type;
}

// Limpia el mensaje de estado del formulario.
function clearFeedback() {
  feedback.textContent = '';
  feedback.dataset.state = 'idle';
}

// Gestiona el inicio de sesión según el modo de ejecución configurado.
async function loginWithApi({ username, password }) {
  if (MOCK_MODE) {
    // Valida las credenciales de prueba antes de devolver la respuesta simulada.
    if (username !== MOCK_CREDENTIALS.username || password !== MOCK_CREDENTIALS.password) {
      throw new Error('Usuario o contraseña incorrectos.');
    }

    return MOCK_LOGIN_RESPONSE;
  }

  return apiLogin(username, password);
}

form.addEventListener('submit', async (event) => {
  event.preventDefault();
  clearFeedback();

  // Extrae los valores del formulario sin depender directamente de los campos del DOM.
  const formData = new FormData(form);
  const username = formData.get('username')?.toString().trim() || '';
  const password = formData.get('password')?.toString() || '';

  if (!username || !password) {
    setFeedback('Introduce usuario y contraseña.');
    return;
  }

  submitButton.disabled = true;
  submitButton.textContent = 'Accediendo...';

  try {
    const response = await loginWithApi({ username, password });

    // Guarda la sesión antes de redirigir para que el dashboard pueda cargarla al iniciarse.
    saveSession(response);
    redirectToDashboard();
  } catch (error) {
    setFeedback(error.message);
  } finally {
    submitButton.disabled = false;
    submitButton.textContent = 'Entrar';
  }
});