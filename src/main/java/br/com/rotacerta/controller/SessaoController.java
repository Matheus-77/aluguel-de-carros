package br.com.rotacerta.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rotacerta.model.ContaAcesso;
import br.com.rotacerta.service.SessaoService;

@RestController
@RequestMapping("/usuarios")
public class SessaoController {

    private final SessaoService sessaoService;

    public SessaoController(SessaoService sessaoService) {
        this.sessaoService = sessaoService;
    }

    @GetMapping("/me")
    public ContaAcesso contaAtual(Authentication auth) {
        return sessaoService.contaAtual(auth.getName());
    }
}