import React from 'react';
import './Card.scss';

interface CardProps {
  children: React.ReactNode;
  padding?: 'sm' | 'md' | 'lg';
  className?: string;
}

export const Card: React.FC<CardProps> = ({ 
  children, 
  padding = 'md',
  className = '' 
}) => {
  return (
    <div className={`card card--${padding} ${className}`}>
      {children}
    </div>
  );
};