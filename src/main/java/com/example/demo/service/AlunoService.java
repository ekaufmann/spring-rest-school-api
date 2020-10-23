package com.example.demo.service;

import com.example.demo.dto.AlunoDTO;
import com.example.demo.model.Aluno;
import com.example.demo.model.Mentoria;
import com.example.demo.repository.AlunoRepository;
import com.example.demo.util.DTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.util.DTOConverter.*;

@Service
public class AlunoService {

    @Autowired
    AlunoRepository alunoRepository;

    public List<AlunoDTO> getAlunos() {
        List<Aluno> alunos = alunoRepository.findAll();
        return alunos.stream()
                .map(DTOConverter::convertAlunoToDTO)
                .collect(Collectors.toList());
    }

    protected Aluno getAlunoById(Long id) {
        return alunoRepository.findById(id).orElse(null);
    }

    public AlunoDTO getAluno(Long id) {
        return convertAlunoToDTO(getAlunoById(id));
    }

    public AlunoDTO criaAluno(AlunoDTO dto) {
        Aluno aluno = new Aluno(dto.getNome(), dto.getClasse());

        return convertAlunoToDTO(alunoRepository.save(aluno));
    }

    public AlunoDTO deleteAluno(Long id) {
        Aluno aluno = getAlunoById(id);
        if(aluno != null) {
            alunoRepository.delete(aluno);
        }
        return convertAlunoToDTO(aluno);
    }

    public AlunoDTO modificaAluno(Long id, AlunoDTO modificado) {
        Aluno aluno = getAlunoById(id);

        aluno.setNome(modificado.getNome() == null ? aluno.getNome() : modificado.getNome());
        aluno.setClasse(modificado.getClasse() == null ? aluno.getClasse() : modificado.getClasse());

        return convertAlunoToDTO(alunoRepository.save(aluno));
    }
}
