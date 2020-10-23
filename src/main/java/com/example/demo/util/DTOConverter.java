package com.example.demo.util;

import com.example.demo.dto.AlunoDTO;
import com.example.demo.dto.MentorDTO;
import com.example.demo.dto.MentoriaDTO;
import com.example.demo.model.Aluno;
import com.example.demo.model.Mentor;
import com.example.demo.model.Mentoria;

public class DTOConverter {

    public static AlunoDTO convertAlunoToDTO(Aluno aluno) {
        if(aluno != null) {
            return new AlunoDTO(aluno.getId(), aluno.getNome(), aluno.getClasse());
        }
        return null;
    }

    public static MentorDTO convertMentorToDTO(Mentor mentor) {
        if(mentor != null) {
            return new MentorDTO(mentor.getId(), mentor.getNome());
        }
        return null;
    }

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
