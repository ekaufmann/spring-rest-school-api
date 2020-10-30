package com.example.demo.repository;

import com.example.demo.model.Avaliacao;
import com.example.demo.model.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    Optional<Avaliacao> findByDisciplinaIdAndDataRealizacao(Long disciplinaId, LocalDate dataRealizacao);
}
