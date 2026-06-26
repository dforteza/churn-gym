import {
  MOCK_MODE,
  apiFetch,
  getAnalisisClienteEndpoint,
  getAnalisisEndpoint,
  getAnalisisLanzarEndpoint,
} from '../config.js';

const PAGE_SIZE = 20;

// Recupera el análisis general que se muestra en el dashboard.
function apiGetAnalisis({ nivelRiesgo, grupo, franjaHoraria, deportePrincipal, nombre, page = 0 } = {}) {
  if (MOCK_MODE) {
    return apiFetch(getAnalisisEndpoint());
  }

  const url = new URL(getAnalisisEndpoint());

  if (nivelRiesgo)      url.searchParams.set('nivelRiesgo',      nivelRiesgo);
  if (grupo)            url.searchParams.set('grupo',            grupo);
  if (franjaHoraria)    url.searchParams.set('franjaHoraria',    franjaHoraria);
  if (deportePrincipal) url.searchParams.set('deportePrincipal', deportePrincipal);
  if (nombre)           url.searchParams.set('nombre',           nombre);

  url.searchParams.set('page', page);
  url.searchParams.set('size', PAGE_SIZE);
  url.searchParams.set('sort', 'probabilidadAbandono,desc');

  return apiFetch(url);
}

// Solicita el lanzamiento o recálculo del análisis completo.
function apiLanzarAnalisis() {
  if (MOCK_MODE) {
    // En modo mock se introduce un pequeño retardo para simular el tiempo
    // de procesamiento de un recálculo real.
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        apiFetch(getAnalisisLanzarEndpoint()).then(resolve).catch(reject);
      }, 900);
    });
  }

  return apiFetch(getAnalisisLanzarEndpoint(), { method: 'POST' });
}

// Recupera la información detallada de un cliente concreto.
async function apiGetDetalleCliente(clientId) {
  if (!MOCK_MODE) {
    return apiFetch(getAnalisisClienteEndpoint(clientId));
  }

  // En modo mock se reutiliza el listado principal para poder abrir
  // el detalle de cualquier cliente incluido en los resultados.
  const analisis = await apiGetAnalisis();
  const rows = Array.isArray(analisis?.resultados)
    ? analisis.resultados
    : analisis?.resultados?.content || [];

  const client = rows.find((item) => String(item.clienteId) === String(clientId));

  if (client) {
    return client;
  }

  const fallbackClient = await apiFetch(getAnalisisClienteEndpoint(clientId));

  if (String(fallbackClient?.clienteId) === String(clientId)) {
    return fallbackClient;
  }

  throw new Error('Cliente no encontrado.');
}

export {
  apiGetAnalisis,
  apiGetDetalleCliente,
  apiLanzarAnalisis,
};