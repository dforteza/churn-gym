import { APP_ROUTES } from '../config.js';
import { apiGetAnalisis, apiLanzarAnalisis } from '../api/analisis-api.js';
import { clearSession, getSession, redirectToLogin, requireAuth } from '../auth.js';
import { prepareCampaignForClients } from '../services/campaign-service.js';
import { exportClientsToExcel } from '../services/export-service.js';
import { getDashboardElements } from '../dashboard/elements.js';
import { countByRisk, extractRows, getFilteredRows, getSelectedRows } from '../dashboard/data.js';
import { renderEmpty, renderRiskChart, renderRows, syncSelectionUi } from '../dashboard/render.js';

// Estado principal del dashboard: análisis cargado, filas disponibles y selección actual.
const state = {
  analisis: null,
  rows: [],
  selectedIds: new Set(),
};

// Referencias a los elementos del DOM utilizados por la vista.
const elements = getDashboardElements();

// El dashboard solo se inicializa si el usuario tiene una sesión válida.
if (requireAuth()) {
  initDashboard();
}

// Configura los eventos principales de la pantalla y realiza la carga inicial.
function initDashboard() {
  const session = getSession();
  elements.userLabel.textContent = session?.username || 'Usuario';

  elements.logout.addEventListener('click', handleLogout);
  elements.riskFilter.addEventListener('change', refreshTable);
  elements.search.addEventListener('input', refreshTable);
  elements.tableBody.addEventListener('click', handleTableClick);
  elements.tableBody.addEventListener('change', handleTableSelection);
  elements.selectAll.addEventListener('change', handleSelectAllVisible);
  elements.sendCampaign.addEventListener('click', handleSendCampaign);
  elements.exportSelected.addEventListener('click', handleExportSelected);

  elements.refresh.addEventListener('click', async () => {
    await loadDashboard({ relaunch: true });
  });

  loadDashboard();
}

// Carga el análisis desde la API y actualiza el estado visual del dashboard.
async function loadDashboard({ relaunch = false } = {}) {
  setFeedback('');
  elements.refresh.disabled = true;
  elements.refresh.textContent = relaunch ? 'Actualizando...' : 'Cargando...';

  try {
    const analisis = relaunch ? await apiLanzarAnalisis() : await apiGetAnalisis();

    state.analisis = analisis;
    state.rows = extractRows(analisis.resultados);

    // Mantiene seleccionados únicamente los clientes que siguen existiendo tras recargar datos.
    state.selectedIds = new Set(
      [...state.selectedIds].filter((id) => state.rows.some((row) => String(row.clienteId) === String(id))),
    );

    renderRiskChart(elements, analisis, state.rows, (risk) => countByRisk(state.rows, risk));
    refreshTable();
  } catch (error) {
    resetDashboardState();
    setFeedback(error.message || 'No se han podido cargar los datos.');
    elements.resultCount.textContent = '0 resultados visibles';
    syncSelectionUi(elements, [], state.selectedIds, 0);
    renderEmpty(elements, 'No se han podido cargar los datos.');
  } finally {
    elements.refresh.disabled = false;
    elements.refresh.textContent = 'Actualizar análisis';
  }
}

// Aplica los filtros actuales y vuelve a renderizar la tabla.
function refreshTable() {
  const visibleRows = getVisibleRows();
  const selectedRows = getCurrentSelectedRows();

  elements.resultCount.textContent = `${visibleRows.length} resultados visibles`;
  renderRows(elements, visibleRows, state.selectedIds, APP_ROUTES.clientDetail);
  syncSelectionUi(elements, visibleRows, state.selectedIds, selectedRows.length);
}

// Cierra la sesión del usuario y redirige a la pantalla de inicio de sesión.
function handleLogout() {
  clearSession();
  redirectToLogin();
}

// Valida que el enlace de detalle contiene el identificador del cliente seleccionado.
function handleTableClick(event) {
  const trigger = event.target.closest('[data-open-detail]');

  if (!trigger) {
    return;
  }

  const clientId = trigger.dataset.clientId;

  if (!clientId) {
    event.preventDefault();
    setFeedback('No se ha encontrado el cliente seleccionado.');
  }
}

// Actualiza la selección cuando el usuario marca o desmarca una fila.
function handleTableSelection(event) {
  const checkbox = event.target.closest('[data-row-select]');

  if (!checkbox) {
    return;
  }

  const clientId = String(checkbox.dataset.clientId || '');

  if (!clientId) {
    return;
  }

  if (checkbox.checked) {
    state.selectedIds.add(clientId);
  } else {
    state.selectedIds.delete(clientId);
  }

  const visibleRows = getVisibleRows();
  const selectedRows = getCurrentSelectedRows();
  syncSelectionUi(elements, visibleRows, state.selectedIds, selectedRows.length);
}

// Marca o desmarca todos los clientes visibles según los filtros aplicados.
function handleSelectAllVisible(event) {
  const visibleRows = getVisibleRows();

  visibleRows.forEach((cliente) => {
    const clientId = String(cliente.clienteId);

    if (event.target.checked) {
      state.selectedIds.add(clientId);
    } else {
      state.selectedIds.delete(clientId);
    }
  });

  refreshTable();
}

// Prepara una campaña de correo dirigida a los clientes seleccionados.
function handleSendCampaign() {
  try {
    const selectedRows = getCurrentSelectedRows();
    const campaign = prepareCampaignForClients(selectedRows);

    window.location.href = campaign.mailto;
    setFeedback(`Campaña preparada para ${campaign.count} clientes en tu aplicación de correo.`);
  } catch (error) {
    setFeedback(error.message);
  }
}

// Exporta a Excel los clientes seleccionados en el dashboard.
function handleExportSelected() {
  try {
    const selectedRows = getCurrentSelectedRows();
    const exportResult = exportClientsToExcel(selectedRows);

    setFeedback(`Se ha exportado un Excel con ${exportResult.count} clientes seleccionados.`);
  } catch (error) {
    setFeedback(error.message);
  }
}

// Devuelve las filas visibles tras aplicar el filtro de riesgo y el buscador.
function getVisibleRows() {
  return getFilteredRows(state.rows, {
    risk: elements.riskFilter.value,
    query: elements.search.value,
  });
}

// Obtiene los clientes seleccionados a partir del estado global de selección.
function getCurrentSelectedRows() {
  return getSelectedRows(state.rows, state.selectedIds);
}

// Restablece el estado interno del dashboard cuando no se pueden cargar los datos.
function resetDashboardState() {
  state.analisis = null;
  state.rows = [];
  state.selectedIds.clear();
}

// Muestra mensajes informativos o de error en la pantalla.
function setFeedback(message) {
  elements.feedback.textContent = message;
}