package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 64)
    private String conteudo;

    private LocalDate dataRealizacao;

    private LocalDate dataCorrecao;

    private Float nota;

    private Boolean active = true;

    @ManyToOne(optional = false)
    @JoinColumn(name = "disciplinaId")
    private Disciplina disciplina;

    @ManyToOne
    @JoinColumn(name = "alunoId")
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "mentorId")
    private Mentor mentor;

    public Avaliacao(String conteudo, LocalDate dataRealizacao, Disciplina disciplina) {
        this.conteudo = conteudo;
        this.dataRealizacao = dataRealizacao;
        this.disciplina = disciplina;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Avaliacao avaliacao = (Avaliacao) o;
        return Objects.equals(id, avaliacao.id) &&
                conteudo.equals(avaliacao.conteudo) &&
                dataRealizacao.equals(avaliacao.dataRealizacao) &&
                disciplina.equals(avaliacao.disciplina);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, conteudo, dataRealizacao, disciplina);
    }
}
