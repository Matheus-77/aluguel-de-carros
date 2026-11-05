package br.com.rotacerta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rotacerta.model.SolicitacaoAluguel;

public interface SolicitacaoRepository extends JpaRepository<SolicitacaoAluguel, Long> {

    List<SolicitacaoAluguel> findByClienteLogin(String login);

    boolean existsByClienteId(Long clienteId);
}