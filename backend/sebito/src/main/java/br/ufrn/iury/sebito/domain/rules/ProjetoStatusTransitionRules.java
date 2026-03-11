package br.ufrn.iury.sebito.domain.rules;

import br.ufrn.iury.sebito.domain.enums.StatusProjeto;

import java.util.Map;
import java.util.Set;

public final class ProjetoStatusTransitionRules {

    private ProjetoStatusTransitionRules() {
    }

    private static final Map<StatusProjeto, Set<StatusProjeto>> TRANSICOES_VALIDAS = Map.of(
            StatusProjeto.RASCUNHO, Set.of(
                    StatusProjeto.EM_ANALISE
            ),
            StatusProjeto.EM_ANALISE, Set.of(
                    StatusProjeto.APROVADO,
                    StatusProjeto.SUSPENSO
            ),
            StatusProjeto.APROVADO, Set.of(
                    StatusProjeto.EM_EXECUCAO,
                    StatusProjeto.SUSPENSO
            ),
            StatusProjeto.EM_EXECUCAO, Set.of(
                    StatusProjeto.PRESTACAO_DE_CONTAS,
                    StatusProjeto.SUSPENSO
            ),
            StatusProjeto.PRESTACAO_DE_CONTAS, Set.of(
                    StatusProjeto.ENCERRADO
            ),
            StatusProjeto.SUSPENSO, Set.of(
                    StatusProjeto.EM_ANALISE,
                    StatusProjeto.ENCERRADO
            ),
            StatusProjeto.ENCERRADO, Set.of()
    );

    public static boolean isTransicaoValida(StatusProjeto statusAtual, StatusProjeto novoStatus) {
        return TRANSICOES_VALIDAS
                .getOrDefault(statusAtual, Set.of())
                .contains(novoStatus);
    }

    public static Set<StatusProjeto> proximosStatusValidos(StatusProjeto statusAtual) {
        return TRANSICOES_VALIDAS.getOrDefault(statusAtual, Set.of());
    }
}