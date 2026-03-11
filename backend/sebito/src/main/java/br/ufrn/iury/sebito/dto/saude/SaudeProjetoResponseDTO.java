package br.ufrn.iury.sebito.dto.saude;

import br.ufrn.iury.sebito.domain.enums.ClassificacaoSaudeProjeto;
import br.ufrn.iury.sebito.domain.enums.StatusProjeto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SaudeProjetoResponseDTO(
        Long projetoId,
        String titulo,
        StatusProjeto status,
        LocalDate dataInicio,
        LocalDate dataFim,
        BigDecimal orcamentoPrevisto,
        BigDecimal orcamentoExecutado,
        Long marcosObrigatoriosPendentes,
        Integer score,
        ClassificacaoSaudeProjeto classificacao
) {
}