package com.servidor.apuntes.biblioteca.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.servidor.apuntes.biblioteca.models.Prestamo;

@Service 
public class PrestamoService {
    private Map<Long, Prestamo> mapaPrest = new ConcurrentHashMap<>();
    private long id = 1;
    private LibroService ls;

    public PrestamoService(LibroService ls) {
        this.ls = ls;
    }
    //POST
    public ResponseEntity<?> crearPrestamo(Prestamo p){

        try {
            //compruebo que exista primero
            if (ls.listarPorId(p.getLibroId()).getStatusCode() == HttpStatus.OK) {
                ResponseEntity<?> rp = ls.disponibilidadLibro(p.getLibroId()); //Cazo la disponibilidad del libro
                if (rp.getStatusCode() != HttpStatus.OK) {
                    return rp; //si no está disponible, lanzo la respuesta al servidor
                }
                //si está disponible, se ha marcado ya como prestado el libro y se crea el prestamo
                p.setDevuelto(false);
                p.setId(id++);
                mapaPrest.put(p.getId(), p);
                return ResponseEntity.ok(p);

            } else {
                throw new NoSuchElementException("El libro que se intenta prestar, no existe");
            }

        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //GET

    public ResponseEntity<?> getPrestamos(){
        try {
            if (mapaPrest.isEmpty()) {
                throw new NoSuchElementException("No hay préstamos");
            }

            return ResponseEntity.ok(mapaPrest);

        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    public ResponseEntity<?> getActivos(){
        try {
            List<Prestamo> lista = new ArrayList<>();

            if (mapaPrest.isEmpty()) {
                throw new NoSuchElementException("No hay préstamos");
            }

            for (Prestamo prestamo : mapaPrest.values()) {
                if (!prestamo.isDevuelto()) {
                    lista.add(prestamo);
                }
            }

            return ResponseEntity.ok(lista);

        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    public ResponseEntity<?> getPrestamoUsuario(String user){
        List<Prestamo> pUser = new ArrayList<>();

        for (Prestamo prestamo : mapaPrest.values()) {
            if (prestamo.getUsuario().equals(user)) {
                pUser.add(prestamo);
            }
        }

        return ResponseEntity.ok(pUser);

    }
    

    //PUT

    public ResponseEntity<?> devolver(long id){
        Prestamo p;

        try {
            if (!mapaPrest.containsKey(id)) {
                throw new NoSuchElementException("No existe este préstamo");
            }
            
            p = mapaPrest.get(id);
            ResponseEntity<?> rp = ls.devolverLibro(p.getLibroId());

            /*Si está prestado no se puede prestar
            si no está prestado no entrará al if y significa que el estado 
            de ese libro ha cambiado siguiendo con la operacion adelante*/
            if (rp.getStatusCode() != HttpStatus.OK) {
                return rp;
            }

            if (!p.isDevuelto()) {
                p.setDevuelto(true);
            }

            mapaPrest.put(p.getId(), p);

            return ResponseEntity.ok(p);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
