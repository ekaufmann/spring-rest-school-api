package com.example.demo.model;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Mentor extends Pessoa {

    @OneToMany(mappedBy = "mentor")
    private List<Aluno> alunos = new ArrayList<>();

    public Mentor(String nome, Aluno aluno) {
        this.nome = nome;
        this.alunos.add(aluno);
    }
}
