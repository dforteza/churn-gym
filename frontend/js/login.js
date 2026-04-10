import { ENDPOINTS, MOCK_MODE, apiFetch } from './config.js';
import { redirectIfAuthenticated, redirectToDashboard, saveSession } from './auth.js';

const MOCK_CREDENTIALS = {
  username: 'admin',
  password: 'admin1234',
};

const form = document.querySelector('[data-login-form]');
const submitButton = document.querySelector('[data-submit-button]');
const feedback = document.querySelector('[data-feedback]');

redirectIfAuthenticated();

function setFeedback(message, type = 'error') {
  feedback.textContent = message;
  feedback.dataset.state = type;
}

function clearFeedback() {
  feedback.textContent = '';
  feedback.dataset.state = 'idle';
}

async function loginWithApi({ username, password }) {
  if (MOCK_MODE) {
    if (username !== MOCK_CREDENTIALS.username || password !== MOCK_CREDENTIALS.password) {
      throw new Error('Credenciales incorrectas. Usa admin / admin1234 en modo mock.');
    }

    return apiFetch(ENDPOINTS.login());
  }

  return apiFetch(ENDPOINTS.login(), {
    method: 'POST',
    body: JSON.stringify({ username, password }),
  });
}

form.addEventListener('submit', async (event) => {
  event.preventDefault();
  clearFeedback();

  const formData = new FormData(form);
  const username = formData.get('username')?.toString().trim() || '';
  const password = formData.get('password')?.toString() || '';

  if (!username || !password) {
    setFeedback('Introduce usuario y contrasena.');
    return;
  }

  submitButton.disabled = true;
  submitButton.textContent = 'Accediendo...';

  try {
    const response = await loginWithApi({ username, password });
    saveSession(response);
    redirectToDashboard();
  } catch (error) {
    setFeedback(error.message);
  } finally {
    submitButton.disabled = false;
    submitButton.textContent = 'Entrar';
  }
});
