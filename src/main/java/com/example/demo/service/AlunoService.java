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

import static com.example.demo.mapper.AlunoMapper.convertAlunoToDTO;

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

    protected Optional<Aluno> getAlunoById(Long id) {
        return alunoRepository.findById(id);
    }

    public Optional<AlunoDTO> getAluno(Long id) {
        return getAlunoById(id).map(AlunoMapper::convertAlunoToDTO);
    }

    public AlunoDTO criaAluno(AlunoDTO dto) {
        Aluno aluno = new Aluno(dto.getNome(), dto.getClasse());

        return convertAlunoToDTO(alunoRepository.save(aluno));
    }

    public Optional<AlunoDTO> deleteAluno(Long id) {
        Optional<Aluno> aluno = getAlunoById(id);
        aluno.ifPresent(alunoRepository::delete);

        return aluno.map(AlunoMapper::convertAlunoToDTO);
    }

    public Optional<AlunoDTO> modificaAluno(Long id, AlunoDTO alunoModificado) {
        Optional<Aluno> aluno = getAlunoById(id);

        aluno.ifPresent(a -> {
            a.setNome(alunoModificado.getNome() == null ? a.getNome() : alunoModificado.getNome());
            a.setClasse(alunoModificado.getClasse() == null ? a.getClasse() : alunoModificado.getClasse());
            alunoRepository.save(a);
        });

        return aluno.map(AlunoMapper::convertAlunoToDTO);
    }
}
