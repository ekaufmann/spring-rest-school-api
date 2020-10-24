package com.example.demo.model;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@NoArgsConstructor
@Entity
public class Mentor extends Pessoa {

    public Mentor(String nome) {
        this.nome = nome;
    }
}
