import { Link } from "react-router-dom";

export default function Layout({ children }) {
  return (
    <div style={{ padding: "20px", fontFamily: "sans-serif" }}>
      <h1>Sebito 2.0</h1>

      <nav style={{ marginBottom: "20px" }}>
        <Link to="/">Projetos</Link> | <Link to="/novo">Novo Projeto</Link>
      </nav>

      {children}
    </div>
  );
}
