package com.example.demo.service;

import com.example.demo.dto.DisciplinaDTOResponse;
import com.example.demo.dto.ProgramaDTO;
import com.example.demo.dto.ProgramaDTOResponse;
import com.example.demo.mapper.ProgramaMapper;
import com.example.demo.model.Disciplina;
import com.example.demo.model.Programa;
import com.example.demo.repository.ProgramaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.demo.mapper.ProgramaMapper.convertDTOToPrograma;

@Service
public class ProgramaService {

    @Autowired
    private ProgramaRepository programaRepository;

    @Autowired
    private DisciplinaService disciplinaService;

    public Optional<List<ProgramaDTOResponse>> getProgramas() {
        return Optional.of(
                programaRepository.findAll()
                                  .parallelStream()
                                  .map(ProgramaMapper::convertProgramaToDTOResponse)
                                  .collect(Collectors.toList()));
    }

    public Optional<Programa> getProgramaById(Long id) {
        return programaRepository.findById(id);
    }

    public Optional<ProgramaDTOResponse> getPrograma(Long id) {
        return getProgramaById(id).map(ProgramaMapper::convertProgramaToDTOResponse);
    }


    public Optional<ProgramaDTOResponse> criaPrograma(ProgramaDTO programaDTO) {
        Optional<Programa> programa = Optional.of(convertDTOToPrograma(programaDTO));
        programa.ifPresent(programaRepository::save);
        return programa.map(ProgramaMapper::convertProgramaToDTOResponse);
    }


    public Optional<ProgramaDTOResponse> deletePrograma(Long id) {
        Optional<Programa> programa = getProgramaById(id);
        programa.ifPresent(programaRepository::delete);
        return programa.map(ProgramaMapper::convertProgramaToDTOResponse);
    }

    public Optional<ProgramaDTOResponse> modificaPrograma(Long id, ProgramaDTO programaModificado) {
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
        return programa.map(ProgramaMapper::convertProgramaToDTOResponse);
    }

    public Optional<ProgramaDTOResponse> addOuRemoveDisciplina(Long programaId, Long disciplinaId, Long operacao) {
        Optional<Programa> programa = getProgramaById(programaId);
        Optional<Disciplina> disciplina = disciplinaService.getDisciplinaById(disciplinaId);
        programa.ifPresent(
                p -> {
                    disciplina.ifPresent(d -> {
                        if (operacao == 1) {
                            p.addDisciplina(d);
                        } else {
                            p.removeDisciplina(d);
                        }
                        programaRepository.save(p);
                    });
                }
        );
        return programa.map(ProgramaMapper::convertProgramaToDTOResponse);
    }
}
