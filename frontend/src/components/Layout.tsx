
import { Outlet } from "react-router-dom";


const Layout = () => {
  return (
    <>
      
      <main style={{ padding: "1rem" }}>
        <Outlet />
      </main>
    </>
  );
};

export default Layout;
