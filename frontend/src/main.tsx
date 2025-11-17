import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.tsx'
import { QueryProvider } from "./providers/QueryProvider";


createRoot(document.getElementById('root')!).render(
  <QueryProvider>
    <App />
  </QueryProvider>
)
