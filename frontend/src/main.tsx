import { createRoot } from 'react-dom/client'
import './index.scss'
import App from './App.tsx'
import { QueryProvider } from "./providers/QueryProvider";
import { BrowserRouter } from 'react-router-dom';
import './main.css';
import './App.scss';

createRoot(document.getElementById('root')!).render(

  <BrowserRouter>
    <QueryProvider>
      <App />
    </QueryProvider>
  </BrowserRouter>
)
