import { useEffect, useState } from "react";
import { api } from "../api/api";
import ProjetoCard from "../components/ProjetoCard";

export default function ProjetosPage() {
  const [projetos, setProjetos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [erro, setErro] = useState("");

  useEffect(() => {
    carregarProjetos();
  }, []);

  async function carregarProjetos() {
    try {
      setLoading(true);
      setErro("");
      const response = await api.get("/projetos");
      setProjetos(response.data);
    } catch (error) {
      setErro("Não foi possível carregar os projetos.");
    } finally {
      setLoading(false);
    }
  }

  return (
    <div>
      <div className="page-header">
        <div>
          <h2>Projetos</h2>
          <p className="muted">
            Visualize os projetos e acompanhe o estado geral do sistema.
          </p>
        </div>
      </div>

      {loading && <p>Carregando projetos...</p>}
      {erro && <div className="alert error">{erro}</div>}

      {!loading && !erro && (
        <div className="grid">
          {projetos.map((projeto) => (
            <ProjetoCard key={projeto.id} projeto={projeto} />
          ))}
        </div>
      )}
    </div>
  );
}
