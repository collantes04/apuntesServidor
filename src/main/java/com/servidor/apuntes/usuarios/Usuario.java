package com.servidor.apuntes.usuarios;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Usuario {

    @NotNull(message = "el id no puede ser null")
    private int id;

    @NotBlank(message = "no puede estar vacio este campo")
    private String nombre;
    
    @Email(message = "email incorrecto")
    private String email;

    public Usuario(int id, String nombre, String email){
        this.id = id;
        this.nombre = nombre;
        this.email = email;
    }

    //Setters y getters
    public String getEmail() {
        return email;
    }
    public int getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
