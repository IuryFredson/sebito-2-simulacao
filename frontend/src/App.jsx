import { BrowserRouter, Routes, Route } from "react-router-dom";

import Layout from "./layout/Layout";
import ProjetosPage from "./pages/ProjetosPage";
import ProjetoDetalhePage from "./pages/ProjetoDetalhePage";
import NovoProjetoPage from "./pages/NovoProjetoPage";

function App() {
  return (
    <BrowserRouter>
      <Layout>
        <Routes>
          <Route path="/" element={<ProjetosPage />} />
          <Route path="/projeto/:id" element={<ProjetoDetalhePage />} />
          <Route path="/novo" element={<NovoProjetoPage />} />
        </Routes>
      </Layout>
    </BrowserRouter>
  );
}

export default App;
