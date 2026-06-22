import { useEffect, useState } from "react";
import api from "../api/axios";

export default function Tickets() {
  const [tickets, setTickets] = useState([]);

  useEffect(() => {
    loadTickets();
  }, []);

  const loadTickets = async () => {
    try {
      const res = await api.get("/tickets");
      setTickets(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  const updateStatus = async (id, status) => {
    try {
      await api.put(
        `/tickets/${id}/status?status=${status}`
      );

      loadTickets();
    } catch (err) {
      console.error(err);
    }
  };

  const getStatusColor = (status) => {
    switch (status) {
        case "OPEN":
            return "red";

        case "IN_PROGRESS":
            return "orange";

        case "RESOLVED":
            return "green";

        default:
            return "black";
        }
    };

  return (
    <div>
      <h1>Tickets</h1>

      {tickets.map((ticket) => (
        <div
          key={ticket.id}
          style={{
            border: "1px solid #ccc",
            padding: "1rem",
            marginBottom: "1rem",
          }}
        >
          <h3>{ticket.title}</h3>

          <p>{ticket.description}</p>

          <strong style={{ color: getStatusColor(ticket.status)}}>
            Status: {ticket.status}
          </strong>

          <br />
          <br />

          <button
            onClick={() =>
              updateStatus(ticket.id, "OPEN")
            }
          >
            Open
          </button>

          <button
            onClick={() =>
              updateStatus(ticket.id, "IN_PROGRESS")
            }
          >
            In Progress
          </button>

          <button
            onClick={() =>
              updateStatus(ticket.id, "RESOLVED")
            }
          >
            Resolved
          </button>
        </div>
      ))}
    </div>
  );
}