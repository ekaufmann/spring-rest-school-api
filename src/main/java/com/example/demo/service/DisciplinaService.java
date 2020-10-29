package com.example.demo.service;

import com.example.demo.dto.DisciplinaDTO;
import com.example.demo.dto.DisciplinaDTOResponse;
import com.example.demo.mapper.DisciplinaMapper;
import com.example.demo.model.Disciplina;
import com.example.demo.repository.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.demo.mapper.DisciplinaMapper.*;

@Service
public class DisciplinaService {

    @Autowired
    DisciplinaRepository disciplinaRepository;

    public Optional<List<DisciplinaDTOResponse>> getDisciplinas() {
        return Optional.of(
                disciplinaRepository.findAll()
                                    .parallelStream()
                                    .map(DisciplinaMapper::convertDisciplinaToDTOResponse)
                                    .collect(Collectors.toList())
        );
    }

    public Optional<Disciplina> getDisciplinaById(Long id) {
        return disciplinaRepository.findById(id);
    }

    public Optional<DisciplinaDTOResponse> getDisciplina(Long id) {
        return getDisciplinaById(id).map(DisciplinaMapper::convertDisciplinaToDTOResponse);
    }


    public Optional<DisciplinaDTOResponse> criaDisciplina(DisciplinaDTO disciplinaDTO) {
        Optional<Disciplina> disciplina = Optional.of(convertDTOToDisciplina(disciplinaDTO));
        disciplina.ifPresent(disciplinaRepository::save);
        return disciplina.map(DisciplinaMapper::convertDisciplinaToDTOResponse);
    }

    public Optional<DisciplinaDTOResponse> deleteDisciplina(Long id) {
        Optional<Disciplina> disciplina = getDisciplinaById(id);
        disciplina.ifPresent(disciplinaRepository::delete);
        return disciplina.map(DisciplinaMapper::convertDisciplinaToDTOResponse);
    }


    public Optional<DisciplinaDTOResponse> modificaDisciplinaa(Long id, DisciplinaDTO disciplinaModificado) {
        Optional<Disciplina> disciplina = getDisciplinaById(id);
        disciplina.ifPresent(
                d -> {
                    String novoNome = disciplinaModificado.getNome();
                    d.setNome(novoNome == null ? d.getNome() : novoNome);

                    disciplinaRepository.save(d);
                }
        );
        return disciplina.map(DisciplinaMapper::convertDisciplinaToDTOResponse);
    }
}
