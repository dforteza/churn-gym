// Convierte cada palabra de una cadena para que comience con mayúscula.
function toTitleCase(value) {
  return String(value)
    .split(' ')
    .map((word) => (word ? `${word.charAt(0).toUpperCase()}${word.slice(1)}` : word))
    .join(' ');
}

// Formatea una fecha en formato corto español, incluyendo fecha y hora.
function formatDate(value) {
  if (!value) {
    return '--';
  }

  return new Intl.DateTimeFormat('es-ES', {
    dateStyle: 'short',
    timeStyle: 'short',
  }).format(new Date(value));
}

// Formatea una probabilidad como porcentaje visible para la interfaz.
function formatProbability(value) {
  const numericValue = Number(value);

  if (!Number.isFinite(numericValue)) {
    return '--';
  }

  const percentage = numericValue <= 1 ? numericValue * 100 : numericValue;
  return `${percentage.toFixed(1)}%`;
}

// Devuelve la probabilidad como número porcentual para cálculos o exportaciones.
function formatProbabilityNumber(value) {
  const numericValue = Number(value);

  if (!Number.isFinite(numericValue)) {
    return 0;
  }

  return numericValue <= 1 ? Number((numericValue * 100).toFixed(1)) : Number(numericValue.toFixed(1));
}

// Normaliza valores de tipo enum para mostrarlos de forma legible.
function formatEnum(value, { titleCase = false } = {}) {
  const normalizedValue = String(value ?? '--')
    .replaceAll('_', ' ')
    .toLowerCase();

  return titleCase ? toTitleCase(normalizedValue) : normalizedValue;
}

// Traduce los grupos internos del análisis a etiquetas comprensibles para el usuario.
function getGroupLabel(value, { titleCase = false } = {}) {
  if (!value) {
    return '--';
  }

  const labels = {
    CONSOLIDADO_EN_RIESGO: 'socio consolidado en riesgo',
    VETERANO_EN_PAUSA:     'socio veterano en pausa',
    VETERANO_ESPORADICO:   'socio veterano esporádico',
    NUEVO_SIN_ENGANCHE:    'socio nuevo que no engancha',
    NUEVO_ENGANCHADO:      'socio nuevo enganchado',
    ACTIVO_ESTABLE:        'socio activo estable',
    IRREGULAR:             'socio irregular',
  };

  const normalizedValue = labels[String(value)] || String(value).toLowerCase();
  return titleCase ? toTitleCase(normalizedValue) : normalizedValue;
}

export {
  formatDate,
  formatEnum,
  formatProbability,
  formatProbabilityNumber,
  getGroupLabel,
};