export default function MarcoList({ marcos, onConcluir }) {
  if (!marcos.length) {
    return <p className="muted">Nenhum marco cadastrado.</p>;
  }

  return (
    <div className="marco-list">
      {marcos.map((marco) => (
        <div key={marco.id} className="card marco-card">
          <div>
            <h4>{marco.nome}</h4>
            <p className="muted">
              Obrigatório: {marco.obrigatorio ? "Sim" : "Não"} | Concluído:{" "}
              {marco.concluido ? "Sim" : "Não"}
            </p>
            <p className="muted">Data limite: {marco.dataLimite}</p>
            {marco.dataConclusao && (
              <p className="muted">Data de conclusão: {marco.dataConclusao}</p>
            )}
          </div>

          {!marco.concluido && (
            <button
              className="button primary"
              onClick={() => onConcluir(marco.id)}
            >
              Concluir
            </button>
          )}
        </div>
      ))}
    </div>
  );
}
