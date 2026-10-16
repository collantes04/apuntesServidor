package com.servidor.apuntes.biblioteca.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data 
public class Prestamo {

    @NotNull
    private long id;

    @NotBlank 
    private String usuario;

    @NotNull 
    private long libroId;

    @NotNull 
    private int dia;

    @NotNull 
    private boolean devuelto = false;
}
