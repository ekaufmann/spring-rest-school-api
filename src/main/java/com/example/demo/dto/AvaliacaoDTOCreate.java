package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoDTOCreate {

    @NotNull(message = "Disciplina precisa ser informada!")
    private Long disciplinaId;

    @NotNull(message = "Conteúdo precisa ser informado!")
    private String conteudo;

    @NotNull(message = "Data de realização precisa ser informada!")
    private LocalDate dataRealizacao;

}
