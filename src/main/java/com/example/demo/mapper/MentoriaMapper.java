package com.example.demo.mapper;

import com.example.demo.dto.MentoriaDTO;
import com.example.demo.dto.MentoriaDTOResponse;
import com.example.demo.model.Mentoria;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MentoriaMapper {

    Mentoria convertDTOToMentoria(MentoriaDTO mentoriaDTO);

    Mentoria convertDTOResponseToMentoria(MentoriaDTOResponse mentoriaDTOResponse);

    MentoriaDTO convertMentoriaToDTO(Mentoria mentora);

    MentoriaDTOResponse convertMentoriaToDTOResponse(Mentoria mentoria);

}

/*    public static MentoriaDTOResponse convertMentoriaToDTOResponse(Mentoria mentoria) {
        if(mentoria != null) {
            return new MentoriaDTOResponse(mentoria.getId(),
                    convertMentorToDTO(mentoria.getMentor()),
                    convertAlunoToDTO(mentoria.getAluno())
            );
        }
        return null;
    }*/
