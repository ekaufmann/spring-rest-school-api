package com.example.demo.mapper;

import com.example.demo.dto.MentoriaDTOResponse;
import com.example.demo.model.Mentoria;

import static com.example.demo.mapper.AlunoMapper.convertAlunoToDTO;
import static com.example.demo.mapper.MentorMapper.convertMentorToDTO;

public class MentoriaMapper {

    public static MentoriaDTOResponse convertMentoriaToDTOResponse(Mentoria mentoria) {
        if(mentoria != null) {
            return new MentoriaDTOResponse(mentoria.getId(),
                    convertMentorToDTO(mentoria.getMentor()),
                    convertAlunoToDTO(mentoria.getAluno())
            );
        }
        return null;
    }
}
