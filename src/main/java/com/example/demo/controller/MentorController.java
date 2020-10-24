package com.example.demo.controller;

import com.example.demo.dto.MentorDTO;
import com.example.demo.model.Mentor;
import com.example.demo.service.MentorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mentor")
public class MentorController {

    @Autowired
    MentorService mentorService;

    @GetMapping
    public ResponseEntity<List<MentorDTO>> getMentores() {
        Optional<List<MentorDTO>> mentores = mentorService.getMentores();
        return mentores.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("{id}")
    public ResponseEntity<MentorDTO> getMentorById(@PathVariable Long id) {
        return mentorService.getMentor(id)
                            .map(ResponseEntity::ok)
                            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MentorDTO> criaMentor(@RequestBody Mentor mentor) {
        MentorDTO dto = mentorService.criaMentor(mentor);
        Long id = dto.getId();

        return ResponseEntity.created(URI.create("/mentor/" + id)).body(dto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MentorDTO> deleteMentor(@PathVariable Long id) {
        return mentorService.deleteMentor(id)
                            .map(ResponseEntity::ok)
                            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<MentorDTO> modificaMentor(@PathVariable Long id, @RequestBody MentorDTO mentorModificado) {
        return mentorService.modificaMentor(id, mentorModificado)
                            .map(ResponseEntity::ok)
                            .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
