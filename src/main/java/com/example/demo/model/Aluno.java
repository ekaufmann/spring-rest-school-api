package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class Aluno extends Pessoa {

    @Column(length = 64)
    private String classe;

    private Byte ativo = 1; // 0 == false; 1 == true;

    public Aluno(String nome, String classe) {
        super(nome);
        this.classe = classe;
    }
}
