package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MentoriaDTO {

    @NotNull(message = "Aluno Id cannot be null")
    @Positive(message = "Aluno Id must be a positive number")
    private Long alunoId;

    @NotNull(message = "Mentor Id cannot be null")
    @Positive(message = "Mentor Id must be a positive number")
    private Long mentorId;
}

