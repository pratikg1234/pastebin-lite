import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import PastePage from "./pages/PastePage";

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/p/:id" element={<PastePage />} />
      </Routes>
    </BrowserRouter>
  );
}
