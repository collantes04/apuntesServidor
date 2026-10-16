package com.servidor.apuntes.biblioteca.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.servidor.apuntes.biblioteca.models.Libro;
import com.servidor.apuntes.biblioteca.service.LibroService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;





@RestController 
@RequestMapping("/api/libros")
public class LibroController {
    private LibroService ls;

    public LibroController(LibroService ls){
        this.ls = ls;
    }
    //GETS
    @GetMapping()
    public ResponseEntity<?> listaLibros() {
        return ls.getLibros();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarId(@PathVariable long id) {
        return ls.listarPorId(id);
    }
    
    @GetMapping("/disponibles")
    public ResponseEntity<?> getDisponibles() {
        return ls.listarDisponibles();
    }

    //POST

    @PostMapping()
    public ResponseEntity<?> createLibro(@RequestBody @Valid Libro lib) {
        return ls.addLibro(lib);
    }
    
    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLibro(@PathVariable long id){
        return ls.deleteLibro(id);
    }
    
}
