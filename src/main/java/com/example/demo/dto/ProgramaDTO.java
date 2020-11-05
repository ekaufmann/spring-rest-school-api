package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgramaDTO {

    private Long id;

    @NotNull(message = "Nome cannot be null")
    @Size(min = 3, max = 64, message = "Nome must be between 3 and 64")
    private String nome;

    @NotNull
    private LocalDate dataInicio;

    @NotNull
    private LocalDate dataFim;

    private Set<DisciplinaDTOResponse> disciplinas;
}
