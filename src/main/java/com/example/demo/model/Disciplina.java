package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
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

    private Boolean active = true;

    private Float media = 7.0f;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "disciplinas")
    private Set<Programa> programas;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disciplina")
    private Set<Avaliacao> avaliacoes = new HashSet<>();

    public Disciplina(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Disciplina that = (Disciplina) o;
        return id.equals(that.id) &&
                nome.equals(that.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }
}
