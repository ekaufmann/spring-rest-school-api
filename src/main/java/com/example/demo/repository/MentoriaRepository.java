package com.example.demo.repository;

import com.example.demo.model.Mentoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MentoriaRepository extends JpaRepository<Mentoria, Long> {

    Page<Mentoria> findAllByActive(Boolean active, Pageable pageable);

    @Modifying
    @Query("UPDATE Mentoria SET active = 0 WHERE id = ?1")
    Integer logicalDelete(Long id);
}
