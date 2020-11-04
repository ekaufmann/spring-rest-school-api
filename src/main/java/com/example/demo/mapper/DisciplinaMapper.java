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
