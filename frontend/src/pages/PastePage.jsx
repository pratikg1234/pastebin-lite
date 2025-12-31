import { useParams } from "react-router-dom";
import ViewPaste from "../components/ViewPaste";

export default function PastePage() {
  const { id } = useParams();
  return <ViewPaste id={id} />;
}
