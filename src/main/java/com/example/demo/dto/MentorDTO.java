package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class MentorDTO {

    private Long id;
    private String nome;
    private List<AlunoDTO> alunos;

    public MentorDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public void setAlunos(List<AlunoDTO> alunos) {
        this.alunos = alunos;
    }
}
