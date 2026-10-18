package com.servidor.apuntes.biblioteca.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.servidor.apuntes.biblioteca.models.Prestamo;
import com.servidor.apuntes.biblioteca.service.PrestamoService;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController 
@RequestMapping("/api/prestamos")
public class PrestamoController {
    @Autowired 
    private PrestamoService ps;


    //GETS

    @GetMapping()
    public ResponseEntity<?> listaPrestamos() {
        try {
            return ResponseEntity.ok(ps.getPrestamos());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/activos")
    public ResponseEntity<?> getActivos() {
        try {
            return ResponseEntity.ok(ps.getActivos());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/usuario/{nombre}")
    public ResponseEntity<?> getPrestUser(@PathVariable String nombre) {
        try {
            return ResponseEntity.ok(ps.getPrestamoUsuario(nombre));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    //POSTS

    @PostMapping()
    public ResponseEntity<?> crearPrestamo(@RequestBody Prestamo p) {
        try {
            return ResponseEntity.ok(ps.crearPrestamo(p));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
    
    //PUTS

    @PutMapping("/{id}/devolver")
    public ResponseEntity<?> devolver(@PathVariable long id) {
        try {
            return ResponseEntity.ok(ps.devolver(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
    
}
