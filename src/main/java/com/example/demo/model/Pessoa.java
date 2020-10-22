package com.example.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@MappedSuperclass
public abstract class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false, length = 128)
    protected String nome;

    @Column(length = 64)
    protected String classe;

    public Pessoa(String nome, String classe) {
        this.nome = nome;
        this.classe = classe;
    }
}
