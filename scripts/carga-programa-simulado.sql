-- =====================================================================
-- Carga masiva: programa simulado "Ingeniería de Sistemas" (10 semestres)
-- =====================================================================
-- Datos de ejemplo/demo, no un pénsum oficial de ninguna institución.
-- Pensado para correr UNA SOLA VEZ en el panel SQL de Clever Cloud
-- (o en MySQL/XAMPP local). Si lo corres dos veces fallará por los
-- códigos únicos (codigo_programa / codigo_asignatura) — eso es
-- intencional, para no duplicar datos por accidente.
--
-- Usa la columna `semestre` de `asignatura` (commit 9f31552 del
-- backend) para que la Malla Curricular de Acadify la agrupe bien.
--
-- IMPORTANTE: pega y ejecuta TODO el script de una sola vez (no
-- línea por línea). La variable @id_programa solo vive dentro de la
-- misma sesión/conexión, y varios paneles SQL abren una conexión
-- nueva por cada sentencia si se corren por separado.
-- =====================================================================

START TRANSACTION;

INSERT INTO programa (codigo_programa, nombre_programa, activo)
VALUES ('SIM-ING-SIS', 'Ingeniería de Sistemas (simulado)', TRUE);

SET @id_programa = LAST_INSERT_ID();

-- Semestre 1
INSERT INTO asignatura (codigo_asignatura, nombre_asignatura, creditos, intensidad_horaria, semestre, id_programa) VALUES
('SIM-101', 'Cálculo I', 4, 64, 1, @id_programa),
('SIM-102', 'Álgebra Lineal', 3, 48, 1, @id_programa),
('SIM-103', 'Introducción a la Programación', 4, 64, 1, @id_programa),
('SIM-104', 'Cátedra Institucional', 2, 32, 1, @id_programa),
('SIM-105', 'Inglés I', 2, 32, 1, @id_programa);

-- Semestre 2
INSERT INTO asignatura (codigo_asignatura, nombre_asignatura, creditos, intensidad_horaria, semestre, id_programa) VALUES
('SIM-201', 'Cálculo II', 4, 64, 2, @id_programa),
('SIM-202', 'Física I', 4, 64, 2, @id_programa),
('SIM-203', 'Programación Orientada a Objetos', 4, 64, 2, @id_programa),
('SIM-204', 'Matemáticas Discretas', 3, 48, 2, @id_programa),
('SIM-205', 'Inglés II', 2, 32, 2, @id_programa);

-- Semestre 3
INSERT INTO asignatura (codigo_asignatura, nombre_asignatura, creditos, intensidad_horaria, semestre, id_programa) VALUES
('SIM-301', 'Cálculo III', 4, 64, 3, @id_programa),
('SIM-302', 'Física II', 4, 64, 3, @id_programa),
('SIM-303', 'Estructuras de Datos', 4, 64, 3, @id_programa),
('SIM-304', 'Bases de Datos I', 4, 64, 3, @id_programa),
('SIM-305', 'Inglés III', 2, 32, 3, @id_programa);

-- Semestre 4
INSERT INTO asignatura (codigo_asignatura, nombre_asignatura, creditos, intensidad_horaria, semestre, id_programa) VALUES
('SIM-401', 'Ecuaciones Diferenciales', 3, 48, 4, @id_programa),
('SIM-402', 'Estadística y Probabilidad', 3, 48, 4, @id_programa),
('SIM-403', 'Arquitectura de Computadores', 3, 48, 4, @id_programa),
('SIM-404', 'Bases de Datos II', 4, 64, 4, @id_programa),
('SIM-405', 'Inglés IV', 2, 32, 4, @id_programa);

-- Semestre 5
INSERT INTO asignatura (codigo_asignatura, nombre_asignatura, creditos, intensidad_horaria, semestre, id_programa) VALUES
('SIM-501', 'Sistemas Operativos', 4, 64, 5, @id_programa),
('SIM-502', 'Ingeniería de Software I', 4, 64, 5, @id_programa),
('SIM-503', 'Redes de Computadores I', 3, 48, 5, @id_programa),
('SIM-504', 'Algoritmos y Complejidad', 3, 48, 5, @id_programa),
('SIM-505', 'Electiva I', 2, 32, 5, @id_programa);

-- Semestre 6
INSERT INTO asignatura (codigo_asignatura, nombre_asignatura, creditos, intensidad_horaria, semestre, id_programa) VALUES
('SIM-601', 'Ingeniería de Software II', 4, 64, 6, @id_programa),
('SIM-602', 'Redes de Computadores II', 3, 48, 6, @id_programa),
('SIM-603', 'Desarrollo Web', 4, 64, 6, @id_programa),
('SIM-604', 'Investigación de Operaciones', 3, 48, 6, @id_programa),
('SIM-605', 'Electiva II', 2, 32, 6, @id_programa);

-- Semestre 7
INSERT INTO asignatura (codigo_asignatura, nombre_asignatura, creditos, intensidad_horaria, semestre, id_programa) VALUES
('SIM-701', 'Inteligencia Artificial', 4, 64, 7, @id_programa),
('SIM-702', 'Seguridad Informática', 3, 48, 7, @id_programa),
('SIM-703', 'Desarrollo de Aplicaciones Móviles', 4, 64, 7, @id_programa),
('SIM-704', 'Gestión de Proyectos de TI', 3, 48, 7, @id_programa),
('SIM-705', 'Electiva III', 2, 32, 7, @id_programa);

-- Semestre 8
INSERT INTO asignatura (codigo_asignatura, nombre_asignatura, creditos, intensidad_horaria, semestre, id_programa) VALUES
('SIM-801', 'Sistemas Distribuidos', 4, 64, 8, @id_programa),
('SIM-802', 'Arquitectura de Software', 4, 64, 8, @id_programa),
('SIM-803', 'Minería de Datos', 3, 48, 8, @id_programa),
('SIM-804', 'Ética Profesional', 2, 32, 8, @id_programa),
('SIM-805', 'Electiva IV', 2, 32, 8, @id_programa);

-- Semestre 9
INSERT INTO asignatura (codigo_asignatura, nombre_asignatura, creditos, intensidad_horaria, semestre, id_programa) VALUES
('SIM-901', 'Práctica Profesional I', 6, 96, 9, @id_programa),
('SIM-902', 'Computación en la Nube', 3, 48, 9, @id_programa),
('SIM-903', 'Calidad de Software', 3, 48, 9, @id_programa),
('SIM-904', 'Electiva V', 2, 32, 9, @id_programa);

-- Semestre 10
INSERT INTO asignatura (codigo_asignatura, nombre_asignatura, creditos, intensidad_horaria, semestre, id_programa) VALUES
('SIM-1001', 'Proyecto de Grado / Práctica Profesional II', 8, 128, 10, @id_programa),
('SIM-1002', 'Emprendimiento y Gestión Empresarial', 3, 48, 10, @id_programa),
('SIM-1003', 'Seminario de Investigación', 2, 32, 10, @id_programa);

COMMIT;

-- Verificación rápida:
-- SELECT semestre, COUNT(*) AS asignaturas, SUM(creditos) AS creditos
-- FROM asignatura WHERE id_programa = @id_programa GROUP BY semestre ORDER BY semestre;
