package com.algamoney.api.web;

import com.algamoney.api.event.RecursoCriadoEvent;
import com.algamoney.api.exception.Error;
import com.algamoney.api.model.Lancamento;
import com.algamoney.api.repository.filter.LancamentoFilter;
import com.algamoney.api.repository.projection.ResumoLancamento;
import com.algamoney.api.service.LancamentoService;
import com.algamoney.api.service.exception.PessoaInativaException;
import com.algamoney.api.service.exception.PessoaInexistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

    @Autowired
    private LancamentoService lancamentoService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and hasAuthority('SCOPE_read')")
    public ResponseEntity<Page<Lancamento>> search(LancamentoFilter lancamentoFilter, Pageable pageable) {
        Page<Lancamento> lancamentos = lancamentoService.getLancamentos(lancamentoFilter, pageable);
        return ResponseEntity.ok(lancamentos);
    }

    @GetMapping(params = "resumo")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and hasAuthority('SCOPE_read')")
    public ResponseEntity<Page<ResumoLancamento>> resume(LancamentoFilter lancamentoFilter, Pageable pageable) {
        Page<ResumoLancamento> resumoLancamentos = lancamentoService.getResumo(lancamentoFilter, pageable);
        return ResponseEntity.ok(resumoLancamentos);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and hasAuthority('SCOPE_read')")
    public ResponseEntity<Lancamento> getLancamento(@PathVariable Long id) {
        var lancamento = lancamentoService.getLancamentoById(id);
        return ResponseEntity.ok(lancamento);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and hasAuthority('SCOPE_write')")
    public ResponseEntity<Lancamento> create(@RequestBody @Valid Lancamento lancamentoRequest,
                                             HttpServletResponse response) {
        Lancamento lancamento = lancamentoService.createLancamento(lancamentoRequest);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamento.getId()));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(lancamento);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_REMOVER_LANCAMENTO') and hasAuthority('SCOPE_write')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        lancamentoService.deleteLancamento(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler({PessoaInativaException.class})
    public ResponseEntity<Object> handlePessoaInexistenteException(PessoaInativaException ex) {
        String msgUsuario = messageSource.getMessage("mensagem.pessoa-inativa", null, LocaleContextHolder.getLocale());
        String msgDesenvolvedor = ex.toString();
        List<Error> errors = Arrays.asList(new Error(msgUsuario, msgDesenvolvedor));
        return ResponseEntity
                .badRequest()
                .body(errors);
    }

    @ExceptionHandler({PessoaInexistenteException.class})
    public ResponseEntity<Object> handlePessoaInexistenteException(PessoaInexistenteException ex) {
        String msgUsuario = messageSource.getMessage("mensagem.pessoa-inexistente", null, LocaleContextHolder.getLocale());
        String msgDesenvolvedor = ex.toString();
        List<Error> errors = Arrays.asList(new Error(msgUsuario, msgDesenvolvedor));
        return ResponseEntity
                .badRequest()
                .body(errors);
    }
}
