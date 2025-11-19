import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.tsx'
import { QueryProvider } from "./providers/QueryProvider";
import { BrowserRouter } from 'react-router-dom';

createRoot(document.getElementById('root')!).render(

  <BrowserRouter>
    <QueryProvider>
      <App />
    </QueryProvider>
  </BrowserRouter>
)
