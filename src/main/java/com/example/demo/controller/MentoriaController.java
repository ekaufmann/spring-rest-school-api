package com.example.demo.controller;

import com.example.demo.dto.MentoriaDTO;
import com.example.demo.dto.MentoriaDTOResponse;
import com.example.demo.service.MentoriaService;
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
@RequestMapping("/mentorias")
public class MentoriaController {

    @Autowired
    MentoriaService mentoriaService;

    @GetMapping
    @ApiOperation(value = "Returns a page of mentoring based on the given parameters")
    public ResponseEntity<Page<MentoriaDTOResponse>> getMentorias(
            @ApiParam(value = "Returns active mentoring if equals 1, inactive if equals 0 or all mentoring if null")
            @RequestParam Boolean active,
            Pageable pageable) {
        Optional<Page<MentoriaDTOResponse>> mentorias = mentoriaService.getMentorias(active, pageable);
        return mentorias.map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Returns a mentoring based on the given id")
    public ResponseEntity<MentoriaDTOResponse> getMentoria(@PathVariable Long id) {
        return mentoriaService.getMentoria(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Logically delete the given mentoring with the given id")
    public ResponseEntity<MentoriaDTOResponse> deleteMentoria(@PathVariable Long id) {
        return mentoriaService.deleteMentoria(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ApiOperation(value = "Create a mentoring based on the given mentoring DTO")
    @ApiImplicitParams(
            @ApiImplicitParam(
                    name = "mentoriaDTO",
                    paramType = "body",
                    example = "{\n  'alunoId': 'long',\n  'mentorId': 'long',\n 'active': '0, 1 or null'\n}"
            )
    )
    public ResponseEntity<MentoriaDTOResponse> createMentoria(@RequestBody @Validated @NotNull MentoriaDTO mentoriaDTO) {
        return mentoriaService.criaMentoria(mentoriaDTO)
                .map(m -> ResponseEntity.created(URI.create("/mentoria/" + m.getId())).body(m))
                .orElseGet(() ->
                        new ResponseEntity<MentoriaDTOResponse>((MentoriaDTOResponse) null, HttpStatus.FORBIDDEN)
                );
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update the mentoring based on the given modified mentoring")
    public ResponseEntity<MentoriaDTOResponse> updateMentoria(@PathVariable Long id, @RequestBody @Validated @NotNull MentoriaDTO mentoriaModificada) {
        return mentoriaService.modificaMentoria(id, mentoriaModificada)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
