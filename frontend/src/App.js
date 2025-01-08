import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import AppLayout from "./components/layout/AppLayout";
import DashboardPage from "./pages/DashboardPage";
import IngredientsPage from "./pages/IngredientsPage";
import FormulasPage from "./pages/FormulasPage";
import AllergensPage from "./pages/AllergensPage";

const App = () => {
  return (
    <Router future={{ v7_startTransition: true, v7_relativeSplatPath: true }}>
      <AppLayout>
        <Routes>
          <Route path="/dashboard" element={<DashboardPage />} />
          <Route path="/formulas" element={<FormulasPage />} />
          <Route path="/ingredients" element={<IngredientsPage />} />
          <Route path="/allergens" element={<AllergensPage />} />
        </Routes>
      </AppLayout>
    </Router>
  );
};

export default App;
