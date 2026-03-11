import { useEffect, useState } from "react";
import { api } from "../api/api";
import { useParams } from "react-router-dom";
import StatusBadge from "../components/StatusBadge";
import SaudeBadge from "../components/SaudeBadge";
import MarcoList from "../components/MarcoList";

export default function ProjetoDetalhePage() {
  const { id } = useParams();

  const [projeto, setProjeto] = useState(null);
  const [saude, setSaude] = useState(null);
  const [marcos, setMarcos] = useState([]);
  const [erro, setErro] = useState("");
  const [loading, setLoading] = useState(true);

  const [novoMarco, setNovoMarco] = useState({
    nome: "",
    obrigatorio: true,
    dataLimite: "",
  });

  const [novoStatus, setNovoStatus] = useState("RASCUNHO");
  const [orcamentoExecutado, setOrcamentoExecutado] = useState("");

  useEffect(() => {
    carregarTudo();
  }, [id]);

  async function carregarTudo() {
    try {
      setLoading(true);
      setErro("");

      const [projetosRes, saudeRes, marcosRes] = await Promise.all([
        api.get("/projetos"),
        api.get(`/projetos/${id}/saude`),
        api.get(`/projetos/${id}/marcos`),
      ]);

      const projetoEncontrado = projetosRes.data.find(
        (p) => String(p.id) === String(id)
      );

      setProjeto(projetoEncontrado);
      setSaude(saudeRes.data);
      setMarcos(marcosRes.data);
      setNovoStatus(projetoEncontrado?.status || "RASCUNHO");
      setOrcamentoExecutado(projetoEncontrado?.orcamentoExecutado ?? "");
    } catch (error) {
      const apiMessage = error?.response?.data?.message;
      setErro(
        apiMessage || "Não foi possível carregar os detalhes do projeto."
      );
    } finally {
      setLoading(false);
    }
  }

  async function criarMarco(e) {
    e.preventDefault();

    try {
      await api.post(`/projetos/${id}/marcos`, {
        nome: novoMarco.nome,
        obrigatorio: novoMarco.obrigatorio,
        dataLimite: novoMarco.dataLimite,
      });

      setNovoMarco({
        nome: "",
        obrigatorio: true,
        dataLimite: "",
      });

      await carregarTudo();
    } catch (error) {
      setErro(error?.response?.data?.message || "Erro ao criar marco.");
    }
  }

  async function concluirMarco(marcoId) {
    try {
      await api.patch(`/projetos/${id}/marcos/${marcoId}/concluir`);
      await carregarTudo();
    } catch (error) {
      setErro(error?.response?.data?.message || "Erro ao concluir marco.");
    }
  }

  async function alterarStatus(e) {
    e.preventDefault();

    try {
      await api.patch(`/projetos/${id}/status`, {
        novoStatus,
      });

      await carregarTudo();
    } catch (error) {
      setErro(error?.response?.data?.message || "Erro ao alterar status.");
    }
  }

  async function atualizarOrcamento(e) {
    e.preventDefault();

    try {
      await api.patch(`/projetos/${id}/orcamento-executado`, {
        orcamentoExecutado: Number(orcamentoExecutado),
      });

      await carregarTudo();
    } catch (error) {
      setErro(error?.response?.data?.message || "Erro ao atualizar orçamento.");
    }
  }

  if (loading) return <p>Carregando...</p>;
  if (!projeto) return <p>Projeto não encontrado.</p>;

  return (
    <div>
      <div className="page-header">
        <div>
          <h2>{projeto.titulo}</h2>
          <p className="muted">{projeto.coordenador}</p>
        </div>

        <StatusBadge status={projeto.status} />
      </div>

      {erro && <div className="alert error">{erro}</div>}

      <div className="details-grid">
        <div className="card">
          <h3>Resumo</h3>
          <div className="stats">
            <div className="stat-box">
              <span>Início</span>
              <strong>{projeto.dataInicio}</strong>
            </div>

            <div className="stat-box">
              <span>Fim</span>
              <strong>{projeto.dataFim}</strong>
            </div>

            <div className="stat-box">
              <span>Orçamento previsto</span>
              <strong>
                R$ {Number(projeto.orcamentoPrevisto).toLocaleString("pt-BR")}
              </strong>
            </div>

            <div className="stat-box">
              <span>Orçamento executado</span>
              <strong>
                R$ {Number(projeto.orcamentoExecutado).toLocaleString("pt-BR")}
              </strong>
            </div>
          </div>
        </div>

        <div className="card">
          <h3>Saúde do projeto</h3>
          {saude && (
            <div className="saude-box">
              <div className="score-circle">{saude.score}</div>
              <div>
                <SaudeBadge classificacao={saude.classificacao} />
                <p className="muted" style={{ marginTop: "10px" }}>
                  Marcos obrigatórios pendentes:{" "}
                  {saude.marcosObrigatoriosPendentes}
                </p>
              </div>
            </div>
          )}
        </div>
      </div>

      <div className="details-grid">
        <div className="card">
          <h3>Alterar status</h3>
          <form onSubmit={alterarStatus} className="inline-form">
            <select
              value={novoStatus}
              onChange={(e) => setNovoStatus(e.target.value)}
            >
              <option value="RASCUNHO">RASCUNHO</option>
              <option value="EM_ANALISE">EM_ANALISE</option>
              <option value="APROVADO">APROVADO</option>
              <option value="EM_EXECUCAO">EM_EXECUCAO</option>
              <option value="PRESTACAO_DE_CONTAS">PRESTACAO_DE_CONTAS</option>
              <option value="ENCERRADO">ENCERRADO</option>
              <option value="SUSPENSO">SUSPENSO</option>
            </select>

            <button className="button primary" type="submit">
              Atualizar
            </button>
          </form>
        </div>

        <div className="card">
          <h3>Atualizar orçamento executado</h3>
          <form onSubmit={atualizarOrcamento} className="inline-form">
            <input
              type="number"
              step="0.01"
              value={orcamentoExecutado}
              onChange={(e) => setOrcamentoExecutado(e.target.value)}
              placeholder="Ex.: 15000"
            />

            <button className="button primary" type="submit">
              Salvar
            </button>
          </form>
        </div>
      </div>

      <div className="details-grid">
        <div className="card">
          <h3>Novo marco</h3>
          <form onSubmit={criarMarco} className="form-grid">
            <div className="field full">
              <label>Nome do marco</label>
              <input
                value={novoMarco.nome}
                onChange={(e) =>
                  setNovoMarco((prev) => ({ ...prev, nome: e.target.value }))
                }
                placeholder="Ex.: Aprovação documental"
                required
              />
            </div>

            <div className="field">
              <label>Obrigatório</label>
              <select
                value={String(novoMarco.obrigatorio)}
                onChange={(e) =>
                  setNovoMarco((prev) => ({
                    ...prev,
                    obrigatorio: e.target.value === "true",
                  }))
                }
              >
                <option value="true">Sim</option>
                <option value="false">Não</option>
              </select>
            </div>

            <div className="field">
              <label>Data limite</label>
              <input
                type="date"
                value={novoMarco.dataLimite}
                onChange={(e) =>
                  setNovoMarco((prev) => ({
                    ...prev,
                    dataLimite: e.target.value,
                  }))
                }
                required
              />
            </div>

            <div className="full">
              <button className="button primary" type="submit">
                Adicionar marco
              </button>
            </div>
          </form>
        </div>

        <div className="card">
          <h3>Marcos</h3>
          <MarcoList marcos={marcos} onConcluir={concluirMarco} />
        </div>
      </div>
    </div>
  );
}
