import { BrowserRouter, Routes, Route } from "react-router-dom";

import api from "./api/axios";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";
import Tickets from "./pages/Tickets";
import CreateTicket from "./pages/CreateTicket";

import ProtectedRoute from "./components/ProtectedRoute";

function App() {
  return (
    <BrowserRouter>
      <Routes>

        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />

        <Route
          path="/"
          element={
            <ProtectedRoute>
              <Dashboard />
            </ProtectedRoute>
          }
        />

        <Route
          path="/tickets"
          element={
            <ProtectedRoute>
              <Tickets />
            </ProtectedRoute>
          }
        />

        <Route
          path="/create-ticket"
          element={
            <ProtectedRoute>
              <CreateTicket />
            </ProtectedRoute>
          }
        />

      </Routes>
    </BrowserRouter>
  );
}

export default App;