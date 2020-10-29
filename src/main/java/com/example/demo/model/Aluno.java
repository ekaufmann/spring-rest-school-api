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

    @Column
    private Boolean active = true; // 0 == false; 1 == true;

    @ManyToOne
    @JoinColumn(name = "programaId")
    private Programa programa;

    public Aluno(String nome, String classe) {
        super(nome);
        this.classe = classe;
    }
}
