package com.example.acadify.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "syllabus")
public class Syllabus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_syllabus")
    private Long idSyllabus;

    @Column(name = "descripcion", length = 1000)
    private String descripcion;

    @OneToOne
    @JoinColumn(name = "id_asignatura", nullable = false, unique = true)
    private Asignatura asignatura;

    @ManyToMany
    @JoinTable(
            name = "syllabus_resultado_aprendizaje",
            joinColumns = @JoinColumn(name = "id_syllabus"),
            inverseJoinColumns = @JoinColumn(name = "id_resultado")
    )
    private List<ResultadoAprendizaje> resultadosCubiertos = new ArrayList<>();

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDate fechaCreacion;

    public Syllabus() {
    }

    public Syllabus(String descripcion, Asignatura asignatura) {
        this.descripcion = descripcion;
        this.asignatura = asignatura;
    }

    @PrePersist
    protected void alCrear() {
        this.fechaCreacion = LocalDate.now();
    }

    public Long getIdSyllabus() { return idSyllabus; }
    public void setIdSyllabus(Long idSyllabus) { this.idSyllabus = idSyllabus; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Asignatura getAsignatura() { return asignatura; }
    public void setAsignatura(Asignatura asignatura) { this.asignatura = asignatura; }

    public List<ResultadoAprendizaje> getResultadosCubiertos() { return resultadosCubiertos; }
    public void setResultadosCubiertos(List<ResultadoAprendizaje> resultadosCubiertos) { this.resultadosCubiertos = resultadosCubiertos; }

    public LocalDate getFechaCreacion() { return fechaCreacion; }
}