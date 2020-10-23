package com.example.demo.repository;

import com.example.demo.model.Mentoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MentoriaRepository extends JpaRepository<Mentoria, Long> {
}
