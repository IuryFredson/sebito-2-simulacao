import { useEffect, useState } from "react";
import { api } from "../api/api";
import { useParams } from "react-router-dom";

export default function ProjetoDetalhePage() {
  const { id } = useParams();

  const [projeto, setProjeto] = useState(null);
  const [saude, setSaude] = useState(null);

  useEffect(() => {
    carregar();
  }, []);

  async function carregar() {
    const projetoRes = await api.get(`/projetos`);
    const saudeRes = await api.get(`/projetos/${id}/saude`);

    const projetoEncontrado = projetoRes.data.find((p) => p.id == id);

    setProjeto(projetoEncontrado);
    setSaude(saudeRes.data);
  }

  if (!projeto) return <p>Carregando...</p>;

  return (
    <div>
      <h2>{projeto.titulo}</h2>

      <p>Status: {projeto.status}</p>

      <p>Orçamento previsto: {projeto.orcamentoPrevisto}</p>

      <h3>Saúde do projeto</h3>

      {saude && (
        <div>
          <p>Score: {saude.score}</p>

          <p>Classificação: {saude.classificacao}</p>
        </div>
      )}
    </div>
  );
}
