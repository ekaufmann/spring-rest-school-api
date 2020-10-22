package com.example.demo.util;

import com.example.demo.dto.AlunoDTO;
import com.example.demo.model.Aluno;
import com.example.demo.model.Mentor;

public class DTOConverter {
    
/*    public static Aluno DTOtoAluno(AlunoDTO dto) {
        
    }*/

    public static AlunoDTO convertAlunoToDTO(Aluno aluno) {
        Mentor mentor = aluno.getMentor();
        return new AlunoDTO(aluno.getId(), aluno.getNome(), aluno.getClasse(), mentor.getId());
    }
}
