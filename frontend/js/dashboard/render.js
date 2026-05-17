import { escapeHtml } from '../utils/escape.js';
import { formatDate, formatProbability } from '../utils/formatters.js';

// Actualiza el gráfico circular y los indicadores generales del análisis.
function renderRiskChart(elements, analisis, rows, countByRisk) {
  const totals = {
    total: analisis.totalClientes ?? rows.length,
    high: analisis.alto ?? countByRisk('ALTO'),
    medium: analisis.medio ?? countByRisk('MEDIO'),
    low: analisis.bajo ?? countByRisk('BAJO'),
  };

  const safeTotal = totals.total || 1;
  const highPercent = (totals.high / safeTotal) * 100;
  const mediumPercent = (totals.medium / safeTotal) * 100;

  elements.riskChart.style.background = `
    conic-gradient(
      var(--high) 0% ${highPercent}%,
      var(--medium) ${highPercent}% ${highPercent + mediumPercent}%,
      var(--low) ${highPercent + mediumPercent}% 100%
    )
  `;

  elements.riskChart.setAttribute(
    'aria-label',
    `Gráfica circular del riesgo global. Alto ${totals.high}, medio ${totals.medium}, bajo ${totals.low}.`,
  );

  elements.chartTotal.textContent = totals.total;
  elements.chartHigh.textContent = `${totals.high} clientes`;
  elements.chartMedium.textContent = `${totals.medium} clientes`;
  elements.chartLow.textContent = `${totals.low} clientes`;
  elements.chartCaption.textContent = 'Distribución actual por intervalos de riesgo.';
  elements.calculated.textContent = `Calculado en ${formatDate(analisis.calculadoEn)}`;
}

// Renderiza la tabla de clientes manteniendo el estado de selección actual.
function renderRows(elements, rows, selectedIds, buildDetailUrl) {
  if (rows.length === 0) {
    renderEmpty(elements, 'No hay clientes con esos filtros.');
    return;
  }

  elements.tableBody.innerHTML = rows.map((cliente) => `
    <tr>
      <td>
        <input
          type="checkbox"
          aria-label="Seleccionar a ${escapeHtml(`${cliente.nombre} ${cliente.apellidos}`)}"
          data-row-select
          data-client-id="${cliente.clienteId}"
          ${selectedIds.has(String(cliente.clienteId)) ? 'checked' : ''}
        >
      </td>
      <td class="client-cell">
        <strong>${escapeHtml(`${cliente.nombre} ${cliente.apellidos}`)}</strong>
        <span>${escapeHtml(cliente.email)}</span>
      </td>
      <td><span class="risk-pill ${cliente.nivelRiesgo}">${cliente.nivelRiesgo}</span></td>
      <td>${formatProbability(cliente.probabilidadAbandono)}</td>
      <td>${cliente.frecuenciaSemanal} visitas/sem.</td>
      <td>${cliente.semanasInactivo} semanas</td>
      <td>
        <a
          class="table-link"
          href="${buildDetailUrl(cliente.clienteId)}"
          data-open-detail
          data-client-id="${cliente.clienteId}"
        >
          Ver detalle
        </a>
      </td>
    </tr>
  `).join('');
}

// Muestra una fila informativa cuando no existen datos para visualizar.
function renderEmpty(elements, message) {
  elements.tableBody.innerHTML = `
    <tr>
      <td colspan="7">${escapeHtml(message)}</td>
    </tr>
  `;
}

// Sincroniza los controles de selección y las acciones disponibles del dashboard.
function syncSelectionUi(elements, rows, selectedIds, selectedRowsCount) {
  const visibleIds = rows.map((cliente) => String(cliente.clienteId));
  const selectedVisibleCount = visibleIds.filter((id) => selectedIds.has(id)).length;

  elements.selectionCount.textContent = `${selectedRowsCount} seleccionados`;
  elements.selectionSummary.textContent = selectedRowsCount === 0
    ? 'Selecciona uno o varios clientes para enviar la campaña o exportarlos a Excel.'
    : `La campaña y la exportación usarán los ${selectedRowsCount} clientes marcados en el listado.`;

  elements.sendCampaign.disabled = selectedRowsCount === 0;
  elements.exportSelected.disabled = selectedRowsCount === 0;
  elements.selectAll.checked = rows.length > 0 && selectedVisibleCount === rows.length;
  elements.selectAll.indeterminate = selectedVisibleCount > 0 && selectedVisibleCount < rows.length;
}

export {
  renderEmpty,
  renderRiskChart,
  renderRows,
  syncSelectionUi,
};