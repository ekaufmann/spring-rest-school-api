package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Aluno extends Pessoa {

    @Column(length = 64, nullable = false)
    private String classe;

    @Column
    private Boolean active = true; // 0 == false; 1 == true;

    @OneToMany(mappedBy = "aluno")
    private Set<Mentoria> mentorias = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "programaId")
    private Programa programa;

    @OneToMany(mappedBy = "aluno")
    private Set<Avaliacao> avaliacoes = new HashSet<>();

    public Aluno(String nome, String classe) {
        super(nome);
        this.classe = classe;
    }

    // Tests
    public Aluno(Long id, String nome, String classe) {
        super(id, nome);
        this.classe = classe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Aluno aluno = (Aluno) o;
        return id.equals(aluno.id) &&
                nome.equals(aluno.nome) &&
                classe.equals(aluno.classe) &&
                active.equals(aluno.active) &&
                Objects.equals(mentorias, aluno.mentorias) &&
                programa.equals(aluno.programa) &&
                Objects.equals(avaliacoes, aluno.avaliacoes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), classe, active, mentorias, programa, avaliacoes);
    }
}
