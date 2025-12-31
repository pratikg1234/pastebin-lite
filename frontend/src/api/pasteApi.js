import axios from "axios";

const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
});

export const createPaste = async (body) => {
  const response = await api.post("/api/pastes", body );
  return response.data;
};

export const getPasteById = async (id) => {
  const response = await api.get(`/api/pastes/${id}`);
  return response.data;
};
