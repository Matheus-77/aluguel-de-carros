package br.com.rotacerta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rotacerta.model.Veiculo;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
}