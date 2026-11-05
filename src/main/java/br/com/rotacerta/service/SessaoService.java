package br.com.rotacerta.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rotacerta.model.ContaAcesso;
import br.com.rotacerta.repository.ContaAcessoRepository;

@Service
@Transactional(readOnly = true)
public class SessaoService {

    private final ContaAcessoRepository contaAcessoRepository;

    public SessaoService(ContaAcessoRepository contaAcessoRepository) {
        this.contaAcessoRepository = contaAcessoRepository;
    }

    public ContaAcesso contaAtual(String login) {
        return contaAcessoRepository.findByLogin(login).orElseThrow();
    }
}