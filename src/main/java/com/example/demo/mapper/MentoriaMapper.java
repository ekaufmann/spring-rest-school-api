package com.example.demo.mapper;

import com.example.demo.dto.MentoriaDTO;
import com.example.demo.model.Mentoria;

import static com.example.demo.mapper.AlunoMapper.convertAlunoToDTO;
import static com.example.demo.mapper.MentorMapper.convertMentorToDTO;

public class MentoriaMapper {

    public static MentoriaDTO convertMentoriaToDTO(Mentoria mentoria) {
        if(mentoria != null) {
            return new MentoriaDTO().responseMentoriaDTO(mentoria.getId(),
                    convertMentorToDTO(mentoria.getMentor()),
                    convertAlunoToDTO(mentoria.getAluno())
            );
        }
        return null;
    }
}
