package br.com.rotacerta.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rotacerta.model.Veiculo;
import br.com.rotacerta.service.FrotaService;

@RestController
@RequestMapping("/automoveis")
public class VeiculoController {

    private final FrotaService frotaService;

    public VeiculoController(FrotaService frotaService) {
        this.frotaService = frotaService;
    }

    @GetMapping
    public List<Veiculo> listar() {
        return frotaService.listarFrota();
    }
}