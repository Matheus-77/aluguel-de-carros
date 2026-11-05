package br.com.rotacerta.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "agente")
public abstract class ParceiroAvaliador extends ContaAcesso {

    @Override
    public String getPerfil() {
        return "AGENTE";
    }
}