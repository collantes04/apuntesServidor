package com.servidor.apuntes.biblioteca.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data 
public class Libro {
    
    @NotNull
    private long id;

    @NotBlank 
    private String titulo;

    @NotBlank
    private String autor;

    @NotNull 
    private boolean disponible = true;
}
