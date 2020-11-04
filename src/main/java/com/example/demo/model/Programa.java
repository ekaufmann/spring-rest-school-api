package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class Programa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 64)
    private String nome;

    @Column(name = "dataInicio")
    private LocalDate dataInicio;

    @Column(name = "dataFim")
    private LocalDate dataFim;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "programa_disciplina",
               joinColumns = {@JoinColumn(name = "programaId", nullable = false)},
                    inverseJoinColumns = {@JoinColumn(name = "disciplinaId", nullable = false)})
    private Set<Disciplina> disciplinas = new HashSet<>();

    public Programa(String nome, LocalDate dataInicio, LocalDate dataFim) {
        this.nome = nome;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public Set<Disciplina> getCopyOfDisciplinas() {
        return Set.copyOf(disciplinas);
    }

    public void addDisciplina(Disciplina disciplina) {
        disciplinas.add(disciplina);
    }

    public void removeDisciplina(Disciplina disciplina) {
        disciplinas.remove(disciplina);
    }

    public Boolean containsDisciplina(Disciplina disciplina) {
        return disciplinas.contains(disciplina);
    }
}
