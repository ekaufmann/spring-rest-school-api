package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 64)
    private String nome;

    private Byte media;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "disciplinas")
    private Set<Programa> programas;

    public Disciplina(String nome) {
        this.nome = nome;
    }

/*    public void calculaMedia() {

    }*/
}
