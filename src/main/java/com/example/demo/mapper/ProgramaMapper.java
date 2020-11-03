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

    /*public static ProgramaDTOResponse convertProgramaToDTOResponse(Programa programa) {
        if(programa != null) {
            Set<DisciplinaDTOResponse> disciplinasDTO = programa.getCopyOfDisciplinas()
                    .parallelStream()
                    .map(DisciplinaMapper::convertDisciplinaToDTOResponse)
                    .collect(Collectors.toSet());
            return new ProgramaDTOResponse(programa.getId(), programa.getNome(), programa.getDataInicio(), programa.getDataFim(), disciplinasDTO);
        }
        return null;
    }

    public static Programa convertDTOToPrograma(ProgramaDTO programaDTO) {
        return new Programa(programaDTO.getNome(), programaDTO.getDataInicio(), programaDTO.getDataFim());
    }*/
