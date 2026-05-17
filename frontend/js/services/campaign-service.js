// Construye el enlace mailto con los destinatarios en copia oculta.
function buildCampaignMailto(emails) {
  const subject = 'Campaña de fidelización Churn Gym';
  const body = [
    'Hola,',
    '',
    'Queremos agradecerte que formes parte de Churn Gym.',
    'Hemos preparado una campaña especial de fidelización con recomendaciones, seguimiento y ventajas para ayudarte a mantener tu rutina.',
    '',
    'Si quieres, responde a este correo y te ayudaremos a encontrar el plan que mejor encaje contigo.',
    '',
    'Un saludo,',
    'Equipo Churn Gym',
  ].join('\n');

  const params = new URLSearchParams({
    bcc: emails.join(','),
    subject,
    body,
  });

  return `mailto:?${params.toString()}`;
}

// Prepara la campaña de correo para los clientes seleccionados en el dashboard.
function prepareCampaignForClients(rows) {
  const emails = [...new Set(rows.map((cliente) => cliente.email).filter(Boolean))];

  if (emails.length === 0) {
    throw new Error('Selecciona al menos un cliente con correo electrónico para preparar la campaña.');
  }

  return {
    count: rows.length,
    mailto: buildCampaignMailto(emails),
  };
}

export {
  prepareCampaignForClients,
};