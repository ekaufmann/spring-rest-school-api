package com.example.demo.mapper;

import com.example.demo.dto.AlunoDTO;
import com.example.demo.model.Aluno;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AlunoMapper {

    Aluno convertDTOToAluno(AlunoDTO alunoDTO);

    @BeforeMapping
    static AlunoDTO convertAlunoToDTO(Aluno aluno) {
        if(aluno != null) {
            return new AlunoDTO(aluno.getId(), aluno.getNome(), aluno.getClasse(), aluno.getActive());
        }
        return null;
    }
}