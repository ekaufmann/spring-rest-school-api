package com.example.demo.repository;

import com.example.demo.model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    Optional<Avaliacao> findByDisciplinaIdAndDataRealizacao(Long disciplinaId, LocalDate dataRealizacao);

    @Modifying
    @Query("UPDATE Avaliacao SET active = 0 WHERE id = ?1")
    Integer logicalDelete(Long id);
}
