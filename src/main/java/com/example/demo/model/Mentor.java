package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Data
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
