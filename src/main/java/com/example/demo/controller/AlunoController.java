package com.example.demo.controller;

import com.example.demo.dto.AlunoDTO;
import com.example.demo.model.Aluno;
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
    public ResponseEntity<List<Aluno>> getAlunos() {
        return new ResponseEntity<List<Aluno>>(alunoService.getAlunos(), HttpStatus.ACCEPTED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Aluno> getAluno(@PathVariable Long id) {
        return ResponseEntity.ok(alunoService.getAlunoById(id));
    }

    @PostMapping
    public ResponseEntity<Boolean> criaAluno(@RequestBody AlunoDTO dto) {
        long id = alunoService.criaAluno(dto);
        return ResponseEntity.created(URI.create("/aluno/" + id)).build(); //retorna o caminho para acesso ao objeto criado
    }

    @DeleteMapping("{id}")
    public void deleteAluno(@PathVariable Long id) {
        alunoService.deleteAluno(id);
    }
}
