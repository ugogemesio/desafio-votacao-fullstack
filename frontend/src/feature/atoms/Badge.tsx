import React from 'react';
import './Badge.scss';

interface BadgeProps {
  variant: 'success' | 'warning' | 'error' | 'info' | 'neutral';
  children: React.ReactNode;
}

export const Badge: React.FC<BadgeProps> = ({ variant, children }) => {
  return <span className={`badge badge--${variant}`}>{children}</span>;
};