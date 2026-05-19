-- V7__insert_socios_simulados.sql
-- =============================================================================
-- 200 socios con distribución realista por grupo de riesgo (Opción A).
-- Organizado en 7 secciones, una por grupo. El primer registro de cada sección
-- es un cliente estratégico pensado para la demo (marcado con -- *).
-- IDs 1–200. No tocar V1–V5 (checksums fijos).
-- =============================================================================


-- ============================================================
-- SECCIÓN 1 — ACTIVO_ESTABLE  (IDs 1–60 · 60 socios)
-- Regla: frecuencia >= 2.0  AND  semanas_inactivo = 0  AND  meses >= 4
-- ============================================================
INSERT INTO clientes_privados (id, nombre, apellidos, dni, gmail) VALUES
(1,  'Elena',          'Rivas Montoya',         '20000001Z', 'elena.rivas@churngym.es'),       -- *
(2,  'Rafael',         'Torres García',          '20000002Z', 'rafael.torres@churngym.es'),
(3,  'Miriam',         'Sánchez López',          '20000003Z', 'miriam.sanchez@churngym.es'),
(4,  'Rubén',          'Gómez Fernández',        '20000004Z', 'ruben.gomez@churngym.es'),
(5,  'Noelia',         'Castro Hernández',       '20000005Z', 'noelia.castro@churngym.es'),
(6,  'Oscar',          'Díaz Martínez',          '20000006Z', 'oscar.diaz@churngym.es'),
(7,  'Sonia',          'Moreno Ruiz',            '20000007Z', 'sonia.moreno@churngym.es'),
(8,  'Héctor',         'Jiménez Pérez',          '20000008Z', 'hector.jimenez@churngym.es'),
(9,  'Vanessa',        'Alonso Romero',          '20000009Z', 'vanessa.alonso@churngym.es'),
(10, 'Enrique',        'Rodríguez Blanco',       '20000010Z', 'enrique.rodriguez@churngym.es'),
(11, 'Rocío',          'Navarro Molina',         '20000011Z', 'rocio.navarro@churngym.es'),
(12, 'Emilio',         'Gutiérrez Torres',       '20000012Z', 'emilio.gutierrez@churngym.es'),
(13, 'Paloma',         'Martín Ortega',          '20000013Z', 'paloma.martin@churngym.es'),
(14, 'Jaime',          'Vázquez Castillo',       '20000014Z', 'jaime.vazquez@churngym.es'),
(15, 'Alicia',         'Suárez Domínguez',       '20000015Z', 'alicia.suarez@churngym.es'),
(16, 'Marcos',         'Herrero Prieto',         '20000016Z', 'marcos.herrero@churngym.es'),
(17, 'Eva',            'Reyes Medina',           '20000017Z', 'eva.reyes@churngym.es'),
(18, 'Felipe',         'Morales Guerrero',       '20000018Z', 'felipe.morales@churngym.es'),
(19, 'Belén',          'Santos Serrano',         '20000019Z', 'belen.santos@churngym.es'),
(20, 'Augusto',        'Ortiz Cano',             '20000020Z', 'augusto.ortiz@churngym.es'),
(21, 'Celia',          'Rubio Marín',            '20000021Z', 'celia.rubio@churngym.es'),
(22, 'Andrés',         'Lozano García',          '20000022Z', 'andres.lozano@churngym.es'),
(23, 'Irene',          'Blanco Sanz',            '20000023Z', 'irene.blanco@churngym.es'),
(24, 'Hugo',           'Delgado Iglesias',       '20000024Z', 'hugo.delgado@churngym.es'),
(25, 'Sandra',         'Cortés Nuñez',           '20000025Z', 'sandra.cortes@churngym.es'),
(26, 'Ángel',          'Medina Garrido',         '20000026Z', 'angel.medina@churngym.es'),
(27, 'Paula',          'Castro Cruz',            '20000027Z', 'paula.castro@churngym.es'),
(28, 'César',          'Ramos Castillo',         '20000028Z', 'cesar.ramos@churngym.es'),
(29, 'Lidia',          'Ortega Santos',          '20000029Z', 'lidia.ortega@churngym.es'),
(30, 'Simón',          'Garrido Lozano',         '20000030Z', 'simon.garrido@churngym.es'),
(31, 'Carla',          'Prieto Rubio',           '20000031Z', 'carla.prieto@churngym.es'),
(32, 'Daniel',         'Morales Serrano',        '20000032Z', 'daniel.morales@churngym.es'),
(33, 'Olga',           'Guerrero Cano',          '20000033Z', 'olga.guerrero@churngym.es'),
(34, 'Rodrigo',        'Castillo Ortiz',         '20000034Z', 'rodrigo.castillo@churngym.es'),
(35, 'Natalia',        'Sanz Blanco',            '20000035Z', 'natalia.sanz@churngym.es'),
(36, 'Iván',           'Nuñez Medina',           '20000036Z', 'ivan.nunez@churngym.es'),
(37, 'Macarena',       'Garrido Torres',         '20000037Z', 'macarena.garrido@churngym.es'),
(38, 'Nicolás',        'Delgado López',          '20000038Z', 'nicolas.delgado@churngym.es'),
(39, 'Esther',         'Marín Romero',           '20000039Z', 'esther.marin@churngym.es'),
(40, 'Borja',          'Iglesias Vázquez',       '20000040Z', 'borja.iglesias@churngym.es'),
(41, 'Gloria',         'Santos García',          '20000041Z', 'gloria.santos@churngym.es'),
(42, 'Xavi',           'Reyes Fernández',        '20000042Z', 'xavi.reyes@churngym.es'),
(43, 'Virginia',       'Moreno Cruz',            '20000043Z', 'virginia.moreno@churngym.es'),
(44, 'Gerard',         'Prieto Díaz',            '20000044Z', 'gerard.prieto@churngym.es'),
(45, 'Leticia',        'Rubio Hernández',        '20000045Z', 'leticia.rubio@churngym.es'),
(46, 'Pau',            'Castillo Martínez',      '20000046Z', 'pau.castillo@churngym.es'),
(47, 'Arantxa',        'Navarro Romero',         '20000047Z', 'arantxa.navarro@churngym.es'),
(48, 'Arnau',          'Ortega Gutiérrez',       '20000048Z', 'arnau.ortega@churngym.es'),
(49, 'Susana',         'Morales Alonso',         '20000049Z', 'susana.morales@churngym.es'),
(50, 'Joan',           'Torres Jiménez',         '20000050Z', 'joan.torres@churngym.es'),
(51, 'Leire',          'Fernández Pérez',        '20000051Z', 'leire.fernandez@churngym.es'),
(52, 'Ferran',         'Blanco Rodríguez',       '20000052Z', 'ferran.blanco@churngym.es'),
(53, 'Amaia',          'García Castillo',        '20000053Z', 'amaia.garcia@churngym.es'),
(54, 'Sergi',          'López Ortiz',            '20000054Z', 'sergi.lopez@churngym.es'),
(55, 'Idoia',          'Martínez Serrano',       '20000055Z', 'idoia.martinez@churngym.es'),
(56, 'Oriol',          'Sánchez Morales',        '20000056Z', 'oriol.sanchez@churngym.es'),
(57, 'Nerea',          'Gómez Santos',           '20000057Z', 'nerea.gomez@churngym.es'),
(58, 'Lluis',          'Hernández Prieto',       '20000058Z', 'lluis.hernandez@churngym.es'),
(59, 'Itziar',         'Díaz Garrido',           '20000059Z', 'itziar.diaz@churngym.es'),
(60, 'Toni',           'Ruiz García',            '20000060Z', 'toni.ruiz@churngym.es');

INSERT INTO clientes_datos (id, cliente_privado_id, anyo_nacimiento, meses_como_socio, franja_horaria, deporte_principal, frecuencia_semanal, semanas_inactivo, tendencia_mensual) VALUES
(1,  1,  1991, 22, 'MANANA', 'MUSCULACION',       5.0, 0,  15.0),  -- *
(2,  2,  1988, 18, 'TARDE',  'MUSCULACION',       4.5, 0,   8.0),
(3,  3,  1992, 12, 'NOCHE',  'CARDIO',            3.0, 0,   5.0),
(4,  4,  1985, 36, 'MANANA', 'CROSSFIT',          4.0, 0,  12.0),
(5,  5,  1990, 24, 'TARDE',  'CLASES_COLECTIVAS', 3.5, 0,   6.0),
(6,  6,  1987, 48, 'MANANA', 'MUSCULACION',       5.5, 0,  18.0),
(7,  7,  1983, 30, 'NOCHE',  'CARDIO',            2.5, 0,  -5.0),
(8,  8,  1994,  8, 'TARDE',  'CROSSFIT',          4.2, 0,  10.0),
(9,  9,  1989, 15, 'MANANA', 'CLASES_COLECTIVAS', 2.8, 0,   3.0),
(10, 10, 1981, 42, 'NOCHE',  'MIXTO',             3.8, 0,  -3.0),
(11, 11, 1993,  6, 'TARDE',  'MUSCULACION',       3.0, 0,   7.0),
(12, 12, 1986, 55, 'MANANA', 'CARDIO',            2.2, 0,  -8.0),
(13, 13, 1979, 28, 'NOCHE',  'CLASES_COLECTIVAS', 4.8, 0,  14.0),
(14, 14, 1995, 10, 'TARDE',  'CROSSFIT',          5.0, 0,  20.0),
(15, 15, 1988, 20, 'MANANA', 'MIXTO',             3.2, 0,   4.0),
(16, 16, 1982, 45, 'NOCHE',  'MUSCULACION',       4.5, 0,  11.0),
(17, 17, 1991, 16, 'TARDE',  'CARDIO',            2.5, 0,   2.0),
(18, 18, 1977, 52, 'MANANA', 'MUSCULACION',       3.5, 0,  -4.0),
(19, 19, 1986,  9, 'NOCHE',  'CLASES_COLECTIVAS', 4.0, 0,   9.0),
(20, 20, 1980, 38, 'TARDE',  'CROSSFIT',          5.2, 0,  16.0),
(21, 21, 1994,  5, 'MANANA', 'CARDIO',            3.8, 0,  12.0),
(22, 22, 1990, 26, 'NOCHE',  'MUSCULACION',       4.8, 0,   7.0),
(23, 23, 1987, 14, 'TARDE',  'CLASES_COLECTIVAS', 2.0, 0,  -2.0),
(24, 24, 1996,  7, 'MANANA', 'CROSSFIT',          5.5, 0,  22.0),
(25, 25, 1984, 32, 'NOCHE',  'CARDIO',            3.2, 0,   5.0),
(26, 26, 1978, 50, 'TARDE',  'MIXTO',             4.0, 0,  -6.0),
(27, 27, 1993, 11, 'MANANA', 'MUSCULACION',       3.5, 0,   8.0),
(28, 28, 1989, 19, 'NOCHE',  'CROSSFIT',          4.5, 0,  13.0),
(29, 29, 1992, 23, 'TARDE',  'CARDIO',            2.5, 0,   3.0),
(30, 30, 1983, 40, 'MANANA', 'MUSCULACION',       6.0, 0,  18.0),
(31, 31, 1997,  4, 'NOCHE',  'CLASES_COLECTIVAS', 2.8, 0,   6.0),
(32, 32, 1985, 35, 'TARDE',  'CROSSFIT',          4.2, 0,  10.0),
(33, 33, 1981, 46, 'MANANA', 'CARDIO',            3.0, 0,  -5.0),
(34, 34, 1988, 17, 'NOCHE',  'MUSCULACION',       5.0, 0,  15.0),
(35, 35, 1991, 21, 'TARDE',  'CLASES_COLECTIVAS', 3.5, 0,   4.0),
(36, 36, 1986, 43, 'MANANA', 'MIXTO',             4.5, 0,   9.0),
(37, 37, 1983, 13, 'NOCHE',  'MUSCULACION',       2.5, 0,  -3.0),
(38, 38, 1994,  8, 'TARDE',  'CROSSFIT',          3.8, 0,  11.0),
(39, 39, 1989, 29, 'MANANA', 'CARDIO',            4.0, 0,   7.0),
(40, 40, 1987, 37, 'NOCHE',  'MUSCULACION',       5.5, 0,  16.0),
(41, 41, 1980, 54, 'TARDE',  'CLASES_COLECTIVAS', 2.2, 0,  -7.0),
(42, 42, 1993,  6, 'MANANA', 'CROSSFIT',          4.8, 0,  14.0),
(43, 43, 1979, 48, 'NOCHE',  'CARDIO',            3.5, 0,  -4.0),
(44, 44, 1995,  9, 'TARDE',  'MUSCULACION',       5.0, 0,  20.0),
(45, 45, 1988, 27, 'MANANA', 'CLASES_COLECTIVAS', 3.0, 0,   5.0),
(46, 46, 1992, 14, 'NOCHE',  'CROSSFIT',          4.2, 0,  12.0),
(47, 47, 1985, 33, 'TARDE',  'MUSCULACION',       3.8, 0,   8.0),
(48, 48, 1990, 20, 'MANANA', 'MIXTO',             4.5, 0,  10.0),
(49, 49, 1984, 44, 'NOCHE',  'CARDIO',            2.8, 0,  -6.0),
(50, 50, 1978, 52, 'TARDE',  'MUSCULACION',       3.2, 0,  -2.0),
(51, 51, 1996,  5, 'MANANA', 'CLASES_COLECTIVAS', 4.0, 0,  15.0),
(52, 52, 1982, 38, 'NOCHE',  'CROSSFIT',          5.5, 0,  13.0),
(53, 53, 1993, 11, 'TARDE',  'CARDIO',            3.5, 0,   6.0),
(54, 54, 1987, 25, 'MANANA', 'MUSCULACION',       4.8, 0,  11.0),
(55, 55, 1981, 47, 'NOCHE',  'CLASES_COLECTIVAS', 2.5, 0,  -8.0),
(56, 56, 1989, 16, 'TARDE',  'CROSSFIT',          4.0, 0,   9.0),
(57, 57, 1991, 30, 'MANANA', 'CARDIO',            3.2, 0,   4.0),
(58, 58, 1985, 42, 'NOCHE',  'MUSCULACION',       5.0, 0,  14.0),
(59, 59, 1979, 55, 'TARDE',  'MIXTO',             2.8, 0,  -5.0),
(60, 60, 1994,  7, 'MANANA', 'CROSSFIT',          4.5, 0,  17.0);


-- ============================================================
-- SECCIÓN 2 — IRREGULAR  (IDs 61–100 · 40 socios)
-- Regla: no encaja en ningún otro grupo (zona gris 4–12 meses
--        o veteranos con 1 semana inactiva)
-- ============================================================
INSERT INTO clientes_privados (id, nombre, apellidos, dni, gmail) VALUES
(61,  'Sofía',          'Mora Leal',              '20000061Z', 'sofia.mora@churngym.es'),        -- *
(62,  'Ramón',          'Pérez Torres',           '20000062Z', 'ramon.perez@churngym.es'),
(63,  'Dolores',        'Martín Hernández',       '20000063Z', 'dolores.martin@churngym.es'),
(64,  'Agustín',        'Gómez López',            '20000064Z', 'agustin.gomez@churngym.es'),
(65,  'Catalina',       'Sánchez Díaz',           '20000065Z', 'catalina.sanchez@churngym.es'),
(66,  'Esteban',        'Fernández García',       '20000066Z', 'esteban.fernandez@churngym.es'),
(67,  'Inmaculada',     'Rodríguez Cruz',         '20000067Z', 'inmaculada.rodriguez@churngym.es'),
(68,  'Julián',         'López Martínez',         '20000068Z', 'julian.lopez@churngym.es'),
(69,  'Esperanza',      'Torres Romero',          '20000069Z', 'esperanza.torres@churngym.es'),
(70,  'Lorenzo',        'Díaz Blanco',            '20000070Z', 'lorenzo.diaz@churngym.es'),
(71,  'Ascensión',      'Moreno Navarro',         '20000071Z', 'ascension.moreno@churngym.es'),
(72,  'Mauricio',       'Gutiérrez Vázquez',      '20000072Z', 'mauricio.gutierrez@churngym.es'),
(73,  'Josefa',         'Hernández Ortega',       '20000073Z', 'josefa.hernandez@churngym.es'),
(74,  'Primitivo',      'Ruiz Castillo',          '20000074Z', 'primitivo.ruiz@churngym.es'),
(75,  'Rosario',        'Alonso Domínguez',       '20000075Z', 'rosario.alonso@churngym.es'),
(76,  'Xavier',         'Vázquez Prieto',         '20000076Z', 'xavier.vazquez@churngym.es'),
(77,  'Maite',          'Navarro Santos',         '20000077Z', 'maite.navarro@churngym.es'),
(78,  'Rafa',           'Castillo Serrano',       '20000078Z', 'rafa.castillo@churngym.es'),
(79,  'Josune',         'Morales Rubio',          '20000079Z', 'josune.morales@churngym.es'),
(80,  'Miquel',         'Torres Blanco',          '20000080Z', 'miquel.torres@churngym.es'),
(81,  'Ainara',         'García Moreno',          '20000081Z', 'ainara.garcia@churngym.es'),
(82,  'Josep',          'Fernández Medina',       '20000082Z', 'josep.fernandez@churngym.es'),
(83,  'Larraitz',       'Sánchez Garrido',        '20000083Z', 'larraitz.sanchez@churngym.es'),
(84,  'Dani',           'Gómez Ortiz',            '20000084Z', 'dani.gomez@churngym.es'),
(85,  'Edurne',         'López García',           '20000085Z', 'edurne.lopez@churngym.es'),
(86,  'Pep',            'Martín Castillo',        '20000086Z', 'pep.martin@churngym.es'),
(87,  'Miren',          'Díaz Hernández',         '20000087Z', 'miren.diaz@churngym.es'),
(88,  'Alexis',         'Rodríguez López',        '20000088Z', 'alexis.rodriguez@churngym.es'),
(89,  'Garbiñe',        'Moreno Torres',          '20000089Z', 'garbine.moreno@churngym.es'),
(90,  'Nacho',          'Gutiérrez Serrano',      '20000090Z', 'nacho.gutierrez@churngym.es'),
(91,  'Concepción',     'Hernández Vázquez',      '20000091Z', 'concepcion.hernandez@churngym.es'),
(92,  'Santi',          'Ruiz Navarro',           '20000092Z', 'santi.ruiz@churngym.es'),
(93,  'Piedad',         'Alonso Cruz',            '20000093Z', 'piedad.alonso@churngym.es'),
(94,  'Carlos',         'Castillo Prieto',        '20000094Z', 'carlos.castillo@churngym.es'),
(95,  'Josefina',       'Vázquez Blanco',         '20000095Z', 'josefina.vazquez@churngym.es'),
(96,  'Emiliano',       'Navarro Morales',        '20000096Z', 'emiliano.navarro@churngym.es'),
(97,  'Remedios',       'Moreno Rubio',           '20000097Z', 'remedios.moreno@churngym.es'),
(98,  'Victorino',      'Gutiérrez Lozano',       '20000098Z', 'victorino.gutierrez@churngym.es'),
(99,  'Filomena',       'Hernández García',       '20000099Z', 'filomena.hernandez@churngym.es'),
(100, 'Baldomero',      'López Serrano',          '20000100Z', 'baldomero.lopez@churngym.es');

INSERT INTO clientes_datos (id, cliente_privado_id, anyo_nacimiento, meses_como_socio, franja_horaria, deporte_principal, frecuencia_semanal, semanas_inactivo, tendencia_mensual) VALUES
(61,  61,  1993,  8, 'TARDE',  'CLASES_COLECTIVAS', 1.5, 1, -25.0),  -- *
(62,  62,  1987,  6, 'MANANA', 'MUSCULACION',       1.2, 0, -18.0),
(63,  63,  1975, 11, 'NOCHE',  'CARDIO',            0.9, 0, -30.0),
(64,  64,  1982,  9, 'TARDE',  'CROSSFIT',          1.8, 1, -20.0),
(65,  65,  1990,  7, 'MANANA', 'CLASES_COLECTIVAS', 1.0, 0, -15.0),
(66,  66,  1985, 12, 'NOCHE',  'MUSCULACION',       1.5, 1, -35.0),
(67,  67,  1977,  5, 'TARDE',  'CARDIO',            0.8, 0, -22.0),
(68,  68,  1993, 10, 'MANANA', 'MIXTO',             1.3, 1, -28.0),
(69,  69,  1980,  8, 'NOCHE',  'CLASES_COLECTIVAS', 1.6, 0, -12.0),
(70,  70,  1988, 15, 'TARDE',  'MUSCULACION',       1.2, 1, -32.0),
(71,  71,  1971,  6, 'MANANA', 'CARDIO',            1.0, 0, -25.0),
(72,  72,  1983, 11, 'NOCHE',  'CROSSFIT',          1.7, 1, -18.0),
(73,  73,  1976,  9, 'TARDE',  'MIXTO',             0.9, 0, -20.0),
(74,  74,  1969,  7, 'MANANA', 'MUSCULACION',       1.4, 1, -40.0),
(75,  75,  1978, 12, 'NOCHE',  'CARDIO',            1.1, 0, -15.0),
(76,  76,  1991, 18, 'TARDE',  'CROSSFIT',          1.5, 1, -30.0),
(77,  77,  1986,  5, 'MANANA', 'CLASES_COLECTIVAS', 1.8, 0, -10.0),
(78,  78,  1989, 10, 'NOCHE',  'MUSCULACION',       1.0, 1, -35.0),
(79,  79,  1984,  8, 'TARDE',  'CARDIO',            1.3, 0, -22.0),
(80,  80,  1979, 16, 'MANANA', 'MIXTO',             1.6, 1, -28.0),
(81,  81,  1992,  6, 'NOCHE',  'CLASES_COLECTIVAS', 0.8, 0, -18.0),
(82,  82,  1986, 11, 'TARDE',  'MUSCULACION',       1.2, 1, -32.0),
(83,  83,  1995,  9, 'MANANA', 'CARDIO',            1.5, 0, -12.0),
(84,  84,  1988,  7, 'NOCHE',  'CROSSFIT',          1.9, 1, -20.0),
(85,  85,  1983, 12, 'TARDE',  'MIXTO',             1.0, 0, -28.0),
(86,  86,  1977,  8, 'MANANA', 'MUSCULACION',       1.4, 1, -38.0),
(87,  87,  1991,  5, 'NOCHE',  'CLASES_COLECTIVAS', 0.9, 0, -15.0),
(88,  88,  1994, 10, 'TARDE',  'CARDIO',            1.7, 1, -25.0),
(89,  89,  1988,  9, 'MANANA', 'CROSSFIT',          1.3, 0, -20.0),
(90,  90,  1985, 17, 'NOCHE',  'MUSCULACION',       1.1, 1, -30.0),
(91,  91,  1972,  6, 'TARDE',  'CARDIO',            0.8, 0, -22.0),
(92,  92,  1990, 11, 'MANANA', 'MIXTO',             1.6, 1, -18.0),
(93,  93,  1974,  8, 'NOCHE',  'CLASES_COLECTIVAS', 1.0, 0, -25.0),
(94,  94,  1987, 12, 'TARDE',  'MUSCULACION',       1.3, 1, -35.0),
(95,  95,  1981,  7, 'MANANA', 'CARDIO',            1.5, 0, -14.0),
(96,  96,  1983,  9, 'NOCHE',  'CROSSFIT',          1.8, 1, -22.0),
(97,  97,  1969,  5, 'TARDE',  'MUSCULACION',       0.9, 0, -30.0),
(98,  98,  1978, 11, 'MANANA', 'MIXTO',             1.2, 1, -28.0),
(99,  99,  1980,  8, 'NOCHE',  'CARDIO',            1.4, 0, -18.0),
(100, 100, 1975, 12, 'TARDE',  'MUSCULACION',       1.0, 1, -40.0);


-- ============================================================
-- SECCIÓN 3 — NUEVO_ENGANCHADO  (IDs 101–130 · 30 socios)
-- Regla: meses <= 3  AND  frecuencia >= 1.0  AND  semanas_inactivo = 0
-- ============================================================
INSERT INTO clientes_privados (id, nombre, apellidos, dni, gmail) VALUES
(101, 'Marc',           'Puig Casals',            '20000101Z', 'marc.puig@churngym.es'),         -- *
(102, 'Andrea',         'Vives Pons',             '20000102Z', 'andrea.vives@churngym.es'),
(103, 'Álvaro',         'Serra Mas',              '20000103Z', 'alvaro.serra@churngym.es'),
(104, 'Carme',          'Sala Costa',             '20000104Z', 'carme.sala@churngym.es'),
(105, 'Guillem',        'Ribas Font',             '20000105Z', 'guillem.ribas@churngym.es'),
(106, 'Marta',          'Oller Roig',             '20000106Z', 'marta.oller@churngym.es'),
(107, 'Pol',            'Esteve Puig',            '20000107Z', 'pol.esteve@churngym.es'),
(108, 'Núria',          'Pujol Ferrer',           '20000108Z', 'nuria.pujol@churngym.es'),
(109, 'Jordi',          'Ferrer Soler',           '20000109Z', 'jordi.ferrer@churngym.es'),
(110, 'Ariadna',        'Bosch Roca',             '20000110Z', 'ariadna.bosch@churngym.es'),
(111, 'Lluc',           'Solà Camps',             '20000111Z', 'lluc.sola@churngym.es'),
(112, 'Gemma',          'Subirats Vila',          '20000112Z', 'gemma.subirats@churngym.es'),
(113, 'Pau',            'Rovira Comas',           '20000113Z', 'pau.rovira@churngym.es'),
(114, 'Ona',            'Colom Puig',             '20000114Z', 'ona.colom@churngym.es'),
(115, 'Biel',           'Vendrell Sala',          '20000115Z', 'biel.vendrell@churngym.es'),
(116, 'Laia',           'Montserrat Mir',         '20000116Z', 'laia.montserrat@churngym.es'),
(117, 'Martí',          'Bruguera Coll',          '20000117Z', 'marti.bruguera@churngym.es'),
(118, 'Júlia',          'Prat Matas',             '20000118Z', 'julia.prat@churngym.es'),
(119, 'Miquel',         'Casellas Feliu',         '20000119Z', 'miquel.casellas@churngym.es'),
(120, 'Aina',           'Figueras Domènech',      '20000120Z', 'aina.figueras@churngym.es'),
(121, 'Bernat',         'Vidal Gironès',          '20000121Z', 'bernat.vidal@churngym.es'),
(122, 'Clara',          'Trias Espigol',          '20000122Z', 'clara.trias@churngym.es'),
(123, 'Carles',         'Codina Arbós',           '20000123Z', 'carles.codina@churngym.es'),
(124, 'Júlia',          'Güell Saura',            '20000124Z', 'julia.guell@churngym.es'),
(125, 'Ricard',         'Boada Pujolàs',          '20000125Z', 'ricard.boada@churngym.es'),
(126, 'Mariona',        'Fàbregas Rius',          '20000126Z', 'mariona.fabregas@churngym.es'),
(127, 'Aleix',          'Querol Benet',           '20000127Z', 'aleix.querol@churngym.es'),
(128, 'Laura',          'Freixas Pla',            '20000128Z', 'laura.freixas@churngym.es'),
(129, 'Jaume',          'Sitges Puigdomènech',    '20000129Z', 'jaume.sitges@churngym.es'),
(130, 'Montserrat',     'Armengol Tous',          '20000130Z', 'montserrat.armengol@churngym.es');

INSERT INTO clientes_datos (id, cliente_privado_id, anyo_nacimiento, meses_como_socio, franja_horaria, deporte_principal, frecuencia_semanal, semanas_inactivo, tendencia_mensual) VALUES
(101, 101, 1999, 2, 'TARDE',  'MUSCULACION',       4.0, 0,  12.0),  -- *
(102, 102, 2000, 1, 'MANANA', 'CLASES_COLECTIVAS', 2.5, 0,   8.0),
(103, 103, 2001, 3, 'NOCHE',  'CROSSFIT',          5.0, 0,  15.0),
(104, 104, 1998, 2, 'TARDE',  'CARDIO',            3.5, 0,   5.0),
(105, 105, 2002, 1, 'MANANA', 'MUSCULACION',       4.5, 0,  18.0),
(106, 106, 1999, 3, 'NOCHE',  'CLASES_COLECTIVAS', 2.0, 0,   3.0),
(107, 107, 2003, 1, 'TARDE',  'CROSSFIT',          5.5, 0,  22.0),
(108, 108, 1997, 2, 'MANANA', 'CARDIO',            3.0, 0,   7.0),
(109, 109, 2000, 3, 'NOCHE',  'MUSCULACION',       4.2, 0,  11.0),
(110, 110, 2001, 1, 'TARDE',  'CLASES_COLECTIVAS', 2.8, 0,   6.0),
(111, 111, 1998, 2, 'MANANA', 'CROSSFIT',          3.8, 0,  14.0),
(112, 112, 2002, 1, 'NOCHE',  'MUSCULACION',       1.5, 0,   4.0),
(113, 113, 1999, 3, 'TARDE',  'CARDIO',            4.8, 0,  16.0),
(114, 114, 2000, 2, 'MANANA', 'CLASES_COLECTIVAS', 3.2, 0,   9.0),
(115, 115, 2001, 1, 'NOCHE',  'CROSSFIT',          5.0, 0,  20.0),
(116, 116, 1998, 3, 'TARDE',  'MUSCULACION',       2.5, 0,   5.0),
(117, 117, 2003, 1, 'MANANA', 'CARDIO',            4.0, 0,  13.0),
(118, 118, 1997, 2, 'NOCHE',  'CLASES_COLECTIVAS', 3.5, 0,   8.0),
(119, 119, 2000, 3, 'TARDE',  'MUSCULACION',       4.5, 0,  10.0),
(120, 120, 2001, 1, 'MANANA', 'CROSSFIT',          2.0, 0,   3.0),
(121, 121, 1999, 2, 'NOCHE',  'CARDIO',            3.8, 0,  12.0),
(122, 122, 2002, 1, 'TARDE',  'MUSCULACION',       4.2, 0,  16.0),
(123, 123, 1998, 3, 'MANANA', 'CLASES_COLECTIVAS', 3.0, 0,   6.0),
(124, 124, 2000, 2, 'NOCHE',  'CROSSFIT',          5.0, 0,  18.0),
(125, 125, 2001, 1, 'TARDE',  'MUSCULACION',       2.8, 0,   9.0),
(126, 126, 1999, 3, 'MANANA', 'CARDIO',            4.0, 0,  11.0),
(127, 127, 2003, 1, 'NOCHE',  'CROSSFIT',          5.5, 0,  24.0),
(128, 128, 1997, 2, 'TARDE',  'CLASES_COLECTIVAS', 3.5, 0,   7.0),
(129, 129, 2000, 3, 'MANANA', 'MUSCULACION',       4.8, 0,  14.0),
(130, 130, 1998, 1, 'NOCHE',  'CARDIO',            2.5, 0,   5.0);


-- ============================================================
-- SECCIÓN 4 — CONSOLIDADO_EN_RIESGO  (IDs 131–155 · 25 socios)
-- Regla: meses > 12  AND  semanas_inactivo >= 3
-- ============================================================
INSERT INTO clientes_privados (id, nombre, apellidos, dni, gmail) VALUES
(131, 'Roberto',        'Solís Vega',             '20000131Z', 'roberto.solis@churngym.es'),     -- *
(132, 'Amparo',         'Reyes Castilla',         '20000132Z', 'amparo.reyes@churngym.es'),
(133, 'Rodrigo',        'Fuentes Medina',         '20000133Z', 'rodrigo.fuentes@churngym.es'),
(134, 'Pilar',          'Guerrero Muñoz',         '20000134Z', 'pilar.guerrero@churngym.es'),
(135, 'Ignacio',        'Cabrera Delgado',        '20000135Z', 'ignacio.cabrera@churngym.es'),
(136, 'Encarnación',    'Bravo Santos',           '20000136Z', 'encarnacion.bravo@churngym.es'),
(137, 'Salvador',       'Reina Toro',             '20000137Z', 'salvador.reina@churngym.es'),
(138, 'Dolores',        'Leal Fuentes',           '20000138Z', 'dolores.leal@churngym.es'),
(139, 'Hipólito',       'Bravo García',           '20000139Z', 'hipolito.bravo@churngym.es'),
(140, 'Asunción',       'Mora Castro',            '20000140Z', 'asuncion.mora@churngym.es'),
(141, 'Serafín',        'Caballero López',        '20000141Z', 'serafin.caballero@churngym.es'),
(142, 'Virtudes',       'Vega Torres',            '20000142Z', 'virtudes.vega@churngym.es'),
(143, 'Bienvenido',     'Reyes Romero',           '20000143Z', 'bienvenido.reyes@churngym.es'),
(144, 'Consuelo',       'Muñoz Guerrero',         '20000144Z', 'consuelo.munoz@churngym.es'),
(145, 'Timoteo',        'Cabrera Jiménez',        '20000145Z', 'timoteo.cabrera@churngym.es'),
(146, 'Milagros',       'Toro Sánchez',           '20000146Z', 'milagros.toro@churngym.es'),
(147, 'Pascual',        'Bravo Fernández',        '20000147Z', 'pascual.bravo@churngym.es'),
(148, 'Cristóbal',      'Fuentes López',          '20000148Z', 'cristobal.fuentes@churngym.es'),
(149, 'Beatriz',        'Cabrera Torres',         '20000149Z', 'beatriz.cabrera@churngym.es'),
(150, 'Gerardo',        'Reina García',           '20000150Z', 'gerardo.reina@churngym.es'),
(151, 'Amelia',         'Bravo Hernández',        '20000151Z', 'amelia.bravo@churngym.es'),
(152, 'Narciso',        'Vega Martínez',          '20000152Z', 'narciso.vega@churngym.es'),
(153, 'Rosario',        'Toro López',             '20000153Z', 'rosario.toro@churngym.es'),
(154, 'Dionisio',       'Muñoz García',           '20000154Z', 'dionisio.munoz@churngym.es'),
(155, 'Remedios',       'Cabrera Ruiz',           '20000155Z', 'remedios.cabrera@churngym.es');

INSERT INTO clientes_datos (id, cliente_privado_id, anyo_nacimiento, meses_como_socio, franja_horaria, deporte_principal, frecuencia_semanal, semanas_inactivo, tendencia_mensual) VALUES
(131, 131, 1985, 36, 'NOCHE',  'MUSCULACION',       0.5,  9, -75.0),  -- *
(132, 132, 1974, 24, 'MANANA', 'CARDIO',            0.8,  5, -60.0),
(133, 133, 1978, 30, 'TARDE',  'CROSSFIT',          0.4,  7, -70.0),
(134, 134, 1971, 48, 'NOCHE',  'CLASES_COLECTIVAS', 0.6,  4, -55.0),
(135, 135, 1969, 15, 'MANANA', 'MUSCULACION',       0.3,  6, -65.0),
(136, 136, 1972, 40, 'TARDE',  'CARDIO',            0.5,  8, -72.0),
(137, 137, 1976, 20, 'NOCHE',  'MUSCULACION',       0.7,  4, -50.0),
(138, 138, 1968, 55, 'MANANA', 'MIXTO',             0.4,  5, -68.0),
(139, 139, 1980, 26, 'TARDE',  'CROSSFIT',          0.6,  6, -62.0),
(140, 140, 1977, 32, 'NOCHE',  'CARDIO',            0.3,  9, -78.0),
(141, 141, 1982, 18, 'MANANA', 'MUSCULACION',       0.8,  3, -48.0),
(142, 142, 1975, 44, 'TARDE',  'CLASES_COLECTIVAS', 0.5,  7, -74.0),
(143, 143, 1979, 22, 'NOCHE',  'MIXTO',             0.4,  5, -58.0),
(144, 144, 1970, 38, 'MANANA', 'CARDIO',            0.6,  4, -52.0),
(145, 145, 1983, 14, 'TARDE',  'MUSCULACION',       1.0,  6, -66.0),
(146, 146, 1973, 50, 'NOCHE',  'CARDIO',            0.3,  8, -80.0),
(147, 147, 1977, 28, 'MANANA', 'CROSSFIT',          0.7,  4, -55.0),
(148, 148, 1981, 35, 'TARDE',  'MUSCULACION',       0.5,  5, -63.0),
(149, 149, 1979, 16, 'NOCHE',  'CARDIO',            0.4,  7, -70.0),
(150, 150, 1974, 42, 'MANANA', 'MIXTO',             0.6,  3, -45.0),
(151, 151, 1976, 19, 'TARDE',  'MUSCULACION',       0.8,  4, -57.0),
(152, 152, 1983, 25, 'NOCHE',  'CROSSFIT',          0.4,  6, -68.0),
(153, 153, 1968, 52, 'MANANA', 'CLASES_COLECTIVAS', 0.3,  9, -82.0),
(154, 154, 1971, 30, 'TARDE',  'CARDIO',            0.6,  5, -60.0),
(155, 155, 1974, 23, 'NOCHE',  'MUSCULACION',       0.5,  4, -53.0);


-- ============================================================
-- SECCIÓN 5 — VETERANO_EN_PAUSA  (IDs 156–175 · 20 socios)
-- Regla: meses > 12  AND  semanas_inactivo = 2
-- ============================================================
INSERT INTO clientes_privados (id, nombre, apellidos, dni, gmail) VALUES
(156, 'Carmen',         'Vidal Ruiz',             '20000156Z', 'carmen.vidal@churngym.es'),      -- *
(157, 'Sebastián',      'Mora López',             '20000157Z', 'sebastian.mora@churngym.es'),
(158, 'Lourdes',        'Torres Fernández',       '20000158Z', 'lourdes.torres@churngym.es'),
(159, 'Bernardo',       'Sanz García',            '20000159Z', 'bernardo.sanz@churngym.es'),
(160, 'Consuelo',       'García Moreno',          '20000160Z', 'consuelo.garcia@churngym.es'),
(161, 'Armando',        'Ruiz Castillo',          '20000161Z', 'armando.ruiz@churngym.es'),
(162, 'Florencia',      'López Hernández',        '20000162Z', 'florencia.lopez@churngym.es'),
(163, 'Teodoro',        'Martínez Díaz',          '20000163Z', 'teodoro.martinez@churngym.es'),
(164, 'Presentación',   'Gómez Sánchez',          '20000164Z', 'presentacion.gomez@churngym.es'),
(165, 'Baldomero',      'Fernández Torres',       '20000165Z', 'baldomero.fernandez@churngym.es'),
(166, 'Soledad',        'Rodríguez Jiménez',      '20000166Z', 'soledad.rodriguez@churngym.es'),
(167, 'Celestino',      'Díaz Alonso',            '20000167Z', 'celestino.diaz@churngym.es'),
(168, 'Virtudes',       'Moreno García',          '20000168Z', 'virtudes.moreno@churngym.es'),
(169, 'Matilde',        'Gutiérrez López',        '20000169Z', 'matilde.gutierrez@churngym.es'),
(170, 'Hortensia',      'Hernández Martínez',     '20000170Z', 'hortensia.hernandez@churngym.es'),
(171, 'Ernesto',        'Ruiz Torres',            '20000171Z', 'ernesto.ruiz@churngym.es'),
(172, 'Felisa',         'López Sánchez',          '20000172Z', 'felisa.lopez@churngym.es'),
(173, 'Clemente',       'García Fernández',       '20000173Z', 'clemente.garcia@churngym.es'),
(174, 'Salomé',         'Martínez Díaz',          '20000174Z', 'salome.martinez@churngym.es'),
(175, 'Lorenzo',        'Sánchez García',         '20000175Z', 'lorenzo.sanchez@churngym.es');

INSERT INTO clientes_datos (id, cliente_privado_id, anyo_nacimiento, meses_como_socio, franja_horaria, deporte_principal, frecuencia_semanal, semanas_inactivo, tendencia_mensual) VALUES
(156, 156, 1982, 28, 'TARDE',  'CLASES_COLECTIVAS', 1.8, 2, -35.0),  -- *
(157, 157, 1979, 22, 'MANANA', 'MUSCULACION',       1.5, 2, -30.0),
(158, 158, 1976, 36, 'NOCHE',  'CARDIO',            1.2, 2, -40.0),
(159, 159, 1983, 18, 'TARDE',  'CROSSFIT',          2.0, 2, -25.0),
(160, 160, 1974, 45, 'MANANA', 'MUSCULACION',       1.0, 2, -45.0),
(161, 161, 1980, 30, 'NOCHE',  'CARDIO',            1.5, 2, -38.0),
(162, 162, 1977, 24, 'TARDE',  'CLASES_COLECTIVAS', 1.8, 2, -28.0),
(163, 163, 1975, 40, 'MANANA', 'MUSCULACION',       1.2, 2, -42.0),
(164, 164, 1972, 15, 'NOCHE',  'MIXTO',             2.5, 2, -22.0),
(165, 165, 1985, 20, 'TARDE',  'CARDIO',            1.0, 2, -50.0),
(166, 166, 1978, 33, 'MANANA', 'CROSSFIT',          1.5, 2, -33.0),
(167, 167, 1973, 48, 'NOCHE',  'MUSCULACION',       0.8, 2, -55.0),
(168, 168, 1981, 26, 'TARDE',  'CARDIO',            1.8, 2, -28.0),
(169, 169, 1976, 17, 'MANANA', 'CLASES_COLECTIVAS', 1.3, 2, -40.0),
(170, 170, 1974, 38, 'NOCHE',  'MUSCULACION',       1.0, 2, -48.0),
(171, 171, 1982, 22, 'TARDE',  'CROSSFIT',          2.0, 2, -30.0),
(172, 172, 1979, 44, 'MANANA', 'CARDIO',            0.8, 2, -52.0),
(173, 173, 1977, 29, 'NOCHE',  'MUSCULACION',       1.5, 2, -35.0),
(174, 174, 1983, 16, 'TARDE',  'CLASES_COLECTIVAS', 1.8, 2, -25.0),
(175, 175, 1975, 35, 'MANANA', 'MIXTO',             1.2, 2, -45.0);


-- ============================================================
-- SECCIÓN 6 — NUEVO_SIN_ENGANCHE  (IDs 176–190 · 15 socios)
-- Regla: meses <= 3  AND  frecuencia < 1.0
-- ============================================================
INSERT INTO clientes_privados (id, nombre, apellidos, dni, gmail) VALUES
(176, 'Nadia',          'Flores Romero',          '20000176Z', 'nadia.flores@churngym.es'),      -- *
(177, 'Kevin',          'Álvarez García',         '20000177Z', 'kevin.alvarez@churngym.es'),
(178, 'Yasmín',         'Torres López',           '20000178Z', 'yasmin.torres@churngym.es'),
(179, 'Brandon',        'Moreno Sánchez',         '20000179Z', 'brandon.moreno@churngym.es'),
(180, 'Aicha',          'Benali Ouazzani',        '20000180Z', 'aicha.benali@churngym.es'),
(181, 'Dylan',          'García Martínez',        '20000181Z', 'dylan.garcia@churngym.es'),
(182, 'Samira',         'López Fernández',        '20000182Z', 'samira.lopez@churngym.es'),
(183, 'Axel',           'Sánchez Díaz',           '20000183Z', 'axel.sanchez@churngym.es'),
(184, 'Fátima',         'Jiménez García',         '20000184Z', 'fatima.jimenez@churngym.es'),
(185, 'Tyler',          'García López',           '20000185Z', 'tyler.garcia@churngym.es'),
(186, 'Xenia',          'Moreno Torres',          '20000186Z', 'xenia.moreno@churngym.es'),
(187, 'Omar',           'Fernández Ruiz',         '20000187Z', 'omar.fernandez@churngym.es'),
(188, 'Inés',           'Fuentes García',         '20000188Z', 'ines.fuentes@churngym.es'),
(189, 'Nicolás',        'Vázquez García',         '20000189Z', 'nicolas.vazquez@churngym.es'),
(190, 'Valentina',      'López Castillo',         '20000190Z', 'valentina.lopez@churngym.es');

INSERT INTO clientes_datos (id, cliente_privado_id, anyo_nacimiento, meses_como_socio, franja_horaria, deporte_principal, frecuencia_semanal, semanas_inactivo, tendencia_mensual) VALUES
(176, 176, 2000, 1, 'TARDE',  'CARDIO',            0.3, 6, -65.0),  -- *
(177, 177, 2002, 2, 'MANANA', 'MUSCULACION',       0.5, 4, -52.0),
(178, 178, 2003, 1, 'NOCHE',  'CLASES_COLECTIVAS', 0.2, 5, -60.0),
(179, 179, 2001, 3, 'TARDE',  'CROSSFIT',          0.7, 3, -45.0),
(180, 180, 2000, 1, 'MANANA', 'CARDIO',            0.3, 7, -70.0),
(181, 181, 2003, 2, 'NOCHE',  'MUSCULACION',       0.5, 4, -55.0),
(182, 182, 2001, 1, 'TARDE',  'CLASES_COLECTIVAS', 0.2, 6, -62.0),
(183, 183, 2002, 3, 'MANANA', 'CROSSFIT',          0.8, 3, -40.0),
(184, 184, 1999, 2, 'NOCHE',  'CARDIO',            0.4, 5, -58.0),
(185, 185, 2003, 1, 'TARDE',  'MUSCULACION',       0.3, 7, -72.0),
(186, 186, 2000, 2, 'MANANA', 'CLASES_COLECTIVAS', 0.6, 4, -48.0),
(187, 187, 2001, 1, 'NOCHE',  'CROSSFIT',          0.2, 6, -65.0),
(188, 188, 2002, 3, 'TARDE',  'CARDIO',            0.7, 3, -42.0),
(189, 189, 2003, 1, 'MANANA', 'MUSCULACION',       0.4, 5, -58.0),
(190, 190, 2000, 2, 'NOCHE',  'CLASES_COLECTIVAS', 0.3, 6, -63.0);


-- ============================================================
-- SECCIÓN 7 — VETERANO_ESPORADICO  (IDs 191–200 · 10 socios)
-- Regla: meses > 12  AND  semanas_inactivo = 0  AND  frecuencia < 2.0
-- ============================================================
INSERT INTO clientes_privados (id, nombre, apellidos, dni, gmail) VALUES
(191, 'Tomás',          'Herrera Blanco',         '20000191Z', 'tomas.herrera@churngym.es'),     -- *
(192, 'Amparo',         'Reina Vidal',            '20000192Z', 'amparo.reina@churngym.es'),
(193, 'Aurelio',        'García López',           '20000193Z', 'aurelio.garcia@churngym.es'),
(194, 'Encarna',        'Morales Torres',         '20000194Z', 'encarna.morales@churngym.es'),
(195, 'Patricio',       'Sanz Fernández',         '20000195Z', 'patricio.sanz@churngym.es'),
(196, 'Milagros',       'Díaz Castillo',          '20000196Z', 'milagros.diaz@churngym.es'),
(197, 'Victoriano',     'López García',           '20000197Z', 'victoriano.lopez@churngym.es'),
(198, 'Purificación',   'Martínez Sánchez',       '20000198Z', 'purificacion.martinez@churngym.es'),
(199, 'Fortunato',      'García Torres',          '20000199Z', 'fortunato.garcia@churngym.es'),
(200, 'Carmela',        'Hernández Ruiz',         '20000200Z', 'carmela.hernandez@churngym.es');

INSERT INTO clientes_datos (id, cliente_privado_id, anyo_nacimiento, meses_como_socio, franja_horaria, deporte_principal, frecuencia_semanal, semanas_inactivo, tendencia_mensual) VALUES
(191, 191, 1978, 42, 'TARDE',  'CARDIO',            0.9, 0, -10.0),  -- *
(192, 192, 1975, 30, 'MANANA', 'CLASES_COLECTIVAS', 1.2, 0, -15.0),
(193, 193, 1970, 48, 'NOCHE',  'MUSCULACION',       0.7, 0,  -8.0),
(194, 194, 1973, 24, 'TARDE',  'CARDIO',            1.5, 0, -20.0),
(195, 195, 1968, 55, 'MANANA', 'MIXTO',             0.5, 0,  -5.0),
(196, 196, 1976, 36, 'NOCHE',  'CLASES_COLECTIVAS', 1.0, 0, -12.0),
(197, 197, 1972, 42, 'TARDE',  'MUSCULACION',       0.8, 0, -18.0),
(198, 198, 1974, 28, 'MANANA', 'CARDIO',            1.5, 0, -22.0),
(199, 199, 1969, 50, 'NOCHE',  'CROSSFIT',          0.6, 0,  -7.0),
(200, 200, 1971, 38, 'TARDE',  'MUSCULACION',       1.0, 0, -14.0);

-- Sincronizar secuencias tras inserción con IDs explícitos.
-- Sin esto, el siguiente INSERT sin ID colisionaría con los existentes.
SELECT setval('clientes_privados_id_seq', 200);
SELECT setval('clientes_datos_id_seq',    200);
