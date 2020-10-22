package com.example.demo.service;

import com.example.demo.model.Mentor;
import com.example.demo.repository.MentorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MentorService {

    @Autowired
    private MentorRepository mentorRepository;

    public List<Mentor> getMentores() {
        return mentorRepository.findAll();
    }

    public long criaMentor(Mentor mentor) {
        mentorRepository.save(mentor);
        return mentor.getId();
    }

    public Mentor getMentorById(Long id) {
        return mentorRepository.findById(id).orElse(null);
    }

    public void deleteMentor(Long id) {
        Mentor mentor = getMentorById(id);
        mentorRepository.delete(mentor);
    }
}
