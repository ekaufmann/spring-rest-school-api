package com.example.demo.mapper;

import com.example.demo.dto.ProgramaDTO;
import com.example.demo.dto.ProgramaDTOResponse;
import com.example.demo.model.Programa;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProgramaMapper {

    ProgramaDTOResponse convertProgramaToDTOResponse(Programa programa);

    Programa convertDTOToPrograma(ProgramaDTO programaDTO);
}
