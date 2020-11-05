package com.example.demo.mapper;

import com.example.demo.dto.ProgramaDTO;
import com.example.demo.model.Programa;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProgramaMapper {

    ProgramaDTO convertProgramaToDTO(Programa programa);

    Programa convertDTOToPrograma(ProgramaDTO programaDTO);
}
