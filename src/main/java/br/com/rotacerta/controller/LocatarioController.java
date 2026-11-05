package br.com.rotacerta.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rotacerta.model.Locatario;
import br.com.rotacerta.service.LocatarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
public class LocatarioController {

    private final LocatarioService locatarioService;

    public LocatarioController(LocatarioService locatarioService) {
        this.locatarioService = locatarioService;
    }

    @PostMapping
    public Locatario cadastrar(@Valid @RequestBody Locatario dados) {
        return locatarioService.cadastrar(dados);
    }

    @GetMapping
    public List<Locatario> listar() {
        return locatarioService.listar();
    }

    @GetMapping("/{id}")
    public Locatario buscar(@PathVariable Long id) {
        return locatarioService.buscar(id);
    }

    @PutMapping("/{id}")
    public Locatario atualizar(@PathVariable Long id, @Valid @RequestBody Locatario dados, Authentication auth) {
        return locatarioService.atualizar(id, dados, auth.getName());
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id, Authentication auth) {
        locatarioService.excluir(id, auth.getName());
    }
}