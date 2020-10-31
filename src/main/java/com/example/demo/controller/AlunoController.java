package com.example.demo.controller;

import com.example.demo.dto.AlunoDTO;
import com.example.demo.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/aluno")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @GetMapping
    public ResponseEntity<List<AlunoDTO>> getAlunos(@RequestParam Boolean active) {
        Optional<List<AlunoDTO>> alunos = alunoService.getAlunos(active);
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
        return alunoService.reativarAluno(id).map(ResponseEntity::ok).orElseGet(
                () -> ResponseEntity.status(HttpStatus.FORBIDDEN).build()
        );
    }

    @PostMapping
    public ResponseEntity<AlunoDTO> criaAluno(@RequestBody AlunoDTO dto) {
        return alunoService.criaAluno(dto).map(a -> ResponseEntity.created(URI.create("/aluno/" + a.getId())).body(a))
                .orElseGet( //new ResponseEntity<>((AlunoDTO) null, HttpStatus.FORBIDDEN)
                () -> {
                    dto.setId(0L);
                    dto.setNome("Aluno já existe!");
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(dto);
                });
    }

    @DeleteMapping("{id}")
    public ResponseEntity<AlunoDTO> deleteAluno(@PathVariable Long id) {
        return alunoService.deleteAluno(id)
                           .map(ResponseEntity::ok)
                           .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<AlunoDTO> modificaAluno(@PathVariable Long id, @RequestBody AlunoDTO alunoModificado) {
        return alunoService.modificaAluno(id, alunoModificado)
                           .map(ResponseEntity::ok)
                           .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
