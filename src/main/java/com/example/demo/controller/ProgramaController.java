package com.example.demo.controller;

import com.example.demo.dto.ProgramaDTO;
import com.example.demo.service.ProgramaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/programas")
public class ProgramaController {

    @Autowired
    private ProgramaService programaService;

    @GetMapping
    public ResponseEntity<Page<ProgramaDTO>> getProgramas(@RequestParam Boolean active, Pageable pageable) {
        Optional<Page<ProgramaDTO>> mentores = programaService.getProgramas(active, pageable);
        return mentores.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("{id}")
    public ResponseEntity<ProgramaDTO> getProgramaById(@PathVariable Long id) {
        return programaService.getPrograma(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProgramaDTO> criaPrograma(@RequestBody @Validated @NotNull ProgramaDTO programaDTO) {
        return programaService.criaPrograma(programaDTO)
                .map(
                        p -> ResponseEntity.created(URI.create("/mentoria/" + p.getId())).body(p))
                .orElseGet(
                        () -> new ResponseEntity<>((ProgramaDTO) null, HttpStatus.FORBIDDEN)
                );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ProgramaDTO> deletePrograma(@PathVariable Long id) {
        return programaService.deletePrograma(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<ProgramaDTO> modificaPrograma(@PathVariable Long id, @RequestBody ProgramaDTO programaModificado) {
        return programaService.modificaPrograma(id, programaModificado)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/disciplinas")
    public ResponseEntity<ProgramaDTO> addOrDeleteDisciplina(@RequestBody Long programaId, @RequestBody Long disciplinaId, @RequestBody Boolean active) {
        // operacao == 1 ? adiciona ; operacao == 0 ? remove;
        return programaService.addOrDeleteDisciplina(programaId, disciplinaId, active)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
