package com.algamoney.api.service;

import com.algamoney.api.model.Lancamento;
import com.algamoney.api.repository.LancamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    public List<Lancamento> getLancamentos() {
        List<Lancamento> lancamentos = lancamentoRepository.findAll();
        return lancamentos.isEmpty() ? Collections.emptyList() : lancamentos;
    }

    public Lancamento getLancamentoById(Long id) {
        Optional<Lancamento> optLancamento = lancamentoRepository.findById(id);
        return optLancamento.orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

    public Lancamento createLancamento(Lancamento lancamentoRequest) {
        lancamentoRepository.save(lancamentoRequest);
        return lancamentoRequest;
    }
}
