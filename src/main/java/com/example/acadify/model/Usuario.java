package com.example.acadify.model;

import  jakarta.persistence.*;

@Entity
@Table(name = "Usuarios")

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column (nullable = false)
    private String nombre;

    @Column (nullable = false, unique = true)
    private String email;

    public Usuario() {}
    public Usuario(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    public long getId() {   return id;    }
    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
}

