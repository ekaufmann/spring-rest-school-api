package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AlunoDTO {

    private Long id;
    private String nome;
    private String classe;

    private Long mentorId;

    public AlunoDTO(String nome, String classe, Long mentorId) {
        this.nome = nome;
        this.classe = classe;
        this.mentorId = mentorId;
    }

    public AlunoDTO(Long id, String nome, String classe, Long mentorId) {
        this(nome, classe, mentorId);
        this.id = id;
    }
}
