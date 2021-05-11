package com.fillipe.testeactglobo.repository;

import com.fillipe.testeactglobo.entity.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {

    public Optional<Usuario> findByDocumento(String documento);
}
