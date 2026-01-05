const BASE_URL = "http://localhost:8080/api/puzzles";

export async function createPuzzle() {
  const res = await fetch(BASE_URL, { method: "POST" });
  return res.json();
}

export async function getPuzzle(id) {
  const res = await fetch(`${BASE_URL}/${id}`);
  return res.json();
}
