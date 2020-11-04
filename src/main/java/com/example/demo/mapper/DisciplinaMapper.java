package com.example.demo.mapper;

import com.example.demo.dto.DisciplinaDTO;
import com.example.demo.dto.DisciplinaDTOResponse;
import com.example.demo.model.Disciplina;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DisciplinaMapper {

    DisciplinaDTOResponse convertDisciplinaToDTOResponse(Disciplina disciplina);

    Disciplina convertDTOToDisciplina(DisciplinaDTO disciplinaDTO);
}

    /*public static DisciplinaDTOResponse convertDisciplinaToDTOResponse(Disciplina disciplina) {
        if(disciplina != null) {
            return new DisciplinaDTOResponse(disciplina.getId(), disciplina.getNome(), disciplina.getActive(), disciplina.getMedia());
        }
        return null;
    }

    public static Disciplina convertDTOToDisciplina(DisciplinaDTO disciplinaDTO) {
        if(disciplinaDTO != null) {
            return new Disciplina(disciplinaDTO.getNome());
        }
        return null;
    }*/
