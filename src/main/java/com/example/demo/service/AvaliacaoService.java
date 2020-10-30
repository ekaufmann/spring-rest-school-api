package com.example.demo.service;

import com.example.demo.dto.AvaliacaoDTO;
import com.example.demo.dto.AvaliacaoDTOResponse;
import com.example.demo.mapper.AvaliacaoMapper;
import com.example.demo.model.Avaliacao;
import com.example.demo.model.Disciplina;
import com.example.demo.repository.AvaliacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.demo.mapper.AvaliacaoMapper.*;

@Service
public class AvaliacaoService {

    @Autowired
    AvaliacaoRepository avaliacaoRepository;

    @Autowired
    DisciplinaService disciplinaService;

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

    @Transactional
    public Optional<AvaliacaoDTOResponse> criaAvaliacao(AvaliacaoDTO avaliacaoDTO) {
        Optional<Avaliacao> avaliacao = getAvaliacaoExistente(avaliacaoDTO);

        if(avaliacao.isEmpty()) {
            Optional<Disciplina> disciplina = disciplinaService.getDisciplinaById(avaliacaoDTO.getDisciplinaId());
            if(disciplina.isPresent()) {
                avaliacao = Optional.of(convertDTOToAvaliacao(avaliacaoDTO, disciplina.get()));
                avaliacao.map(avaliacaoRepository::save);
            }
        }
        return avaliacao.map(AvaliacaoMapper::convertAvaliacaoToDTOResponse);
    }

    private Optional<Avaliacao> getAvaliacaoExistente(AvaliacaoDTO avaliacaoDTO) {
        return avaliacaoRepository.findByDisciplinaIdAndDataRealizacao(
                avaliacaoDTO.getDisciplinaId(), avaliacaoDTO.getDataRealizacao());
    }

    @Transactional
    public Optional<AvaliacaoDTOResponse> deleteAvaliacao(Long id) {
        return avaliacaoRepository.logicalDelete(id) == 1 ? getAvaliacao(id) : Optional.empty();
    }
}
