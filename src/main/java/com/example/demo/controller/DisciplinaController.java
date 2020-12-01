package com.example.demo.controller;

import com.example.demo.dto.DisciplinaDTO;
import com.example.demo.dto.DisciplinaDTOResponse;
import com.example.demo.service.DisciplinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/disciplinas")
public class DisciplinaController {

    @Autowired
    private DisciplinaService disciplinaService;

    @GetMapping
    public ResponseEntity<Page<DisciplinaDTOResponse>> getDisciplinas(@RequestParam Boolean active, Pageable pageable) {
        Optional<Page<DisciplinaDTOResponse>> disciplinas = disciplinaService.getDisciplinas(active, pageable);
        return disciplinas.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("{id}")
    public ResponseEntity<DisciplinaDTOResponse> getDisciplinaById(@PathVariable Long id) {
        return disciplinaService.getDisciplina(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DisciplinaDTOResponse> criaDisciplina(@RequestBody DisciplinaDTO disciplinaDTO) {
        return disciplinaService.criaDisciplina(disciplinaDTO)
                .map(
                        d -> ResponseEntity.created(URI.create("/disciplina/" + d.getId())).body(d)
                ).orElseGet(
                        () -> new ResponseEntity<>((DisciplinaDTOResponse) null, HttpStatus.FORBIDDEN)
                );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<DisciplinaDTOResponse> deletePrograma(@PathVariable Long id) {
        return disciplinaService.deleteDisciplina(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<DisciplinaDTOResponse> modificaDisciplina(@PathVariable Long id, @RequestBody DisciplinaDTO disciplinaModificada) {
        return disciplinaService.modificaDisciplinaa(id, disciplinaModificada)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
