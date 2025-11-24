import './App.scss'
import { AppRoutes } from './routes';
import { Header } from './components/Header';
import { Footer } from './components/Footer';
function App() {


  return (
    <>
      <Header></Header>
      <AppRoutes></AppRoutes>
      <Footer></Footer>
    </>
  )
}

export default App;
