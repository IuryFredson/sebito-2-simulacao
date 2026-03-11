import { useState } from "react";
import { api } from "../api/api";
import { useNavigate } from "react-router-dom";

export default function NovoProjetoPage() {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    titulo: "",
    coordenador: "",
    dataInicio: "",
    dataFim: "",
    orcamentoPrevisto: "",
  });

  const [erro, setErro] = useState("");
  const [loading, setLoading] = useState(false);

  function handleChange(e) {
    setForm((prev) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  }

  async function handleSubmit(e) {
    e.preventDefault();

    try {
      setLoading(true);
      setErro("");

      await api.post("/projetos", {
        ...form,
        orcamentoPrevisto: Number(form.orcamentoPrevisto),
      });

      navigate("/");
    } catch (error) {
      const apiMessage = error?.response?.data?.message;
      setErro(apiMessage || "Não foi possível criar o projeto.");
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="form-page">
      <div className="page-header">
        <div>
          <h2>Novo Projeto</h2>
          <p className="muted">Cadastre um novo projeto acadêmico.</p>
        </div>
      </div>

      <div className="card form-card">
        <form onSubmit={handleSubmit} className="form-grid">
          <div className="field full">
            <label>Título</label>
            <input
              name="titulo"
              value={form.titulo}
              onChange={handleChange}
              placeholder="Ex.: Projeto de IA Aplicada"
              required
            />
          </div>

          <div className="field full">
            <label>Coordenador</label>
            <input
              name="coordenador"
              value={form.coordenador}
              onChange={handleChange}
              placeholder="Nome do coordenador"
              required
            />
          </div>

          <div className="field">
            <label>Data de início</label>
            <input
              type="date"
              name="dataInicio"
              value={form.dataInicio}
              onChange={handleChange}
              required
            />
          </div>

          <div className="field">
            <label>Data de fim</label>
            <input
              type="date"
              name="dataFim"
              value={form.dataFim}
              onChange={handleChange}
              required
            />
          </div>

          <div className="field full">
            <label>Orçamento previsto</label>
            <input
              type="number"
              step="0.01"
              name="orcamentoPrevisto"
              value={form.orcamentoPrevisto}
              onChange={handleChange}
              placeholder="50000"
              required
            />
          </div>

          {erro && <div className="alert error full">{erro}</div>}

          <div className="full">
            <button className="button primary" type="submit" disabled={loading}>
              {loading ? "Salvando..." : "Criar Projeto"}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
