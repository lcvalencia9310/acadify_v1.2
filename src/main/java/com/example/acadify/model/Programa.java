package com.example.acadify.model;

import jakarta.persistence.*;

@Entity
@Table(name = "programa")
public class Programa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_programa")
    private Long idPrograma;

    @Column(name = "codigo_programa", nullable = false, unique = true)
    private String codigoPrograma;

    @Column(name = "nombre_programa", nullable = false)
    private String nombrePrograma;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    public Programa() {
    }

    public Programa(String codigoPrograma, String nombrePrograma, Boolean activo) {
        this.codigoPrograma = codigoPrograma;
        this.nombrePrograma = nombrePrograma;
        this.activo = activo;
    }

    public Long getIdPrograma() { return idPrograma; }
    public void setIdPrograma(Long idPrograma) { this.idPrograma = idPrograma; }

    public String getCodigoPrograma() { return codigoPrograma; }
    public void setCodigoPrograma(String codigoPrograma) { this.codigoPrograma = codigoPrograma; }

    public String getNombrePrograma() { return nombrePrograma; }
    public void setNombrePrograma(String nombrePrograma) { this.nombrePrograma = nombrePrograma; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}
