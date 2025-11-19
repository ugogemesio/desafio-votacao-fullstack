
import React from 'react';
import './Footer.scss';

export const Footer: React.FC = () => {
  return (
    <footer className="footer">
      <div className="footer__container">
        <div className="footer__content">
          <p className="footer__text">
            Desenvolvido como desafio técnico
          </p>
          <div className="footer__links">
            <a href="https://github.com/ugogemesio/desafio-votacao-fullstack" className="footer__link">GitHub</a>
          </div>
        </div>
      </div>
    </footer>
  );
};