package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MentoriaDTO {

    protected Long id;
    private Long alunoId;
    private Long mentorId;

    public MentoriaDTO(Long id, Long alunoId, Long mentorId) {
        this.id = id;
        this.alunoId = alunoId;
        this.mentorId = mentorId;
    }

    public MentoriaDTO responseMentoriaDTO(Long id, MentorDTO mentor, AlunoDTO aluno) {
        return new MentoriaDTOResponse(id, mentor, aluno);
    }

    private class MentoriaDTOResponse extends MentoriaDTO {

        private MentorDTO mentor;
        private AlunoDTO aluno;

        public MentoriaDTOResponse(Long id, MentorDTO mentor, AlunoDTO aluno) {
            this.id = id;
            this.mentor = mentor;
            this.aluno = aluno;
        }
    };
}

