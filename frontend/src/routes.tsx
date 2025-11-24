
import { Routes, Route, Navigate } from "react-router-dom";
import LoginPage from "./feature/login/pages/LoginsPage";
import AssociadosPage from "./feature/associado/pages/AssociadosPage";

import VotarPage from "./feature/voto/pages/VotarPage";
import { PautaPage } from "./feature/pauta/pages/PautaPage";
import { SessaoPage } from "./feature/sessao/pages/SessaoPage";


export function AppRoutes() {
  return (
    
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route >
          <Route path="/" element={<Navigate to="/login" />} />
          <Route path="/pautas" element={<PautaPage />} />
          <Route path="/associados" element={<AssociadosPage />} /> {/*  USADA PARA TESTES */}
          <Route path="/sessoes" element={<SessaoPage />} />
          <Route path="/votar/:sessaoId" element={<VotarPage />} />
        </Route>
        <Route path="*" element={<h1>404 - Página não encontrada</h1>} />
      </Routes>
    
  );
}
