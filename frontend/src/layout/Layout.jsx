import { Link, useLocation } from "react-router-dom";

export default function Layout({ children }) {
  const location = useLocation();

  return (
    <div className="app-shell">
      <aside className="sidebar">
        <div className="brand">
          <div className="brand-mark">S</div>
          <div>
            <h1>Sebito 2.0</h1>
            <p>Gestão de Projetos Acadêmicos</p>
          </div>
        </div>

        <nav className="nav-menu">
          <Link
            className={
              location.pathname === "/" ? "nav-link active" : "nav-link"
            }
            to="/"
          >
            Projetos
          </Link>

          <Link
            className={
              location.pathname === "/novo" ? "nav-link active" : "nav-link"
            }
            to="/novo"
          >
            Novo Projeto
          </Link>
        </nav>
      </aside>

      <main className="content">{children}</main>
    </div>
  );
}
