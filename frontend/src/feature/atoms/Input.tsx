import React from 'react';
import './Input.scss';

interface InputProps {
  type?: 'text' | 'number' | 'email' | 'password';
  placeholder?: string;
  value?: string | number;
  onChange?: (value: string) => void;
  disabled?: boolean;
  error?: string;
  label?: string;
  autoFocus?: boolean;
  min?: string | number;
  max?: string | number;
}

export const Input: React.FC<InputProps> = ({
  type = 'text',
  placeholder,
  value,
  onChange,
  disabled = false,
  error,
  label,
  autoFocus = false,
  min,
  max
}) => {
  return (
    <div className="input-container">
      {label && <label className="input-label">{label}</label>}
      <input
        type={type}
        className={`input ${error ? 'input--error' : ''}`}
        placeholder={placeholder}
        value={value}
        onChange={(e) => onChange?.(e.target.value)}
        disabled={disabled}
        autoFocus={autoFocus}
        min={min}
        max={max}
      />
      {error && <span className="input-error">{error}</span>}
    </div>
  );
};