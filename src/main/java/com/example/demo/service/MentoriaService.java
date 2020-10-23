package com.example.demo.service;

import com.example.demo.dto.MentoriaDTO;
import com.example.demo.model.Aluno;
import com.example.demo.model.Mentor;
import com.example.demo.model.Mentoria;
import com.example.demo.repository.MentoriaRepository;
import com.example.demo.util.DTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.util.DTOConverter.convertMentoriaToDTO;

@Service
public class MentoriaService {

    @Autowired
    MentoriaRepository mentoriaRepository;

    @Autowired
    MentorService mentorService;

    @Autowired
    AlunoService alunoService;

    public List<MentoriaDTO> getMentorias() {
        List<Mentoria> mentorias = mentoriaRepository.findAll();
        return mentorias.stream()
                .map(DTOConverter::convertMentoriaToDTO)
                .collect(Collectors.toList());
    }

    public MentoriaDTO criaMentoria(MentoriaDTO dto) {
        Mentor mentor = mentorService.getMentorById(dto.getMentorId());
        Aluno aluno = alunoService.getAlunoById(dto.getAlunoId());

        return convertMentoriaToDTO(mentoriaRepository.save(new Mentoria(mentor, aluno)));
    }
}
