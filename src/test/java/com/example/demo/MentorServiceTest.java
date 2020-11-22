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
import java.util.stream.Collectors;

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
        Optional<List<MentorDTO>> mentoresOpt = mentorService.getMentores(null);
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

    @Test
    public void deveRetornarListaDeMentoresDTOPeloStatusActiveFalse() {
        Boolean active = false;
        List<MentorDTO> mentoresDTO;
        List<Mentor> mentoresFalse = mentoresTeste.stream()
                .filter(a -> a.getActive() == active)
                .collect(Collectors.toList());
        when(mentorRepository.findAllByActive(active)).thenReturn(mentoresFalse);
        Optional<List<MentorDTO>> mentoresOpt = mentorService.getMentores(active);
        assertTrue(mentoresOpt.isPresent());
        mentoresDTO = mentoresOpt.get();

        assertAll(
                () -> verify(mentorRepository, times(1)).findAllByActive(active),
                () -> assertEquals(mentoresFalse.size(), mentoresDTO.size()),
                () -> mentoresDTO.forEach(aluno -> assertFalse(aluno.getActive())),
                () -> {
                    for(byte i = 0; i < mentoresDTO.size(); i++) {
                        assertTrue(compareDTOWithMentor(mentoresDTO.get(i), mentoresFalse.get(i)));
                    }
                }
        );
    }

    @Test
    public void deveRetornarListaDeMentoresDTOPeloStatusActiveTrue() {
        Boolean active = true;
        List<MentorDTO> mentoresDTO;
        List<Mentor> mentoresFalse = mentoresTeste.stream()
                .filter(a -> a.getActive() == active)
                .collect(Collectors.toList());
        when(mentorRepository.findAllByActive(active)).thenReturn(mentoresFalse);
        Optional<List<MentorDTO>> mentoresOpt = mentorService.getMentores(active);
        assertTrue(mentoresOpt.isPresent());
        mentoresDTO = mentoresOpt.get();

        assertAll(
                () -> verify(mentorRepository, times(1)).findAllByActive(active),
                () -> assertEquals(mentoresFalse.size(), mentoresDTO.size()),
                () -> mentoresDTO.forEach(aluno -> assertTrue(aluno.getActive())),
                () -> {
                    for(byte i = 0; i < mentoresDTO.size(); i++) {
                        assertTrue(compareDTOWithMentor(mentoresDTO.get(i), mentoresFalse.get(i)));
                    }
                }
        );
    }

    // FIND BY ID
    @Test
    public void deveRetornarMentorDTOPelaIdInformada() {
        when(mentorRepository.findById(id)).thenReturn(Optional.of(mentor));
        Optional<MentorDTO> mentorByIndex = mentorService.getMentor(id);
        assertTrue(mentorByIndex.isPresent());
        MentorDTO mentorDTO = mentorByIndex.get();

        assertAll(
                () -> verify(mentorRepository, times(1)).findById(id),
                () -> assertTrue(compareDTOWithMentor(mentorDTO, mentor))
        );
    }

    @Test
    public void deveRetornarOptionalEmptySeAIdForNula() {
        //when(mentorRepository.findById(any())).thenReturn(null);
        Optional<MentorDTO> mentorByIndex = mentorService.getMentor(null);
        assertTrue(mentorByIndex.isEmpty());
    }

    // DELETE
    @Test
    public void deveRetornarMentorDTODeletadoLogicamente() {
        when(mentorRepository.logicalDelete(id)).thenReturn(1);
        when(mentorRepository.findById(id)).thenReturn(Optional.of(mentor));
        mentor.setActive(false);
        Optional<MentorDTO> mentorDTO = mentorService.deleteMentor(id);
        assertTrue(mentorDTO.isPresent());

        assertAll(
                () -> verify(mentorRepository, times(1)).logicalDelete(id),
                () -> verify(mentorRepository, times(1)).findById(id),
                () -> mentorDTO.ifPresent(m -> assertEquals(false, m.getActive())),
                () -> assertEquals(mentorDTO.get().getActive(), mentor.getActive())
        );
    }

    @Test
    public void deveRetornarOptionalEmptyQuandoNaoDeletarLogicamente() {
        when(mentorRepository.logicalDelete(id)).thenReturn(0);
        Optional<MentorDTO> mentorDTO = mentorService.deleteMentor(id);

        assertAll(
                () -> verify(mentorRepository, times(1)).logicalDelete(id),
                () -> assertTrue(mentorDTO.isEmpty())
        );
    }

    @Test
    public void deveRetornarOptionalEmptyQuandoDeletarLogicamenteIdNulo() {
        //when(mentorRepository.logicalDelete(null)).thenReturn(null);
        Optional<MentorDTO> mentorDTO = mentorService.deleteMentor(null);
        assertTrue(mentorDTO.isEmpty());
    }

    // REACTIVATE
    @Test
    public void deveRetornarMentorDTOReativadoLogicamente() {
        when(mentorRepository.reativarMentor(id)).thenReturn(1);
        when(mentorRepository.findById(id)).thenReturn(Optional.of(mentor));
        mentor.setActive(true);
        Optional<MentorDTO> mentorDTO = mentorService.reativarMentor(id);
        assertTrue(mentorDTO.isPresent());

        assertAll(
                () -> verify(mentorRepository, times(1)).reativarMentor(id),
                () -> verify(mentorRepository, times(1)).findById(id),
                () -> mentorDTO.ifPresent(m -> assertEquals(true, m.getActive())),
                () -> assertEquals(mentorDTO.get().getActive(), mentor.getActive())
        );
    }

    @Test
    public void deveRetornarOptionalEmptyQuandoNaoReativarLogicamente() {
        when(mentorRepository.reativarMentor(id)).thenReturn(0);
        Optional<MentorDTO> mentorDTO = mentorService.reativarMentor(id);

        assertAll(
                () -> verify(mentorRepository, times(1)).reativarMentor(id),
                () -> assertTrue(mentorDTO.isEmpty())
        );
    }

    @Test
    public void deveRetornarOptionalEmptyQuandoReativarLogicamenteIdNulo() {
        //when(mentorRepository.reativarMentor(null)).thenReturn(null);
        Optional<MentorDTO> mentorDTO = mentorService.reativarMentor(null);
        assertTrue(mentorDTO.isEmpty());
    }

    // CREATE
    @Test
    public void deveRetornarMentorDTOCriado() {
        when(mentorRepository.findByNome(dtoTeste.getNome())).thenReturn(Optional.empty());
        Mentor mentor = mentorMapper.convertDTOToMentor(dtoTeste);
        when(mentorRepository.save(any(Mentor.class))).thenReturn(mentor);

        Optional<MentorDTO> mentorDTO = mentorService.criaMentor(dtoTeste);
        assertTrue(mentorDTO.isPresent());

        assertAll(
                () -> verify(mentorRepository, times(1)).findByNome(dtoTeste.getNome()),
                () -> verify(mentorRepository, times(1)).save(any(Mentor.class)),
                () -> assertTrue(compareDTOWithMentor(mentorDTO.get(), mentor))
        );
    }

    @Test
    public void deveRetornarOptionalEmptySeOMentorForEncontradoPeloNome() {
        Optional<Mentor> mentorVazio = Optional.of(mentorMapper.convertDTOToMentor(dtoTeste));
        when(mentorRepository.findByNome(dtoTeste.getNome())).thenReturn(mentorVazio);
        Optional<MentorDTO> dtoVazio = mentorService.criaMentor(dtoTeste);

        assertAll(
                () -> verify(mentorRepository, times(1)).findByNome(dtoTeste.getNome()),
                () -> assertTrue(dtoVazio.isEmpty())
        );
    }

    @Test
    public void deveRetornarOptionalEmptySeOMentorForNulo() {
        Optional<MentorDTO> dtoVazio = mentorService.criaMentor(null);
        assertTrue(dtoVazio.isEmpty());
    }

    // UPDATE
    @Test
    public void deveRetornarMentorDTOAoModificarMentor() {
        when(mentorRepository.findById(id)).thenReturn(Optional.of(mentor));
        when(mentorRepository.save(any(Mentor.class))).thenReturn(mentor);

        Optional<MentorDTO> mentorDTO = mentorService.modificaMentor(id, dtoTeste);
        assertTrue(mentorDTO.isPresent());

        assertAll(
                () -> verify(mentorRepository, times(1)).findById(id),
                () -> assertTrue(compareDTOWithMentor(mentorDTO.get(), mentor))
        );
    }

    @Test
    public void deveRetornarOptionalEmptySeOMentorModificadoForNulo() {
        when(mentorRepository.findById(id)).thenReturn(Optional.of(mentor));
        Optional<MentorDTO> mentorDTO = mentorService.modificaMentor(id, null);

        assertTrue(mentorDTO.isEmpty());
        verify(mentorRepository, times(1)).findById(id);
    }

    @Test
    public void deveRetornarOptionalEmptySeAIdInformadaForNula() {
        Optional<MentorDTO> mentorDTO = mentorService.modificaMentor(null, dtoTeste);
        assertTrue(mentorDTO.isEmpty());
        verify(mentorRepository, times(1)).findById(any());
    }
}
