package com.example.demo.controller;

import com.example.demo.dto.MentorDTO;
import com.example.demo.service.MentorService;
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
@RequestMapping("/mentor")
public class MentorController {

    @Autowired
    MentorService mentorService;

    @GetMapping
    public ResponseEntity<List<MentorDTO>> getMentores(@RequestParam Boolean active) {
        Optional<List<MentorDTO>> mentores = mentorService.getMentores(active);
        return mentores.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("{id}")
    public ResponseEntity<MentorDTO> getMentorById(@PathVariable Long id) {
        return mentorService.getMentor(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/reativar")
    public ResponseEntity<MentorDTO> reativarMentor(@RequestParam Long id) {
        return mentorService.reativarMentor(id)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.status(HttpStatus.FORBIDDEN).build()
                );
    }

    @PostMapping
    public ResponseEntity<MentorDTO> criaMentor(@RequestBody @Validated @NotNull MentorDTO mentorDTO) {
        return mentorService.criaMentor(mentorDTO).map(
                m -> ResponseEntity.created(URI.create("/mentor/" + m.getId())).body(m)
        ).orElseGet(
                () -> {
                    MentorDTO dtoResponse = new MentorDTO(0L, "Mentor já existe!", false);
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(dtoResponse);
                }
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MentorDTO> deleteMentor(@PathVariable Long id) {
        return mentorService.deleteMentor(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<MentorDTO> modificaMentor(@PathVariable Long id, @RequestBody @Validated @NotNull MentorDTO mentorModificado) {
        return mentorService.modificaMentor(id, mentorModificado)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
