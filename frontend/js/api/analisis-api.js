import {
  MOCK_MODE,
  apiFetch,
  getAnalisisClienteEndpoint,
  getAnalisisEndpoint,
  getAnalisisLanzarEndpoint,
} from '../config.js';

// Recupera el análisis general que se muestra en el dashboard.
function apiGetAnalisis() {
  if (MOCK_MODE) {
    return apiFetch(getAnalisisEndpoint());
  }

  const url = new URL(getAnalisisEndpoint());

  // Se solicitan todos los clientes en una única petición para que los filtros
  // y las selecciones del dashboard no se vean afectados por la paginación.
  url.searchParams.set('size', '1000');
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