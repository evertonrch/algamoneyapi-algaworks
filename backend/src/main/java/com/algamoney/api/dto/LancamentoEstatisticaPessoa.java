package com.algamoney.api.dto;

import com.algamoney.api.model.Pessoa;
import com.algamoney.api.model.TipoLancamento;

import java.math.BigDecimal;

public class LancamentoEstatisticaPessoa {

    private TipoLancamento tipo;
    private Pessoa pessoa;
    private BigDecimal total;

    public LancamentoEstatisticaPessoa(TipoLancamento tipo, Pessoa pessoa, BigDecimal total) {
        this.tipo = tipo;
        this.pessoa = pessoa;
        this.total = total;
    }

    public TipoLancamento getTipo() {
        return tipo;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public BigDecimal getTotal() {
        return total;
    }
}
