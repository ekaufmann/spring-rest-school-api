package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgramaDTOResponse extends ProgramaDTO{

    private Long id;
    private Set<DisciplinaDTOResponse> disciplinas;

    public ProgramaDTOResponse(Long id, String nome, LocalDate dataInicio, LocalDate dataFim) {
        super(nome, dataInicio, dataFim);
        this.id = id;
    }

    public ProgramaDTOResponse(Long id, String nome, LocalDate dataInicio, LocalDate dataFim, Set<DisciplinaDTOResponse> disciplinas) {
        this(id, nome, dataInicio, dataFim);
        this.disciplinas = disciplinas;
    }
}
