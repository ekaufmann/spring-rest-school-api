package com.example.demo.controller;

import com.example.demo.dto.AlunoDTO;
import com.example.demo.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @GetMapping
    public ResponseEntity<Page<AlunoDTO>> getAlunos(@RequestParam Boolean active, Pageable pageable) {
        Optional<Page<AlunoDTO>> alunos = alunoService.getAlunos(active, pageable);

        return alunos.map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }

    @GetMapping("{id}")
    public ResponseEntity<AlunoDTO> getAluno(@PathVariable Long id) {
        Optional<AlunoDTO> dto = alunoService.getAluno(id);

        return dto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/reativar")
    public ResponseEntity<AlunoDTO> reativarAluno(@RequestParam Long id) {
        return alunoService.reativarAluno(id)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.status(HttpStatus.FORBIDDEN).build()
                );
    }

    @PostMapping
    public ResponseEntity<AlunoDTO> criaAluno(@RequestBody @Validated @NotNull AlunoDTO dto) {
        return alunoService.criaAluno(dto).map(
                a -> ResponseEntity.created(URI.create("/aluno/" + a.getId())).body(a)
        ).orElseGet(
                () -> {
                    AlunoDTO dtoResponse = new AlunoDTO("Aluno já existe!", "-");
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(dtoResponse);
                });
    }

    @DeleteMapping("{id}")
    public ResponseEntity<AlunoDTO> deleteAluno(@PathVariable Long id) {
        return alunoService.deleteAluno(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<AlunoDTO> modificaAluno(@PathVariable Long id, @RequestBody @Validated @NotNull AlunoDTO alunoModificado) {
        return alunoService.modificaAluno(id, alunoModificado)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/programa")
    public ResponseEntity<AlunoDTO> setPrograma(@PathVariable Long id, @RequestBody Long programaId) {
        return alunoService.setPrograma(id, programaId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
