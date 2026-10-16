package com.servidor.apuntes.gimnasio.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.servidor.apuntes.gimnasio.models.Reserva;

public class ReservaService {
    private Map<Long, Reserva> reservas = new HashMap<>();
    private long siguienteId = 1;

    public ResponseEntity<?> crearReserva(Reserva reserva) {

        try {

            if (reserva.getCliente() == null) {
                
                throw new IllegalArgumentException(
                    "El cliente es obligatorio"
                );
            }

            if (reserva.getPlaza()<=0) {
                throw new IllegalArgumentException(
                    "El número de plazas no debe ser menor que 0"
                );
            }

            if (reserva.getPlaza() > 5) {
                throw new IllegalArgumentException(
                    "No se puede reservar más de 5 plazas"
                );
            }

            reserva.setId(siguienteId++);
            reserva.setConfirmada(false);

            reservas.put(reserva.getId(), reserva);

            return ResponseEntity.ok(reserva);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        
    }

    public ResponseEntity<?> confirmarReserva(long id) {

        try {
            if (!reservas.containsKey(id)) {
                throw new IllegalArgumentException(
                    "La reserva no existe"
                );
            }

            Reserva res = reservas.get(id);

            if (res.isConfirmada()) {
                throw new IllegalArgumentException(
                    "La reserva ya estaba confirmada"
                );
            }

            res.setConfirmada(true);

            return ResponseEntity.ok(res);
        } catch (IllegalArgumentException e) {
            
            return ResponseEntity.badRequest().body(e.getMessage());

        }

    }

    public ResponseEntity<?> borrarReserva(long id){

        try {

            if (!reservas.containsKey(id)) {
                throw new IllegalAccessError(
                    "La reseva no existe"
                );
            }

            Reserva eliminada = reservas.remove(id);

            return ResponseEntity.ok(eliminada);

        } catch (IllegalAccessError e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    } 


    public ResponseEntity<?> listar(){
        try {
            if (reservas.isEmpty()) {
                throw new IllegalArgumentException(
                    "No hay reservas disponibles"
                );
            }

            return ResponseEntity.ok(reservas);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
