package com.example.demo;

import com.example.demo.dto.AlunoDTO;
import com.example.demo.dto.ProgramaDTO;
import com.example.demo.mapper.AlunoMapper;
import com.example.demo.model.Aluno;
import com.example.demo.model.Programa;
import com.example.demo.repository.AlunoRepository;
import com.example.demo.service.AlunoService;
import com.example.demo.service.ProgramaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlunoServiceTest {

    @Mock
    AlunoRepository alunoRepository;

    @Mock
    ProgramaService programaService;

    @InjectMocks
    AlunoService alunoService;

    @Spy
    AlunoMapper alunoMapper = Mappers.getMapper(AlunoMapper.class);

    private Aluno aluno, aluno1, aluno2, aluno3, aluno4;
    private final AlunoDTO dtoTeste;
    private final List<Aluno> alunosTeste;
    private final Long id;
    private final Programa programa;

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

        dtoTeste = new AlunoDTO(1L, "DTO Teste", "DTO Teste", true, null);

        LocalDate dataInicio = LocalDate.of(2020, 10, 18);
        LocalDate dataFim = LocalDate.of(2021, 4, 18);
        programa = new Programa(1L, "Insiders", dataInicio, dataFim, null);
    }

    private Boolean compareDTOWithAluno(AlunoDTO dto, Aluno aluno) {
        return dto.getId().equals(aluno.getId()) &&
                dto.getNome().equals(aluno.getNome()) &&
                dto.getClasse().equals(aluno.getClasse()) &&
                dto.getActive() == aluno.getActive();
    }

    private Boolean compareDTOWithPrograma(ProgramaDTO dto, Programa programa) {
        return dto.getId().equals(programa.getId()) &&
                dto.getNome().equals(programa.getNome()) &&
                dto.getDataInicio().equals(programa.getDataInicio()) &&
                dto.getDataFim().equals(programa.getDataFim());
    }

    // FIND ALL
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
                        assertTrue(compareDTOWithAluno(dto, aluno));
                    }
                }
        );
    }

    // FIND ALL BY ACTIVE
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
                        assertTrue(compareDTOWithAluno(dto, aluno));
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
                        assertTrue(compareDTOWithAluno(dto, aluno));
                    }
                }
        );
    }

    // FIND BY ID
    @Test
    public void deveRetornarAlunoDTOPelaIdInformada() {
        when(alunoRepository.findById(id)).thenReturn(Optional.of(aluno));

        Optional<AlunoDTO> alunoByIndex = alunoService.getAluno(id);

        assertTrue(alunoByIndex.isPresent());
        AlunoDTO alunoDTO = alunoByIndex.get();

        assertAll(
                () -> verify(alunoRepository, times(1)).findById(1L),
                () -> assertTrue(compareDTOWithAluno(alunoDTO, aluno))
        );
    }

    @Test
    public void deveRetornarOptionalEmptySeAIdForNula() {
        // o mock não aceitou a passagem do null diretamente, porém o service sim
        //when(alunoRepository.findById(any())).thenReturn(null);
        Optional<AlunoDTO> alunoByIndex = alunoService.getAluno(null);
        assertTrue(alunoByIndex.isEmpty());
    }

    // DELETE
    @Test
    public void deveRetornarAlunoDTODeletadoLogicamente() {
        when(alunoRepository.logicalDelete(id)).thenReturn(1);
        when(alunoRepository.findById(id)).thenReturn(Optional.of(aluno));
        aluno.setActive(false);
        Optional<AlunoDTO> alunoDTO = alunoService.deleteAluno(id);
        assertTrue(alunoDTO.isPresent());

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
    public void deveRetornarOptionalEmptyQuandoDeletarLogicamenteIdNulo() {
        //when(alunoRepository.logicalDelete(null)).thenReturn(null);
        Optional<AlunoDTO> alunoDTO = alunoService.deleteAluno(null);
        assertTrue(alunoDTO.isEmpty());
    }

    // REACTIVATE
    @Test
    public void deveRetornarAlunoDTOReativadoLogicamente() {
        when(alunoRepository.reativarAluno(id)).thenReturn(1);
        when(alunoRepository.findById(id)).thenReturn(Optional.of(aluno));
        aluno.setActive(true);
        Optional<AlunoDTO> alunoDTO = alunoService.reativarAluno(id);
        assertTrue(alunoDTO.isPresent());

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

    @Test
    public void deveRetornarOptionalEmptyQuandoReativarLogicamenteIdNulo() {
        //when(alunoRepository.reativarAluno(null)).thenReturn(null);
        Optional<AlunoDTO> alunoDTO = alunoService.reativarAluno(null);
        assertTrue(alunoDTO.isEmpty());
    }

    // CREATE
    @Test
    public void deveRetornarAlunoCriado() {
        when(alunoRepository.findByNome(dtoTeste.getNome())).thenReturn(Optional.empty());
        Aluno aluno = alunoMapper.convertDTOToAluno(dtoTeste);
        when(alunoRepository.save(any(Aluno.class))).thenReturn(aluno);

        Optional<AlunoDTO> alunoDTO = alunoService.criaAluno(dtoTeste);
        assertTrue(alunoDTO.isPresent());

        assertAll(
                () -> verify(alunoRepository, times(1)).findByNome(dtoTeste.getNome()),
                () -> verify(alunoRepository, times(1)).save(any(Aluno.class)),
                () -> assertTrue(compareDTOWithAluno(alunoDTO.get(), aluno))
        );
    }

    @Test
    public void deveRetornarOptionalEmptySeOAlunoForEncontradoPeloNome() {
        Optional<Aluno> alunoVazio = Optional.of(alunoMapper.convertDTOToAluno(dtoTeste));
        when(alunoRepository.findByNome(dtoTeste.getNome())).thenReturn(alunoVazio);
        Optional<AlunoDTO> dtoVazio = alunoService.criaAluno(dtoTeste);

        assertAll(
                () -> verify(alunoRepository, times(1)).findByNome(dtoTeste.getNome()),
                () -> assertTrue(dtoVazio.isEmpty())
        );
    }

    @Test
    public void deveRetornarOptionalEmptySeOAlunoForNulo() {
        Optional<AlunoDTO> dtoVazio = alunoService.criaAluno(null);
        assertTrue(dtoVazio.isEmpty());
    }

    // UPDATE
    @Test
    public void deveRetornarAlunoDTOAoModificarAluno() {
        when(alunoRepository.findById(id)).thenReturn(Optional.of(aluno));
        when(alunoRepository.save(any(Aluno.class))).thenReturn(aluno);

        Optional<AlunoDTO> alunoDTO = alunoService.modificaAluno(id, dtoTeste);
        assertTrue(alunoDTO.isPresent());

        assertAll(
                () -> verify(alunoRepository, times(1)).findById(id),
                () -> assertTrue(compareDTOWithAluno(alunoDTO.get(), aluno))
        );
    }

    @Test
    public void deveRetornarAlunoDTOComProgramaNuloAoModificarAluno() {
        when(alunoRepository.findById(id)).thenReturn(Optional.of(aluno));
        when(alunoRepository.save(any(Aluno.class))).thenReturn(aluno);

        Optional<AlunoDTO> alunoDTO = alunoService.modificaAluno(id, dtoTeste);
        assertTrue(alunoDTO.isPresent());

        assertAll(
                () -> verify(alunoRepository, times(1)).findById(id),
                () -> assertTrue(compareDTOWithAluno(alunoDTO.get(), aluno))
        );
    }

    @Test
    public void deveRetornarOptionalEmptySeOAlunoModificadoForNulo() {
        when(alunoRepository.findById(id)).thenReturn(Optional.of(aluno));
        Optional<AlunoDTO> alunoDTO = alunoService.modificaAluno(id, null);

        assertTrue(alunoDTO.isEmpty());
        verify(alunoRepository, times(1)).findById(id);
    }

    @Test
    public void deveRetornarOptionalEmptySeAIdInformadaForNula() {
        Optional<AlunoDTO> alunoDTO = alunoService.modificaAluno(null, dtoTeste);
        assertTrue(alunoDTO.isEmpty());
        verify(alunoRepository, times(1)).findById(any());
    }

    // SET PROGRAMA
    @Test
    public void deveRetornarAlunoDTOAoDefinirProgramaDoAluno() {
        when(programaService.getProgramaById(id)).thenReturn(Optional.of(programa));
        when(alunoRepository.findById(id)).thenReturn(Optional.of(aluno));

        Optional<AlunoDTO> alunoDTO = alunoService.setPrograma(id, id);
        assertTrue(alunoDTO.isPresent());

        ProgramaDTO programaDTO = alunoDTO.get().getPrograma();

        assertAll(
                () -> verify(programaService, times(1)).getProgramaById(id),
                () -> verify(alunoRepository, times(1)).findById(id),
                () -> assertTrue(compareDTOWithAluno(alunoDTO.get(), aluno)),
                () -> assertTrue(compareDTOWithPrograma(programaDTO, programa))
        );
    }

    @Test
    public void deveRetornarAlunoDTOComProgramaNulo() {
        when(programaService.getProgramaById(null)).thenReturn(Optional.empty());
        when(alunoRepository.findById(id)).thenReturn(Optional.of(aluno));
        aluno.setPrograma(null);

        Optional<AlunoDTO> alunoDTO = alunoService.setPrograma(id, null);
        assertTrue(alunoDTO.isPresent());
        ProgramaDTO programaDTO = alunoDTO.get().getPrograma();

        assertAll(
                () -> verify(programaService, times(1)).getProgramaById(null),
                () -> verify(alunoRepository, times(1)).findById(id),
                () -> assertTrue(compareDTOWithAluno(alunoDTO.get(), aluno)),
                () -> assertNull(programaDTO)
        );
    }
}
