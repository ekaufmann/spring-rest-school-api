package com.example.demo.service;

import com.example.demo.dto.MentoriaDTO;
import com.example.demo.dto.MentoriaDTOResponse;
import com.example.demo.mapper.MentoriaMapper;
import com.example.demo.model.Aluno;
import com.example.demo.model.Mentor;
import com.example.demo.model.Mentoria;
import com.example.demo.repository.MentoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MentoriaService {

    @Autowired
    MentoriaRepository mentoriaRepository;

    @Autowired
    MentoriaMapper mentoriaMapper;

    @Autowired
    MentorService mentorService;

    @Autowired
    AlunoService alunoService;

    public Optional<Page<MentoriaDTOResponse>> getMentorias(Boolean active, Pageable pageable) {
        Page<Mentoria> mentorias;
        if (active != null) {
            mentorias = mentoriaRepository.findAllByActive(active, pageable);
        } else {
            mentorias = mentoriaRepository.findAll(pageable);
        }
        return Optional.of(mentorias
                .map(mentoriaMapper::convertMentoriaToDTOResponse));
    }

    public Optional<Mentoria> getMentoriaById(Long id) { return mentoriaRepository.findById(id); }

    public Optional<MentoriaDTOResponse> getMentoria(Long id) {
        return getMentoriaById(id).map(mentoriaMapper::convertMentoriaToDTOResponse);
    }

    public Optional<MentoriaDTOResponse> criaMentoria(MentoriaDTO dto) {
        Optional<Mentor> mentor = mentorService.getMentorById(dto.getMentorId());
        Optional<Aluno> aluno = alunoService.getAlunoById(dto.getAlunoId());
        Optional<Mentoria> mentoriaOpt = Optional.empty();

        if(mentor.isPresent() && aluno.isPresent()) {
            Mentoria mentoria = new Mentoria(mentor.get(), aluno.get());
            mentoriaOpt = Optional.of(mentoriaRepository.save(mentoria));
        }

        return mentoriaOpt.map(mentoriaMapper::convertMentoriaToDTOResponse);
    }

    @Transactional
    public Optional<MentoriaDTOResponse> deleteMentoria(Long id) {
        return mentoriaRepository.logicalDelete(id) != 0 ? getMentoriaById(id).map(mentoriaMapper::convertMentoriaToDTOResponse) : Optional.empty();
    }

    public Optional<MentoriaDTOResponse> modificaMentoria(Long id, MentoriaDTO mentoriaModificada) {
        Optional<Mentoria> mentoria = getMentoriaById(id);
        Long alunoId = mentoriaModificada.getAlunoId();
        Long mentorId = mentoriaModificada.getMentorId();
        mentoria.ifPresent(m -> {
            Optional<Aluno> aluno = Optional.empty();
            Optional<Mentor> mentor = Optional.empty();
            if(alunoId != null) {
                aluno = alunoService.getAlunoById(alunoId);
            }
            if(mentorId != null) {
                 mentor = mentorService.getMentorById(mentorId);
            }

            m.setAluno(aluno.orElseGet(m::getAluno));
            m.setMentor(mentor.orElseGet(m::getMentor));
        });
        mentoria.map(mentoriaRepository::save);

        return mentoria.map(mentoriaMapper::convertMentoriaToDTOResponse);
    }
}
