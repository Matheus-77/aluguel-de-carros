package br.com.rotacerta.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.rotacerta.model.SolicitacaoAluguel;
import br.com.rotacerta.service.SolicitacaoService;

@RestController
@RequestMapping("/pedidos")
public class SolicitacaoController {

    private final SolicitacaoService solicitacaoService;

    public SolicitacaoController(SolicitacaoService solicitacaoService) {
        this.solicitacaoService = solicitacaoService;
    }

    @PostMapping
    public SolicitacaoAluguel criar(@RequestBody SolicitacaoAluguel dados, Authentication auth) {
        return solicitacaoService.criar(dados, auth.getName());
    }

    @GetMapping
    public List<SolicitacaoAluguel> listar(Authentication auth) {
        return solicitacaoService.listar(auth.getName(), ehParceiroAvaliador(auth));
    }

    @GetMapping("/{id}")
    public SolicitacaoAluguel buscar(@PathVariable Long id, Authentication auth) {
        return solicitacaoService.buscar(id, auth.getName(), ehParceiroAvaliador(auth));
    }

    @PutMapping("/{id}")
    public SolicitacaoAluguel modificar(
            @PathVariable Long id,
            @RequestBody SolicitacaoAluguel dados,
            Authentication auth) {
        return solicitacaoService.modificar(id, dados, auth.getName(), ehParceiroAvaliador(auth));
    }

    @PostMapping("/{id}/cancelar")
    public SolicitacaoAluguel cancelar(@PathVariable Long id, Authentication auth) {
        return solicitacaoService.cancelar(id, auth.getName());
    }

    @PostMapping("/{id}/avaliar")
    public SolicitacaoAluguel avaliar(
            @PathVariable Long id,
            @RequestParam boolean aprovado,
            Authentication auth) {
        return solicitacaoService.avaliar(id, aprovado, auth.getName(), ehParceiroAvaliador(auth));
    }

    private boolean ehParceiroAvaliador(Authentication auth) {
        return auth.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_AGENTE"));
    }
}