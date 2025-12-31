import { useEffect, useState } from "react";
import { getPasteById } from "../api/pasteApi";
import ErrorBox from "./ErrorBox";

export default function ViewPaste({ id }) {
  const [content, setContent] = useState("");
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchPaste = async () => {
      try {
        const data = await getPasteById(id);
        setContent(data.content);
      } catch {
        setError("Paste not found or expired");
      }
    };

    fetchPaste();
  }, [id]);

  return (
    <div>
      <h2>View Paste</h2>

      {content && (
        <pre style={{ background: "#f5f5f5", padding: "10px" }}>
          {content}
        </pre>
      )}

      <ErrorBox message={error} />
    </div>
  );
}
