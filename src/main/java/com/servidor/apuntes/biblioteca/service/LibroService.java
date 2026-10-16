package com.servidor.apuntes.biblioteca.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.servidor.apuntes.biblioteca.models.Libro;

@Service 
public class LibroService {
    private Map<Long, Libro> mapaLibros = new ConcurrentHashMap<>();
    private long siguienteId = 1;
    //GETS
    public ResponseEntity<?> getLibros(){
        try {
            if (mapaLibros.isEmpty()) {
                throw new NoSuchElementException("No hay libros disponibles");
            }

            return ResponseEntity.ok(mapaLibros);

        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    public ResponseEntity<?> listarPorId(long id){
        try {
            Libro l = mapaLibros.get(id);
            if (l == null) {
                throw new NoSuchElementException("No existe el libro con id " + id);
            }
            return ResponseEntity.ok(l);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    public ResponseEntity<?> listarDisponibles(){
        List<Libro> listaDisponibles = new ArrayList<>();
        try {
             if (mapaLibros.isEmpty()) {
                throw new NoSuchElementException("No hay libros disponibles");
            }

            for (long n : mapaLibros.keySet()) {
                Libro act = mapaLibros.get(n);

                if (act.isDisponible()) {
                    listaDisponibles.add(act);
                }
            }

            return ResponseEntity.ok(listaDisponibles);

        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    public ResponseEntity<?> disponibilidadLibro(long id){

        try {
            Libro l = mapaLibros.get(id);

            if (l.isDisponible()) {
                l.setDisponible(false);
                //Notificamos al servidor que se ha cambiado la disponibilidad
                return ResponseEntity.ok("Ha cambiado la disponibilidad de: " + l.getTitulo() + " a prestado");
            } else {
                throw new IllegalStateException("El libro " + l.getTitulo() + " está prestado");
            }
        } catch (IllegalStateException e) {
            //lanzamos 409 que es conflict
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    public ResponseEntity<?> devolverLibro(long id){
    
        Libro l = mapaLibros.get(id);

        if (!l.isDisponible()) {
            l.setDisponible(true);
            return ResponseEntity.ok("Se ha devuelto el libro " + l.getTitulo());
        } 
        return ResponseEntity.status(HttpStatus.CONFLICT).body("El libro no ha sido prestado");
        
    }

    //POST

    public ResponseEntity<?> addLibro(Libro lib){
        lib.setDisponible(true);
        lib.setId(siguienteId++);

        mapaLibros.put(lib.getId(), lib);
        return ResponseEntity.ok(lib);
    }

    //DELETE

    public ResponseEntity<?> deleteLibro(long id){
        try {
            if (mapaLibros.isEmpty()) {
                throw new NoSuchElementException("No hay libros disponibles");
            }

            Libro eliminado = mapaLibros.remove(id);

            return ResponseEntity.ok(eliminado);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
