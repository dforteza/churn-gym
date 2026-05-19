-- V7__insert_socios_simulados.sql
-- =============================================================================
-- 200 socios con distribución realista por grupo de riesgo (Opción A).
-- Organizado en 7 secciones, una por grupo. El primer registro de cada sección
-- es un cliente estratégico pensado para la demo (marcado con -- *).
-- IDs 51–250. No tocar V1–V6 (checksums fijos).
-- =============================================================================


-- ============================================================
-- SECCIÓN 1 — ACTIVO_ESTABLE  (IDs 51–110 · 60 socios)
-- Regla: frecuencia >= 2.0  AND  semanas_inactivo = 0  AND  meses >= 4
-- ============================================================
INSERT INTO clientes_privados (id, nombre, apellidos, dni, gmail) VALUES
(51,  'Elena',          'Rivas Montoya',         '20000051Z', 'elena.rivas@churngym.es'),       -- *
(52,  'Rafael',         'Torres García',          '20000052Z', 'rafael.torres@churngym.es'),
(53,  'Miriam',         'Sánchez López',          '20000053Z', 'miriam.sanchez@churngym.es'),
(54,  'Rubén',          'Gómez Fernández',        '20000054Z', 'ruben.gomez@churngym.es'),
(55,  'Noelia',         'Castro Hernández',       '20000055Z', 'noelia.castro@churngym.es'),
(56,  'Oscar',          'Díaz Martínez',          '20000056Z', 'oscar.diaz@churngym.es'),
(57,  'Sonia',          'Moreno Ruiz',            '20000057Z', 'sonia.moreno@churngym.es'),
(58,  'Héctor',         'Jiménez Pérez',          '20000058Z', 'hector.jimenez@churngym.es'),
(59,  'Vanessa',        'Alonso Romero',          '20000059Z', 'vanessa.alonso@churngym.es'),
(60,  'Enrique',        'Rodríguez Blanco',       '20000060Z', 'enrique.rodriguez@churngym.es'),
(61,  'Rocío',          'Navarro Molina',         '20000061Z', 'rocio.navarro@churngym.es'),
(62,  'Emilio',         'Gutiérrez Torres',       '20000062Z', 'emilio.gutierrez@churngym.es'),
(63,  'Paloma',         'Martín Ortega',          '20000063Z', 'paloma.martin@churngym.es'),
(64,  'Jaime',          'Vázquez Castillo',       '20000064Z', 'jaime.vazquez@churngym.es'),
(65,  'Alicia',         'Suárez Domínguez',       '20000065Z', 'alicia.suarez@churngym.es'),
(66,  'Marcos',         'Herrero Prieto',         '20000066Z', 'marcos.herrero@churngym.es'),
(67,  'Eva',            'Reyes Medina',           '20000067Z', 'eva.reyes@churngym.es'),
(68,  'Felipe',         'Morales Guerrero',       '20000068Z', 'felipe.morales@churngym.es'),
(69,  'Belén',          'Santos Serrano',         '20000069Z', 'belen.santos@churngym.es'),
(70,  'Augusto',        'Ortiz Cano',             '20000070Z', 'augusto.ortiz@churngym.es'),
(71,  'Celia',          'Rubio Marín',            '20000071Z', 'celia.rubio@churngym.es'),
(72,  'Andrés',         'Lozano García',          '20000072Z', 'andres.lozano@churngym.es'),
(73,  'Irene',          'Blanco Sanz',            '20000073Z', 'irene.blanco@churngym.es'),
(74,  'Hugo',           'Delgado Iglesias',       '20000074Z', 'hugo.delgado@churngym.es'),
(75,  'Sandra',         'Cortés Nuñez',           '20000075Z', 'sandra.cortes@churngym.es'),
(76,  'Ángel',          'Medina Garrido',         '20000076Z', 'angel.medina@churngym.es'),
(77,  'Paula',          'Castro Cruz',            '20000077Z', 'paula.castro@churngym.es'),
(78,  'César',          'Ramos Castillo',         '20000078Z', 'cesar.ramos@churngym.es'),
(79,  'Lidia',          'Ortega Santos',          '20000079Z', 'lidia.ortega@churngym.es'),
(80,  'Simón',          'Garrido Lozano',         '20000080Z', 'simon.garrido@churngym.es'),
(81,  'Carla',          'Prieto Rubio',           '20000081Z', 'carla.prieto@churngym.es'),
(82,  'Daniel',         'Morales Serrano',        '20000082Z', 'daniel.morales@churngym.es'),
(83,  'Olga',           'Guerrero Cano',          '20000083Z', 'olga.guerrero@churngym.es'),
(84,  'Rodrigo',        'Castillo Ortiz',         '20000084Z', 'rodrigo.castillo@churngym.es'),
(85,  'Natalia',        'Sanz Blanco',            '20000085Z', 'natalia.sanz@churngym.es'),
(86,  'Iván',           'Nuñez Medina',           '20000086Z', 'ivan.nunez@churngym.es'),
(87,  'Macarena',       'Garrido Torres',         '20000087Z', 'macarena.garrido@churngym.es'),
(88,  'Nicolás',        'Delgado López',          '20000088Z', 'nicolas.delgado@churngym.es'),
(89,  'Esther',         'Marín Romero',           '20000089Z', 'esther.marin@churngym.es'),
(90,  'Borja',          'Iglesias Vázquez',       '20000090Z', 'borja.iglesias@churngym.es'),
(91,  'Gloria',         'Santos García',          '20000091Z', 'gloria.santos@churngym.es'),
(92,  'Xavi',           'Reyes Fernández',        '20000092Z', 'xavi.reyes@churngym.es'),
(93,  'Virginia',       'Moreno Cruz',            '20000093Z', 'virginia.moreno@churngym.es'),
(94,  'Gerard',         'Prieto Díaz',            '20000094Z', 'gerard.prieto@churngym.es'),
(95,  'Leticia',        'Rubio Hernández',        '20000095Z', 'leticia.rubio@churngym.es'),
(96,  'Pau',            'Castillo Martínez',      '20000096Z', 'pau.castillo@churngym.es'),
(97,  'Arantxa',        'Navarro Romero',         '20000097Z', 'arantxa.navarro@churngym.es'),
(98,  'Arnau',          'Ortega Gutiérrez',       '20000098Z', 'arnau.ortega@churngym.es'),
(99,  'Susana',         'Morales Alonso',         '20000099Z', 'susana.morales@churngym.es'),
(100, 'Joan',           'Torres Jiménez',         '20000100Z', 'joan.torres@churngym.es'),
(101, 'Leire',          'Fernández Pérez',        '20000101Z', 'leire.fernandez@churngym.es'),
(102, 'Ferran',         'Blanco Rodríguez',       '20000102Z', 'ferran.blanco@churngym.es'),
(103, 'Amaia',          'García Castillo',        '20000103Z', 'amaia.garcia@churngym.es'),
(104, 'Sergi',          'López Ortiz',            '20000104Z', 'sergi.lopez@churngym.es'),
(105, 'Idoia',          'Martínez Serrano',       '20000105Z', 'idoia.martinez@churngym.es'),
(106, 'Oriol',          'Sánchez Morales',        '20000106Z', 'oriol.sanchez@churngym.es'),
(107, 'Nerea',          'Gómez Santos',           '20000107Z', 'nerea.gomez@churngym.es'),
(108, 'Lluis',          'Hernández Prieto',       '20000108Z', 'lluis.hernandez@churngym.es'),
(109, 'Itziar',         'Díaz Garrido',           '20000109Z', 'itziar.diaz@churngym.es'),
(110, 'Toni',           'Ruiz García',            '20000110Z', 'toni.ruiz@churngym.es');

INSERT INTO clientes_datos (id, cliente_privado_id, anyo_nacimiento, meses_como_socio, franja_horaria, deporte_principal, frecuencia_semanal, semanas_inactivo, tendencia_mensual) VALUES
(51,  51,  1991, 22, 'MANANA', 'MUSCULACION',       5.0, 0,  15.0),  -- *
(52,  52,  1988, 18, 'TARDE',  'MUSCULACION',       4.5, 0,   8.0),
(53,  53,  1992, 12, 'NOCHE',  'CARDIO',            3.0, 0,   5.0),
(54,  54,  1985, 36, 'MANANA', 'CROSSFIT',          4.0, 0,  12.0),
(55,  55,  1990, 24, 'TARDE',  'CLASES_COLECTIVAS', 3.5, 0,   6.0),
(56,  56,  1987, 48, 'MANANA', 'MUSCULACION',       5.5, 0,  18.0),
(57,  57,  1983, 30, 'NOCHE',  'CARDIO',            2.5, 0,  -5.0),
(58,  58,  1994,  8, 'TARDE',  'CROSSFIT',          4.2, 0,  10.0),
(59,  59,  1989, 15, 'MANANA', 'CLASES_COLECTIVAS', 2.8, 0,   3.0),
(60,  60,  1981, 42, 'NOCHE',  'MIXTO',             3.8, 0,  -3.0),
(61,  61,  1993,  6, 'TARDE',  'MUSCULACION',       3.0, 0,   7.0),
(62,  62,  1986, 55, 'MANANA', 'CARDIO',            2.2, 0,  -8.0),
(63,  63,  1979, 28, 'NOCHE',  'CLASES_COLECTIVAS', 4.8, 0,  14.0),
(64,  64,  1995, 10, 'TARDE',  'CROSSFIT',          5.0, 0,  20.0),
(65,  65,  1988, 20, 'MANANA', 'MIXTO',             3.2, 0,   4.0),
(66,  66,  1982, 45, 'NOCHE',  'MUSCULACION',       4.5, 0,  11.0),
(67,  67,  1991, 16, 'TARDE',  'CARDIO',            2.5, 0,   2.0),
(68,  68,  1977, 52, 'MANANA', 'MUSCULACION',       3.5, 0,  -4.0),
(69,  69,  1986,  9, 'NOCHE',  'CLASES_COLECTIVAS', 4.0, 0,   9.0),
(70,  70,  1980, 38, 'TARDE',  'CROSSFIT',          5.2, 0,  16.0),
(71,  71,  1994,  5, 'MANANA', 'CARDIO',            3.8, 0,  12.0),
(72,  72,  1990, 26, 'NOCHE',  'MUSCULACION',       4.8, 0,   7.0),
(73,  73,  1987, 14, 'TARDE',  'CLASES_COLECTIVAS', 2.0, 0,  -2.0),
(74,  74,  1996,  7, 'MANANA', 'CROSSFIT',          5.5, 0,  22.0),
(75,  75,  1984, 32, 'NOCHE',  'CARDIO',            3.2, 0,   5.0),
(76,  76,  1978, 50, 'TARDE',  'MIXTO',             4.0, 0,  -6.0),
(77,  77,  1993, 11, 'MANANA', 'MUSCULACION',       3.5, 0,   8.0),
(78,  78,  1989, 19, 'NOCHE',  'CROSSFIT',          4.5, 0,  13.0),
(79,  79,  1992, 23, 'TARDE',  'CARDIO',            2.5, 0,   3.0),
(80,  80,  1983, 40, 'MANANA', 'MUSCULACION',       6.0, 0,  18.0),
(81,  81,  1997,  4, 'NOCHE',  'CLASES_COLECTIVAS', 2.8, 0,   6.0),
(82,  82,  1985, 35, 'TARDE',  'CROSSFIT',          4.2, 0,  10.0),
(83,  83,  1981, 46, 'MANANA', 'CARDIO',            3.0, 0,  -5.0),
(84,  84,  1988, 17, 'NOCHE',  'MUSCULACION',       5.0, 0,  15.0),
(85,  85,  1991, 21, 'TARDE',  'CLASES_COLECTIVAS', 3.5, 0,   4.0),
(86,  86,  1986, 43, 'MANANA', 'MIXTO',             4.5, 0,   9.0),
(87,  87,  1983, 13, 'NOCHE',  'MUSCULACION',       2.5, 0,  -3.0),
(88,  88,  1994,  8, 'TARDE',  'CROSSFIT',          3.8, 0,  11.0),
(89,  89,  1989, 29, 'MANANA', 'CARDIO',            4.0, 0,   7.0),
(90,  90,  1987, 37, 'NOCHE',  'MUSCULACION',       5.5, 0,  16.0),
(91,  91,  1980, 54, 'TARDE',  'CLASES_COLECTIVAS', 2.2, 0,  -7.0),
(92,  92,  1993,  6, 'MANANA', 'CROSSFIT',          4.8, 0,  14.0),
(93,  93,  1979, 48, 'NOCHE',  'CARDIO',            3.5, 0,  -4.0),
(94,  94,  1995,  9, 'TARDE',  'MUSCULACION',       5.0, 0,  20.0),
(95,  95,  1988, 27, 'MANANA', 'CLASES_COLECTIVAS', 3.0, 0,   5.0),
(96,  96,  1992, 14, 'NOCHE',  'CROSSFIT',          4.2, 0,  12.0),
(97,  97,  1985, 33, 'TARDE',  'MUSCULACION',       3.8, 0,   8.0),
(98,  98,  1990, 20, 'MANANA', 'MIXTO',             4.5, 0,  10.0),
(99,  99,  1984, 44, 'NOCHE',  'CARDIO',            2.8, 0,  -6.0),
(100, 100, 1978, 52, 'TARDE',  'MUSCULACION',       3.2, 0,  -2.0),
(101, 101, 1996,  5, 'MANANA', 'CLASES_COLECTIVAS', 4.0, 0,  15.0),
(102, 102, 1982, 38, 'NOCHE',  'CROSSFIT',          5.5, 0,  13.0),
(103, 103, 1993, 11, 'TARDE',  'CARDIO',            3.5, 0,   6.0),
(104, 104, 1987, 25, 'MANANA', 'MUSCULACION',       4.8, 0,  11.0),
(105, 105, 1981, 47, 'NOCHE',  'CLASES_COLECTIVAS', 2.5, 0,  -8.0),
(106, 106, 1989, 16, 'TARDE',  'CROSSFIT',          4.0, 0,   9.0),
(107, 107, 1991, 30, 'MANANA', 'CARDIO',            3.2, 0,   4.0),
(108, 108, 1985, 42, 'NOCHE',  'MUSCULACION',       5.0, 0,  14.0),
(109, 109, 1979, 55, 'TARDE',  'MIXTO',             2.8, 0,  -5.0),
(110, 110, 1994,  7, 'MANANA', 'CROSSFIT',          4.5, 0,  17.0);


-- ============================================================
-- SECCIÓN 2 — IRREGULAR  (IDs 111–150 · 40 socios)
-- Regla: no encaja en ningún otro grupo (zona gris 4–12 meses
--        o veteranos con 1 semana inactiva)
-- ============================================================
INSERT INTO clientes_privados (id, nombre, apellidos, dni, gmail) VALUES
(111, 'Sofía',          'Mora Leal',              '20000111Z', 'sofia.mora@churngym.es'),        -- *
(112, 'Ramón',          'Pérez Torres',           '20000112Z', 'ramon.perez@churngym.es'),
(113, 'Dolores',        'Martín Hernández',       '20000113Z', 'dolores.martin@churngym.es'),
(114, 'Agustín',        'Gómez López',            '20000114Z', 'agustin.gomez@churngym.es'),
(115, 'Catalina',       'Sánchez Díaz',           '20000115Z', 'catalina.sanchez@churngym.es'),
(116, 'Esteban',        'Fernández García',       '20000116Z', 'esteban.fernandez@churngym.es'),
(117, 'Inmaculada',     'Rodríguez Cruz',         '20000117Z', 'inmaculada.rodriguez@churngym.es'),
(118, 'Julián',         'López Martínez',         '20000118Z', 'julian.lopez@churngym.es'),
(119, 'Esperanza',      'Torres Romero',          '20000119Z', 'esperanza.torres@churngym.es'),
(120, 'Lorenzo',        'Díaz Blanco',            '20000120Z', 'lorenzo.diaz@churngym.es'),
(121, 'Ascensión',      'Moreno Navarro',         '20000121Z', 'ascension.moreno@churngym.es'),
(122, 'Mauricio',       'Gutiérrez Vázquez',      '20000122Z', 'mauricio.gutierrez@churngym.es'),
(123, 'Josefa',         'Hernández Ortega',       '20000123Z', 'josefa.hernandez@churngym.es'),
(124, 'Primitivo',      'Ruiz Castillo',          '20000124Z', 'primitivo.ruiz@churngym.es'),
(125, 'Rosario',        'Alonso Domínguez',       '20000125Z', 'rosario.alonso@churngym.es'),
(126, 'Xavier',         'Vázquez Prieto',         '20000126Z', 'xavier.vazquez@churngym.es'),
(127, 'Maite',          'Navarro Santos',         '20000127Z', 'maite.navarro@churngym.es'),
(128, 'Rafa',           'Castillo Serrano',       '20000128Z', 'rafa.castillo@churngym.es'),
(129, 'Josune',         'Morales Rubio',          '20000129Z', 'josune.morales@churngym.es'),
(130, 'Miquel',         'Torres Blanco',          '20000130Z', 'miquel.torres@churngym.es'),
(131, 'Ainara',         'García Moreno',          '20000131Z', 'ainara.garcia@churngym.es'),
(132, 'Josep',          'Fernández Medina',       '20000132Z', 'josep.fernandez@churngym.es'),
(133, 'Larraitz',       'Sánchez Garrido',        '20000133Z', 'larraitz.sanchez@churngym.es'),
(134, 'Dani',           'Gómez Ortiz',            '20000134Z', 'dani.gomez@churngym.es'),
(135, 'Edurne',         'López García',           '20000135Z', 'edurne.lopez@churngym.es'),
(136, 'Pep',            'Martín Castillo',        '20000136Z', 'pep.martin@churngym.es'),
(137, 'Miren',          'Díaz Hernández',         '20000137Z', 'miren.diaz@churngym.es'),
(138, 'Alexis',         'Rodríguez López',        '20000138Z', 'alexis.rodriguez@churngym.es'),
(139, 'Garbiñe',        'Moreno Torres',          '20000139Z', 'garbine.moreno@churngym.es'),
(140, 'Nacho',          'Gutiérrez Serrano',      '20000140Z', 'nacho.gutierrez@churngym.es'),
(141, 'Concepción',     'Hernández Vázquez',      '20000141Z', 'concepcion.hernandez@churngym.es'),
(142, 'Santi',          'Ruiz Navarro',           '20000142Z', 'santi.ruiz@churngym.es'),
(143, 'Piedad',         'Alonso Cruz',            '20000143Z', 'piedad.alonso@churngym.es'),
(144, 'Carlos',         'Castillo Prieto',        '20000144Z', 'carlos.castillo@churngym.es'),
(145, 'Josefina',       'Vázquez Blanco',         '20000145Z', 'josefina.vazquez@churngym.es'),
(146, 'Emiliano',       'Navarro Morales',        '20000146Z', 'emiliano.navarro@churngym.es'),
(147, 'Remedios',       'Moreno Rubio',           '20000147Z', 'remedios.moreno@churngym.es'),
(148, 'Victorino',      'Gutiérrez Lozano',       '20000148Z', 'victorino.gutierrez@churngym.es'),
(149, 'Filomena',       'Hernández García',       '20000149Z', 'filomena.hernandez@churngym.es'),
(150, 'Baldomero',      'López Serrano',          '20000150Z', 'baldomero.lopez@churngym.es');

INSERT INTO clientes_datos (id, cliente_privado_id, anyo_nacimiento, meses_como_socio, franja_horaria, deporte_principal, frecuencia_semanal, semanas_inactivo, tendencia_mensual) VALUES
(111, 111, 1993,  8, 'TARDE',  'CLASES_COLECTIVAS', 1.5, 1, -25.0),  -- *
(112, 112, 1987,  6, 'MANANA', 'MUSCULACION',       1.2, 0, -18.0),
(113, 113, 1975, 11, 'NOCHE',  'CARDIO',            0.9, 0, -30.0),
(114, 114, 1982,  9, 'TARDE',  'CROSSFIT',          1.8, 1, -20.0),
(115, 115, 1990,  7, 'MANANA', 'CLASES_COLECTIVAS', 1.0, 0, -15.0),
(116, 116, 1985, 12, 'NOCHE',  'MUSCULACION',       1.5, 1, -35.0),
(117, 117, 1977,  5, 'TARDE',  'CARDIO',            0.8, 0, -22.0),
(118, 118, 1993, 10, 'MANANA', 'MIXTO',             1.3, 1, -28.0),
(119, 119, 1980,  8, 'NOCHE',  'CLASES_COLECTIVAS', 1.6, 0, -12.0),
(120, 120, 1988, 15, 'TARDE',  'MUSCULACION',       1.2, 1, -32.0),
(121, 121, 1971,  6, 'MANANA', 'CARDIO',            1.0, 0, -25.0),
(122, 122, 1983, 11, 'NOCHE',  'CROSSFIT',          1.7, 1, -18.0),
(123, 123, 1976,  9, 'TARDE',  'MIXTO',             0.9, 0, -20.0),
(124, 124, 1969,  7, 'MANANA', 'MUSCULACION',       1.4, 1, -40.0),
(125, 125, 1978, 12, 'NOCHE',  'CARDIO',            1.1, 0, -15.0),
(126, 126, 1991, 18, 'TARDE',  'CROSSFIT',          1.5, 1, -30.0),
(127, 127, 1986,  5, 'MANANA', 'CLASES_COLECTIVAS', 1.8, 0, -10.0),
(128, 128, 1989, 10, 'NOCHE',  'MUSCULACION',       1.0, 1, -35.0),
(129, 129, 1984,  8, 'TARDE',  'CARDIO',            1.3, 0, -22.0),
(130, 130, 1979, 16, 'MANANA', 'MIXTO',             1.6, 1, -28.0),
(131, 131, 1992,  6, 'NOCHE',  'CLASES_COLECTIVAS', 0.8, 0, -18.0),
(132, 132, 1986, 11, 'TARDE',  'MUSCULACION',       1.2, 1, -32.0),
(133, 133, 1995,  9, 'MANANA', 'CARDIO',            1.5, 0, -12.0),
(134, 134, 1988,  7, 'NOCHE',  'CROSSFIT',          1.9, 1, -20.0),
(135, 135, 1983, 12, 'TARDE',  'MIXTO',             1.0, 0, -28.0),
(136, 136, 1977,  8, 'MANANA', 'MUSCULACION',       1.4, 1, -38.0),
(137, 137, 1991,  5, 'NOCHE',  'CLASES_COLECTIVAS', 0.9, 0, -15.0),
(138, 138, 1994, 10, 'TARDE',  'CARDIO',            1.7, 1, -25.0),
(139, 139, 1988,  9, 'MANANA', 'CROSSFIT',          1.3, 0, -20.0),
(140, 140, 1985, 17, 'NOCHE',  'MUSCULACION',       1.1, 1, -30.0),
(141, 141, 1972,  6, 'TARDE',  'CARDIO',            0.8, 0, -22.0),
(142, 142, 1990, 11, 'MANANA', 'MIXTO',             1.6, 1, -18.0),
(143, 143, 1974,  8, 'NOCHE',  'CLASES_COLECTIVAS', 1.0, 0, -25.0),
(144, 144, 1987, 12, 'TARDE',  'MUSCULACION',       1.3, 1, -35.0),
(145, 145, 1981,  7, 'MANANA', 'CARDIO',            1.5, 0, -14.0),
(146, 146, 1983,  9, 'NOCHE',  'CROSSFIT',          1.8, 1, -22.0),
(147, 147, 1969,  5, 'TARDE',  'MUSCULACION',       0.9, 0, -30.0),
(148, 148, 1978, 11, 'MANANA', 'MIXTO',             1.2, 1, -28.0),
(149, 149, 1980,  8, 'NOCHE',  'CARDIO',            1.4, 0, -18.0),
(150, 150, 1975, 12, 'TARDE',  'MUSCULACION',       1.0, 1, -40.0);


-- ============================================================
-- SECCIÓN 3 — NUEVO_ENGANCHADO  (IDs 151–180 · 30 socios)
-- Regla: meses <= 3  AND  frecuencia >= 1.0  AND  semanas_inactivo = 0
-- ============================================================
INSERT INTO clientes_privados (id, nombre, apellidos, dni, gmail) VALUES
(151, 'Marc',           'Puig Casals',            '20000151Z', 'marc.puig@churngym.es'),         -- *
(152, 'Andrea',         'Vives Pons',             '20000152Z', 'andrea.vives@churngym.es'),
(153, 'Álvaro',         'Serra Mas',              '20000153Z', 'alvaro.serra@churngym.es'),
(154, 'Carme',          'Sala Costa',             '20000154Z', 'carme.sala@churngym.es'),
(155, 'Guillem',        'Ribas Font',             '20000155Z', 'guillem.ribas@churngym.es'),
(156, 'Marta',          'Oller Roig',             '20000156Z', 'marta.oller@churngym.es'),
(157, 'Pol',            'Esteve Puig',            '20000157Z', 'pol.esteve@churngym.es'),
(158, 'Núria',          'Pujol Ferrer',           '20000158Z', 'nuria.pujol@churngym.es'),
(159, 'Jordi',          'Ferrer Soler',           '20000159Z', 'jordi.ferrer@churngym.es'),
(160, 'Ariadna',        'Bosch Roca',             '20000160Z', 'ariadna.bosch@churngym.es'),
(161, 'Lluc',           'Solà Camps',             '20000161Z', 'lluc.sola@churngym.es'),
(162, 'Gemma',          'Subirats Vila',          '20000162Z', 'gemma.subirats@churngym.es'),
(163, 'Pau',            'Rovira Comas',           '20000163Z', 'pau.rovira@churngym.es'),
(164, 'Ona',            'Colom Puig',             '20000164Z', 'ona.colom@churngym.es'),
(165, 'Biel',           'Vendrell Sala',          '20000165Z', 'biel.vendrell@churngym.es'),
(166, 'Laia',           'Montserrat Mir',         '20000166Z', 'laia.montserrat@churngym.es'),
(167, 'Martí',          'Bruguera Coll',          '20000167Z', 'marti.bruguera@churngym.es'),
(168, 'Júlia',          'Prat Matas',             '20000168Z', 'julia.prat@churngym.es'),
(169, 'Miquel',         'Casellas Feliu',         '20000169Z', 'miquel.casellas@churngym.es'),
(170, 'Aina',           'Figueras Domènech',      '20000170Z', 'aina.figueras@churngym.es'),
(171, 'Bernat',         'Vidal Gironès',          '20000171Z', 'bernat.vidal@churngym.es'),
(172, 'Clara',          'Trias Espigol',          '20000172Z', 'clara.trias@churngym.es'),
(173, 'Carles',         'Codina Arbós',           '20000173Z', 'carles.codina@churngym.es'),
(174, 'Júlia',          'Güell Saura',            '20000174Z', 'julia.guell@churngym.es'),
(175, 'Ricard',         'Boada Pujolàs',          '20000175Z', 'ricard.boada@churngym.es'),
(176, 'Mariona',        'Fàbregas Rius',          '20000176Z', 'mariona.fabregas@churngym.es'),
(177, 'Aleix',          'Querol Benet',           '20000177Z', 'aleix.querol@churngym.es'),
(178, 'Laura',          'Freixas Pla',            '20000178Z', 'laura.freixas@churngym.es'),
(179, 'Jaume',          'Sitges Puigdomènech',    '20000179Z', 'jaume.sitges@churngym.es'),
(180, 'Montserrat',     'Armengol Tous',          '20000180Z', 'montserrat.armengol@churngym.es');

INSERT INTO clientes_datos (id, cliente_privado_id, anyo_nacimiento, meses_como_socio, franja_horaria, deporte_principal, frecuencia_semanal, semanas_inactivo, tendencia_mensual) VALUES
(151, 151, 1999, 2, 'TARDE',  'MUSCULACION',       4.0, 0,  12.0),  -- *
(152, 152, 2000, 1, 'MANANA', 'CLASES_COLECTIVAS', 2.5, 0,   8.0),
(153, 153, 2001, 3, 'NOCHE',  'CROSSFIT',          5.0, 0,  15.0),
(154, 154, 1998, 2, 'TARDE',  'CARDIO',            3.5, 0,   5.0),
(155, 155, 2002, 1, 'MANANA', 'MUSCULACION',       4.5, 0,  18.0),
(156, 156, 1999, 3, 'NOCHE',  'CLASES_COLECTIVAS', 2.0, 0,   3.0),
(157, 157, 2003, 1, 'TARDE',  'CROSSFIT',          5.5, 0,  22.0),
(158, 158, 1997, 2, 'MANANA', 'CARDIO',            3.0, 0,   7.0),
(159, 159, 2000, 3, 'NOCHE',  'MUSCULACION',       4.2, 0,  11.0),
(160, 160, 2001, 1, 'TARDE',  'CLASES_COLECTIVAS', 2.8, 0,   6.0),
(161, 161, 1998, 2, 'MANANA', 'CROSSFIT',          3.8, 0,  14.0),
(162, 162, 2002, 1, 'NOCHE',  'MUSCULACION',       1.5, 0,   4.0),
(163, 163, 1999, 3, 'TARDE',  'CARDIO',            4.8, 0,  16.0),
(164, 164, 2000, 2, 'MANANA', 'CLASES_COLECTIVAS', 3.2, 0,   9.0),
(165, 165, 2001, 1, 'NOCHE',  'CROSSFIT',          5.0, 0,  20.0),
(166, 166, 1998, 3, 'TARDE',  'MUSCULACION',       2.5, 0,   5.0),
(167, 167, 2003, 1, 'MANANA', 'CARDIO',            4.0, 0,  13.0),
(168, 168, 1997, 2, 'NOCHE',  'CLASES_COLECTIVAS', 3.5, 0,   8.0),
(169, 169, 2000, 3, 'TARDE',  'MUSCULACION',       4.5, 0,  10.0),
(170, 170, 2001, 1, 'MANANA', 'CROSSFIT',          2.0, 0,   3.0),
(171, 171, 1999, 2, 'NOCHE',  'CARDIO',            3.8, 0,  12.0),
(172, 172, 2002, 1, 'TARDE',  'MUSCULACION',       4.2, 0,  16.0),
(173, 173, 1998, 3, 'MANANA', 'CLASES_COLECTIVAS', 3.0, 0,   6.0),
(174, 174, 2000, 2, 'NOCHE',  'CROSSFIT',          5.0, 0,  18.0),
(175, 175, 2001, 1, 'TARDE',  'MUSCULACION',       2.8, 0,   9.0),
(176, 176, 1999, 3, 'MANANA', 'CARDIO',            4.0, 0,  11.0),
(177, 177, 2003, 1, 'NOCHE',  'CROSSFIT',          5.5, 0,  24.0),
(178, 178, 1997, 2, 'TARDE',  'CLASES_COLECTIVAS', 3.5, 0,   7.0),
(179, 179, 2000, 3, 'MANANA', 'MUSCULACION',       4.8, 0,  14.0),
(180, 180, 1998, 1, 'NOCHE',  'CARDIO',            2.5, 0,   5.0);


-- ============================================================
-- SECCIÓN 4 — CONSOLIDADO_EN_RIESGO  (IDs 181–205 · 25 socios)
-- Regla: meses > 12  AND  semanas_inactivo >= 3
-- ============================================================
INSERT INTO clientes_privados (id, nombre, apellidos, dni, gmail) VALUES
(181, 'Roberto',        'Solís Vega',             '20000181Z', 'roberto.solis@churngym.es'),     -- *
(182, 'Amparo',         'Reyes Castilla',         '20000182Z', 'amparo.reyes@churngym.es'),
(183, 'Rodrigo',        'Fuentes Medina',         '20000183Z', 'rodrigo.fuentes@churngym.es'),
(184, 'Pilar',          'Guerrero Muñoz',         '20000184Z', 'pilar.guerrero@churngym.es'),
(185, 'Ignacio',        'Cabrera Delgado',        '20000185Z', 'ignacio.cabrera@churngym.es'),
(186, 'Encarnación',    'Bravo Santos',           '20000186Z', 'encarnacion.bravo@churngym.es'),
(187, 'Salvador',       'Reina Toro',             '20000187Z', 'salvador.reina@churngym.es'),
(188, 'Dolores',        'Leal Fuentes',           '20000188Z', 'dolores.leal@churngym.es'),
(189, 'Hipólito',       'Bravo García',           '20000189Z', 'hipolito.bravo@churngym.es'),
(190, 'Asunción',       'Mora Castro',            '20000190Z', 'asuncion.mora@churngym.es'),
(191, 'Serafín',        'Caballero López',        '20000191Z', 'serafin.caballero@churngym.es'),
(192, 'Virtudes',       'Vega Torres',            '20000192Z', 'virtudes.vega@churngym.es'),
(193, 'Bienvenido',     'Reyes Romero',           '20000193Z', 'bienvenido.reyes@churngym.es'),
(194, 'Consuelo',       'Muñoz Guerrero',         '20000194Z', 'consuelo.munoz@churngym.es'),
(195, 'Timoteo',        'Cabrera Jiménez',        '20000195Z', 'timoteo.cabrera@churngym.es'),
(196, 'Milagros',       'Toro Sánchez',           '20000196Z', 'milagros.toro@churngym.es'),
(197, 'Pascual',        'Bravo Fernández',        '20000197Z', 'pascual.bravo@churngym.es'),
(198, 'Cristóbal',      'Fuentes López',          '20000198Z', 'cristobal.fuentes@churngym.es'),
(199, 'Beatriz',        'Cabrera Torres',         '20000199Z', 'beatriz.cabrera@churngym.es'),
(200, 'Gerardo',        'Reina García',           '20000200Z', 'gerardo.reina@churngym.es'),
(201, 'Amelia',         'Bravo Hernández',        '20000201Z', 'amelia.bravo@churngym.es'),
(202, 'Narciso',        'Vega Martínez',          '20000202Z', 'narciso.vega@churngym.es'),
(203, 'Rosario',        'Toro López',             '20000203Z', 'rosario.toro@churngym.es'),
(204, 'Dionisio',       'Muñoz García',           '20000204Z', 'dionisio.munoz@churngym.es'),
(205, 'Remedios',       'Cabrera Ruiz',           '20000205Z', 'remedios.cabrera@churngym.es');

INSERT INTO clientes_datos (id, cliente_privado_id, anyo_nacimiento, meses_como_socio, franja_horaria, deporte_principal, frecuencia_semanal, semanas_inactivo, tendencia_mensual) VALUES
(181, 181, 1985, 36, 'NOCHE',  'MUSCULACION',       0.5,  9, -75.0),  -- *
(182, 182, 1974, 24, 'MANANA', 'CARDIO',            0.8,  5, -60.0),
(183, 183, 1978, 30, 'TARDE',  'CROSSFIT',          0.4,  7, -70.0),
(184, 184, 1971, 48, 'NOCHE',  'CLASES_COLECTIVAS', 0.6,  4, -55.0),
(185, 185, 1969, 15, 'MANANA', 'MUSCULACION',       0.3,  6, -65.0),
(186, 186, 1972, 40, 'TARDE',  'CARDIO',            0.5,  8, -72.0),
(187, 187, 1976, 20, 'NOCHE',  'MUSCULACION',       0.7,  4, -50.0),
(188, 188, 1968, 55, 'MANANA', 'MIXTO',             0.4,  5, -68.0),
(189, 189, 1980, 26, 'TARDE',  'CROSSFIT',          0.6,  6, -62.0),
(190, 190, 1977, 32, 'NOCHE',  'CARDIO',            0.3,  9, -78.0),
(191, 191, 1982, 18, 'MANANA', 'MUSCULACION',       0.8,  3, -48.0),
(192, 192, 1975, 44, 'TARDE',  'CLASES_COLECTIVAS', 0.5,  7, -74.0),
(193, 193, 1979, 22, 'NOCHE',  'MIXTO',             0.4,  5, -58.0),
(194, 194, 1970, 38, 'MANANA', 'CARDIO',            0.6,  4, -52.0),
(195, 195, 1983, 14, 'TARDE',  'MUSCULACION',       1.0,  6, -66.0),
(196, 196, 1973, 50, 'NOCHE',  'CARDIO',            0.3,  8, -80.0),
(197, 197, 1977, 28, 'MANANA', 'CROSSFIT',          0.7,  4, -55.0),
(198, 198, 1981, 35, 'TARDE',  'MUSCULACION',       0.5,  5, -63.0),
(199, 199, 1979, 16, 'NOCHE',  'CARDIO',            0.4,  7, -70.0),
(200, 200, 1974, 42, 'MANANA', 'MIXTO',             0.6,  3, -45.0),
(201, 201, 1976, 19, 'TARDE',  'MUSCULACION',       0.8,  4, -57.0),
(202, 202, 1983, 25, 'NOCHE',  'CROSSFIT',          0.4,  6, -68.0),
(203, 203, 1968, 52, 'MANANA', 'CLASES_COLECTIVAS', 0.3,  9, -82.0),
(204, 204, 1971, 30, 'TARDE',  'CARDIO',            0.6,  5, -60.0),
(205, 205, 1974, 23, 'NOCHE',  'MUSCULACION',       0.5,  4, -53.0);


-- ============================================================
-- SECCIÓN 5 — VETERANO_EN_PAUSA  (IDs 206–225 · 20 socios)
-- Regla: meses > 12  AND  semanas_inactivo = 2
-- ============================================================
INSERT INTO clientes_privados (id, nombre, apellidos, dni, gmail) VALUES
(206, 'Carmen',         'Vidal Ruiz',             '20000206Z', 'carmen.vidal@churngym.es'),      -- *
(207, 'Sebastián',      'Mora López',             '20000207Z', 'sebastian.mora@churngym.es'),
(208, 'Lourdes',        'Torres Fernández',       '20000208Z', 'lourdes.torres@churngym.es'),
(209, 'Bernardo',       'Sanz García',            '20000209Z', 'bernardo.sanz@churngym.es'),
(210, 'Consuelo',       'García Moreno',          '20000210Z', 'consuelo.garcia@churngym.es'),
(211, 'Armando',        'Ruiz Castillo',          '20000211Z', 'armando.ruiz@churngym.es'),
(212, 'Florencia',      'López Hernández',        '20000212Z', 'florencia.lopez@churngym.es'),
(213, 'Teodoro',        'Martínez Díaz',          '20000213Z', 'teodoro.martinez@churngym.es'),
(214, 'Presentación',   'Gómez Sánchez',          '20000214Z', 'presentacion.gomez@churngym.es'),
(215, 'Baldomero',      'Fernández Torres',       '20000215Z', 'baldomero.fernandez@churngym.es'),
(216, 'Soledad',        'Rodríguez Jiménez',      '20000216Z', 'soledad.rodriguez@churngym.es'),
(217, 'Celestino',      'Díaz Alonso',            '20000217Z', 'celestino.diaz@churngym.es'),
(218, 'Virtudes',       'Moreno García',          '20000218Z', 'virtudes.moreno@churngym.es'),
(219, 'Matilde',        'Gutiérrez López',        '20000219Z', 'matilde.gutierrez@churngym.es'),
(220, 'Hortensia',      'Hernández Martínez',     '20000220Z', 'hortensia.hernandez@churngym.es'),
(221, 'Ernesto',        'Ruiz Torres',            '20000221Z', 'ernesto.ruiz@churngym.es'),
(222, 'Felisa',         'López Sánchez',          '20000222Z', 'felisa.lopez@churngym.es'),
(223, 'Clemente',       'García Fernández',       '20000223Z', 'clemente.garcia@churngym.es'),
(224, 'Salomé',         'Martínez Díaz',          '20000224Z', 'salome.martinez@churngym.es'),
(225, 'Lorenzo',        'Sánchez García',         '20000225Z', 'lorenzo.sanchez@churngym.es');

INSERT INTO clientes_datos (id, cliente_privado_id, anyo_nacimiento, meses_como_socio, franja_horaria, deporte_principal, frecuencia_semanal, semanas_inactivo, tendencia_mensual) VALUES
(206, 206, 1982, 28, 'TARDE',  'CLASES_COLECTIVAS', 1.8, 2, -35.0),  -- *
(207, 207, 1979, 22, 'MANANA', 'MUSCULACION',       1.5, 2, -30.0),
(208, 208, 1976, 36, 'NOCHE',  'CARDIO',            1.2, 2, -40.0),
(209, 209, 1983, 18, 'TARDE',  'CROSSFIT',          2.0, 2, -25.0),
(210, 210, 1974, 45, 'MANANA', 'MUSCULACION',       1.0, 2, -45.0),
(211, 211, 1980, 30, 'NOCHE',  'CARDIO',            1.5, 2, -38.0),
(212, 212, 1977, 24, 'TARDE',  'CLASES_COLECTIVAS', 1.8, 2, -28.0),
(213, 213, 1975, 40, 'MANANA', 'MUSCULACION',       1.2, 2, -42.0),
(214, 214, 1972, 15, 'NOCHE',  'MIXTO',             2.5, 2, -22.0),
(215, 215, 1985, 20, 'TARDE',  'CARDIO',            1.0, 2, -50.0),
(216, 216, 1978, 33, 'MANANA', 'CROSSFIT',          1.5, 2, -33.0),
(217, 217, 1973, 48, 'NOCHE',  'MUSCULACION',       0.8, 2, -55.0),
(218, 218, 1981, 26, 'TARDE',  'CARDIO',            1.8, 2, -28.0),
(219, 219, 1976, 17, 'MANANA', 'CLASES_COLECTIVAS', 1.3, 2, -40.0),
(220, 220, 1974, 38, 'NOCHE',  'MUSCULACION',       1.0, 2, -48.0),
(221, 221, 1982, 22, 'TARDE',  'CROSSFIT',          2.0, 2, -30.0),
(222, 222, 1979, 44, 'MANANA', 'CARDIO',            0.8, 2, -52.0),
(223, 223, 1977, 29, 'NOCHE',  'MUSCULACION',       1.5, 2, -35.0),
(224, 224, 1983, 16, 'TARDE',  'CLASES_COLECTIVAS', 1.8, 2, -25.0),
(225, 225, 1975, 35, 'MANANA', 'MIXTO',             1.2, 2, -45.0);


-- ============================================================
-- SECCIÓN 6 — NUEVO_SIN_ENGANCHE  (IDs 226–240 · 15 socios)
-- Regla: meses <= 3  AND  frecuencia < 1.0
-- ============================================================
INSERT INTO clientes_privados (id, nombre, apellidos, dni, gmail) VALUES
(226, 'Nadia',          'Flores Romero',          '20000226Z', 'nadia.flores@churngym.es'),      -- *
(227, 'Kevin',          'Álvarez García',         '20000227Z', 'kevin.alvarez@churngym.es'),
(228, 'Yasmín',         'Torres López',           '20000228Z', 'yasmin.torres@churngym.es'),
(229, 'Brandon',        'Moreno Sánchez',         '20000229Z', 'brandon.moreno@churngym.es'),
(230, 'Aicha',          'Benali Ouazzani',        '20000230Z', 'aicha.benali@churngym.es'),
(231, 'Dylan',          'García Martínez',        '20000231Z', 'dylan.garcia@churngym.es'),
(232, 'Samira',         'López Fernández',        '20000232Z', 'samira.lopez@churngym.es'),
(233, 'Axel',           'Sánchez Díaz',           '20000233Z', 'axel.sanchez@churngym.es'),
(234, 'Fátima',         'Jiménez García',         '20000234Z', 'fatima.jimenez@churngym.es'),
(235, 'Tyler',          'García López',           '20000235Z', 'tyler.garcia@churngym.es'),
(236, 'Xenia',          'Moreno Torres',          '20000236Z', 'xenia.moreno@churngym.es'),
(237, 'Omar',           'Fernández Ruiz',         '20000237Z', 'omar.fernandez@churngym.es'),
(238, 'Inés',           'Fuentes García',         '20000238Z', 'ines.fuentes@churngym.es'),
(239, 'Nicolás',        'Vázquez García',         '20000239Z', 'nicolas.vazquez@churngym.es'),
(240, 'Valentina',      'López Castillo',         '20000240Z', 'valentina.lopez@churngym.es');

INSERT INTO clientes_datos (id, cliente_privado_id, anyo_nacimiento, meses_como_socio, franja_horaria, deporte_principal, frecuencia_semanal, semanas_inactivo, tendencia_mensual) VALUES
(226, 226, 2000, 1, 'TARDE',  'CARDIO',            0.3, 6, -65.0),  -- *
(227, 227, 2002, 2, 'MANANA', 'MUSCULACION',       0.5, 4, -52.0),
(228, 228, 2003, 1, 'NOCHE',  'CLASES_COLECTIVAS', 0.2, 5, -60.0),
(229, 229, 2001, 3, 'TARDE',  'CROSSFIT',          0.7, 3, -45.0),
(230, 230, 2000, 1, 'MANANA', 'CARDIO',            0.3, 7, -70.0),
(231, 231, 2003, 2, 'NOCHE',  'MUSCULACION',       0.5, 4, -55.0),
(232, 232, 2001, 1, 'TARDE',  'CLASES_COLECTIVAS', 0.2, 6, -62.0),
(233, 233, 2002, 3, 'MANANA', 'CROSSFIT',          0.8, 3, -40.0),
(234, 234, 1999, 2, 'NOCHE',  'CARDIO',            0.4, 5, -58.0),
(235, 235, 2003, 1, 'TARDE',  'MUSCULACION',       0.3, 7, -72.0),
(236, 236, 2000, 2, 'MANANA', 'CLASES_COLECTIVAS', 0.6, 4, -48.0),
(237, 237, 2001, 1, 'NOCHE',  'CROSSFIT',          0.2, 6, -65.0),
(238, 238, 2002, 3, 'TARDE',  'CARDIO',            0.7, 3, -42.0),
(239, 239, 2003, 1, 'MANANA', 'MUSCULACION',       0.4, 5, -58.0),
(240, 240, 2000, 2, 'NOCHE',  'CLASES_COLECTIVAS', 0.3, 6, -63.0);


-- ============================================================
-- SECCIÓN 7 — VETERANO_ESPORADICO  (IDs 241–250 · 10 socios)
-- Regla: meses > 12  AND  semanas_inactivo = 0  AND  frecuencia < 2.0
-- ============================================================
INSERT INTO clientes_privados (id, nombre, apellidos, dni, gmail) VALUES
(241, 'Tomás',          'Herrera Blanco',         '20000241Z', 'tomas.herrera@churngym.es'),     -- *
(242, 'Amparo',         'Reina Vidal',            '20000242Z', 'amparo.reina@churngym.es'),
(243, 'Aurelio',        'García López',           '20000243Z', 'aurelio.garcia@churngym.es'),
(244, 'Encarna',        'Morales Torres',         '20000244Z', 'encarna.morales@churngym.es'),
(245, 'Patricio',       'Sanz Fernández',         '20000245Z', 'patricio.sanz@churngym.es'),
(246, 'Milagros',       'Díaz Castillo',          '20000246Z', 'milagros.diaz@churngym.es'),
(247, 'Victoriano',     'López García',           '20000247Z', 'victoriano.lopez@churngym.es'),
(248, 'Purificación',   'Martínez Sánchez',       '20000248Z', 'purificacion.martinez@churngym.es'),
(249, 'Fortunato',      'García Torres',          '20000249Z', 'fortunato.garcia@churngym.es'),
(250, 'Carmela',        'Hernández Ruiz',         '20000250Z', 'carmela.hernandez@churngym.es');

INSERT INTO clientes_datos (id, cliente_privado_id, anyo_nacimiento, meses_como_socio, franja_horaria, deporte_principal, frecuencia_semanal, semanas_inactivo, tendencia_mensual) VALUES
(241, 241, 1978, 42, 'TARDE',  'CARDIO',            0.9, 0, -10.0),  -- *
(242, 242, 1975, 30, 'MANANA', 'CLASES_COLECTIVAS', 1.2, 0, -15.0),
(243, 243, 1970, 48, 'NOCHE',  'MUSCULACION',       0.7, 0,  -8.0),
(244, 244, 1973, 24, 'TARDE',  'CARDIO',            1.5, 0, -20.0),
(245, 245, 1968, 55, 'MANANA', 'MIXTO',             0.5, 0,  -5.0),
(246, 246, 1976, 36, 'NOCHE',  'CLASES_COLECTIVAS', 1.0, 0, -12.0),
(247, 247, 1972, 42, 'TARDE',  'MUSCULACION',       0.8, 0, -18.0),
(248, 248, 1974, 28, 'MANANA', 'CARDIO',            1.5, 0, -22.0),
(249, 249, 1969, 50, 'NOCHE',  'CROSSFIT',          0.6, 0,  -7.0),
(250, 250, 1971, 38, 'TARDE',  'MUSCULACION',       1.0, 0, -14.0);
