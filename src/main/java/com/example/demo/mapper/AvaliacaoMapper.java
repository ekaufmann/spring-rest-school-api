package com.example.demo.mapper;

import com.example.demo.dto.*;
import com.example.demo.model.Avaliacao;
import com.example.demo.model.Disciplina;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AvaliacaoMapper {

    AvaliacaoDTOResponse convertAvaliacaoToDTOResponse(Avaliacao avaliacao);

    Avaliacao convertDTOToAvaliacao(AvaliacaoDTOCreate avaliacaoDTOCreate, Disciplina disciplina);
}
