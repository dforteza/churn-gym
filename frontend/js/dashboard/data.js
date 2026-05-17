import { formatEnum, getGroupLabel } from '../utils/formatters.js';

// Normaliza la estructura de resultados para trabajar siempre con un array de clientes.
function extractRows(resultados) {
  if (Array.isArray(resultados)) {
    return resultados;
  }

  if (Array.isArray(resultados?.content)) {
    return resultados.content;
  }

  return [];
}

// Filtra los clientes por nivel de riesgo y por el texto introducido en el buscador.
function getFilteredRows(rows, { risk, query }) {
  const normalizedQuery = query.trim().toLowerCase();

  return rows.filter((cliente) => {
    const matchesRisk = risk === 'TODOS' || cliente.nivelRiesgo === risk;
    const searchable = [
      cliente.nombre,
      cliente.apellidos,
      cliente.email,
      cliente.grupo,
      getGroupLabel(cliente.grupo),
      formatEnum(cliente.deportePrincipal),
      formatEnum(cliente.franjaHoraria),
    ].join(' ').toLowerCase();

    return matchesRisk && searchable.includes(normalizedQuery);
  });
}

// Devuelve únicamente los clientes que han sido seleccionados en la tabla.
function getSelectedRows(rows, selectedIds) {
  return rows.filter((cliente) => selectedIds.has(String(cliente.clienteId)));
}

// Calcula el número de clientes que pertenecen a un nivel de riesgo concreto.
function countByRisk(rows, risk) {
  return rows.filter((cliente) => cliente.nivelRiesgo === risk).length;
}

export {
  countByRisk,
  extractRows,
  getFilteredRows,
  getSelectedRows,
};