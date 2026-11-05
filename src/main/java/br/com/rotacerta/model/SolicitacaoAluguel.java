package br.com.rotacerta.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
@Table(name = "pedido")
public class SolicitacaoAluguel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Locatario cliente;

    @ManyToOne
    private Veiculo automovel;
    @Enumerated(EnumType.STRING)
    private FaseSolicitacao status;

    public Long getId() {
        return id;
    }

    public Locatario getCliente() {
        return cliente;
    }

    public void setCliente(Locatario cliente) {
        this.cliente = cliente;
    }

    public Veiculo getAutomovel() {
        return automovel;
    }

    public void setAutomovel(Veiculo automovel) {
        this.automovel = automovel;
    }

    public FaseSolicitacao getStatus() {
        return status;
    }

    public void setStatus(FaseSolicitacao status) {
        this.status = status;
    }
}