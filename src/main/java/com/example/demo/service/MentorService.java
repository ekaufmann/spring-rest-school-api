package com.example.demo.service;

import com.example.demo.dto.MentorDTO;
import com.example.demo.model.Mentor;
import com.example.demo.repository.MentorRepository;
import com.example.demo.mapper.MentorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MentorService {

    @Autowired
    private MentorRepository mentorRepository;

    @Autowired
    private MentorMapper mentorMapper;

    public Optional<Page<MentorDTO>> getMentores(Boolean active, Pageable pageable) {
        Page<Mentor> mentores;

        if (active != null) {
            mentores = mentorRepository.findAllByActive(active, pageable);
        } else {
            mentores = mentorRepository.findAll(pageable);
        }
        return Optional.of(mentores
                .map(mentorMapper::convertMentorToDTO)
        );
    }

    protected Optional<Mentor> getMentorById(Long id) {
        return mentorRepository.findById(id);
    }

    public Optional<MentorDTO> getMentor(Long id) {
        return getMentorById(id).map(mentorMapper::convertMentorToDTO);
    }

    public Optional<MentorDTO> criaMentor(MentorDTO mentorDTO) {
        Optional<Mentor> mentor;
        if (mentorDTO != null) {
            mentor = mentorRepository.findByNome(mentorDTO.getNome());
            if (mentor.isPresent()) {
                return Optional.empty();
            }
        }
        mentor = Optional.ofNullable(mentorMapper.convertDTOToMentor(mentorDTO));
        mentor.ifPresent(mentorRepository::save);
        return mentor.map(mentorMapper::convertMentorToDTO);
    }

    @Transactional
    public Optional<MentorDTO> deleteMentor(Long id) {
        if (id != null) {
            return mentorRepository.logicalDelete(id) != 0 ? getMentor(id) : Optional.empty();
        }
        return Optional.empty();
    }

    @Transactional
    public Optional<MentorDTO> reativarMentor(Long id) {
        if (id != null) {
            return mentorRepository.reativarMentor(id) != 0 ? getMentor(id) : Optional.empty();
        }
        return Optional.empty();
    }

    public Optional<MentorDTO> modificaMentor(Long id, MentorDTO mentorModificado) {
        Optional<Mentor> mentor = getMentorById(id);

        if (mentorModificado == null) {
            return Optional.empty();
        }

            mentor.ifPresent(m -> {
                m.setNome(mentorModificado.getNome() == null ? m.getNome() : mentorModificado.getNome());
                mentorRepository.save(m);
            });

        return mentor.map(mentorMapper::convertMentorToDTO);
    }
}
