package com.example.demo.repository;

import com.example.demo.model.Aluno;
import com.example.demo.model.Disciplina;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {

    Page<Disciplina> findAllByActive(Boolean active, Pageable pageable);

    @Modifying
    @Query("UPDATE Disciplina SET active = 0 WHERE id = ?1")
    Integer logicalDelete(Long id);
}
