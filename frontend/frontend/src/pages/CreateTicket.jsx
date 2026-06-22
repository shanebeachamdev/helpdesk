import { useState } from "react";
import api from "../api/axios";
import "../CreateTicket.css";

export default function CreateTicket() {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const res = await api.post("/tickets", {
        title,
        description,
      });

      console.log("Ticket created:", res.data);

      setTitle("");
      setDescription("");
    } catch (err) {
      console.error("Failed to create ticket:", err);
    }
  };

  return (
    <div className="create-ticket-page">
      <div className="create-ticket-card">
        <h2>Create Ticket</h2>

        <form onSubmit={handleSubmit} className="create-ticket-form">
          <label>
            Title
            <input
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              placeholder="Enter a title"
              required
            />
          </label>

          <label>
            Description
            <textarea
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              placeholder="Describe the issue..."
              rows="5"
              required
            />
          </label>

          <button type="submit">Create Ticket</button>
        </form>
      </div>
    </div>
  );
}