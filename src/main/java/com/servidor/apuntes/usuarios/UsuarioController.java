package com.servidor.apuntes.usuarios;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.executable.ValidateOnExecution;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api")
public class UsuarioController {
    
    private List<Usuario> listaUsuarios = new ArrayList<>();

    
    @PostMapping("/usuarios")
    public String addUser(@Valid @RequestBody Usuario usuario) {
        listaUsuarios.add(usuario);
        return "Usuario añadido correctamente";
    }
    

    @PostMapping("/adduser")
    public ResponseEntity<String> addUsuario(@Valid @RequestBody Usuario usuario) {
        listaUsuarios.add(usuario);
        return ResponseEntity.status(HttpStatus.CREATED)
        .body("201 created");
    }
    
    
}
