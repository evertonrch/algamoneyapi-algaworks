package com.algamoney.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.List;

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

    @JsonIgnoreProperties("pessoa") // Ignora propriedade dentro de Contato, evita recurssão infinita
    @Valid
    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL)
    private List<Contato> contatos;

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

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @JsonIgnore
    @Transient
    public boolean isInativo() {
        return !this.ativo;
    }

    public List<Contato> getContatos() {
        return Collections.unmodifiableList(contatos);
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
