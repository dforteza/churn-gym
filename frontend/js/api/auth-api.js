import { MOCK_MODE, apiFetch, getLoginEndpoint } from '../config.js';

// Gestiona la petición de inicio de sesión según el modo de ejecución configurado.
function apiLogin(username, password) {
  return apiFetch(getLoginEndpoint(), {
    method: MOCK_MODE ? 'GET' : 'POST',
    body: MOCK_MODE ? undefined : JSON.stringify({ username, password }),
  });
}

export {
  apiLogin,
};