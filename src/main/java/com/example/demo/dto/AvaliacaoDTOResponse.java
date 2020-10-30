package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoDTOResponse{

    private Long id;
    private String conteudo;
    private LocalDate dataRealizacao;
    private LocalDate dataCorrecao;
    private Float nota;
    private Boolean active;
    private DisciplinaDTOResponse disciplina;
    private AlunoDTO aluno;
    private MentorDTO mentor;
}
