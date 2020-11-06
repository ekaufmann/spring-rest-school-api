package com.example.demo;

import com.example.demo.dto.MentorDTO;
import com.example.demo.mapper.MentorMapper;
import com.example.demo.model.Mentor;
import com.example.demo.repository.MentorRepository;
import com.example.demo.service.MentorService;
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
public class MentorServiceTest {

    @InjectMocks
    MentorService mentorService;

    @Mock
    MentorRepository mentorRepository;

    @Spy
    MentorMapper mentorMapper = Mappers.getMapper(MentorMapper.class);

    private final Long id;
    private Mentor mentor, mentor1, mentor2, mentor3;
    private final MentorDTO dtoTeste;
    private final List<Mentor> mentoresTeste;

    {
        id = 1L;

        mentor = new Mentor(1L, "Mentor");
        mentor1 = new Mentor(2L, "Mentor 1");
        mentor2 = new Mentor(3L, "Mentor 2");
        mentor3 = new Mentor(4L, "Mentor 3");
        mentoresTeste = new ArrayList<>(List.of(mentor, mentor1, mentor2, mentor3));

        dtoTeste = new MentorDTO(1L, "Mentor DTO", true);
    }


    private Boolean compareDTOWithMentor(MentorDTO mentorDTO, Mentor mentor) {
        return mentorDTO.getId().equals(mentor.getId()) &&
                mentorDTO.getNome().equals(mentor.getNome()) &&
                mentorDTO.getActive() == mentor.getActive();
    }

    // FIND ALL
    @Test
    public void deveRetornarListaComTodosOsMentores() {
        when(mentorRepository.findAll()).thenReturn(mentoresTeste);
        Optional<List<MentorDTO>> mentoresOpt = mentorService.getMentores();
        assertTrue(mentoresOpt.isPresent());

        List<MentorDTO> mentoresDTO = mentoresOpt.get();

        assertAll(
                () -> verify(mentorRepository, times(1)).findAll(),
                () -> assertEquals(mentoresDTO.size(), mentoresTeste.size()),
                () -> {
                    for(byte i = 0; i < mentoresDTO.size(); i++) {
                        assertTrue(compareDTOWithMentor(mentoresDTO.get(i), mentoresTeste.get(i)));
                    }
                }
        );
    }
}
