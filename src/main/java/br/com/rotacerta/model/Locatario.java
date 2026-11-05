package br.com.rotacerta.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "cliente")
public class Locatario extends ContaAcesso {

    @NotBlank
    private String rg;

    @NotBlank
    private String cpf;

    @NotBlank
    private String endereco;

    @NotBlank
    private String profissao;

    @Valid
    @Size(max = 3)
    @CollectionTable(name = "cliente_rendimentos", joinColumns = @JoinColumn(name = "cliente_id"))
    @ElementCollection
    private List<FonteRenda> rendimentos = new ArrayList<>();

    @Override
    public String getPerfil() {
        return "CLIENTE";
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public List<FonteRenda> getRendimentos() {
        return rendimentos;
    }

    public void setRendimentos(List<FonteRenda> rendimentos) {
        this.rendimentos = rendimentos;
    }
}