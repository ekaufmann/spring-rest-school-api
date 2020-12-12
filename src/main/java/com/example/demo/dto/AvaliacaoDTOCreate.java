package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoDTOCreate {

    @NotNull(message = "Disciplina precisa ser informada!")
    private Long disciplinaId;

    @NotNull(message = "Conteúdo precisa ser informado!")
    @Size(min = 5, max = 64, message = "Conteúdo must be between 5 and 64")
    private String conteudo;

    @NotNull(message = "Data de realização precisa ser informada!")
    private LocalDate dataRealizacao;

}
