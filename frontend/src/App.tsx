import './App.css'
import { AppRoutes } from './routes';

import { Header } from './components/Header';
import { Footer } from './components/Footer';
function App() {


  return (
    <>
      {/* <SessoesPage></SessoesPage>
      <PautasPage></PautasPage>*/}
      <Header></Header>
      <AppRoutes></AppRoutes>
      <Footer></Footer>
      {/* <AssociadosPage></AssociadosPage> */}
      {/* <VotarPage></VotarPage> */}
      
    </>
  )
}

export default App;
