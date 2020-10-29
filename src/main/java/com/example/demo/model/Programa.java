package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "programa_disciplina",
               joinColumns = {@JoinColumn(name = "programaId", nullable = false)},
                    inverseJoinColumns = {@JoinColumn(name = "disciplinaId", nullable = false)})
    private List<Disciplina> disciplinas;

    public Programa(String nome, LocalDate dataInicio, LocalDate dataFim) {
        this.nome = nome;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }
}
