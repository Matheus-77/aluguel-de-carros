package br.com.rotacerta.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rotacerta.model.ContaAcesso;

public interface ContaAcessoRepository extends JpaRepository<ContaAcesso, Long> {

    Optional<ContaAcesso> findByLogin(String login);
}