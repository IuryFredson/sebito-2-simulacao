package br.ufrn.iury.sebito.service;

import br.ufrn.iury.sebito.domain.enums.ClassificacaoSaudeProjeto;
import br.ufrn.iury.sebito.domain.model.Projeto;
import br.ufrn.iury.sebito.domain.repository.MarcoRepository;
import br.ufrn.iury.sebito.domain.repository.ProjetoRepository;
import br.ufrn.iury.sebito.dto.saude.SaudeProjetoResponseDTO;
import br.ufrn.iury.sebito.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class SaudeProjetoService {

    private final ProjetoRepository projetoRepository;
    private final MarcoRepository marcoRepository;

    public SaudeProjetoService(ProjetoRepository projetoRepository, MarcoRepository marcoRepository) {
        this.projetoRepository = projetoRepository;
        this.marcoRepository = marcoRepository;
    }

    public SaudeProjetoResponseDTO calcularSaude(Long projetoId) {
        Projeto projeto = projetoRepository.findById(projetoId)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado"));

        long marcosObrigatoriosPendentes =
                marcoRepository.countByProjetoIdAndObrigatorioTrueAndConcluidoFalse(projetoId);

        int score = 100;

        score -= calcularPenalidadePorAtraso(projeto);
        score -= calcularPenalidadePorOrcamento(projeto);
        score -= calcularPenalidadePorMarcosPendentes(marcosObrigatoriosPendentes);

        if (score < 0) {
            score = 0;
        }

        ClassificacaoSaudeProjeto classificacao = classificar(score);

        return new SaudeProjetoResponseDTO(
                projeto.getId(),
                projeto.getTitulo(),
                projeto.getStatus(),
                projeto.getDataInicio(),
                projeto.getDataFim(),
                projeto.getOrcamentoPrevisto(),
                projeto.getOrcamentoExecutado(),
                marcosObrigatoriosPendentes,
                score,
                classificacao
        );
    }

    private int calcularPenalidadePorAtraso(Projeto projeto) {
        LocalDate hoje = LocalDate.now();

        if (hoje.isAfter(projeto.getDataFim())) {
            return 35;
        }

        long duracaoTotal = projeto.getDataInicio().until(projeto.getDataFim()).getDays();
        long diasRestantes = hoje.until(projeto.getDataFim()).getDays();

        if (duracaoTotal > 0 && diasRestantes >= 0) {
            double percentualTempoRestante = (double) diasRestantes / duracaoTotal;

            if (percentualTempoRestante < 0.2) {
                return 15;
            }
        }

        return 0;
    }

    private int calcularPenalidadePorOrcamento(Projeto projeto) {
        BigDecimal previsto = projeto.getOrcamentoPrevisto();
        BigDecimal executado = projeto.getOrcamentoExecutado();

        if (previsto == null || previsto.compareTo(BigDecimal.ZERO) <= 0) {
            return 0;
        }

        if (executado == null) {
            return 0;
        }

        BigDecimal percentualExecutado = executado.multiply(BigDecimal.valueOf(100))
                .divide(previsto, 2, java.math.RoundingMode.HALF_UP);

        if (percentualExecutado.compareTo(BigDecimal.valueOf(100)) > 0) {
            return 30;
        }

        if (percentualExecutado.compareTo(BigDecimal.valueOf(80)) > 0) {
            return 10;
        }

        return 0;
    }

    private int calcularPenalidadePorMarcosPendentes(long marcosObrigatoriosPendentes) {
        if (marcosObrigatoriosPendentes >= 3) {
            return 30;
        }

        if (marcosObrigatoriosPendentes == 2) {
            return 20;
        }

        if (marcosObrigatoriosPendentes == 1) {
            return 10;
        }

        return 0;
    }

    private ClassificacaoSaudeProjeto classificar(int score) {
        if (score >= 80) {
            return ClassificacaoSaudeProjeto.SAUDAVEL;
        }

        if (score >= 50) {
            return ClassificacaoSaudeProjeto.ATENCAO;
        }

        return ClassificacaoSaudeProjeto.CRITICO;
    }
}