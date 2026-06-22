import { APP_ROUTES } from '../config.js';
import { apiGetDetalleCliente } from '../api/analisis-api.js';
import { clearSession, getSession, redirectToLogin, requireAuth } from '../auth.js';
import { formatEnum, formatProbability, getGroupLabel } from '../utils/formatters.js';

// Centraliza las referencias a los elementos del DOM utilizados en la pantalla de detalle.
const elements = {
  userLabel: document.querySelector('[data-user-label]'),
  back: document.querySelector('[data-back]'),
  logout: document.querySelector('[data-logout]'),
  feedback: document.querySelector('[data-detail-feedback]'),
  clientName: document.querySelector('[data-client-name]'),
  clientEmail: document.querySelector('[data-client-email]'),
  clientRisk: document.querySelector('[data-client-risk]'),
  clientProbability: document.querySelector('[data-client-probability]'),
  clientGroup: document.querySelector('[data-client-group]'),
  clientSport: document.querySelector('[data-client-sport]'),
  clientTime: document.querySelector('[data-client-time]'),
  clientMembership: document.querySelector('[data-client-membership]'),
  clientFrequency: document.querySelector('[data-client-frequency]'),
  clientInactive: document.querySelector('[data-client-inactive]'),
  clientTrend: document.querySelector('[data-client-trend]'),
  heroCard:    document.querySelector('.hero-card'),
  probFill:    document.querySelector('[data-prob-fill]'),
};

// La pantalla de detalle aplica la misma protección de sesión que el dashboard.
if (requireAuth()) {
  const session = getSession();
  elements.userLabel.textContent = session?.username || 'Usuario';

  elements.back.addEventListener('click', () => {
    window.location.href = APP_ROUTES.dashboard;
  });

  elements.logout.addEventListener('click', () => {
    clearSession();
    redirectToLogin();
  });

  loadClientDetail();
}

async function loadClientDetail() {
  // El identificador del cliente se obtiene de la URL para permitir enlaces directos al detalle.
  const clientId = new URLSearchParams(window.location.search).get('id');

  if (!clientId) {
    setFeedback('No se ha indicado ningún cliente para mostrar.');
    renderMissingClient();
    return;
  }

  try {
    const client = await apiGetDetalleCliente(clientId);
    renderClient(client);
    setFeedback('');
  } catch (error) {
    setFeedback(error.message || 'No se ha podido cargar el detalle del cliente.');
    renderMissingClient();
  }
}

function renderClient(client) {
  // Renderiza los datos del cliente utilizando una estructura compatible con mock y API real.
  elements.clientName.textContent = `${client.nombre} ${client.apellidos}`;
  elements.clientEmail.textContent = client.email;
  elements.heroCard.className = `hero-card ${client.nivelRiesgo}`;
  elements.clientRisk.textContent = client.nivelRiesgo;
  elements.clientRisk.className = `risk-pill ${client.nivelRiesgo}`;
  elements.clientProbability.textContent = formatProbability(client.probabilidadAbandono);
  elements.clientProbability.className = client.nivelRiesgo;
  elements.probFill.style.width = `${Math.round(client.probabilidadAbandono * 100)}%`;
  elements.probFill.className = `prob-fill ${client.nivelRiesgo}`;
  elements.clientGroup.textContent = getGroupLabel(client.grupo, { titleCase: true });
  elements.clientSport.textContent = formatEnum(client.deportePrincipal, { titleCase: true });
  elements.clientTime.textContent = formatEnum(client.franjaHoraria, { titleCase: true });
  elements.clientMembership.textContent = `${client.mesesComoSocio} meses`;
  elements.clientFrequency.textContent = `${client.frecuenciaSemanal} visitas/sem.`;
  elements.clientInactive.textContent = `${client.semanasInactivo} semanas`;
  const trend = client.tendenciaMensual;
  const arrow = trend < 0 ? '↓' : trend > 0 ? '↑' : '';
  elements.clientTrend.textContent = `${arrow} ${trend}%`.trim();
  elements.clientTrend.className = trend < 0 ? 'trend-negative' : trend > 0 ? 'trend-positive' : '';
}

function renderMissingClient() {
  // Establece un estado neutro para evitar mostrar información parcial si la carga falla.
  elements.clientName.textContent = 'Cliente no disponible';
  elements.clientEmail.textContent = '--';
  elements.clientRisk.textContent = '--';
  elements.clientRisk.className = 'risk-pill';
  elements.clientProbability.textContent = '--';
  elements.clientGroup.textContent = '--';
  elements.clientSport.textContent = '--';
  elements.clientTime.textContent = '--';
  elements.clientMembership.textContent = '--';
  elements.clientFrequency.textContent = '--';
  elements.clientInactive.textContent = '--';
  elements.clientTrend.textContent = '--';
}

function setFeedback(message) {
  elements.feedback.textContent = message;
}