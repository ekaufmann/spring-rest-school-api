package com.example.demo.mapper;

import com.example.demo.dto.*;
import com.example.demo.model.Avaliacao;
import com.example.demo.model.Disciplina;
import com.example.demo.mapper.AlunoMapper;

import static com.example.demo.mapper.DisciplinaMapper.convertDisciplinaToDTOResponse;
import static com.example.demo.mapper.MentorMapper.convertMentorToDTO;

public class AvaliacaoMapper {

    public static AvaliacaoDTOResponse convertAvaliacaoToDTOResponse(Avaliacao avaliacao) {
        if(avaliacao != null) {
            DisciplinaDTOResponse disciplina = convertDisciplinaToDTOResponse(avaliacao.getDisciplina());
            AlunoDTO aluno = AlunoMapper.convertAlunoToDTO(avaliacao.getAluno());
            MentorDTO mentor = convertMentorToDTO(avaliacao.getMentor());

            return new AvaliacaoDTOResponse(
                    avaliacao.getId(),
                    avaliacao.getConteudo(),
                    avaliacao.getDataRealizacao(),
                    avaliacao.getDataCorrecao(),
                    avaliacao.getNota(),
                    avaliacao.getActive(),
                    disciplina,
                    aluno,
                    mentor
            );
        }
        return null;
    }

    public static Avaliacao convertDTOToAvaliacao(AvaliacaoDTOCreate avaliacaoDTOCreate, Disciplina disciplina) {

        if(avaliacaoDTOCreate != null) {
            return new Avaliacao(avaliacaoDTOCreate.getConteudo(), avaliacaoDTOCreate.getDataRealizacao(), disciplina);
        }
        return null;
    }
}
