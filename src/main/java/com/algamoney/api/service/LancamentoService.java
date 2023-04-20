package com.algamoney.api.service;

import com.algamoney.api.model.Lancamento;
import com.algamoney.api.model.Pessoa;
import com.algamoney.api.repository.LancamentoRepository;
import com.algamoney.api.repository.PessoaRepository;
import com.algamoney.api.repository.filter.LancamentoFilter;
import com.algamoney.api.service.exception.PessoaInativaException;
import com.algamoney.api.service.exception.PessoaInexistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;


    public Page<Lancamento> getLancamentos(LancamentoFilter lancamentoFilter, Pageable pageable) {
        return lancamentoRepository.filtrar(lancamentoFilter, pageable);
    }

    public Lancamento getLancamentoById(Long id) {
        Optional<Lancamento> optLancamento = lancamentoRepository.findById(id);
        return optLancamento.orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

    public Lancamento createLancamento(Lancamento lancamentoRequest) {
        Optional<Pessoa> optPessoa = pessoaRepository.findById(lancamentoRequest.getPessoa().getId());
        if (optPessoa.isEmpty())
            throw new PessoaInexistenteException();

        Pessoa pessoa = optPessoa.get();
        if (pessoa.isInativo())
            throw new PessoaInativaException();

        return lancamentoRepository.save(lancamentoRequest);
    }

    public void deleteLancamento(Long id) {
        lancamentoRepository.deleteById(id);
    }
}
