package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MentoriaDTO {

    private Long alunoId;
    private Long mentorId;

    public MentoriaDTO(Long alunoId, Long mentorId) {
        this.alunoId = alunoId;
        this.mentorId = mentorId;
    }
}

