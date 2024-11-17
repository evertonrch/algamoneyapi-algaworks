package com.algamoney.api.repository.filter;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class LancamentoFilter {

    private String descricao;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataVencimentoFrom;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataVencimentoTo;


    public String getDescricao() {
        return descricao;
    }

    public LocalDate getDataVencimentoFrom() {
        return dataVencimentoFrom;
    }

    public LocalDate getDataVencimentoTo() {
        return dataVencimentoTo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setDataVencimentoFrom(LocalDate dataVencimentoFrom) {
        this.dataVencimentoFrom = dataVencimentoFrom;
    }

    public void setDataVencimentoTo(LocalDate dataVencimentoTo) {
        this.dataVencimentoTo = dataVencimentoTo;
    }
}
