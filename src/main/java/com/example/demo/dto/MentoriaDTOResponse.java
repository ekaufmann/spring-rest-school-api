package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MentoriaDTOResponse {

    private Long id;
    private Boolean active;
    private MentorDTO mentor;
    private AlunoDTO aluno;
}
