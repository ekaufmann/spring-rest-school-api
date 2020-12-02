package com.example.demo.controller;

import com.example.demo.dto.AlunoDTO;
import com.example.demo.service.AlunoService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    @ApiOperation(value = "Returns a page of students based on the given parameters")
    public ResponseEntity<Page<AlunoDTO>> getAlunos(
            @ApiParam(value = "Returns active students if equals 1, inactive if equals 0 or all students if null")
            @RequestParam Boolean active,
            Pageable pageable) {
        Optional<Page<AlunoDTO>> alunos = alunoService.getAlunos(active, pageable);

        return alunos.map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }

    @GetMapping("{id}")
    @ApiOperation(value = "Returns a student based on the given id")
    public ResponseEntity<AlunoDTO> getAluno(@PathVariable Long id) {
        Optional<AlunoDTO> dto = alunoService.getAluno(id);

        return dto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "Logically delete the student with the given id")
    public ResponseEntity<AlunoDTO> deleteAluno(@PathVariable Long id) {
        return alunoService.deleteAluno(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/reativar")
    @ApiOperation(value = "Logically reactivates the student with the given id")
    public ResponseEntity<AlunoDTO> reativarAluno(@RequestParam Long id) {
        return alunoService.reativarAluno(id)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.status(HttpStatus.FORBIDDEN).build()
                );
    }

    @PostMapping
    @ApiOperation(value = "Creates a student based on the given student DTO")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "alunoDTO",
                    paramType = "body",
                    example = "{\n  'nome': 'string', \n  'classe': 'string', \n  'active': '0 or 1 or null'\n}")
    })
    public ResponseEntity<AlunoDTO> createAluno(@RequestBody @Validated @NotNull AlunoDTO alunoDTO) {
        return alunoService.criaAluno(alunoDTO).map(
                a -> ResponseEntity.created(URI.create("/aluno/" + a.getId())).body(a)
        ).orElseGet(
                () -> {
                    AlunoDTO dtoResponse = new AlunoDTO("Aluno já existe!", "-");
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(dtoResponse);
                });
    }

    @PutMapping("{id}")
    @ApiOperation(value = "Update the student based on the given modified student")
    public ResponseEntity<AlunoDTO> updateAluno(@PathVariable Long id, @RequestBody @Validated @NotNull AlunoDTO alunoModificado) {
        return alunoService.modificaAluno(id, alunoModificado)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/programa")
    @ApiOperation(value = "Defines the student's program based on the given ids")
    public ResponseEntity<AlunoDTO> setPrograma(@PathVariable Long id, @RequestBody Long programaId) {
        return alunoService.setPrograma(id, programaId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
