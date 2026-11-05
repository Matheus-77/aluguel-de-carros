package br.com.rotacerta.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import br.com.rotacerta.model.Locatario;
import br.com.rotacerta.repository.ContaAcessoRepository;
import br.com.rotacerta.repository.LocatarioRepository;
import br.com.rotacerta.repository.SolicitacaoRepository;

@Service
@Transactional(readOnly = true)
public class LocatarioService {

    private final LocatarioRepository locatarioRepository;
    private final ContaAcessoRepository contaAcessoRepository;
    private final SolicitacaoRepository solicitacaoRepository;
    private final PasswordEncoder passwordEncoder;

    public LocatarioService(
            LocatarioRepository locatarioRepository,
            ContaAcessoRepository contaAcessoRepository,
            SolicitacaoRepository solicitacaoRepository,
            PasswordEncoder passwordEncoder) {
        this.locatarioRepository = locatarioRepository;
        this.contaAcessoRepository = contaAcessoRepository;
        this.solicitacaoRepository = solicitacaoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Locatario cadastrar(Locatario locatario) {
        if (estaVazio(locatario.getLogin()) || estaVazio(locatario.getSenha())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (contaAcessoRepository.findByLogin(locatario.getLogin()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        locatario.setSenha(passwordEncoder.encode(locatario.getSenha()));
        return locatarioRepository.save(locatario);
    }

    public List<Locatario> listar() {
        return locatarioRepository.findAll();
    }

    public Locatario buscar(Long id) {
        return locatarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public Locatario atualizar(Long id, Locatario dados, String loginAtual) {
        Locatario locatario = buscarProprioCadastro(id, loginAtual);
        locatario.setNome(dados.getNome());
        locatario.setRg(dados.getRg());
        locatario.setCpf(dados.getCpf());
        locatario.setEndereco(dados.getEndereco());
        locatario.setProfissao(dados.getProfissao());
        locatario.setRendimentos(dados.getRendimentos());
        return locatarioRepository.save(locatario);
    }

    @Transactional
    public void excluir(Long id, String loginAtual) {
        Locatario locatario = buscarProprioCadastro(id, loginAtual);
        if (solicitacaoRepository.existsByClienteId(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        locatarioRepository.delete(locatario);
    }

    private Locatario buscarProprioCadastro(Long id, String loginAtual) {
        Locatario locatario = buscar(id);
        if (!locatario.getLogin().equals(loginAtual)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return locatario;
    }

    private boolean estaVazio(String texto) {
        return texto == null || texto.isBlank();
    }
}