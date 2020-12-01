package com.example.demo.service;

import com.example.demo.dto.ProgramaDTO;
import com.example.demo.mapper.ProgramaMapper;
import com.example.demo.model.Disciplina;
import com.example.demo.model.Programa;
import com.example.demo.repository.ProgramaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProgramaService {

    @Autowired
    private ProgramaRepository programaRepository;

    @Autowired
    private ProgramaMapper programaMapper;

    @Autowired
    private DisciplinaService disciplinaService;

    public Optional<Page<ProgramaDTO>> getProgramas(@RequestParam Boolean active, Pageable pageable) {
        Page<Programa> programas;

        if (active != null) {
            programas = programaRepository.findAllByActive(active, pageable);
        } else {
            programas = programaRepository.findAll(pageable);
        }

        return Optional.of(programas
                .map(programaMapper::convertProgramaToDTO));
    }

    public Optional<Programa> getProgramaById(Long id) {
        return programaRepository.findById(id);
    }

    public Optional<ProgramaDTO> getPrograma(Long id) {
        return getProgramaById(id).map(programaMapper::convertProgramaToDTO);
    }


    public Optional<ProgramaDTO> criaPrograma(ProgramaDTO programaDTO) {
        Optional<Programa> programa = Optional.of(programaMapper.convertDTOToPrograma(programaDTO));
        programa.ifPresent(programaRepository::save);
        return programa.map(programaMapper::convertProgramaToDTO);
    }


    public Optional<ProgramaDTO> deletePrograma(Long id) {
        Optional<Programa> programa = getProgramaById(id);
        programa.ifPresent(programaRepository::delete);
        return programa.map(programaMapper::convertProgramaToDTO);
    }

    public Optional<ProgramaDTO> modificaPrograma(Long id, ProgramaDTO programaModificado) {
        Optional<Programa> programa = getProgramaById(id);
        programa.ifPresent(
                p -> {
                    String novoNome = programaModificado.getNome();
                    LocalDate novaDataInicio = programaModificado.getDataInicio();
                    LocalDate novaDataFim = programaModificado.getDataFim();

                    p.setNome(novoNome == null ? p.getNome() : novoNome);
                    p.setDataInicio(novaDataInicio == null ? p.getDataInicio() : novaDataInicio);
                    p.setDataFim(novaDataFim == null ? p.getDataFim() : novaDataFim);

                    programaRepository.save(p);
                });
        return programa.map(programaMapper::convertProgramaToDTO);
    }

    public Optional<ProgramaDTO> addOrDeleteDisciplina(Long programaId, Long disciplinaId, Boolean active) {
        Optional<Programa> programa = getProgramaById(programaId);
        Optional<Disciplina> disciplina = disciplinaService.getDisciplinaById(disciplinaId);

        programa.ifPresent(
                p -> {
                    disciplina.ifPresent(d -> {
                        if (p.containsDisciplina(d)) {
                            d.setActive(active);
                        } else {
                            p.addDisciplina(d);
                        }
                        programaRepository.save(p);
                    });
                }
        );
        return programa.map(programaMapper::convertProgramaToDTO);
    }
}
