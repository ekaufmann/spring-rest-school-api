package com.example.demo.controller;

import com.example.demo.dto.MentorDTO;
import com.example.demo.model.Mentor;
import com.example.demo.service.MentorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/mentor")
public class MentorController {

    @Autowired
    MentorService mentorService;

    @GetMapping
    public ResponseEntity<List<MentorDTO>> getMentores() {
        return new ResponseEntity<List<MentorDTO>>(mentorService.getMentores(), HttpStatus.ACCEPTED);
    }

    @GetMapping("{id}")
    public ResponseEntity<MentorDTO> getMentorById(@PathVariable Long id) {
        return ResponseEntity.ok(mentorService.getMentor(id));
    }

    @PostMapping
    public ResponseEntity<MentorDTO> criaMentor(@RequestBody Mentor mentor) {
        MentorDTO dto = mentorService.criaMentor(mentor);
        Long id = dto.getId();

        return ResponseEntity.created(URI.create("/mentor/" + id)).body(dto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MentorDTO> deleteMentor(@PathVariable Long id) {
        MentorDTO dto = mentorService.deleteMentor(id);
        if(dto == null) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(dto);
    }

    @PutMapping("{id}")
    public ResponseEntity<MentorDTO> modificaMentor(@PathVariable Long id, @RequestBody MentorDTO modificado) {
        return ResponseEntity.ok(mentorService.modificaMentor(id, modificado));
    }
}
