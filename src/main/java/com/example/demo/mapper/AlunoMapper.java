package com.example.demo.mapper;

import com.example.demo.dto.AlunoDTO;
import com.example.demo.model.Aluno;

public class AlunoMapper {

    public static AlunoDTO convertAlunoToDTO(Aluno aluno) {
        if(aluno != null) {
            return new AlunoDTO(aluno.getId(), aluno.getNome(), aluno.getClasse(), aluno.getActive());
        }
        return null;
    }
}
