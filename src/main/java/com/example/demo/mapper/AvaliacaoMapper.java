package com.example.demo.mapper;

import com.example.demo.dto.AlunoDTO;
import com.example.demo.dto.AvaliacaoDTOResponse;
import com.example.demo.dto.DisciplinaDTOResponse;
import com.example.demo.dto.MentorDTO;
import com.example.demo.model.Avaliacao;
import com.example.demo.model.Mentor;

import static com.example.demo.mapper.DisciplinaMapper.convertDisciplinaToDTOResponse;
import static com.example.demo.mapper.AlunoMapper.convertAlunoToDTO;
import static com.example.demo.mapper.MentorMapper.convertMentorToDTO;

public class AvaliacaoMapper {

    public static AvaliacaoDTOResponse convertAvaliacaoToDTOResponse(Avaliacao avaliacao) {
        if(avaliacao != null) {
            DisciplinaDTOResponse disciplina = convertDisciplinaToDTOResponse(avaliacao.getDisciplina());
            AlunoDTO aluno = convertAlunoToDTO(avaliacao.getAluno());
            MentorDTO mentor = convertMentorToDTO(avaliacao.getMentor());

            return new AvaliacaoDTOResponse(avaliacao.getId(),
                    avaliacao.getConteudo(),
                    avaliacao.getDataRealizacao(),
                    avaliacao.getDataCorrecao(),
                    avaliacao.getNota(),
                    disciplina,
                    aluno,
                    mentor);
        }
        return null;
    }
}
