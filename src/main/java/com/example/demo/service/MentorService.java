package com.example.demo.service;

import com.example.demo.dto.MentorDTO;
import com.example.demo.model.Mentor;
import com.example.demo.repository.MentorRepository;
import com.example.demo.mapper.MentorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.mapper.MentorMapper.convertMentorToDTO;

// TODO Mudar retornos para Optional<>
@Service
public class MentorService {

    @Autowired
    private MentorRepository mentorRepository;

    public List<MentorDTO> getMentores() {
        return mentorRepository.findAll()
                .parallelStream()
                .map(MentorMapper::convertMentorToDTO)
                .collect(Collectors.toList());
    }

    protected Mentor getMentorById(Long id) {
        return mentorRepository.findById(id).orElse(null);
    }

    public MentorDTO getMentor(Long id) { return convertMentorToDTO(getMentorById(id)); }

    public MentorDTO criaMentor(Mentor mentor) {
        mentorRepository.save(mentor);
        return convertMentorToDTO(mentor);
    }

    public MentorDTO deleteMentor(Long id) {
        Mentor mentor = getMentorById(id);
        if(mentor != null) {
            mentorRepository.delete(mentor);
        }
        return convertMentorToDTO(mentor);
    }

    public MentorDTO modificaMentor(Long id, MentorDTO modificado) {
        Mentor mentor = getMentorById(id);

        mentor.setNome(modificado.getNome() == null ? mentor.getNome() : modificado.getNome());

        return convertMentorToDTO(mentorRepository.save(mentor));
    }
}
