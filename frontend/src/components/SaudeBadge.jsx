export default function SaudeBadge({ classificacao }) {
  const map = {
    SAUDAVEL: "badge success",
    ATENCAO: "badge warning",
    CRITICO: "badge danger",
  };

  return (
    <span className={map[classificacao] || "badge neutral"}>
      {classificacao}
    </span>
  );
}
