package com.example.demo.controller;

import com.example.demo.dto.AvaliacaoDTOCreate;
import com.example.demo.dto.AvaliacaoDTOResponse;
import com.example.demo.dto.AvaliacaoDTOUpdate;
import com.example.demo.service.AvaliacaoService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    @Autowired
    AvaliacaoService avaliacaoService;

    @GetMapping
    @ApiOperation(value = "Returns a page of exams based on the given parameters")
    public ResponseEntity<Page<AvaliacaoDTOResponse>> getAvaliacaos(@RequestParam Boolean active, Pageable pageable) {
        Optional<Page<AvaliacaoDTOResponse>> avaliacoes = avaliacaoService.getAvaliacoes(active, pageable);
        return avaliacoes.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("{id}")
    @ApiOperation(value = "Returns a exam based on the given id")
    public ResponseEntity<AvaliacaoDTOResponse> getAvaliacao(@PathVariable Long id) {
        return avaliacaoService.getAvaliacao(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ApiOperation(value = "Creates a exam based on the given exam DTO")
    @ApiImplicitParams(
            @ApiImplicitParam(
                    name = "AvaliacaoDTOCreate",
                    paramType = "body",
                    example = "{\n  'disciplinaId': 'long',\n  'conteudo': 'string',\n  'dataRealizacao': 'localDate'\n}"
            )
    )
    public ResponseEntity<AvaliacaoDTOResponse> createAvaliacao(@RequestBody @Validated @NotNull AvaliacaoDTOCreate AvaliacaoDTOCreate) {
        return avaliacaoService.criaAvaliacao(AvaliacaoDTOCreate)
                .map(
                        a -> ResponseEntity.created(URI.create("/mentoria/" + a.getId())).body(a))
                .orElseGet(
                        () -> new ResponseEntity<>((AvaliacaoDTOResponse) null, HttpStatus.FORBIDDEN)
                );
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "Logically delete the exam with the given id")
    public ResponseEntity<AvaliacaoDTOResponse> deleteAvaliacao(@PathVariable Long id) {
        System.out.println("Aceitou delete");
        return avaliacaoService.deleteAvaliacao(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    @ApiOperation(value = "Update the exam based on the given modified exam")
    public ResponseEntity<AvaliacaoDTOResponse> updateAvaliacao(@PathVariable Long id, @RequestBody AvaliacaoDTOUpdate AvaliacaoModificada) {
        return avaliacaoService.modificaAvaliacao(id, AvaliacaoModificada)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
