package com.servidor.apuntes.biblioteca.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

import com.servidor.apuntes.biblioteca.models.Libro;

@Service 
public class LibroService {
    private Map<Long, Libro> mapaLibros = new ConcurrentHashMap<>();
    private long siguienteId = 1;
    //GETS
    public Map<Long, Libro> getLibros() throws NoSuchElementException{
            if (mapaLibros.isEmpty()) {
                throw new NoSuchElementException("No hay libros disponibles");
            }

            return mapaLibros;
    }

    public Libro listarPorId(long id) throws NoSuchElementException{
        
            Libro l = mapaLibros.get(id);
            if (l == null) {
                throw new NoSuchElementException("No existe el libro con id " + id);
            }
            return l;
        
    }


    public List<Libro> listarDisponibles() throws NoSuchElementException{
        List<Libro> listaDisponibles = new ArrayList<>();
        
        if (mapaLibros.isEmpty()) {
            throw new NoSuchElementException("No hay libros disponibles");
        }

        for (Libro act : mapaLibros.values()) {

            if (act.isDisponible()) {
                listaDisponibles.add(act);
            }

        }

        return listaDisponibles;
    }

    public boolean disponibilidadLibro(long id) throws IllegalStateException{
 
        Libro l = mapaLibros.get(id);

        if (l.isDisponible()) {
            l.setDisponible(false);
            //Notificamos al servidor que se ha cambiado la disponibilidad
            return true;
        } 

        return false;
        
    }

    public boolean devolverLibro(long id){
    
        Libro l = mapaLibros.get(id);

        if (!l.isDisponible()) {
            l.setDisponible(true);
            return true;
        } 
        return false;
        
    }

    //POST

    public Libro addLibro(Libro lib){
        lib.setDisponible(true);
        lib.setId(siguienteId++);

        mapaLibros.put(lib.getId(), lib);
        return lib;
    }

    //DELETE

    public Libro deleteLibro(long id) throws NoSuchElementException{
        if (mapaLibros.isEmpty()) {
            throw new NoSuchElementException("No hay libros disponibles");
        }

        Libro eliminado = mapaLibros.remove(id);

        return eliminado;
       
    }
}
