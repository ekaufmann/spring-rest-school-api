package com.example.demo.util;

import com.example.demo.dto.AlunoDTO;
import com.example.demo.dto.MentorDTO;
import com.example.demo.model.Aluno;
import com.example.demo.model.Mentor;

import java.util.stream.Collectors;

public class DTOConverter {

    public static AlunoDTO convertAlunoToDTO(Aluno aluno) {
        if(aluno != null) {
            Mentor mentor = aluno.getMentor();
            return new AlunoDTO(aluno.getId(), aluno.getNome(), aluno.getClasse(), mentor.getId());
        }
        return null;
    }

    public static MentorDTO convertMentorToDTO(Mentor mentor) {
        if(mentor != null) {
            MentorDTO dto = new MentorDTO(mentor.getId(), mentor.getNome());
            dto.setAlunos(mentor.getAlunos()
                    .stream()
                    .map(DTOConverter::convertAlunoToDTO)
                    .collect(Collectors.toList())
            );
            return dto;
        }
        return null;
    }
}
