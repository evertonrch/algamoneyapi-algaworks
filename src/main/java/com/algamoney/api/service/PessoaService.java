package com.algamoney.api.service;

import com.algamoney.api.model.Pessoa;
import com.algamoney.api.repository.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

//@Component
@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public Pessoa updatePessoa(Long id, Pessoa pessoaRequest) {
        Optional<Pessoa> findPessoa = pessoaRepository.findById(id);
        if (findPessoa.isEmpty())
            throw new EmptyResultDataAccessException(1);

        Pessoa pessoa = findPessoa.get();
        BeanUtils.copyProperties(pessoaRequest, pessoa, "id");
        pessoaRepository.save(pessoa);
        return pessoa;
    }
}
