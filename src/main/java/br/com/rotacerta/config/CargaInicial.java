package br.com.rotacerta.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.rotacerta.model.BancoParceiro;
import br.com.rotacerta.model.ContaAcesso;
import br.com.rotacerta.model.LocadoraParceira;
import br.com.rotacerta.model.ParceiroAvaliador;
import br.com.rotacerta.model.Veiculo;
import br.com.rotacerta.repository.ContaAcessoRepository;
import br.com.rotacerta.repository.VeiculoRepository;

@Component
public class CargaInicial implements CommandLineRunner {

    private final ContaAcessoRepository contas;
    private final VeiculoRepository veiculos;
    private final PasswordEncoder passwordEncoder;
    private final String senhaParceiros;

    public CargaInicial(
            ContaAcessoRepository contas,
            VeiculoRepository veiculos,
            PasswordEncoder passwordEncoder,
            @Value("${AGENTE_SENHA}") String senhaParceiros) {
        this.contas = contas;
        this.veiculos = veiculos;
        this.passwordEncoder = passwordEncoder;
        this.senhaParceiros = senhaParceiros;
    }

    @Override
    public void run(String... args) {
        ContaAcesso locadora = registrarParceiro(new LocadoraParceira(), "RotaCerta Locadora", "empresa");
        registrarParceiro(new BancoParceiro(), "Banco Horizonte", "banco");

        if (veiculos.count() == 0) {
            registrarVeiculo("12345678901", 2023, "Fiat", "Argo", "ABC1D23", locadora);
            registrarVeiculo("12345678902", 2024, "Volkswagen", "Polo", "DEF4G56", locadora);
            registrarVeiculo("12345678903", 2022, "Chevrolet", "Onix", "GHI7J89", locadora);
        }
    }

    private ContaAcesso registrarParceiro(ParceiroAvaliador parceiro, String nome, String login) {
        return contas.findByLogin(login).orElseGet(() -> {
            parceiro.setNome(nome);
            parceiro.setLogin(login);
            parceiro.setSenha(passwordEncoder.encode(senhaParceiros));
            return contas.save(parceiro);
        });
    }

    private void registrarVeiculo(
            String matricula,
            int ano,
            String marca,
            String modelo,
            String placa,
            ContaAcesso proprietario) {
        Veiculo veiculo = new Veiculo();
        veiculo.setMatricula(matricula);
        veiculo.setAno(ano);
        veiculo.setMarca(marca);
        veiculo.setModelo(modelo);
        veiculo.setPlaca(placa);
        veiculo.setProprietario(proprietario);
        veiculos.save(veiculo);
    }
}