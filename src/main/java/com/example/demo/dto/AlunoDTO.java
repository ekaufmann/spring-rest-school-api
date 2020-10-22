package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlunoDTO {

    private Long id;
    private String nome;
    private String classe;
    private String mentorNome;
    private Long mentorId;

    public AlunoDTO(String nome, String classe) {
        this.nome = nome;
        this.classe = classe;
    }
}
