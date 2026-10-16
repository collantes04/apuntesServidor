package com.servidor.apuntes.biblioteca.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.servidor.apuntes.biblioteca.models.Prestamo;
import com.servidor.apuntes.biblioteca.service.PrestamoService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController 
@RequestMapping("/api/prestamos")
public class PrestamoController {
    private PrestamoService ps;

    public PrestamoController(PrestamoService ps){
        this.ps = ps;
    }
    //GETS

    @GetMapping()
    public ResponseEntity<?> listaPrestamos() {
        return ps.getPrestamos();
    }

    @GetMapping("/activos")
    public ResponseEntity<?> getActivos() {
        return ps.getActivos();
    }

    @GetMapping("/usuario/{nombre}")
    public ResponseEntity<?> getMethodName(@PathVariable String nombre) {
        return ps.getPrestamoUsuario(nombre);
    }
    
    //POSTS

    @PostMapping()
    public ResponseEntity<?> crearPrestamo(@RequestBody Prestamo p) {
        return ps.crearPrestamo(p);
    }
    
    //PUTS

    @PutMapping("/{id}/devolver")
    public ResponseEntity<?> devolver(@PathVariable long id) {
        return ps.devolver(id);
    }
    
}
