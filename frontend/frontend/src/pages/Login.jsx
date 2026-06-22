import { useState, useContext } from "react";
import { useNavigate, Link } from "react-router-dom";
import { AuthContext } from "../context/AuthContext";
import api from "../api/axios";

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const { login } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await api.post("/auth/login", {
      email,
      password,
    });

    console.log("LOGIN RESPONSE:", response.data);

    login(response.data.token);

    console.log("TOKEN SAVED");

    navigate("/");
    } catch (err) {
      setError("Invalid email or password");
      console.error(err);
    }
  };

  return (
    <div>
      <h1>HelpDesk Login</h1>

      {error && <p>{error}</p>}

      <form onSubmit={handleSubmit}>
        <div>
          <label>Email</label>
          <br />
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>

        <br />

        <div>
          <label>Password</label>
          <br />
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>

        <br />

        <button type="submit">
          Login
        </button>
      </form>

      <br />

      <Link to="/register">
        Need an account?
      </Link>
    </div>
  );
}

export default Login;