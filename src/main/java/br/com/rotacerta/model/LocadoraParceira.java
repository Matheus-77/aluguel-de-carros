package br.com.rotacerta.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "empresa")
public class LocadoraParceira extends ParceiroAvaliador {
}