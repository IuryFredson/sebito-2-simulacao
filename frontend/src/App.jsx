import { BrowserRouter, Routes, Route } from "react-router-dom";
import Layout from "./layout/Layout";
import ProjetosPage from "./pages/ProjetosPage";
import NovoProjetoPage from "./pages/NovoProjetoPage";
import ProjetoDetalhePage from "./pages/ProjetoDetalhePage";

function App() {
  return (
    <BrowserRouter>
      <Layout>
        <Routes>
          <Route path="/" element={<ProjetosPage />} />
          <Route path="/novo" element={<NovoProjetoPage />} />
          <Route path="/projeto/:id" element={<ProjetoDetalhePage />} />
        </Routes>
      </Layout>
    </BrowserRouter>
  );
}

export default App;
