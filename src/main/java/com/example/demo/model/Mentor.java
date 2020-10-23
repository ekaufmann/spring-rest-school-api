package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Data
@NoArgsConstructor
@Entity
public class Mentor extends Pessoa {

    public Mentor(String nome) {
        this.nome = nome;
    }
}
