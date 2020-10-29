package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Byte nota;

    private LocalDate dataRealizacao;

    private LocalDate dataCorrecao;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "disciplinaId")
    private Disciplina disciplina;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "alunoId")
    private Aluno aluno;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mentorId")
    private Mentor mentor;
}
