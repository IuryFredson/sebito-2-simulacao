import { Link } from "react-router-dom";
import StatusBadge from "./StatusBadge";

export default function ProjetoCard({ projeto }) {
  return (
    <div className="card project-card">
      <div className="project-card-top">
        <div>
          <h3>{projeto.titulo}</h3>
          <p className="muted">{projeto.coordenador}</p>
        </div>

        <StatusBadge status={projeto.status} />
      </div>

      <div className="project-meta">
        <div>
          <span className="label">Início</span>
          <strong>{projeto.dataInicio}</strong>
        </div>

        <div>
          <span className="label">Fim</span>
          <strong>{projeto.dataFim}</strong>
        </div>

        <div>
          <span className="label">Orçamento</span>
          <strong>
            R$ {Number(projeto.orcamentoPrevisto).toLocaleString("pt-BR")}
          </strong>
        </div>
      </div>

      <div className="project-actions">
        <Link className="button secondary" to={`/projeto/${projeto.id}`}>
          Ver detalhes
        </Link>
      </div>
    </div>
  );
}
