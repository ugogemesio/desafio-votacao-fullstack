import React from 'react';
import './Button.scss';

interface ButtonProps {
  variant?: 'primary' | 'secondary' | 'danger' | 'success';
  size?: 'sm' | 'md' | 'lg';
  disabled?: boolean;
  onClick?: () => void;
  children: React.ReactNode;
  type?: 'button' | 'submit' | 'reset';
  fullWidth?: boolean;
  loading?: boolean;
}

export const Button: React.FC<ButtonProps> = ({
  variant = 'primary',    
  size = 'md',
  disabled = false,
  onClick,
  children,
  type = 'button',
  fullWidth = false,
  loading = false
}) => {
  return (
    <button
      type={type}
      className={`btn btn--${variant} btn--${size} ${fullWidth ? 'btn--full-width' : ''} ${loading ? 'btn--loading' : ''}`}
      disabled={disabled || loading}
      onClick={onClick}
    >
      {loading && <span className="btn__spinner"></span>}
      {children}
    </button>
  );
};