package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 64)
    private String nome;

    private Byte nota;

    private LocalDate dataAvaliacao;

    @ManyToOne
    @JoinColumn(name = "mentorId")
    private Mentor mentor;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "disciplinas")
    private Set<Programa> programas;
}
