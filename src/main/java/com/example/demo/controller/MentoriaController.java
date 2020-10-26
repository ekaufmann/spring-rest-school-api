package com.example.demo.controller;

import com.example.demo.dto.MentoriaDTO;
import com.example.demo.dto.MentoriaDTOResponse;
import com.example.demo.model.Mentoria;
import com.example.demo.service.MentoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/mentoria")
public class MentoriaController {

    @Autowired
    MentoriaService mentoriaService;

    @GetMapping
    public ResponseEntity<List<MentoriaDTOResponse>> getMentorias() {
        return ResponseEntity.ok(mentoriaService.getMentorias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MentoriaDTOResponse> getMentoria(@PathVariable Long id) {
        return mentoriaService.getMentoria(id)
                              .map(ResponseEntity::ok)
                              .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MentoriaDTOResponse> criaMentoria(@RequestBody MentoriaDTO dto) {
        return mentoriaService.criaMentoria(dto)
                              .map(m -> ResponseEntity.created(URI.create("/mentoria/" + m.getId())).body(m))
                              .orElseGet(() ->
                                  new ResponseEntity<MentoriaDTOResponse>((MentoriaDTOResponse) null, HttpStatus.FORBIDDEN)
                              );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MentoriaDTOResponse> deleteMentoria(@PathVariable Long id) {
        return mentoriaService.deleteMentoria(id)
                              .map(ResponseEntity::ok)
                              .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MentoriaDTOResponse> modificaMentoria(@PathVariable Long id, @RequestBody MentoriaDTO mentoriaModificada) {
        return mentoriaService.modificaMentoria(id, mentoriaModificada)
                              .map(ResponseEntity::ok)
                              .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
