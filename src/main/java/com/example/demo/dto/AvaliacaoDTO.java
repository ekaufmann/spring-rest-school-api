package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoDTO {

    private Long disciplinaId;
    private String conteudo;
    private LocalDate dataRealizacao;

}
