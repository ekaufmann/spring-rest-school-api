package com.example.demo.service;

import com.example.demo.dto.AvaliacaoDTOCreate;
import com.example.demo.dto.AvaliacaoDTOResponse;
import com.example.demo.dto.AvaliacaoDTOUpdate;
import com.example.demo.mapper.AvaliacaoMapper;
import com.example.demo.model.Aluno;
import com.example.demo.model.Avaliacao;
import com.example.demo.model.Disciplina;
import com.example.demo.model.Mentor;
import com.example.demo.repository.AvaliacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    @Autowired
    AlunoService alunoService;

    @Autowired
    MentorService mentorService;

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
    public Optional<AvaliacaoDTOResponse> criaAvaliacao(AvaliacaoDTOCreate avaliacaoDTOCreate) {
        Optional<Avaliacao> avaliacao = getAvaliacaoExistente(avaliacaoDTOCreate);

        if(avaliacao.isEmpty()) {
            Optional<Disciplina> disciplina = disciplinaService.getDisciplinaById(avaliacaoDTOCreate.getDisciplinaId());
            if(disciplina.isPresent()) {
                avaliacao = Optional.of(convertDTOToAvaliacao(avaliacaoDTOCreate, disciplina.get()));
                avaliacao.map(avaliacaoRepository::save);
            }
        }
        return avaliacao.map(AvaliacaoMapper::convertAvaliacaoToDTOResponse);
    }

    private Optional<Avaliacao> getAvaliacaoExistente(AvaliacaoDTOCreate avaliacaoDTOCreate) {
        return avaliacaoRepository.findByDisciplinaIdAndDataRealizacao(
                avaliacaoDTOCreate.getDisciplinaId(), avaliacaoDTOCreate.getDataRealizacao());
    }

    @Transactional
    public Optional<AvaliacaoDTOResponse> deleteAvaliacao(Long id) {
        return avaliacaoRepository.logicalDelete(id) == 1 ? getAvaliacao(id) : Optional.empty();
    }

    public Optional<AvaliacaoDTOResponse> modificaAvaliacao(Long id, AvaliacaoDTOUpdate avaliacaoModificada) {
        Optional<Avaliacao> avaliacao = getAvaliacaoById(id);

        if(avaliacao.isPresent()) {
            avaliacao = makeChanges(avaliacao.get(), avaliacaoModificada);
            avaliacao.map(avaliacaoRepository::save);
        }
        return avaliacao.map(AvaliacaoMapper::convertAvaliacaoToDTOResponse);
    }

    private Optional<Avaliacao> makeChanges(Avaliacao avaliacao, AvaliacaoDTOUpdate avaliacaoModificada) {
        Long disciplinaId = avaliacaoModificada.getDisciplinaId();
        if(disciplinaId != null) {
            Optional<Disciplina> disciplina = disciplinaService.getDisciplinaById(avaliacaoModificada.getDisciplinaId());
            disciplina.ifPresent(avaliacao::setDisciplina);
        }

        String conteudo = avaliacaoModificada.getConteudo();
        LocalDate dataRealizacao = avaliacaoModificada.getDataRealizacao();
        LocalDate dataCorrecao = avaliacaoModificada.getDataCorrecao();
        Float nota = avaliacaoModificada.getNota();
        Boolean active = avaliacaoModificada.getActive();
        Optional<Aluno> aluno = alunoService.getAlunoById(avaliacaoModificada.getAlunoId());
        Optional<Mentor> mentor = mentorService.getMentorById(avaliacaoModificada.getMentorId());

        avaliacao.setConteudo(conteudo == null ? avaliacao.getConteudo() : conteudo);
        avaliacao.setDataRealizacao(dataRealizacao == null ? avaliacao.getDataRealizacao() : dataRealizacao);
        avaliacao.setDataCorrecao(dataCorrecao == null ? avaliacao.getDataCorrecao() : dataCorrecao);
        avaliacao.setNota(nota == null ? avaliacao.getNota() : nota);
        avaliacao.setActive(active == null ? avaliacao.getActive() : active);
        avaliacao.setAluno(aluno.orElseGet(avaliacao::getAluno));
        avaliacao.setMentor(mentor.orElseGet(avaliacao::getMentor));

        return Optional.of(avaliacao);
    }
}
