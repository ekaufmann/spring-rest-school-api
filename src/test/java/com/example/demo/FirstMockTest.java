package com.example.demo;

import com.example.demo.dto.AlunoDTO;
import com.example.demo.mapper.AlunoMapper;
import com.example.demo.model.Aluno;
import com.example.demo.repository.AlunoRepository;
import com.example.demo.service.AlunoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class FirstMockTest {

    @Mock
    AlunoRepository alunoRepository;

    @InjectMocks
    AlunoService alunoService;

    @Test
    public void test() {
        var id = 1L;
        Aluno aluno = new Aluno("t", "teste");
        Mockito.when(alunoRepository.findById(id)).thenReturn(Optional.of(aluno));
        Optional<AlunoDTO> alunoByIndex = this.alunoService.getAluno(id);

        Assertions.assertTrue(alunoByIndex.isPresent());

        AlunoDTO alunoDTO = alunoByIndex.get();

        Assertions.assertEquals(alunoDTO.getNome(), aluno.getNome());
        Assertions.assertEquals(alunoDTO.getClasse(), aluno.getClasse());
    }
}
