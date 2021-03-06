package com.example.demo.service;

import com.example.demo.dto.AlunoDTO;
import com.example.demo.mapper.AlunoMapper;
import com.example.demo.model.Aluno;
import com.example.demo.model.Programa;
import com.example.demo.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private AlunoMapper alunoMapper;

    @Autowired
    private ProgramaService programaService;

    public Optional<Page<AlunoDTO>> getAlunos(Boolean active, Pageable pageable) {
        Page<Aluno> alunos;

        if (active != null) {
            alunos = alunoRepository.findAllByActive(active, pageable);
        } else {
            alunos = alunoRepository.findAll(pageable);
        }

        return Optional.of(alunos.map(a -> alunoMapper.convertAlunoToDTO(a)));
    }

    public Optional<Aluno> getAlunoById(Long id) {
        return alunoRepository.findById(id);
    }

    public Optional<AlunoDTO> getAluno(Long id) {
        return getAlunoById(id).map(alunoMapper::convertAlunoToDTO);
    }

    // TODO Find a better way to validate
    public Optional<AlunoDTO> criaAluno(AlunoDTO alunoDTO) {
        Optional<Aluno> aluno;
        if (alunoDTO != null) {
            aluno = alunoRepository.findByNome(alunoDTO.getNome());

            if (aluno.isPresent()) {
                return Optional.empty();
            }
        }
        aluno = Optional.ofNullable(alunoMapper.convertDTOToAluno(alunoDTO));
        aluno.ifPresent(alunoRepository::save);
        return aluno.map(alunoMapper::convertAlunoToDTO);
    }

    @Transactional
    public Optional<AlunoDTO> deleteAluno(Long id) {
        if (id != null) {
            return alunoRepository.logicalDelete(id) != 0 ? getAluno(id) : Optional.empty();
        }
        return Optional.empty();
    }

    @Transactional
    public Optional<AlunoDTO> reativarAluno(Long id) {
        if (id != null) {
            return alunoRepository.reativarAluno(id) != 0 ? getAluno(id) : Optional.empty();
        }
        return Optional.empty();
    }

    public Optional<AlunoDTO> modificaAluno(Long id, AlunoDTO alunoModificado) {
        Optional<Aluno> aluno = getAlunoById(id);

        if (alunoModificado == null) {
            return Optional.empty();
        }

        aluno.ifPresent(a -> {
            a.setNome(alunoModificado.getNome() == null ? a.getNome() : alunoModificado.getNome());
            a.setClasse(alunoModificado.getClasse() == null ? a.getClasse() : alunoModificado.getClasse());

            alunoRepository.save(a);
        });

        return aluno.map(alunoMapper::convertAlunoToDTO);
    }

    public Optional<AlunoDTO> setPrograma(Long id, Long programaId) {
        Optional<Aluno> aluno = getAlunoById(id);
        Optional<Programa> programa = programaService.getProgramaById(programaId);

        aluno.ifPresent(a -> {
            Programa programaAnterior = a.getPrograma() == null ? null : a.getPrograma();
            a.setPrograma(programa.orElse(programaAnterior));
            alunoRepository.save(a);
        });
        return aluno.map(alunoMapper::convertAlunoToDTO);
    }
}
