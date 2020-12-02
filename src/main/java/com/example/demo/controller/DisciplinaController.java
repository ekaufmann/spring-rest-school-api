package com.example.demo.controller;

import com.example.demo.dto.DisciplinaDTO;
import com.example.demo.dto.DisciplinaDTOResponse;
import com.example.demo.service.DisciplinaService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    @ApiOperation(value = "Returns a page of disciplines based on the given parameters")
    public ResponseEntity<Page<DisciplinaDTOResponse>> getDisciplinas(
            @ApiParam(value = "Returns active discipline if equals 1, inactive if equals 0 or all disciplines if null")
            @RequestParam Boolean active,
            Pageable pageable) {
        Optional<Page<DisciplinaDTOResponse>> disciplinas = disciplinaService.getDisciplinas(active, pageable);
        return disciplinas.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("{id}")
    @ApiOperation(value = "Returns a discipline based on the given id")
    public ResponseEntity<DisciplinaDTOResponse> getDisciplina(@PathVariable Long id) {
        return disciplinaService.getDisciplina(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "Logically delete the discipline with the given id")
    public ResponseEntity<DisciplinaDTOResponse> deleteDisciplina(@PathVariable Long id) {
        return disciplinaService.deleteDisciplina(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ApiOperation(value = "Creates a discipline based on the given discipline DTO")
    @ApiImplicitParams(
            @ApiImplicitParam(
                    name = "disciplinaDTO",
                    paramType = "body",
                    example = "{\n  'nome': 'string'\n}"
            )
    )
    public ResponseEntity<DisciplinaDTOResponse> createDisciplina(@RequestBody DisciplinaDTO disciplinaDTO) {
        return disciplinaService.criaDisciplina(disciplinaDTO)
                .map(
                        d -> ResponseEntity.created(URI.create("/disciplina/" + d.getId())).body(d)
                ).orElseGet(
                        () -> new ResponseEntity<>((DisciplinaDTOResponse) null, HttpStatus.FORBIDDEN)
                );
    }

    @PutMapping("{id}")
    @ApiOperation(value = "Update the discipline based on the given modified discipline")
    public ResponseEntity<DisciplinaDTOResponse> updateDisciplina(@PathVariable Long id, @RequestBody DisciplinaDTO disciplinaModificada) {
        return disciplinaService.modificaDisciplinaa(id, disciplinaModificada)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
