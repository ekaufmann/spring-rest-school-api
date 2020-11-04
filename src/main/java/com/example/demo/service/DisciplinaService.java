package com.example.demo.service;

import com.example.demo.dto.DisciplinaDTO;
import com.example.demo.dto.DisciplinaDTOResponse;
import com.example.demo.mapper.DisciplinaMapper;
import com.example.demo.model.Disciplina;
import com.example.demo.repository.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private DisciplinaMapper disciplinaMapper;

    public Optional<List<DisciplinaDTOResponse>> getDisciplinas(Boolean active) {

        if(active != null) {
            return Optional.of(
                    disciplinaRepository.findAllByActive(active)
                            .parallelStream()
                            .map(disciplinaMapper::convertDisciplinaToDTOResponse)
                            .collect(Collectors.toList())
            );
        }
        return Optional.of(disciplinaRepository.findAll()
                .parallelStream()
                .map(disciplinaMapper::convertDisciplinaToDTOResponse)
                .collect(Collectors.toList()));
    }

    public Optional<Disciplina> getDisciplinaById(Long id) {
        return disciplinaRepository.findById(id);
    }

    public Optional<DisciplinaDTOResponse> getDisciplina(Long id) {
        return getDisciplinaById(id).map(disciplinaMapper::convertDisciplinaToDTOResponse);
    }


    public Optional<DisciplinaDTOResponse> criaDisciplina(DisciplinaDTO disciplinaDTO) {
        Optional<Disciplina> disciplina = Optional.of(disciplinaMapper.convertDTOToDisciplina(disciplinaDTO));
        disciplina.ifPresent(disciplinaRepository::save);
        return disciplina.map(disciplinaMapper::convertDisciplinaToDTOResponse);
    }

    @Transactional
    public Optional<DisciplinaDTOResponse> deleteDisciplina(Long id) {
        if(disciplinaRepository.logicalDelete(id) == 1) {
            return getDisciplina(id);
        }
        return Optional.empty();
    }


    public Optional<DisciplinaDTOResponse> modificaDisciplinaa(Long id, DisciplinaDTO disciplinaModificado) {
        Optional<Disciplina> disciplina = getDisciplinaById(id);
        disciplina.ifPresent(
                d -> {
                    String novoNome = disciplinaModificado.getNome();
                    Boolean situacao = disciplinaModificado.getActive();

                    d.setNome(novoNome == null ? d.getNome() : novoNome);
                    d.setActive(situacao == null ? d.getActive() : situacao);

                    disciplinaRepository.save(d);
                }
        );
        return disciplina.map(disciplinaMapper::convertDisciplinaToDTOResponse);
    }
}
