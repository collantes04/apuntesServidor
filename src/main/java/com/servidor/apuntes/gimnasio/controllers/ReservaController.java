package com.servidor.apuntes.gimnasio.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.servidor.apuntes.gimnasio.models.Reserva;
import com.servidor.apuntes.gimnasio.services.ReservaService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;




@RestController 
@RequestMapping("/api/reservas")
public class ReservaController {
    private ReservaService rs = new ReservaService();
    
    @PostMapping()
    public ResponseEntity<?> crear(@RequestBody Reserva reserva) {
        return rs.crearReserva(reserva);
    }

    @PutMapping("/{id}/confirmar")
    public ResponseEntity<?> confirmar(@PathVariable long id) {
        return rs.confirmarReserva(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrar(@PathVariable long id){
        return rs.borrarReserva(id);
    }

    @GetMapping()
    public ResponseEntity<?> listadoReservas() {
        return rs.listar();
    }
    
    
}
