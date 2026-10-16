package com.servidor.apuntes.gimnasio.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Reserva {
    @NotNull 
    private long id;

    @NotBlank 
    private String cliente;

    @NotBlank 
    private String actividad;
    
    @Max (5)
    @Min (1)
    private int plaza;
    
    @NotNull 
    private boolean confirmada;

}
