package com.algamoney.api.web;

import com.algamoney.api.event.RecursoCriadoEvent;
import com.algamoney.api.model.Lancamento;
import com.algamoney.api.service.LancamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

    @Autowired
    private LancamentoService lancamentoService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public ResponseEntity<List<Lancamento>> all() {
        List<Lancamento> lancamentos = lancamentoService.getLancamentos();
        return ResponseEntity.ok(lancamentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lancamento> getLancamento(@PathVariable Long id) {
        var lancamento = lancamentoService.getLancamentoById(id);
        return ResponseEntity.ok(lancamento);
    }

    @PostMapping
    public ResponseEntity<Lancamento> create(@RequestBody @Valid Lancamento lancamentoRequest,
                                             HttpServletResponse response) {
        Lancamento lancamento = lancamentoService.createLancamento(lancamentoRequest);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamento.getId()));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(lancamento);
    }
}
