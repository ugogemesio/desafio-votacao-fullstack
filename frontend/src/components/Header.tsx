import React from 'react';
import './Header.scss';

export const Header: React.FC = () => {
  return (
    <header className="header">
      <div className="header__container">
        <div className="header__logo">
          <h1>Sistema de Votação Cooperativa</h1>
        </div>
        <nav className="header__nav">
          <a href="/pautas" className="header__nav-link">Pautas</a>
          <a href="/sessoes" className="header__nav-link">Sessões de Votação</a>
        </nav>
        <div className="header__profile">
          <div className="header__avatar">U</div>
        </div>
      </div>
    </header>
  );
};