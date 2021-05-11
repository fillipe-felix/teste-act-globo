package com.fillipe.testeactglobo.controller;

import com.fillipe.testeactglobo.dto.MessageResponseDTO;
import com.fillipe.testeactglobo.dto.UsuarioDTO;
import com.fillipe.testeactglobo.entity.Usuario;
import com.fillipe.testeactglobo.exception.UsuarioNotFoundException;
import com.fillipe.testeactglobo.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/usuario")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<MessageResponseDTO> cadastrarUsuario(@RequestBody UsuarioDTO usuario){
        MessageResponseDTO objMsg = usuarioService.cadastrarUsuario(usuario);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(objMsg.getId()).toUri();
        return ResponseEntity.created(uri).body(objMsg);
    }

    @GetMapping("/{documento}")
    public ResponseEntity<Usuario> buscarUsuarioPorDocumento(@PathVariable String documento) throws UsuarioNotFoundException {
        Usuario usuario = usuarioService.buscarUsuarioPorDocumento(documento);
        return ResponseEntity.ok(usuario);
    }
}
