package com.example.demo.repository;

import com.example.demo.model.Mentoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MentoriaRepository extends JpaRepository<Mentoria, Long> {

    List<Mentoria> findAllByActive(Boolean active);

    @Modifying
    @Query("UPDATE Mentoria SET active = 0 WHERE id = ?1")
    Integer logicalDelete(Long id);
}
