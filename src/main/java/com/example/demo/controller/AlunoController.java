package com.example.demo.controller;

import com.example.demo.dto.AlunoDTO;
import com.example.demo.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/aluno")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @GetMapping
    public ResponseEntity<List<AlunoDTO>> getAlunos() {
        return new ResponseEntity<List<AlunoDTO>>(alunoService.getAlunos(), HttpStatus.ACCEPTED);
    }

    @GetMapping("{id}")
    public ResponseEntity<AlunoDTO> getAluno(@PathVariable Long id) {
        return ResponseEntity.ok(alunoService.getAluno(id));
    }

    @PostMapping
    public ResponseEntity<AlunoDTO> criaAluno(@RequestBody AlunoDTO dto) {
        AlunoDTO dtoResponse = alunoService.criaAluno(dto);
        Long id = dtoResponse.getId();

        return ResponseEntity.created(URI.create("/aluno/" + id)).body(dtoResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<AlunoDTO> deleteAluno(@PathVariable Long id) {
        AlunoDTO dto = alunoService.deleteAluno(id);
        if(dto == null) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(dto);
    }

    @PutMapping("{id}")
    public ResponseEntity<AlunoDTO> modificaAluno(@PathVariable Long id, @RequestBody AlunoDTO modificado) {
        return ResponseEntity.ok(alunoService.modificaAluno(id, modificado));
    }
}
