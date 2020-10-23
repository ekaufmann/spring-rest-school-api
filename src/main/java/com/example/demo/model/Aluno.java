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

    @ManyToOne
    @JoinColumn(name = "id_mentor")
    private Mentor mentor;

    public Aluno(String nome, String classe, Mentor mentor) {
        super(nome);
        this.classe = classe;
        this.mentor = mentor;
    }
}
