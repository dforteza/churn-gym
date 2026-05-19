import { APP_ROUTES } from '../config.js';
import { apiGetAnalisis, apiLanzarAnalisis } from '../api/analisis-api.js';
import { clearSession, getSession, redirectToLogin, requireAuth } from '../auth.js';
import { prepareCampaignForClients } from '../services/campaign-service.js';
import { exportClientsToExcel } from '../services/export-service.js';
import { getDashboardElements } from '../dashboard/elements.js';
import { countByRisk, extractPagination, extractRows, getFilteredRows, getSelectedRows } from '../dashboard/data.js';
import { renderEmpty, renderPagination, renderRiskChart, renderRows, syncSelectionUi } from '../dashboard/render.js';

// Estado principal del dashboard: análisis cargado, filas disponibles, selección y paginación.
const state = {
  analisis: null,
  rows: [],
  selectedIds: new Set(),
  page: 0,
  totalPages: 1,
  totalElements: 0,
  initialLoaded: false,
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

  elements.perfil.addEventListener('click', () => { window.location.href = APP_ROUTES.perfil; });
  elements.logout.addEventListener('click', handleLogout);

  // Filtros de backend: cada cambio lanza una nueva petición desde la página 0.
  elements.riskFilter.addEventListener('change',   () => loadDashboard());
  elements.grupoFilter.addEventListener('change',  () => loadDashboard());
  elements.franjaFilter.addEventListener('change', () => loadDashboard());
  elements.deporteFilter.addEventListener('change', () => loadDashboard());

  // Búsqueda de texto: filtrado en memoria sobre los resultados de la página actual.
  elements.search.addEventListener('input', refreshTable);

  elements.tableBody.addEventListener('click', handleTableClick);
  elements.tableBody.addEventListener('change', handleTableSelection);
  elements.selectAll.addEventListener('change', handleSelectAllVisible);
  elements.sendCampaign.addEventListener('click', handleSendCampaign);
  elements.exportSelected.addEventListener('click', handleExportSelected);

  elements.prevPage.addEventListener('click', () => loadDashboard({ page: state.page - 1 }));
  elements.nextPage.addEventListener('click', () => loadDashboard({ page: state.page + 1 }));

  elements.refresh.addEventListener('click', async () => {
    await loadDashboard({ relaunch: true });
  });

  loadDashboard();
}

// Carga el análisis desde la API y actualiza el estado visual del dashboard.
async function loadDashboard({ relaunch = false, page = 0 } = {}) {
  setFeedback('');
  elements.refresh.disabled = true;
  elements.refresh.textContent = relaunch ? 'Actualizando...' : 'Cargando...';
  state.page = page;

  try {
    const filters = getActiveFilters();

    if (relaunch) {
      await apiLanzarAnalisis();
    }

    const analisis = await apiGetAnalisis({ ...filters, page });

    state.analisis = analisis;
    state.rows = extractRows(analisis.resultados);

    const pagination = extractPagination(analisis.resultados);
    state.totalPages    = pagination.totalPages;
    state.totalElements = pagination.totalElements;

    // Si es la primera carga y no hay resultados, lanza el análisis automáticamente.
    if (!relaunch && !state.initialLoaded && state.totalElements === 0) {
      state.initialLoaded = true;
      await loadDashboard({ relaunch: true });
      return;
    }
    state.initialLoaded = true;

    // Mantiene seleccionados únicamente los clientes que siguen en la página actual.
    state.selectedIds = new Set(
      [...state.selectedIds].filter((id) => state.rows.some((row) => String(row.clienteId) === String(id))),
    );

    renderRiskChart(elements, analisis, state.rows, (risk) => countByRisk(state.rows, risk));
    refreshTable();
  } catch (error) {
    resetDashboardState();
    setFeedback(error.message || 'No se han podido cargar los datos.');
    elements.resultCount.textContent = '0 resultados';
    syncSelectionUi(elements, [], state.selectedIds, 0);
    renderEmpty(elements, 'No se han podido cargar los datos.');
    renderPagination(elements, 0, 1);
  } finally {
    elements.refresh.disabled = false;
    elements.refresh.textContent = 'Actualizar análisis';
  }
}

// Aplica la búsqueda de texto y vuelve a renderizar la tabla y la paginación.
function refreshTable() {
  const visibleRows = getVisibleRows();
  const selectedRows = getCurrentSelectedRows();

  elements.resultCount.textContent = `${state.totalElements} resultados · Página ${state.page + 1} de ${Math.max(state.totalPages, 1)}`;
  renderRows(elements, visibleRows, state.selectedIds, APP_ROUTES.clientDetail);
  renderPagination(elements, state.page, state.totalPages);
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

// Devuelve los valores activos de los filtros de backend.
function getActiveFilters() {
  return {
    nivelRiesgo:     elements.riskFilter.value   || undefined,
    grupo:           elements.grupoFilter.value  || undefined,
    franjaHoraria:   elements.franjaFilter.value || undefined,
    deportePrincipal: elements.deporteFilter.value || undefined,
  };
}

// Devuelve las filas visibles aplicando solo la búsqueda de texto (filtros de backend ya aplicados).
function getVisibleRows() {
  return getFilteredRows(state.rows, { query: elements.search.value });
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
  state.page = 0;
  state.totalPages = 1;
  state.totalElements = 0;
}

// Muestra mensajes informativos o de error en la pantalla.
function setFeedback(message) {
  elements.feedback.textContent = message;
}