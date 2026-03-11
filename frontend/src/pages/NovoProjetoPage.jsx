import { useState } from "react";
import { api } from "../api/api";
import { useNavigate } from "react-router-dom";

export default function NovoProjetoPage() {
  const navigate = useNavigate();

  const [titulo, setTitulo] = useState("");
  const [coordenador, setCoordenador] = useState("");

  async function criarProjeto(e) {
    e.preventDefault();

    await api.post("/projetos", {
      titulo,
      coordenador,
      dataInicio: "2026-03-15",
      dataFim: "2026-12-20",
      orcamentoPrevisto: 10000,
    });

    navigate("/");
  }

  return (
    <div>
      <h2>Novo Projeto</h2>

      <form onSubmit={criarProjeto}>
        <input
          placeholder="Título"
          value={titulo}
          onChange={(e) => setTitulo(e.target.value)}
        />

        <br />

        <input
          placeholder="Coordenador"
          value={coordenador}
          onChange={(e) => setCoordenador(e.target.value)}
        />

        <br />

        <button type="submit">Criar Projeto</button>
      </form>
    </div>
  );
}
