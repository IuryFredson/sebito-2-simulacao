package br.ufrn.iury.sebito.service;

import br.ufrn.iury.sebito.domain.enums.StatusProjeto;
import br.ufrn.iury.sebito.domain.model.Projeto;
import br.ufrn.iury.sebito.domain.repository.MarcoRepository;
import br.ufrn.iury.sebito.domain.repository.ProjetoRepository;
import br.ufrn.iury.sebito.domain.rules.ProjetoStatusTransitionRules;
import br.ufrn.iury.sebito.dto.projeto.AlterarStatusProjetoRequestDTO;
import br.ufrn.iury.sebito.dto.projeto.AtualizarOrcamentoExecutadoRequestDTO;
import br.ufrn.iury.sebito.dto.projeto.ProjetoRequestDTO;
import br.ufrn.iury.sebito.dto.projeto.ProjetoResponseDTO;
import br.ufrn.iury.sebito.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Service
public class ProjetoService {

    private final ProjetoRepository projetoRepository;
    private final MarcoRepository marcoRepository;

    public ProjetoService(ProjetoRepository projetoRepository, MarcoRepository marcoRepository) {
        this.projetoRepository = projetoRepository;
        this.marcoRepository = marcoRepository;
    }

    public ProjetoResponseDTO criar(ProjetoRequestDTO requestDTO) {
        validarDatas(requestDTO);

        Projeto projeto = new Projeto();
        projeto.setTitulo(requestDTO.titulo());
        projeto.setCoordenador(requestDTO.coordenador());
        projeto.setDataInicio(requestDTO.dataInicio());
        projeto.setDataFim(requestDTO.dataFim());
        projeto.setOrcamentoPrevisto(requestDTO.orcamentoPrevisto());
        projeto.setOrcamentoExecutado(BigDecimal.ZERO);
        projeto.setStatus(StatusProjeto.RASCUNHO);

        Projeto projetoSalvo = projetoRepository.save(projeto);

        return toResponseDTO(projetoSalvo);
    }

    public List<ProjetoResponseDTO> listarTodos() {
        return projetoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public ProjetoResponseDTO alterarStatus(Long projetoId, AlterarStatusProjetoRequestDTO requestDTO) {
        Projeto projeto = projetoRepository.findById(projetoId)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado"));

        validarMudancaDeStatus(projeto, requestDTO.novoStatus());

        projeto.setStatus(requestDTO.novoStatus());

        Projeto projetoAtualizado = projetoRepository.save(projeto);

        return toResponseDTO(projetoAtualizado);
    }

    public ProjetoResponseDTO atualizarOrcamentoExecutado(Long projetoId, AtualizarOrcamentoExecutadoRequestDTO requestDTO) {
        Projeto projeto = projetoRepository.findById(projetoId)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado"));

        projeto.setOrcamentoExecutado(requestDTO.orcamentoExecutado());

        Projeto projetoAtualizado = projetoRepository.save(projeto);

        return toResponseDTO(projetoAtualizado);
    }

    private void validarDatas(ProjetoRequestDTO requestDTO) {
        if (requestDTO.dataFim().isBefore(requestDTO.dataInicio())) {
            throw new IllegalArgumentException("A data de fim não pode ser anterior à data de início");
        }
    }

    private void validarMudancaDeStatus(Projeto projeto, StatusProjeto novoStatus) {
        StatusProjeto statusAtual = projeto.getStatus();

        if (statusAtual == novoStatus) {
            throw new IllegalArgumentException("O projeto já está no status informado");
        }

        boolean transicaoValida = ProjetoStatusTransitionRules.isTransicaoValida(statusAtual, novoStatus);

        if (!transicaoValida) {
            Set<StatusProjeto> proximosValidos = ProjetoStatusTransitionRules.proximosStatusValidos(statusAtual);

            throw new IllegalArgumentException(
                    "Transição de status inválida: de " + statusAtual +
                            " para " + novoStatus +
                            ". Próximos status válidos: " + proximosValidos
            );
        }

        if (novoStatus == StatusProjeto.EM_EXECUCAO) {
            long marcosObrigatoriosPendentes =
                    marcoRepository.countByProjetoIdAndObrigatorioTrueAndConcluidoFalse(projeto.getId());

            if (marcosObrigatoriosPendentes > 0) {
                throw new IllegalArgumentException(
                        "O projeto não pode ir para EM_EXECUCAO enquanto houver marcos obrigatórios pendentes"
                );
            }
        }
    }

    private ProjetoResponseDTO toResponseDTO(Projeto projeto) {
        return new ProjetoResponseDTO(
                projeto.getId(),
                projeto.getTitulo(),
                projeto.getCoordenador(),
                projeto.getDataInicio(),
                projeto.getDataFim(),
                projeto.getOrcamentoPrevisto(),
                projeto.getOrcamentoExecutado(),
                projeto.getStatus()
        );
    }
}