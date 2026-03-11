package br.ufrn.iury.sebito.dto.projeto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AtualizarOrcamentoExecutadoRequestDTO(

        @NotNull(message = "O orçamento executado é obrigatório")
        @DecimalMin(value = "0.0", inclusive = true, message = "O orçamento executado não pode ser negativo")
        BigDecimal orcamentoExecutado
) {
}