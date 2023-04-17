package com.algamoney.api.web;

import com.algamoney.api.event.RecursoCriadoEvent;
import com.algamoney.api.model.Categoria;
import com.algamoney.api.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public ResponseEntity<List<Categoria>> all() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return !categorias.isEmpty() ?
                ResponseEntity.ok(categorias) :
                ResponseEntity.noContent().build();
    }

    @GetMapping("/{identifier}")
    public ResponseEntity<Categoria> getCategoria(@PathVariable("identifier") Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        return categoria
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Categoria> create(@RequestBody @Valid Categoria categoria, HttpServletResponse response) {
        Categoria novaCategoria = categoriaRepository.save(categoria);
        // Evento de criar header Location
        publisher.publishEvent(new RecursoCriadoEvent(this, response, novaCategoria.getId()));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(novaCategoria);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        categoriaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
