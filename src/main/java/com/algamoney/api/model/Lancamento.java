package com.algamoney.api.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tb_lancamento")
public class Lancamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Descrição não pode ser nulo")
    @NotBlank(message = "Descrição não pode estar em branco")
    private String descricao;

    @NotNull(message = "Data de vencimento não pode ser nulo")
    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;

    @Null
    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;

    @NotNull
    @Digits(integer = 10, fraction = 2, message = "Valor inválido")
    @Column(scale = 10, precision = 2)
    private BigDecimal valor;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoLancamento tipoLancamento;

    @Lob
    private String observacao;

    @NotNull(message = "Categoria não pode ser nulo")
    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @NotNull(message = "Pessoa não pode ser nulo")
    @ManyToOne
    @JoinColumn(name = "id_pessoa")
    private Pessoa pessoa;

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getObservacao() {
        return observacao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public TipoLancamento getTipoLancamento() {
        return tipoLancamento;
    }
}
