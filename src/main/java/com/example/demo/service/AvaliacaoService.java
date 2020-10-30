package com.example.demo.service;

import com.example.demo.dto.AvaliacaoDTO;
import com.example.demo.dto.AvaliacaoDTOResponse;
import com.example.demo.mapper.AvaliacaoMapper;
import com.example.demo.model.Avaliacao;
import com.example.demo.repository.AvaliacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AvaliacaoService {

    @Autowired
    AvaliacaoRepository avaliacaoRepository;

    public Optional<List<AvaliacaoDTOResponse>> getAvaliacoes() {
        return Optional.of(avaliacaoRepository.findAll()
                           .parallelStream()
                           .map(AvaliacaoMapper::convertAvaliacaoToDTOResponse)
                           .collect(Collectors.toList())
        );
    }

    public Optional<Avaliacao> getAvaliacaoById(Long id) {
        return avaliacaoRepository.findById(id);
    }

    public Optional<AvaliacaoDTOResponse> getAvaliacao(Long id) {
        return getAvaliacaoById(id).map(AvaliacaoMapper::convertAvaliacaoToDTOResponse);
    }

    public Optional<AvaliacaoDTOResponse> criaAvaliacao(AvaliacaoDTO avaliacaoDTO) {
        if(verificaAvaliacaoExistente(avaliacaoDTO)) {
            return Optional.empty();
        }



    }

    private Boolean verificaAvaliacaoExistente(AvaliacaoDTO avaliacaoDTO) {
        Optional<Avaliacao> avaliacaoExistente = avaliacaoRepository.findByDisciplinaAndDataRealizacao(
                avaliacaoDTO.getDisciplinaId(), avaliacaoDTO.getDataRealizacao());
        return avaliacaoExistente.isPresent();
    }
}
