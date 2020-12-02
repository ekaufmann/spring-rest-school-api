package com.example.demo.controller;

import com.example.demo.dto.ProgramaDTO;
import com.example.demo.service.ProgramaService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    @ApiOperation(value = "Returns a page of programs based on the given parameters")
    public ResponseEntity<Page<ProgramaDTO>> getProgramas(
            @ApiParam(value = "Returns active programs if equals 1, inactive if equals 0 or all programs if null")
            @RequestParam Boolean active, Pageable pageable) {
        Optional<Page<ProgramaDTO>> mentores = programaService.getProgramas(active, pageable);
        return mentores.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("{id}")
    @ApiOperation(value = "Returns a program based on the given id")
    public ResponseEntity<ProgramaDTO> getPrograma(@PathVariable Long id) {
        return programaService.getPrograma(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "Logically delete the program with the given id")
    public ResponseEntity<ProgramaDTO> deletePrograma(@PathVariable Long id) {
        return programaService.deletePrograma(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ApiOperation(value = "Create the program based on the given program DTO")
    @ApiImplicitParams(
            @ApiImplicitParam(
                    name = "programaDTO",
                    paramType = "body",
                    example = "{\n  'nome': 'string',\n  'dataInicio': 'localDate',\n  'dataFim': 'localDate'\n}"
            )
    )
    public ResponseEntity<ProgramaDTO> createPrograma(@RequestBody @Validated @NotNull ProgramaDTO programaDTO) {
        return programaService.criaPrograma(programaDTO)
                .map(
                        p -> ResponseEntity.created(URI.create("/mentoria/" + p.getId())).body(p))
                .orElseGet(
                        () -> new ResponseEntity<>((ProgramaDTO) null, HttpStatus.FORBIDDEN)
                );
    }

    @PutMapping("{id}")
    @ApiOperation(value = "Update the program based on the given modified program")
    public ResponseEntity<ProgramaDTO> updatePrograma(@PathVariable Long id, @RequestBody ProgramaDTO programaModificado) {
        return programaService.modificaPrograma(id, programaModificado)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/disciplinas")
    @ApiOperation(value = "Add or delete the discipline on/from the program with the given id")
    public ResponseEntity<ProgramaDTO> addOrDeleteDisciplina(
            @RequestBody Long programaId,
            @RequestBody Long disciplinaId,
            @RequestBody Boolean active) {
        // operacao == 1 ? adiciona ; operacao == 0 ? remove;
        return programaService.addOrDeleteDisciplina(programaId, disciplinaId, active)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
