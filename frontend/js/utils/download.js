// Crea una URL temporal para descargar el Blob y la libera después de iniciar la descarga.
function downloadBlob(fileName, blob) {
  const url = URL.createObjectURL(blob);
  const link = document.createElement('a');

  link.href = url;
  link.download = fileName;
  document.body.append(link);
  link.click();
  link.remove();
  URL.revokeObjectURL(url);
}

export {
  downloadBlob,
};
