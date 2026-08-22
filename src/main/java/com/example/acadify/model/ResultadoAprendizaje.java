package com.example.acadify.model;

import jakarta.persistence.*;

@Entity
@Table(name = "resultado_aprendizaje")
public class ResultadoAprendizaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resultado")
    private Long idResultado;

    @Column(name = "descripcion", nullable = false, length = 500)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "id_asignatura", nullable = false)
    private Asignatura asignatura;

    public ResultadoAprendizaje() {
    }

    public ResultadoAprendizaje(String descripcion, Asignatura asignatura) {
        this.descripcion = descripcion;
        this.asignatura = asignatura;
    }

    public Long getIdResultado() { return idResultado; }
    public void setIdResultado(Long idResultado) { this.idResultado = idResultado; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Asignatura getAsignatura() { return asignatura; }
    public void setAsignatura(Asignatura asignatura) { this.asignatura = asignatura; }
}
