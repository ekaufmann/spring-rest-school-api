package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class Mentoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, cascade = {CascadeType.ALL})
    @JoinColumn(name = "mentorId", nullable = false)
    private Mentor mentor;

    @ManyToOne(optional = false, cascade = {CascadeType.ALL})
    @JoinColumn(name = "alunoId", nullable = false)
    private Aluno aluno;

    private Boolean active;

    public Mentoria(Mentor mentor, Aluno aluno) {
        this.mentor = mentor;
        this.aluno = aluno;
    }
}
