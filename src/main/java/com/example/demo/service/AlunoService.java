package com.example.demo.service;

import com.example.demo.dto.AlunoDTO;
import com.example.demo.model.Aluno;
import com.example.demo.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoService {

    @Autowired
    AlunoRepository alunoRepository;

    @Autowired
    MentorService mentorService;

    public List<Aluno> getAlunos() {
        return alunoRepository.findAll();
    }

    public Aluno getAlunoById(Long id) { return alunoRepository.findById(id).orElse(null); }

    public long criaAluno(AlunoDTO dto) {
        Aluno aluno = new Aluno(dto.getNome(), dto.getClasse(), mentorService.getMentorById(dto.getMentorId()));
        alunoRepository.save(aluno);
        return aluno.getId();
    }

    public void deleteAluno(Long id) {
        Aluno aluno = getAlunoById(id);
        if(aluno != null) {
            alunoRepository.delete(aluno);
        }
    }
}
