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

// Filtra los clientes por el texto del buscador. Los demás filtros los gestiona el backend.
function getFilteredRows(rows, { query }) {
  const normalizedQuery = query.trim().toLowerCase();

  if (!normalizedQuery) return rows;

  return rows.filter((cliente) => {
    const searchable = [
      cliente.nombre,
      cliente.apellidos,
      cliente.email,
      cliente.grupo,
      getGroupLabel(cliente.grupo),
      formatEnum(cliente.deportePrincipal),
      formatEnum(cliente.franjaHoraria),
    ].join(' ').toLowerCase();

    return searchable.includes(normalizedQuery);
  });
}

// Extrae la información de paginación del objeto Page devuelto por Spring.
function extractPagination(resultados) {
  if (resultados && typeof resultados === 'object' && !Array.isArray(resultados)) {
    return {
      totalPages:    resultados.totalPages    ?? 1,
      totalElements: resultados.totalElements ?? 0,
      number:        resultados.number        ?? 0,
    };
  }

  return { totalPages: 1, totalElements: 0, number: 0 };
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
  extractPagination,
  extractRows,
  getFilteredRows,
  getSelectedRows,
};