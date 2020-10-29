package com.example.demo.service;

import com.example.demo.dto.MentorDTO;
import com.example.demo.model.Mentor;
import com.example.demo.repository.MentorRepository;
import com.example.demo.mapper.MentorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.demo.mapper.MentorMapper.convertMentorToDTO;

@Service
public class MentorService {

    @Autowired
    private MentorRepository mentorRepository;

    public Optional<List<MentorDTO>> getMentores() {
        return Optional.of(
                mentorRepository.findAll()
                .parallelStream()
                .map(MentorMapper::convertMentorToDTO)
                .collect(Collectors.toList())
        );
    }

    protected Optional<Mentor> getMentorById(Long id) {
        return mentorRepository.findById(id);
    }

    public Optional<MentorDTO> getMentor(Long id) {
        return getMentorById(id).map(MentorMapper::convertMentorToDTO);
    }

    public MentorDTO criaMentor(Mentor mentor) {
        mentorRepository.save(mentor);
        return convertMentorToDTO(mentor);
    }

    public Optional<MentorDTO> deleteMentor(Long id) {
        Optional<Mentor> mentor = getMentorById(id);
        mentor.ifPresent(mentorRepository::delete);

        return mentor.map(MentorMapper::convertMentorToDTO);
    }

    public Optional<MentorDTO> modificaMentor(Long id, MentorDTO mentorModificado) {
        Optional<Mentor> mentor = getMentorById(id);

        mentor.ifPresent(m -> {
            m.setNome(mentorModificado.getNome() == null ? m.getNome() : mentorModificado.getNome());
            mentorRepository.save(m);
        });

        return mentor.map(MentorMapper::convertMentorToDTO);
    }
}
