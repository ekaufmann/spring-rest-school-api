package com.example.demo.model;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Aluno extends Pessoa {

    @ManyToOne
    @JoinColumn(name = "id_mentor")
    private Mentor mentor;

    public Aluno(String nome, String classe, Mentor mentor) {
        super(nome, classe);
        this.mentor = mentor;
    }
}
