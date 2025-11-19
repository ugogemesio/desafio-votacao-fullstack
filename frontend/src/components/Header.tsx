import React from 'react';
import { Link } from 'react-router-dom';
import './Header.scss';

export const Header: React.FC = () => {
  return (
    <header className="header">
      <div className="header__container">
        <div className="header__logo">
          <h1>Sistema de Votação Cooperativa</h1>
        </div>
        <nav className="header__nav">
          <Link to="/pautas" className="header__nav-link">Pautas</Link>
          <Link to="/sessoes" className="header__nav-link">Sessões de Votação</Link>
        </nav>
        <div className="header__profile">
          <div className="header__avatar">U</div>
        </div>
      </div>
    </header>
  );
};