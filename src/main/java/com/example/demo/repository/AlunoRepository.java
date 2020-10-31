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
    @Query(value = "UPDATE aluno a " +
            "INNER JOIN mentoria m ON m.aluno_id = a.id " +
            "SET a.active = 0, m.active = 0 " +
            "WHERE a.id = ?1", nativeQuery = true)
    Integer logicalDelete(Long id);

    @Modifying
    @Query(value = "UPDATE aluno a " +
            "INNER JOIN mentoria m ON m.aluno_id = a.id " +
            "INNER JOIN mentor ON mentor.id = m.mentor_id " +
            "SET a.active = 1, m.active = 1 " +
            "WHERE a.id = ?1 " +
            "   AND mentor.active = 1", nativeQuery = true)
    Integer reativarAluno(Long id);

    Optional<Aluno> findByNome(String nome);
}
