package com.example.demo.controller;

import com.example.demo.dto.MentorDTO;
import com.example.demo.service.MentorService;
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
@RequestMapping("/mentores")
public class MentorController {

    @Autowired
    MentorService mentorService;

    @GetMapping
    @ApiOperation(value = "Returns a page of mentors based on the given parameters")
    public ResponseEntity<Page<MentorDTO>> getMentores(@RequestParam Boolean active, Pageable pageable) {
        Optional<Page<MentorDTO>> mentores = mentorService.getMentores(active, pageable);
        return mentores.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("{id}")
    @ApiOperation(value = "Returns a mentor based on the given id")
    public ResponseEntity<MentorDTO> getMentor(@PathVariable Long id) {
        return mentorService.getMentor(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "Logically delete the mentor with the given id")
    public ResponseEntity<MentorDTO> deleteMentor(@PathVariable Long id) {
        return mentorService.deleteMentor(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/reativar")
    @ApiOperation(value = "Logically reactivate the mentor with the given id")
    public ResponseEntity<MentorDTO> reativarMentor(@RequestParam Long id) {
        return mentorService.reativarMentor(id)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.status(HttpStatus.FORBIDDEN).build()
                );
    }

    @PostMapping
    @ApiOperation(value = "Creates the mentor based on the given mentor DTO")
    @ApiImplicitParams(
            @ApiImplicitParam(
                    name = "mentorDTO",
                    paramType = "body",
                    example = "{\n  'name': 'string',\n  'active': '0, 1 or null'\n}"
            )
    )
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

    @PutMapping("{id}")
    @ApiOperation(value = "Update the mentor based on the given modified mentor")
    public ResponseEntity<MentorDTO> modificaMentor(@PathVariable Long id, @RequestBody @Validated @NotNull MentorDTO mentorModificado) {
        return mentorService.modificaMentor(id, mentorModificado)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
