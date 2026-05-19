// Obtiene las referencias a los elementos del DOM utilizados por el dashboard.
function getDashboardElements() {
  return {
    userLabel: document.querySelector('[data-user-label]'),
    logout: document.querySelector('[data-logout]'),
    refresh: document.querySelector('[data-refresh]'),
    riskFilter: document.querySelector('[data-risk-filter]'),
    grupoFilter: document.querySelector('[data-grupo-filter]'),
    franjaFilter: document.querySelector('[data-franja-filter]'),
    deporteFilter: document.querySelector('[data-deporte-filter]'),
    search: document.querySelector('[data-search]'),
    calculated: document.querySelector('[data-calculated]'),
    resultCount: document.querySelector('[data-result-count]'),
    tableBody: document.querySelector('[data-table-body]'),
    feedback: document.querySelector('[data-dashboard-feedback]'),
    selectAll: document.querySelector('[data-select-all]'),
    selectionCount: document.querySelector('[data-selection-count]'),
    selectionSummary: document.querySelector('[data-selection-summary]'),
    sendCampaign: document.querySelector('[data-send-campaign]'),
    exportSelected: document.querySelector('[data-export-selected]'),
    riskChart: document.querySelector('[data-risk-chart]'),
    chartCaption: document.querySelector('[data-chart-caption]'),
    chartTotal: document.querySelector('[data-chart-total]'),
    chartHigh: document.querySelector('[data-chart-high]'),
    chartMedium: document.querySelector('[data-chart-medium]'),
    chartLow: document.querySelector('[data-chart-low]'),
    prevPage: document.querySelector('[data-prev-page]'),
    nextPage: document.querySelector('[data-next-page]'),
    pageInfo: document.querySelector('[data-page-info]'),
  };
}

export {
  getDashboardElements,
};