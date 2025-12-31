import { useState } from "react";
import { createPaste } from "../api/pasteApi";
import ErrorBox from "./ErrorBox";

export default function CreatePaste() {
  const [content, setContent] = useState("");
  const [pasteUrl, setPasteUrl] = useState("");
  const [error, setError] = useState("");
  const [ttl, setTtl] = useState(60);
  const [maxViews, setMaxViews] = useState(5);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setPasteUrl("");

    const body = {
      content: content,
      ttl_seconds: Number(ttl),
      max_views: Number(maxViews)
    };

    if (!content.trim()) {
      setError("Paste content cannot be empty");
      return;
    }

    try {
      const data = await createPaste(body);
      setPasteUrl(`${window.location.origin}/p/${data.id}`);
      setContent("");
    } catch {
      setError("Failed to create paste");
    }
  };

  return (
    <div>
      <h2>Create a Paste</h2>

      <textarea
        rows="10"
        cols="60"
        value={content}
        onChange={(e) => setContent(e.target.value)}
        placeholder="Enter your paste content..."
        required
      />
      <input
        type="number"
        placeholder="TTL in seconds"
        value={ttl}
        onChange={(e) => setTtl(e.target.value)}
        min={1}
      />
      <input
        type="number"
        placeholder="Max Views"
        value={maxViews}
        onChange={(e) => setMaxViews(e.target.value)}
        min={1}
      />

      <br />
      <button onClick={handleSubmit}>Create Paste</button>

      {pasteUrl && (
        <p>
          Share link:{" "}
          <a href={pasteUrl} target="_blank">
            {pasteUrl}
          </a>
        </p>
      )}

      <ErrorBox message={error} />
    </div>
  );
}
