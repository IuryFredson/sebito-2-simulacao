package br.ufrn.iury.sebito.dto.projeto;

import br.ufrn.iury.sebito.domain.enums.StatusProjeto;
import jakarta.validation.constraints.NotNull;

public record AlterarStatusProjetoRequestDTO(

        @NotNull(message = "O novo status é obrigatório")
        StatusProjeto novoStatus
) {
}