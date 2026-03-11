import { useEffect, useState } from "react";
import { api } from "../api/api";
import { Link } from "react-router-dom";

export default function ProjetosPage() {
  const [projetos, setProjetos] = useState([]);

  useEffect(() => {
    carregarProjetos();
  }, []);

  async function carregarProjetos() {
    const response = await api.get("/projetos");
    setProjetos(response.data);
  }

  return (
    <div>
      <h2>Projetos</h2>

      {projetos.map((projeto) => (
        <div
          key={projeto.id}
          style={{
            border: "1px solid #ccc",
            padding: "12px",
            marginBottom: "10px",
          }}
        >
          <h3>{projeto.titulo}</h3>

          <p>Status: {projeto.status}</p>

          <Link to={`/projeto/${projeto.id}`}>Ver detalhes</Link>
        </div>
      ))}
    </div>
  );
}
