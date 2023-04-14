package com.algamoney.api.web;

import com.algamoney.api.model.Categoria;
import com.algamoney.api.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public ResponseEntity<List<Categoria>> all() {
        List<Categoria> categorias = categoriaRepository.findAll();

        if (categorias.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }

    @GetMapping("/{identifier}")
    public ResponseEntity<Categoria> getCategoria(@PathVariable("identifier") Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        return categoria
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Categoria> create(@RequestBody @Valid Categoria categoria) {
        Categoria novaCategoria = categoriaRepository.save(categoria);

        URI uri = createLocationResource(novaCategoria);
        return ResponseEntity
                // Método .created() já defini um header Location por padrão
                .created(uri)
                .body(novaCategoria);
    }

    private URI createLocationResource(Categoria novaCategoria) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novaCategoria.getId())
                .toUri();
    }
}
