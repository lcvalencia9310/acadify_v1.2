package com.example.acadify.model;

import jakarta.persistence.*;

@Entity
@Table(name = "asignatura")
public class Asignatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asignatura")
    private Long idAsignatura;

    @Column(name = "codigo_asignatura", nullable = false, unique = true)
    private String codigoAsignatura;

    @Column(name = "nombre_asignatura", nullable = false)
    private String nombreAsignatura;

    @Column(name = "creditos", nullable = false)
    private Integer creditos;

    @Column(name = "intensidad_horaria", nullable = false)
    private Integer intensidadHoraria;

    // Nullable a propósito: las asignaturas ya existentes no tenían semestre asignado.
    @Column(name = "semestre")
    private Integer semestre;

    @ManyToOne
    @JoinColumn(name = "id_programa", nullable = false)
    private Programa programa;

    public Asignatura() {
    }

    public Asignatura(String codigoAsignatura, String nombreAsignatura, Integer creditos,
                      Integer intensidadHoraria, Integer semestre, Programa programa) {
        this.codigoAsignatura = codigoAsignatura;
        this.nombreAsignatura = nombreAsignatura;
        this.creditos = creditos;
        this.intensidadHoraria = intensidadHoraria;
        this.semestre = semestre;
        this.programa = programa;
    }

    public Long getIdAsignatura() { return idAsignatura; }
    public void setIdAsignatura(Long idAsignatura) { this.idAsignatura = idAsignatura; }

    public String getCodigoAsignatura() { return codigoAsignatura; }
    public void setCodigoAsignatura(String codigoAsignatura) { this.codigoAsignatura = codigoAsignatura; }

    public String getNombreAsignatura() { return nombreAsignatura; }
    public void setNombreAsignatura(String nombreAsignatura) { this.nombreAsignatura = nombreAsignatura; }

    public Integer getCreditos() { return creditos; }
    public void setCreditos(Integer creditos) { this.creditos = creditos; }

    public Integer getIntensidadHoraria() { return intensidadHoraria; }
    public void setIntensidadHoraria(Integer intensidadHoraria) { this.intensidadHoraria = intensidadHoraria; }

    public Integer getSemestre() { return semestre; }
    public void setSemestre(Integer semestre) { this.semestre = semestre; }

    public Programa getPrograma() { return programa; }
    public void setPrograma(Programa programa) { this.programa = programa; }
}
