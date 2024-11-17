package com.algamoney.api.repository.projection;

import com.algamoney.api.model.TipoLancamento;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ResumoLancamento {

    private Long codigo;
    private String descricao;
    private LocalDate dataPagamento;
    private LocalDate dataVencimento;
    private BigDecimal valor;
    private TipoLancamento tipo;
    private String categoria;
    private String pessoa;

    public ResumoLancamento(Long codigo, String descricao, LocalDate dataPagamento, LocalDate dataVencimento, BigDecimal valor, TipoLancamento tipo, String categoria, String pessoa) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.dataPagamento = dataPagamento;
        this.dataVencimento = dataVencimento;
        this.valor = valor;
        this.tipo = tipo;
        this.categoria = categoria;
        this.pessoa = pessoa;
    }

    public Long getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public TipoLancamento getTipo() {
        return tipo;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getPessoa() {
        return pessoa;
    }
}
