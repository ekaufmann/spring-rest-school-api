package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MentoriaDTOResponse {

    private Long id;
    private MentorDTO mentor;
    private AlunoDTO aluno;

    public MentoriaDTOResponse(Long id, MentorDTO mentor, AlunoDTO aluno) {
        this.id = id;
        this.mentor = mentor;
        this.aluno = aluno;
    }
}
