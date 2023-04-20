package com.algamoney.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tb_pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 40)
    private String nome;

    @NotNull
    private Boolean ativo;

    @Embedded
    private Endereco endereco;

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @JsonIgnore
    @Transient
    public boolean isInativo() {
        return !this.ativo;
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", ativo=" + ativo +
                ", endereco=" + endereco +
                '}';
    }
}
