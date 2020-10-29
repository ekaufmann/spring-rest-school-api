package com.example.demo.model;

import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@NoArgsConstructor
@Entity
public class Mentor extends Pessoa {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mentor")
    private Set<Mentoria> mentorias;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mentor")
    private Set<Avaliacao> avaliacoes;

    public Mentor(String nome) {
        this.nome = nome;
    }
}
