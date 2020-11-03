package com.example.demo.service;

import com.example.demo.dto.AlunoDTO;
import com.example.demo.mapper.AlunoMapper;
import com.example.demo.model.Aluno;
import com.example.demo.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private AlunoMapper alunoMapper;

    public Optional<List<AlunoDTO>> getAlunos(Boolean active) {
        List<Aluno> alunos;

        if(active != null) {
            alunos = alunoRepository.findAllByActive(active);
        } else {
            alunos = alunoRepository.findAll();
        }

        return Optional.of(alunos
                .parallelStream()
                .map(AlunoMapper::convertAlunoToDTO)
                .collect(Collectors.toList()));
    }

    protected Optional<Aluno> getAlunoById(Long id) {
        return alunoRepository.findById(id);
    }

    public Optional<AlunoDTO> getAluno(Long id) {
        return getAlunoById(id).map(AlunoMapper::convertAlunoToDTO);
    }

    // TODO Find a better way to validate
    public Optional<AlunoDTO> criaAluno(AlunoDTO alunoDTO) {
        Optional<Aluno> alunoByNome = alunoRepository.findByNome(alunoDTO.getNome());

        if(alunoByNome.isPresent()) {
            return Optional.empty();
        }

        Optional<Aluno> aluno = Optional.of(alunoMapper.convertDTOToAluno(alunoDTO));
        aluno.ifPresent(alunoRepository::save);
        return aluno.map(AlunoMapper::convertAlunoToDTO);
    }

    @Transactional
    public Optional<AlunoDTO> deleteAluno(Long id) {
        return alunoRepository.logicalDelete(id) != 0 ? getAluno(id) : Optional.empty();
    }

    @Transactional
    public Optional<AlunoDTO> reativarAluno(Long id) {
        return alunoRepository.reativarAluno(id) != 0 ? getAluno(id) : Optional.empty();
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
