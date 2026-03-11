export default function StatusBadge({ status }) {
  const map = {
    RASCUNHO: "badge neutral",
    EM_ANALISE: "badge info",
    APROVADO: "badge success",
    EM_EXECUCAO: "badge primary",
    PRESTACAO_DE_CONTAS: "badge warning",
    ENCERRADO: "badge success",
    SUSPENSO: "badge danger",
  };

  return <span className={map[status] || "badge neutral"}>{status}</span>;
}
