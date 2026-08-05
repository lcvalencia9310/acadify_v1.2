package com.example.acadify.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "syllabus")
public class Syllabus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_syllabus")
    private Long idSyllabus;

    @Column(name = "nombre_asignatura", nullable = false)
    private String nombreAsignatura;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "creditos", nullable = false)
    private Integer creditos;

    @Column(name = "intensidad_horaria", nullable = false)
    private Integer intensidadHoraria;

    @Column(name = "programa_academico", nullable = false)
    private String programaAcademico;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDate fechaCreacion;

    public Syllabus() {
    }

    public Syllabus(String nombreAsignatura, String descripcion, Integer creditos,
                    Integer intensidadHoraria, String programaAcademico) {
        this.nombreAsignatura = nombreAsignatura;
        this.descripcion = descripcion;
        this.creditos = creditos;
        this.intensidadHoraria = intensidadHoraria;
        this.programaAcademico = programaAcademico;
    }

    // Se ejecuta automáticamente justo antes de que Hibernate haga el INSERT
    @PrePersist
    protected void alCrear() {
        this.fechaCreacion = LocalDate.now();
    }

    public Long getIdSyllabus() { return idSyllabus; }
    public void setIdSyllabus(Long idSyllabus) { this.idSyllabus = idSyllabus; }

    public String getNombreAsignatura() { return nombreAsignatura; }
    public void setNombreAsignatura(String nombreAsignatura) { this.nombreAsignatura = nombreAsignatura; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Integer getCreditos() { return creditos; }
    public void setCreditos(Integer creditos) { this.creditos = creditos; }

    public Integer getIntensidadHoraria() { return intensidadHoraria; }
    public void setIntensidadHoraria(Integer intensidadHoraria) { this.intensidadHoraria = intensidadHoraria; }

    public String getProgramaAcademico() { return programaAcademico; }
    public void setProgramaAcademico(String programaAcademico) { this.programaAcademico = programaAcademico; }

    public LocalDate getFechaCreacion() { return fechaCreacion; }
}