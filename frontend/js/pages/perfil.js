import { APP_ROUTES } from '../config.js';
import { apiGetPerfil, apiUpdatePerfil } from '../api/perfil-api.js';
import { clearSession, getSession, redirectToLogin, requireAuth } from '../auth.js';

const elements = {
  userLabel:    document.querySelector('[data-user-label]'),
  back:         document.querySelector('[data-back]'),
  logout:       document.querySelector('[data-logout]'),
  feedback:     document.querySelector('[data-perfil-feedback]'),

  // Campos que aparecen tanto en el hero como en el panel — se actualizan todos.
  gymNames:     document.querySelectorAll('[data-gym-name]'),
  gymDir1s:     document.querySelectorAll('[data-gym-dir1]'),
  gymDir2s:     document.querySelectorAll('[data-gym-dir2]'),

  // Campos únicos
  gymTel:       document.querySelector('[data-gym-tel]'),
  gymEmail:     document.querySelector('[data-gym-email]'),
  gymUsername:  document.querySelector('[data-gym-username]'),
  gymSince:     document.querySelector('[data-gym-since]'),

  // Formulario de edición
  editToggle:   document.querySelector('[data-edit-toggle]'),
  editForm:     document.querySelector('[data-edit-form]'),
  inputName:    document.querySelector('[data-input-name]'),
  inputDir1:    document.querySelector('[data-input-dir1]'),
  inputDir2:    document.querySelector('[data-input-dir2]'),
  inputTel:     document.querySelector('[data-input-tel]'),
  cancelEdit:   document.querySelector('[data-cancel-edit]'),
};

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

  elements.editToggle.addEventListener('click', () => toggleForm(true));
  elements.cancelEdit.addEventListener('click', () => toggleForm(false));
  elements.editForm.addEventListener('submit', handleSubmit);

  loadPerfil();
}

async function loadPerfil() {
  try {
    const perfil = await apiGetPerfil();
    renderPerfil(perfil);
  } catch (error) {
    setFeedback(error.message || 'No se ha podido cargar el perfil.');
  }
}

function renderPerfil(perfil) {
  elements.gymNames.forEach(el => { el.textContent = perfil.nombreGimnasio || '—'; });
  elements.gymDir1s.forEach(el => { el.textContent = perfil.direccion1     || '—'; });
  elements.gymDir2s.forEach(el => { el.textContent = perfil.direccion2     || '—'; });
  elements.gymTel.textContent      = perfil.telefono || '—';
  elements.gymEmail.textContent    = perfil.email;
  elements.gymUsername.textContent = perfil.username;
  elements.gymSince.textContent    = formatDate(perfil.createdAt);

  elements.inputName.value = perfil.nombreGimnasio || '';
  elements.inputDir1.value = perfil.direccion1     || '';
  elements.inputDir2.value = perfil.direccion2     || '';
  elements.inputTel.value  = perfil.telefono       || '';
}

async function handleSubmit(event) {
  event.preventDefault();
  setFeedback('');

  const submitBtn = elements.editForm.querySelector('[data-submit-edit]');
  submitBtn.disabled = true;
  submitBtn.textContent = 'Guardando...';

  try {
    const perfil = await apiUpdatePerfil({
      nombreGimnasio: elements.inputName.value.trim() || null,
      direccion1:     elements.inputDir1.value.trim() || null,
      direccion2:     elements.inputDir2.value.trim() || null,
      telefono:       elements.inputTel.value.trim()  || null,
    });

    renderPerfil(perfil);
    toggleForm(false);
    setFeedback('Perfil actualizado correctamente.');
  } catch (error) {
    setFeedback(error.message || 'No se ha podido guardar el perfil.');
  } finally {
    submitBtn.disabled = false;
    submitBtn.textContent = 'Guardar cambios';
  }
}

function toggleForm(show) {
  elements.editForm.hidden   = !show;
  elements.editToggle.hidden = show;
  setFeedback('');
}

function formatDate(isoString) {
  if (!isoString) return '—';
  return new Date(isoString).toLocaleDateString('es-ES', {
    day: 'numeric', month: 'long', year: 'numeric',
  });
}

function setFeedback(message) {
  elements.feedback.textContent = message;
}
