package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AlunoDTO {

    private Long id;
    private String nome;
    private String classe;
    private Byte ativo;

    public AlunoDTO(String nome, String classe) {
        this.nome = nome;
        this.classe = classe;
    }

    public AlunoDTO(Long id, String nome, String classe, Byte ativo) {
        this(nome, classe);
        this.id = id;
        this.ativo = ativo;
    }
}
