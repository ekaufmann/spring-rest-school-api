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
    @Query(value = "UPDATE aluno " +
            "INNER JOIN mentoria ON mentoria.aluno_id = aluno.id " +
            "SET aluno.active = 0, mentoria.active = 0 " +
            "WHERE aluno.id = ?1", nativeQuery = true)
    Integer logicalDelete(Long id);

    @Modifying
    @Query(value = "UPDATE aluno " +
            "INNER JOIN mentoria ON mentoria.aluno_id = aluno.id " +
            "INNER JOIN mentor ON mentor.id = mentoria.mentor_id " +
            "SET aluno.active = 1, mentoria.active = 1 " +
            "WHERE aluno.id = ?1 " +
            "   AND mentor.active = 1", nativeQuery = true)
    Integer reativarAluno(Long id);

    Optional<Aluno> findByNome(String nome);
}
