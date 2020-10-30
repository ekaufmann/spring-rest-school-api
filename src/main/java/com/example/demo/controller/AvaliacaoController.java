package com.example.demo.controller;

import com.example.demo.dto.AvaliacaoDTO;
import com.example.demo.dto.AvaliacaoDTOResponse;
import com.example.demo.model.Avaliacao;
import com.example.demo.service.AvaliacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/avaliacao")
public class AvaliacaoController {

    @Autowired
    AvaliacaoService avaliacaoService;

    @GetMapping
    public ResponseEntity<List<AvaliacaoDTOResponse>> getAvaliacaos() {
        Optional<List<AvaliacaoDTOResponse>> avaliacoes = avaliacaoService.getAvaliacoes();
        return avaliacoes.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("{id}")
    public ResponseEntity<AvaliacaoDTOResponse> getAvaliacaoById(@PathVariable Long id) {
        return avaliacaoService.getAvaliacao(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AvaliacaoDTOResponse> criaAvaliacao(@RequestBody @Validated @NotNull AvaliacaoDTO AvaliacaoDTO) {
        return avaliacaoService.criaAvaliacao(AvaliacaoDTO)
                .map(
                        a -> ResponseEntity.created(URI.create("/mentoria/" + a.getId())).body(a))
                .orElseGet(
                        () -> new ResponseEntity<>((AvaliacaoDTOResponse) null, HttpStatus.FORBIDDEN)
                );
    }

/*    @DeleteMapping("{id}")
    public ResponseEntity<AvaliacaoDTOResponse> deleteAvaliacao(@PathVariable Long id) {
        return avaliacaoService.deleteAvaliacao(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<AvaliacaoDTOResponse> modificaAvaliacao(@PathVariable Long id, @RequestBody AvaliacaoDTO AvaliacaoModificado) {
        return avaliacaoService.modificaAvaliacao(id, AvaliacaoModificado)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }*/
}
