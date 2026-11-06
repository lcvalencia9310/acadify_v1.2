-- =====================================================================
-- Resultados de Aprendizaje y Syllabus para el programa simulado
-- "Ingeniería de Sistemas" (SIM-ING-SIS)
-- =====================================================================
-- Requiere haber corrido antes carga-programa-simulado.sql (programa +
-- 47 asignaturas). Este script NO vuelve a insertar programa ni
-- asignaturas: busca el programa ya cargado por su codigo_programa.
--
-- Carga: 94 Resultados de Aprendizaje (2 por asignatura, texto
-- genérico basado en el nombre de cada una) + 47 Syllabus (1 por
-- asignatura) + el vínculo de cada syllabus con los resultados de su
-- misma asignatura.
--
-- Corre TODO el script de una sola vez (no línea por línea) para que
-- la variable @id_programa se mantenga durante toda la sesión.
--
-- Si lo corres dos veces vas a duplicar los Resultados de Aprendizaje
-- (esa tabla no tiene restricción única); el Syllabus sí está
-- protegido (id_asignatura es único), así que esa parte fallará en un
-- segundo intento — es la señal de que ya se cargó.
-- =====================================================================

START TRANSACTION;

SET @id_programa = (SELECT id_programa FROM programa WHERE codigo_programa = 'SIM-ING-SIS');

-- Resultados de Aprendizaje (2 por asignatura)
INSERT INTO resultado_aprendizaje (descripcion, id_asignatura)
SELECT CONCAT('Aplica los conceptos fundamentales de ', nombre_asignatura, ' en la solución de problemas propios del programa.'), id_asignatura
FROM asignatura WHERE id_programa = @id_programa;

INSERT INTO resultado_aprendizaje (descripcion, id_asignatura)
SELECT CONCAT('Desarrolla ejercicios prácticos y evalúa resultados relacionados con ', nombre_asignatura, '.'), id_asignatura
FROM asignatura WHERE id_programa = @id_programa;

-- Syllabus (1 por asignatura, 1:1)
INSERT INTO syllabus (descripcion, id_asignatura, fecha_creacion)
SELECT
    CONCAT(
        'Plan de contenido de ', nombre_asignatura, ': fundamentos teóricos, ',
        'actividades prácticas y evaluación por proyectos. Créditos: ', creditos,
        ' · Intensidad horaria: ', intensidad_horaria, ' h.'
    ),
    id_asignatura,
    CURDATE()
FROM asignatura WHERE id_programa = @id_programa;

-- Vincula cada syllabus con los Resultados de Aprendizaje de su misma asignatura
INSERT INTO syllabus_resultado_aprendizaje (id_syllabus, id_resultado)
SELECT s.id_syllabus, r.id_resultado
FROM syllabus s
JOIN asignatura a ON a.id_asignatura = s.id_asignatura
JOIN resultado_aprendizaje r ON r.id_asignatura = a.id_asignatura
WHERE a.id_programa = @id_programa;

COMMIT;

-- Verificación rápida:
-- SELECT a.nombre_asignatura, COUNT(DISTINCT r.id_resultado) AS resultados, COUNT(DISTINCT s.id_syllabus) AS syllabus
-- FROM asignatura a
-- LEFT JOIN resultado_aprendizaje r ON r.id_asignatura = a.id_asignatura
-- LEFT JOIN syllabus s ON s.id_asignatura = a.id_asignatura
-- WHERE a.id_programa = @id_programa
-- GROUP BY a.id_asignatura;
