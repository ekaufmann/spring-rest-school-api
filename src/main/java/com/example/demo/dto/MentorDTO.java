package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MentorDTO {

    private Long id;
    private String nome;

    public MentorDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }
}
