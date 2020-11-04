package com.example.demo.mapper;

import com.example.demo.dto.AlunoDTO;
import com.example.demo.model.Aluno;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AlunoMapper {

    Aluno convertDTOToAluno(AlunoDTO alunoDTO);

    AlunoDTO convertAlunoToDTO(Aluno aluno);

}