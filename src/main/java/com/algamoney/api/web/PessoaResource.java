package com.algamoney.api.web;

import com.algamoney.api.event.RecursoCriadoEvent;
import com.algamoney.api.model.Pessoa;
import com.algamoney.api.repository.PessoaRepository;
import com.algamoney.api.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and hasAuthority('SCOPE_read')")
    public ResponseEntity<List<Pessoa>> all() {
        List<Pessoa> pessoas = pessoaRepository.findAll();
        return !pessoas.isEmpty() ?
                ResponseEntity.ok(pessoas) :
                ResponseEntity.noContent().build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and hasAuthority('SCOPE_write')")
    public ResponseEntity<Pessoa> create(@RequestBody @Valid Pessoa pessoa, HttpServletResponse response) {
        Pessoa newPessoa = pessoaRepository.save(pessoa);

        // Evento de criar header Location
        publisher.publishEvent(new RecursoCriadoEvent(this, response, newPessoa.getId()));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newPessoa);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and hasAuthority('SCOPE_read')")
    public ResponseEntity<Pessoa> getPessoa(@PathVariable Long id) {
        return pessoaRepository
                .findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Atualização total
    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> updatePessoa(@PathVariable Long id,
                                               @Valid @RequestBody Pessoa pessoaRequest) {
        Pessoa pessoa = pessoaService.updatePessoa(id, pessoaRequest);
        return ResponseEntity.ok(pessoa);
    }

    // Atualização parcial
    @PutMapping("/{id}/ativo")
    public ResponseEntity<?> atualizaPropriedadeAtivo(@PathVariable Long id, @RequestBody Boolean ativo) {
        pessoaService.atualizarPropriedadeAtivo(id, ativo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
