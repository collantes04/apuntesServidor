package com.servidor.apuntes.biblioteca.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servidor.apuntes.biblioteca.models.Libro;
import com.servidor.apuntes.biblioteca.models.Prestamo;

@Service 
public class PrestamoService {
    private Map<Long, Prestamo> mapaPrest = new ConcurrentHashMap<>();
    private long id = 1;

    @Autowired 
    private LibroService ls;

    //POST
    public Prestamo crearPrestamo(Prestamo p) throws NoSuchElementException{
        
        Libro l = ls.listarPorId(p.getLibroId()); //compruebo que exista primero
        if (l != null) {

            if (ls.disponibilidadLibro(p.getLibroId())) { //esto cambia la disponibilidad y devuelve un booleano
                p.setDevuelto(false);
                p.setId(id++);
                mapaPrest.put(p.getId(), p);
            } else {
                throw new NoSuchElementException("El libro que se intenta prestar, no está disponible");
            }
            //si está disponible, se ha marcado ya como prestado el libro y se crea el prestamo

        } else {
            throw new NoSuchElementException("El libro que se intenta prestar, no existe");
        }

        return p;
    }

    //GET

    public Map<Long, Prestamo> getPrestamos(){
    
            if (mapaPrest.isEmpty()) {
                throw new NoSuchElementException("No hay préstamos");
            }

            return mapaPrest;

    }

    public List<Prestamo> getActivos(){
            List<Prestamo> lista = new ArrayList<>();

            if (mapaPrest.isEmpty()) {
                throw new NoSuchElementException("No hay préstamos");
            }

            for (Prestamo prestamo : mapaPrest.values()) {
                if (!prestamo.isDevuelto()) {
                    lista.add(prestamo);
                }
            }

            return lista;

    }

    public List<Prestamo> getPrestamoUsuario(String user){
        List<Prestamo> pUser = new ArrayList<>();

        for (Prestamo prestamo : mapaPrest.values()) {
            if (prestamo.getUsuario().equals(user)) {
                pUser.add(prestamo);
            }
        }

        return pUser;

    }
    

    //PUT

    public Prestamo devolver(long id){
        Prestamo p;

        if (!mapaPrest.containsKey(id)) {
            throw new NoSuchElementException("No existe este préstamo");
        }
        
        p = mapaPrest.get(id);

        /*Si está prestado no se puede prestar
        si no está prestado no entrará al if y significa que el estado 
        de ese libro ha cambiado siguiendo con la operacion adelante*/
        if (!ls.devolverLibro(p.getLibroId())) {
            throw new NoSuchElementException("El libro ya está prestado");
        }

        if (!p.isDevuelto()) {
            p.setDevuelto(true);
        }

        mapaPrest.put(p.getId(), p);

        return p;
    }

}
