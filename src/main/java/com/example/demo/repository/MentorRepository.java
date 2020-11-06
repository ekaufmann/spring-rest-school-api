package com.example.demo.repository;

import com.example.demo.model.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MentorRepository extends JpaRepository<Mentor, Long> {

    List<Mentor> findAllByActive(Boolean active);

    @Modifying
    @Query(value = "UPDATE mentor " +
            "INNER JOIN mentoria ON mentoria.mentor_id = mentor.id " +
            "SET mentor.active = 0, mentoria.active = 0 " +
            "WHERE mentor.id = ?1", nativeQuery = true)
    Integer logicalDelete(Long id);

    @Modifying
    @Query(value = "UPDATE mentor " +
            "INNER JOIN mentoria ON mentoria.mentor_id = mentor.id " +
            "INNER JOIN aluno ON aluno.id = mentoria.aluno_id " +
            "SET mentor.active = 1, mentoria.active = 1 " +
            "WHERE mentor.id = ?1 " +
            "   AND aluno.active = 1", nativeQuery = true)
    Integer reativarMentor(Long id);
}
