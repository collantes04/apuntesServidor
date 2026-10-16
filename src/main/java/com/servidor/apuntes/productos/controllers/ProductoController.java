package com.servidor.apuntes.productos.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.MethodNotAllowedException;

import com.servidor.apuntes.productos.models.Producto;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/products")
public class ProductoController {
    private Map<Integer, Producto> mapaProd = new HashMap<>();

    @PostMapping("")
    public String addProducto(@Valid @RequestBody Producto p) {
        this.mapaProd.put(p.getId(), p);
        
        return "Añadido el producto correctamente";
    }

    @GetMapping("")
    public Map<Integer, Producto> listarProductos() {
        return mapaProd;
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        
        try {

            if (!mapaProd.containsKey(id)) {
                throw new Exception(
                    "No existe el elemento con id " + id
                );
            } else mapaProd.remove(id);

        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
        return ResponseEntity.ok("Se ha eliminado correctamente");
    }
    
}

/*Implementa los siguientes endpoints:
GET /products: Devuelve una lista de productos.
POST /products: Permite agregar un nuevo producto.
DELETE /products/:id: Elimina un producto por su ID.
 */