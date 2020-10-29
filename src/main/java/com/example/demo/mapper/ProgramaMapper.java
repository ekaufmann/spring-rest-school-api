package com.example.demo.mapper;

import com.example.demo.dto.ProgramaDTOResponse;
import com.example.demo.model.Disciplina;
import com.example.demo.model.Programa;

import java.util.List;
import java.util.stream.Collector;

public class ProgramaMapper {

    public static ProgramaDTOResponse convertProgramaToDTOResponse(Programa programa) {
        if(programa != null) {
/*            List<DisciplinaDTO> disciplinasDTO = programa.getDisciplinas()
                                                         .parallelStream()
                                                         .map(DisicplinaMapper::DisciplinaToDTO)
                                                         .collect(Collector.toList());*/
            return new ProgramaDTOResponse(programa.getId(), programa.getNome(), programa.getDataInicio(), programa.getDataFim());
            //return new ProgramaDTOResponse(1L, "Funcionou");
        }
        return null;
    }

}
