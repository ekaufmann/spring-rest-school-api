package com.example.demo;

import com.example.demo.dto.AlunoDTO;
import com.example.demo.mapper.AlunoMapper;
import com.example.demo.model.Aluno;
import com.example.demo.model.Pessoa;
import com.example.demo.repository.AlunoRepository;
import com.example.demo.service.AlunoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlunoTest {

    @Mock
    AlunoRepository alunoRepository;

    @InjectMocks
    AlunoService alunoService;

    @Spy
    AlunoMapper alunoMapper = Mappers.getMapper(AlunoMapper.class);

    private Aluno aluno, aluno1, aluno2, aluno3, aluno4;
    private final List<Aluno> alunosTeste;
    private final Long id;

    {
        id = 1L;
        aluno = new Aluno(1L, "aluno teste", "classe teste");
        aluno1 = new Aluno(2L, "aluno1 teste", "classe1 teste");
        aluno2 = new Aluno(3L, "aluno2 teste", "classe2 teste");
        aluno2.setActive(false);
        aluno3 = new Aluno(4L, "aluno3 teste", "classe3 teste");
        aluno4 = new Aluno(5L, "aluno4 teste", "classe4 teste");
        aluno4.setActive(false);
        alunosTeste = new ArrayList<>(List.of(aluno, aluno1, aluno2, aluno3, aluno4));
    }

    @Test
    public void deveRetornarListaComTodosOsAlunos() {
        when(alunoRepository.findAll()).thenReturn(alunosTeste);
        Optional<List<AlunoDTO>> alunosOpt = alunoService.getAlunos(null);
        List<AlunoDTO> alunosDTO;

        assertTrue(alunosOpt.isPresent());
        alunosDTO = alunosOpt.get();

        assertAll(
                () -> verify(alunoRepository, times(1)).findAll(),
                () -> assertEquals(alunosDTO.size(), alunosTeste.size()),
                () -> {
                    AlunoDTO dto;
                    Aluno aluno;
                    for(byte i = 0; i < alunosDTO.size(); i++) {
                        dto = alunosDTO.get(i);
                        aluno = alunosTeste.get(i);
                        assertEquals(dto.getId(), aluno.getId());
                        assertEquals(dto.getNome(), aluno.getNome());
                        assertEquals(dto.getClasse(), aluno.getClasse());
                        assertEquals(dto.getActive(), aluno.getActive());
                    }
                }
        );
    }

    @Test
    public void deveRetornarListaDeAlunosDTOPeloStatusActiveFalse() {
        Boolean active = false;
        List<AlunoDTO> alunosDTO;
        List<Aluno> alunosFalse = alunosTeste.stream()
                .filter(a -> a.getActive() == active)
                .collect(Collectors.toList());

        when(alunoRepository.findAllByActive(active)).thenReturn(alunosFalse);

        Optional<List<AlunoDTO>> alunosOpt = alunoService.getAlunos(active);

        assertTrue(alunosOpt.isPresent());
        alunosDTO = alunosOpt.get();

        assertAll(
                () -> verify(alunoRepository, times(1)).findAllByActive(active),
                () -> assertEquals(alunosFalse.size(), alunosDTO.size()),
                () -> alunosDTO.forEach(a -> assertFalse(a.getActive())),
                () -> {
                    AlunoDTO dto;
                    Aluno aluno;
                    for (byte i = 0; i < alunosDTO.size(); i++) {
                        dto = alunosDTO.get(i);
                        aluno = alunosFalse.get(i);
                        assertEquals(dto.getId(), aluno.getId());
                        assertEquals(dto.getNome(), aluno.getNome());
                        assertEquals(dto.getClasse(), aluno.getClasse());
                        assertEquals(dto.getActive(), aluno.getActive());
                    }
                }
        );
    }

    @Test
    public void deveRetornarListaDeAlunosDTOPeloStatusActiveTrue() {
        Boolean active = true;
        List<AlunoDTO> alunosDTO;
        List<Aluno> alunosTrue = alunosTeste.stream()
                .filter(a->a.getActive() == active)
                .collect(Collectors.toList());

        when(alunoRepository.findAllByActive(active)).thenReturn(alunosTrue);

        Optional<List<AlunoDTO>> alunosOpt = alunoService.getAlunos(active);

        assertTrue(alunosOpt.isPresent());
        alunosDTO = alunosOpt.get();

        assertAll(
                () -> verify(alunoRepository, times(1)).findAllByActive(active),
                () -> assertEquals(alunosTrue.size(), alunosDTO.size()),
                () -> alunosDTO.forEach(a -> assertTrue(a.getActive())),
                () -> {
                    AlunoDTO dto;
                    Aluno aluno;
                    for (byte i = 0; i < alunosDTO.size(); i++) {
                        dto = alunosDTO.get(i);
                        aluno = alunosTrue.get(i);
                        assertEquals(dto.getId(), aluno.getId());
                        assertEquals(dto.getNome(), aluno.getNome());
                        assertEquals(dto.getClasse(), aluno.getClasse());
                        assertEquals(dto.getActive(), aluno.getActive());
                    }
                }
        );
    }

    @Test
    public void deveRetornarAlunoDTOPelaIdInformada() {
        when(alunoRepository.findById(id)).thenReturn(Optional.of(aluno));

        Optional<AlunoDTO> alunoByIndex = this.alunoService.getAluno(id);

        assertTrue(alunoByIndex.isPresent());

        AlunoDTO alunoDTO = alunoByIndex.get();

        assertAll(
                () -> verify(alunoRepository, times(1)).findById(1L),
                () -> assertEquals(alunoDTO.getId(), aluno.getId()),
                () -> assertEquals(alunoDTO.getNome(), aluno.getNome()),
                () -> assertEquals(alunoDTO.getClasse(), aluno.getClasse()),
                () -> assertEquals(alunoDTO.getActive(), aluno.getActive())
        );
    }

    @Test
    public void deveRetornarAlunoDeletadoLogicamente() {
        when(alunoRepository.logicalDelete(id)).thenReturn(1);
        when(alunoRepository.findById(id)).thenReturn(Optional.of(aluno));
        aluno.setActive(false);
        Optional<AlunoDTO> alunoDTO = alunoService.deleteAluno(id);

        assertAll(
                () -> verify(alunoRepository, times(1)).logicalDelete(id),
                () -> verify(alunoRepository, times(1)).findById(id),
                () -> alunoDTO.ifPresent(a -> assertEquals(false, a.getActive())),
                () -> assertEquals(alunoDTO.get().getActive(), aluno.getActive())
        );
    }

    @Test
    public void deveRetornarOptionalEmptyQuandoNaoDeletarLogicamente() {
        when(alunoRepository.logicalDelete(id)).thenReturn(0);

        Optional<AlunoDTO> alunoDTO = alunoService.deleteAluno(id);

        assertAll(
                () -> verify(alunoRepository, times(1)).logicalDelete(id),
                () -> assertTrue(alunoDTO.isEmpty())
        );
    }

    @Test
    public void deveRetornarAlunoReativadoLogicamente() {
        when(alunoRepository.reativarAluno(id)).thenReturn(1);
        when(alunoRepository.findById(id)).thenReturn(Optional.of(aluno));
        aluno.setActive(true);
        Optional<AlunoDTO> alunoDTO = alunoService.reativarAluno(id);

        assertAll(
                () -> verify(alunoRepository, times(1)).reativarAluno(id),
                () -> verify(alunoRepository, times(1)).findById(id),
                () -> alunoDTO.ifPresent(a -> assertEquals(true, a.getActive())),
                () -> assertEquals(alunoDTO.get().getActive(), aluno.getActive())
        );
    }

    @Test
    public void deveRetornarOptionalEmptyQuandoNaoReativarLogicamente() {
        when(alunoRepository.reativarAluno(id)).thenReturn(0);

        Optional<AlunoDTO> alunoDTO = alunoService.reativarAluno(id);

        assertAll(
                () -> verify(alunoRepository, times(1)).reativarAluno(id),
                () -> assertTrue(alunoDTO.isEmpty())
        );
    }
}
