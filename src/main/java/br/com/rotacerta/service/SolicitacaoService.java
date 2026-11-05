package br.com.rotacerta.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import br.com.rotacerta.model.FaseSolicitacao;
import br.com.rotacerta.model.Locatario;
import br.com.rotacerta.model.SolicitacaoAluguel;
import br.com.rotacerta.model.Veiculo;
import br.com.rotacerta.repository.LocatarioRepository;
import br.com.rotacerta.repository.SolicitacaoRepository;
import br.com.rotacerta.repository.VeiculoRepository;

@Service
@Transactional(readOnly = true)
public class SolicitacaoService {

    private final SolicitacaoRepository solicitacaoRepository;
    private final LocatarioRepository locatarioRepository;
    private final VeiculoRepository veiculoRepository;

    public SolicitacaoService(
            SolicitacaoRepository solicitacaoRepository,
            LocatarioRepository locatarioRepository,
            VeiculoRepository veiculoRepository) {
        this.solicitacaoRepository = solicitacaoRepository;
        this.locatarioRepository = locatarioRepository;
        this.veiculoRepository = veiculoRepository;
    }

    @Transactional
    public SolicitacaoAluguel criar(SolicitacaoAluguel dados, String loginAtual) {
        Locatario locatario = locatarioRepository.findByLogin(loginAtual)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        SolicitacaoAluguel solicitacao = new SolicitacaoAluguel();
        solicitacao.setCliente(locatario);
        solicitacao.setAutomovel(buscarVeiculo(dados));
        solicitacao.setStatus(FaseSolicitacao.EM_ANALISE);
        return solicitacaoRepository.save(solicitacao);
    }

    public List<SolicitacaoAluguel> listar(String loginAtual, boolean parceiro) {
        return parceiro
                ? solicitacaoRepository.findAll()
                : solicitacaoRepository.findByClienteLogin(loginAtual);
    }

    public SolicitacaoAluguel buscar(Long id, String loginAtual, boolean parceiro) {
        SolicitacaoAluguel solicitacao = solicitacaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!parceiro && !solicitacao.getCliente().getLogin().equals(loginAtual)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return solicitacao;
    }

    @Transactional
    public SolicitacaoAluguel modificar(Long id, SolicitacaoAluguel dados, String loginAtual, boolean parceiro) {
        SolicitacaoAluguel solicitacao = buscar(id, loginAtual, parceiro);
        exigirAnalise(solicitacao);
        solicitacao.setAutomovel(buscarVeiculo(dados));
        return solicitacaoRepository.save(solicitacao);
    }

    @Transactional
    public SolicitacaoAluguel cancelar(Long id, String loginAtual) {
        SolicitacaoAluguel solicitacao = buscar(id, loginAtual, false);
        if (solicitacao.getStatus() != FaseSolicitacao.EM_ANALISE
                && solicitacao.getStatus() != FaseSolicitacao.APROVADO) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        solicitacao.setStatus(FaseSolicitacao.CANCELADO);
        return solicitacaoRepository.save(solicitacao);
    }

    @Transactional
    public SolicitacaoAluguel avaliar(Long id, boolean aprovado, String loginAtual, boolean parceiro) {
        if (!parceiro) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        SolicitacaoAluguel solicitacao = buscar(id, loginAtual, true);
        exigirAnalise(solicitacao);
        solicitacao.setStatus(aprovado ? FaseSolicitacao.APROVADO : FaseSolicitacao.REPROVADO);
        return solicitacaoRepository.save(solicitacao);
    }

    private Veiculo buscarVeiculo(SolicitacaoAluguel dados) {
        if (dados == null || dados.getAutomovel() == null || dados.getAutomovel().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return veiculoRepository.findById(dados.getAutomovel().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    private void exigirAnalise(SolicitacaoAluguel solicitacao) {
        if (solicitacao.getStatus() != FaseSolicitacao.EM_ANALISE) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
}