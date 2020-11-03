package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
//@NoArgsConstructor
@Entity
public class Aluno extends Pessoa {

    @Column(length = 64)
    private String classe;

    @Column
    private Boolean active = true; // 0 == false; 1 == true;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aluno")
    private Set<Mentoria> mentorias;

    @ManyToOne
    @JoinColumn(name = "programaId")
    private Programa programa;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aluno")
    private Set<Avaliacao> avaliacoes;

    public Aluno() {}

    public Aluno(String nome, String classe) {
        super(nome);
        this.classe = classe;
    }
}
