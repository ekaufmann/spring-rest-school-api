package com.example.demo.controller;

import com.example.demo.dto.AlunoDTO;
import com.example.demo.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<AlunoDTO>> getAlunos() {
        List<AlunoDTO> alunos = alunoService.getAlunos();
        return alunos != null ? ResponseEntity.ok(alunos) : ResponseEntity.notFound().build() ;
    }

    @GetMapping("{id}")
    public ResponseEntity<AlunoDTO> getAluno(@PathVariable Long id) {
        Optional<AlunoDTO> dto = alunoService.getAluno(id);

        return dto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AlunoDTO> criaAluno(@RequestBody AlunoDTO dto) {
        AlunoDTO dtoResponse = alunoService.criaAluno(dto);
        Long id = dtoResponse.getId();

        return ResponseEntity.created(URI.create("/aluno/" + id)).body(dtoResponse);
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
