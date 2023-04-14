package com.algamoney.api.web;

import com.algamoney.api.model.Pessoa;
import com.algamoney.api.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

    @Autowired
    private PessoaRepository pessoaRepository;

    @GetMapping
    public ResponseEntity<List<Pessoa>> all() {
        List<Pessoa> pessoas = pessoaRepository.findAll();
        return !pessoas.isEmpty() ?
                ResponseEntity.ok(pessoas) :
                ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Pessoa> create(@RequestBody @Valid Pessoa pessoa) {
        Pessoa newPessoa = pessoaRepository.save(pessoa);

        URI uri = creataLocationResource(newPessoa);
        return ResponseEntity.created(uri).body(newPessoa);
    }

    private URI creataLocationResource(Pessoa newPessoa) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newPessoa.getId())
                .toUri();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> getPessoa(@PathVariable Long id) {
        return pessoaRepository
                .findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
