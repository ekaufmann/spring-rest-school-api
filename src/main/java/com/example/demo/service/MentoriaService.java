package com.example.demo.service;

import com.example.demo.dto.MentoriaDTO;
import com.example.demo.dto.MentoriaDTOResponse;
import com.example.demo.mapper.MentoriaMapper;
import com.example.demo.model.Aluno;
import com.example.demo.model.Mentor;
import com.example.demo.model.Mentoria;
import com.example.demo.repository.MentoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.demo.mapper.MentoriaMapper.convertMentoriaToDTOResponse;

// TODO Mudar retornos para Optional<>
@Service
public class MentoriaService {

    @Autowired
    MentoriaRepository mentoriaRepository;

    @Autowired
    MentorService mentorService;

    @Autowired
    AlunoService alunoService;

    public List<MentoriaDTOResponse> getMentorias() {
        List<Mentoria> mentorias = mentoriaRepository.findAll();
        return mentorias.parallelStream()
                .map(MentoriaMapper::convertMentoriaToDTOResponse)
                .collect(Collectors.toList());
    }

    public Mentoria getMentoriaById(Long id) { return mentoriaRepository.findById(id).orElse(null); }

    public Optional<MentoriaDTOResponse> getMentoria(Long id) {
        return Optional.ofNullable(
                convertMentoriaToDTOResponse(getMentoriaById(id))
        );
    }

    public Optional<MentoriaDTOResponse> criaMentoria(MentoriaDTO dto) {
        Optional<Mentor> mentor = mentorService.getMentorById(dto.getMentorId());
        Optional<Aluno> aluno = alunoService.getAlunoById(dto.getAlunoId());
        Optional<Mentoria> mentoriaOpt = Optional.empty();

        if(mentor.isPresent() && aluno.isPresent()) {
            Mentoria mentoria = new Mentoria(mentor.get(), aluno.get());
            mentoriaOpt = Optional.of(mentoriaRepository.save(mentoria));
        }

        return mentoriaOpt.map(MentoriaMapper::convertMentoriaToDTOResponse);
    }

    public Optional<MentoriaDTOResponse> deleteMentoria(Long id) {
        Optional<Mentoria> mentoria = Optional.ofNullable(getMentoriaById(id));
        mentoria.ifPresent(mentoriaRepository::delete);
        return mentoria.map(MentoriaMapper::convertMentoriaToDTOResponse);
    }

    public Optional<MentoriaDTOResponse> modificaMentoria(Long id, MentoriaDTO mentoriaModificada) {
        Optional<Mentoria> mentoria = Optional.ofNullable(getMentoriaById(id));
        mentoria.ifPresent(m -> {
            Optional<Aluno> aluno = alunoService.getAlunoById(mentoriaModificada.getAlunoId());
            Optional<Mentor> mentor = mentorService.getMentorById(mentoriaModificada.getMentorId());

            m.setAluno(aluno.orElseGet(m::getAluno));
            m.setMentor(mentor.orElseGet(m::getMentor));

            mentoriaRepository.save(m);
        });

        return mentoria.map(MentoriaMapper::convertMentoriaToDTOResponse);
    }
}
