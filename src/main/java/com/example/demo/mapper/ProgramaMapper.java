package com.example.demo.mapper;

import com.example.demo.dto.ProgramaDTO;
import com.example.demo.dto.ProgramaDTOResponse;
import com.example.demo.model.Programa;

public class ProgramaMapper {

    public static ProgramaDTOResponse convertProgramaToDTOResponse(Programa programa) {
        if(programa != null) {
/*            List<DisciplinaDTO> disciplinasDTO = programa.getDisciplinas()
                                                         .parallelStream()
                                                         .map(DisicplinaMapper::DisciplinaToDTO)
                                                         .collect(Collector.toList());*/
            return new ProgramaDTOResponse(programa.getId(), programa.getNome(), programa.getDataInicio(), programa.getDataFim());
        }
        return null;
    }

    public static Programa convertDTOToPrograma(ProgramaDTO programaDTO) {
        return new Programa(programaDTO.getNome(), programaDTO.getDataInicio(), programaDTO.getDataFim());
    }

}
