import { Link } from "react-router-dom";

export default function Dashboard() {
  return (
    <div>
      <h1>Help Desk Dashboard</h1>

      <Link to="/create-ticket">
        Create Ticket
      </Link>

      <br />
      <br />

      <Link to="/tickets">
        View Tickets
      </Link>
    </div>
  );
}