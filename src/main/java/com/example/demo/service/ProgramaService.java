package com.example.demo.service;

import com.example.demo.dto.ProgramaDTO;
import com.example.demo.dto.ProgramaDTOResponse;
import com.example.demo.mapper.ProgramaMapper;
import com.example.demo.repository.ProgramaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProgramaService {

    @Autowired
    private ProgramaRepository programaRepository;

    public Optional<List<ProgramaDTOResponse>> getProgramas() {
        return Optional.of(
                programaRepository.findAll()
                                  .parallelStream()
                                  .map(ProgramaMapper::ProgramaToDTO)
                                  .collect(Collectors.toList()));
    }
}
