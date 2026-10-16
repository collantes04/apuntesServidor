package com.servidor.apuntes.productos.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class Producto {
    
    @NotNull 
    private int id;

    private static int contador = 1;
    
    @NotBlank 
    private String nombre;

    @Min(value = 0)
    private int precio;

    @NotBlank
    private String categoria;

    public Producto(@NotBlank String nombre, @Min(0) int precio, @NotBlank String categoria) {
        this.id += contador;
        contador++;
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
    }

    //Getters y Setters

    public int getId() {
        return id;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    
    
}
