package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlunoDTO {

    private Long id;

    @Size(min = 5, max = 96, message = "Must be between 5 and 96")
    @NotNull(message = "Nome cannot be null")
    private String nome;

    @Size(min = 2, max = 64)
    @NotNull(message = "Classe cannot be null")
    private String classe;

    @NotNull
    private Boolean active;

    private ProgramaDTO programa;

    public AlunoDTO(String nome, String classe) {
        this.nome = nome;
        this.classe = classe;
    }
}
