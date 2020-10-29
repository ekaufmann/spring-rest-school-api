package com.example.demo.repository;

import com.example.demo.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    List<Aluno> findAllByActive(Boolean active);

    Aluno findByIdAndActive(Long id, Boolean active);

    @Modifying
    @Query("UPDATE Aluno SET active = 0 WHERE id = ?1")
    Integer logicalDelete(Long id);

    Optional<Aluno> findByNome(String nome);
}
