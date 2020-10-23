package com.example.demo.service;

import com.example.demo.dto.AlunoDTO;
import com.example.demo.mapper.AlunoMapper;
import com.example.demo.model.Aluno;
import com.example.demo.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.demo.mapper.AlunoMapper.*;

@Service
public class AlunoService {

    @Autowired
    AlunoRepository alunoRepository;

    public List<AlunoDTO> getAlunos() {
        List<Aluno> alunos = alunoRepository.findAll();
        return alunos.parallelStream()
                .map(AlunoMapper::convertAlunoToDTO)
                .collect(Collectors.toList());
    }

    protected Aluno getAlunoById(Long id) {
        return alunoRepository.findById(id).orElse(null);
    }

    public Optional<AlunoDTO> getAluno(Long id) {
        return Optional.ofNullable(
                (convertAlunoToDTO(getAlunoById(id)))
        );
    }

    public AlunoDTO criaAluno(AlunoDTO dto) {
        Aluno aluno = new Aluno(dto.getNome(), dto.getClasse());

        return convertAlunoToDTO(alunoRepository.save(aluno));
    }

    public Optional<AlunoDTO> deleteAluno(Long id) {
        Optional<Aluno> aluno = Optional.ofNullable(getAlunoById(id));
        aluno.ifPresent(alunoRepository::delete);

        return aluno.map(AlunoMapper::convertAlunoToDTO);
    }

    public Optional<AlunoDTO> modificaAluno(Long id, AlunoDTO modificado) {
        Aluno aluno = getAlunoById(id);

        aluno.setNome(modificado.getNome() == null ? aluno.getNome() : modificado.getNome());
        aluno.setClasse(modificado.getClasse() == null ? aluno.getClasse() : modificado.getClasse());

        return Optional.ofNullable(convertAlunoToDTO(alunoRepository.save(aluno)));
    }
}
