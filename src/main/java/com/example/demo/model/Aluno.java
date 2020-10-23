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

    public Aluno(String nome, String classe) {
        super(nome);
        this.classe = classe;
    }
}
