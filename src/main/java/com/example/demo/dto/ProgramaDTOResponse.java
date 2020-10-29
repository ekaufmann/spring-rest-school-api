package com.example.demo.dto;

import com.example.demo.model.Disciplina;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class ProgramaDTOResponse extends ProgramaDTO{

/*    private List<DisciplinaDTO> disciplinasDTO;

    public ProgramaDTOResponse(String nome, LocalDate dataInicio, LocalDate dataFim, List<DisciplinaDTO> disciplinasDTO) {
        super(nome, dataInicio, dataFim);
        this.disciplinasDTO = disciplinasDTO;
    }*/

    private String teste;

    public ProgramaDTOResponse(String teste) {
        this.teste = teste;
    }
}
