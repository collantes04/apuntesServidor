package com.servidor.apuntes.biblioteca.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.servidor.apuntes.biblioteca.models.Libro;
import com.servidor.apuntes.biblioteca.service.LibroService;

import jakarta.validation.Valid;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;






@RestController 
@RequestMapping("/api/libros")
public class LibroController {

    @Autowired
    private LibroService ls;

    //GETS
    @GetMapping()
    public ResponseEntity<?> listaLibros() {
        try {
            return ResponseEntity.ok(ls.getLibros());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarId(@PathVariable long id) {
        try {
            return ResponseEntity.ok(ls.listarPorId(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    @GetMapping("/disponibles")
    public ResponseEntity<?> getDisponibles() {
        try {
            return ResponseEntity.ok(ls.listarDisponibles());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //POST

    @PostMapping()
    public ResponseEntity<?> createLibro(@RequestBody @Valid Libro lib) {
        return ResponseEntity.ok(ls.addLibro(lib));
    }
    
    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLibro(@PathVariable long id){
        try {
            return ResponseEntity.ok(ls.deleteLibro(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/status")
    public ResponseEntity<?> status() {
        return ResponseEntity.ok().body("Petición realizada");
    }
    
    
}
