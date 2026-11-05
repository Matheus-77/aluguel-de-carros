package br.com.rotacerta.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rotacerta.model.Veiculo;
import br.com.rotacerta.repository.VeiculoRepository;

@Service
@Transactional(readOnly = true)
public class FrotaService {

    private final VeiculoRepository veiculoRepository;

    public FrotaService(VeiculoRepository veiculoRepository) {
        this.veiculoRepository = veiculoRepository;
    }

    public List<Veiculo> listarFrota() {
        return veiculoRepository.findAll();
    }
}