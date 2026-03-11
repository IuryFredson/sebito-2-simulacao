package br.ufrn.iury.sebito.dto.projeto;

import br.ufrn.iury.sebito.domain.enums.StatusProjeto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProjetoResponseDTO(
        Long id,
        String titulo,
        String coordenador,
        LocalDate dataInicio,
        LocalDate dataFim,
        BigDecimal orcamentoPrevisto,
        BigDecimal orcamentoExecutado,
        StatusProjeto status
) {
}