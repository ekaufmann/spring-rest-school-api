package com.example.demo.dto;

import com.example.demo.model.Disciplina;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgramaDTOResponse extends ProgramaDTO{

    private Long id;
    private String teste;
    private LocalDate dataInicio;
    private LocalDate dataFim;

    /*private List<DisciplinaDTO> disciplinasDTO;

    public ProgramaDTOResponse(String nome, LocalDate dataInicio, LocalDate dataFim, List<DisciplinaDTO> disciplinasDTO) {
        super(nome, dataInicio, dataFim);
        this.disciplinasDTO = disciplinasDTO;
    }*/
}
