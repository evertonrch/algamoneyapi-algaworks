package com.algamoney.api.web;

import com.algamoney.api.model.Lancamento;
import com.algamoney.api.service.LancamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

    @Autowired
    private LancamentoService lancamentoService;

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
}
