package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Mentor extends Pessoa {

    private Boolean active = true;

    @OneToMany(mappedBy = "mentor")
    private Set<Mentoria> mentorias = new HashSet<>();

    @OneToMany(mappedBy = "mentor")
    private Set<Avaliacao> avaliacoes = new HashSet<>();

    public Mentor(Long id, String nome) {
        super(id, nome);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Mentor mentor = (Mentor) o;
        return Objects.equals(id, mentor.id) &&
                Objects.equals(nome, mentor.nome) &&
                Objects.equals(active, mentor.active) &&
                Objects.equals(mentorias, mentor.mentorias) &&
                Objects.equals(avaliacoes, mentor.avaliacoes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), active, mentorias, avaliacoes);
    }
}
