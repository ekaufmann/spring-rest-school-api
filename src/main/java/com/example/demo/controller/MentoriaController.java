package com.example.demo.controller;

import com.example.demo.dto.MentoriaDTO;
import com.example.demo.model.Mentoria;
import com.example.demo.service.MentoriaService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<MentoriaDTO>> getMentorias() {
        return ResponseEntity.ok(mentoriaService.getMentorias());
    }

    @PostMapping
    public ResponseEntity<MentoriaDTO> criaMentoria(@RequestBody MentoriaDTO dto) {
        MentoriaDTO dtoResponse = mentoriaService.criaMentoria(dto);
        Long id = dtoResponse.getId();

        return ResponseEntity.created(URI.create("/mentoria/" + id)).body(dtoResponse);
    }
}
