package br.com.rotacerta.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "banco")
public class BancoParceiro extends ParceiroAvaliador {
}