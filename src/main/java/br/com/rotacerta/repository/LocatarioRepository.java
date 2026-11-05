package br.com.rotacerta.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rotacerta.model.Locatario;

public interface LocatarioRepository extends JpaRepository<Locatario, Long> {

    Optional<Locatario> findByLogin(String login);
}