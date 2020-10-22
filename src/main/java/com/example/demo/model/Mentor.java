package com.example.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Mentor extends Pessoa {

    @OneToMany(mappedBy = "mentor")
    private List<Aluno> alunos;

    public Mentor(String nome, Aluno aluno) {
        this.nome = nome;
        this.alunos.add(aluno);
    }
}
