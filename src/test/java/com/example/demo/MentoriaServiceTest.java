package com.example.demo;

import com.example.demo.dto.MentoriaDTOResponse;
import com.example.demo.mapper.MentoriaMapper;
import com.example.demo.model.Aluno;
import com.example.demo.model.Mentor;
import com.example.demo.model.Mentoria;
import com.example.demo.repository.MentoriaRepository;
import com.example.demo.service.AlunoService;
import com.example.demo.service.MentorService;
import com.example.demo.service.MentoriaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MentoriaServiceTest {

    @InjectMocks
    MentoriaService mentoriaService;

    @Mock
    MentoriaRepository mentoriaRepository;

    @Mock
    MentorService mentorService;

    @Mock
    AlunoService alunoService;

    @Spy
    MentoriaMapper mentoriaMapper = Mappers.getMapper(MentoriaMapper.class);

    private final Long id;
    private Mentoria mentoria1, mentoria2;
    private Mentor mentor1, mentor2;
    private Aluno aluno1, aluno2;
    private final List<Mentoria> mentoriasTeste;

    {
        id = 1L;

        mentor1 = new Mentor(1L, "Mentor 1");
        mentor2 = new Mentor(2L, "Mentor 2");

        aluno1 = new Aluno(1L, "Aluno 1", "Java");
        aluno2 = new Aluno(2L, "Aluno 2", "Javascript");

        mentoria1 = new Mentoria(mentor1, aluno1);
        mentoria2 = new Mentoria(mentor2, aluno2);

        mentoriasTeste = new ArrayList<>(List.of(mentoria1, mentoria2));
    }

/*    private boolean compareDTOWithMentoria(MentoriaDTOResponse mentoriaDTOResponse, Mentoria mentoria) {
        return mentoriaDTOResponse.getMentor().equals(mentoria.getMentor()) &&
                mentoriaDTOResponse.getAluno().equals(mentoria.getAluno());
    }*/

    // FIND ALL
    @Test
    public void deveRetornarListaComTodasAsMentorias() {
        when(mentoriaRepository.findAll()).thenReturn(mentoriasTeste);
        Optional<List<MentoriaDTOResponse>> mentorias = mentoriaService.getMentorias(null);
        assertTrue(mentorias.isPresent());

        List<MentoriaDTOResponse> mentoriasDTO = mentorias.get();

        assertAll(
                () -> verify(mentoriaRepository, times(1)).findAll(),
                () -> assertEquals(mentoriasDTO.size(), mentoriasTeste.size())
/*                () -> {
                    for(byte i = 0; i < mentoriasDTO.size(); i++) {
                        assertTrue(compareDTOWithMentoria(mentoriasDTO.get(i), mentoriasTeste.get(i)));
                    }
                }*/
        );
    }

}
