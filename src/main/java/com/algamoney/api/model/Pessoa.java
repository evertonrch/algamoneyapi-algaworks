package com.algamoney.api.model;

import javax.persistence.*;

@Entity
@Table(name = "tb_pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean ativo;

    @Embedded
    private Endereco endereco;

    public Long getId() {
        return id;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public Endereco getEndereco() {
        return endereco;
    }
}
