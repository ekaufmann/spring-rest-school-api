package com.example.demo.mapper;

import com.example.demo.dto.*;
import com.example.demo.model.Avaliacao;
import com.example.demo.model.Disciplina;
import com.example.demo.model.Mentor;
import com.example.demo.service.DisciplinaService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.example.demo.mapper.DisciplinaMapper.convertDisciplinaToDTOResponse;
import static com.example.demo.mapper.AlunoMapper.convertAlunoToDTO;
import static com.example.demo.mapper.MentorMapper.convertMentorToDTO;

public class AvaliacaoMapper {

    public static AvaliacaoDTOResponse convertAvaliacaoToDTOResponse(Avaliacao avaliacao) {
        if(avaliacao != null) {
            DisciplinaDTOResponse disciplina = convertDisciplinaToDTOResponse(avaliacao.getDisciplina());
            AlunoDTO aluno = convertAlunoToDTO(avaliacao.getAluno());
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

    public static Avaliacao convertDTOToAvaliacao(AvaliacaoDTO avaliacaoDTO, Disciplina disciplina) {

        if(avaliacaoDTO != null) {
            return new Avaliacao(avaliacaoDTO.getConteudo(), avaliacaoDTO.getDataRealizacao(), disciplina);
        }
        return null;
    }
}
