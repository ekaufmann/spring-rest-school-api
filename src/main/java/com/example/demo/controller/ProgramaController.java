package com.example.demo.controller;

import com.example.demo.dto.DisciplinaDTOResponse;
import com.example.demo.dto.MentoriaDTOResponse;
import com.example.demo.dto.ProgramaDTO;
import com.example.demo.dto.ProgramaDTOResponse;
import com.example.demo.model.Programa;
import com.example.demo.service.ProgramaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/programa")
public class ProgramaController {

    @Autowired
    private ProgramaService programaService;

    @GetMapping
    public ResponseEntity<List<ProgramaDTOResponse>> getProgramas() {
        Optional<List<ProgramaDTOResponse>> mentores = programaService.getProgramas();
        return mentores.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("{id}")
    public ResponseEntity<ProgramaDTOResponse> getProgramaById(@PathVariable Long id) {
        return programaService.getPrograma(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProgramaDTOResponse> criaPrograma(@RequestBody ProgramaDTO programaDTO) {
        return programaService.criaPrograma(programaDTO)
                .map(
                        p -> ResponseEntity.created(URI.create("/mentoria/" + p.getId())).body(p))
                .orElseGet(
                        () -> new ResponseEntity<>((ProgramaDTOResponse) null, HttpStatus.FORBIDDEN)
                );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ProgramaDTOResponse> deletePrograma(@PathVariable Long id) {
        return programaService.deletePrograma(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<ProgramaDTOResponse> modificaPrograma(@PathVariable Long id, @RequestBody ProgramaDTO programaModificado) {
        return programaService.modificaPrograma(id, programaModificado)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/disciplinas")
    public ResponseEntity<ProgramaDTOResponse> addOrDeleteDisciplina(@RequestParam Long programaId, @RequestParam Long disciplinaId, @RequestParam Boolean active) {
        // operacao == 1 ? adiciona ; operacao == 0 ? remove;
        return programaService.addOrDeleteDisciplina(programaId, disciplinaId, active)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
