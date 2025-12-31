export default function ErrorBox({ message }) {
  if (!message) return null;

  return (
    <div style={{ color: "red", marginTop: "10px" }}>
      {message}
    </div>
  );
}
