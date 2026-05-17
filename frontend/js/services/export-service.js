import { downloadBlob } from '../utils/download.js';
import { escapeXml } from '../utils/escape.js';
import { formatEnum, formatProbabilityNumber, getGroupLabel } from '../utils/formatters.js';

// Genera una celda XML compatible con Excel, aplicando formato de texto o número.
function createCell(value, type = 'String') {
  if (value === null || value === undefined || value === '') {
    return '<Cell ss:StyleID="text"><Data ss:Type="String">--</Data></Cell>';
  }

  if (type === 'Number') {
    return `<Cell ss:StyleID="number"><Data ss:Type="Number">${Number(value)}</Data></Cell>`;
  }

  return `<Cell ss:StyleID="text"><Data ss:Type="String">${escapeXml(String(value))}</Data></Cell>`;
}

// Construye el contenido XML del libro de Excel con los clientes seleccionados.
function buildExcelWorkbook(rows) {
  const headers = [
    'Cliente ID',
    'Nombre',
    'Correo electrónico',
    'Nivel de riesgo',
    'Probabilidad de abandono (%)',
    'Frecuencia semanal',
    'Semanas de inactividad',
    'Tendencia mensual (%)',
    'Meses como socio',
    'Franja horaria',
    'Deporte principal',
    'Grupo',
  ];

  const dataRows = rows.map((cliente) => ([
    createCell(cliente.clienteId, 'Number'),
    createCell(`${cliente.nombre} ${cliente.apellidos}`),
    createCell(cliente.email),
    createCell(cliente.nivelRiesgo),
    createCell(formatProbabilityNumber(cliente.probabilidadAbandono), 'Number'),
    createCell(cliente.frecuenciaSemanal, 'Number'),
    createCell(cliente.semanasInactivo, 'Number'),
    createCell(cliente.tendenciaMensual, 'Number'),
    createCell(cliente.mesesComoSocio, 'Number'),
    createCell(formatEnum(cliente.franjaHoraria)),
    createCell(formatEnum(cliente.deportePrincipal)),
    createCell(getGroupLabel(cliente.grupo)),
  ]));

  const headerXml = headers.map((header) => `
          <Cell ss:StyleID="header"><Data ss:Type="String">${escapeXml(header)}</Data></Cell>`).join('');

  const rowsXml = dataRows.map((row) => `
        <Row>${row.join('')}</Row>`).join('');

  return `<?xml version="1.0"?>
<Workbook xmlns="urn:schemas-microsoft-com:office:spreadsheet"
 xmlns:o="urn:schemas-microsoft-com:office:office"
 xmlns:x="urn:schemas-microsoft-com:office:excel"
 xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet">
  <Styles>
    <Style ss:ID="header">
      <Font ss:Bold="1" ss:Color="#FFFFFF"/>
      <Interior ss:Color="#1F6F5F" ss:Pattern="Solid"/>
      <Alignment ss:Horizontal="Center" ss:Vertical="Center"/>
    </Style>
    <Style ss:ID="text">
      <Alignment ss:Vertical="Center"/>
    </Style>
    <Style ss:ID="number">
      <Alignment ss:Horizontal="Right" ss:Vertical="Center"/>
    </Style>
  </Styles>
  <Worksheet ss:Name="Clientes seleccionados">
    <Table>
      <Row>${headerXml}
      </Row>${rowsXml}
    </Table>
  </Worksheet>
</Workbook>`;
}

// Exporta los clientes seleccionados a un archivo Excel descargable.
function exportClientsToExcel(rows) {
  if (rows.length === 0) {
    throw new Error('Selecciona al menos un cliente para exportar.');
  }

  const workbookXml = buildExcelWorkbook(rows);
  const blob = new Blob([workbookXml], { type: 'application/vnd.ms-excel;charset=utf-8;' });
  const fileName = `clientes-seleccionados-${new Date().toISOString().slice(0, 10)}.xls`;

  downloadBlob(fileName, blob);

  return {
    count: rows.length,
    fileName,
  };
}

export {
  exportClientsToExcel,
};