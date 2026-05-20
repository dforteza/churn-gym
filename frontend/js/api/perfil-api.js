import { apiFetch, getPerfilEndpoint } from '../config.js';

function apiGetPerfil() {
  return apiFetch(getPerfilEndpoint());
}

function apiUpdatePerfil(data) {
  return apiFetch(getPerfilEndpoint(), {
    method: 'PUT',
    body: JSON.stringify(data),
  });
}

export { apiGetPerfil, apiUpdatePerfil };
