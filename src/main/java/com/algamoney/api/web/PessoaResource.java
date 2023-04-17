package com.algamoney.api.web;

import com.algamoney.api.event.RecursoCriadoEvent;
import com.algamoney.api.model.Pessoa;
import com.algamoney.api.repository.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public ResponseEntity<List<Pessoa>> all() {
        List<Pessoa> pessoas = pessoaRepository.findAll();
        return !pessoas.isEmpty() ?
                ResponseEntity.ok(pessoas) :
                ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Pessoa> create(@RequestBody @Valid Pessoa pessoa, HttpServletResponse response) {
        Pessoa newPessoa = pessoaRepository.save(pessoa);
        // Evento de criar header Location
        publisher.publishEvent(new RecursoCriadoEvent(this, response, newPessoa.getId()));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newPessoa);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> getPessoa(@PathVariable Long id) {
        return pessoaRepository
                .findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> updatePessoa(@PathVariable Long id,
                                               @Valid @RequestBody Pessoa pessoaRequest) {
        Optional<Pessoa> findPessoa = pessoaRepository.findById(id);
        if (findPessoa.isEmpty())
            throw new EmptyResultDataAccessException(1);

        Pessoa pessoa = findPessoa.get();
        BeanUtils.copyProperties(pessoaRequest, pessoa, "id");
        pessoaRepository.save(pessoa);
        return ResponseEntity.ok(pessoa);
    }
}
