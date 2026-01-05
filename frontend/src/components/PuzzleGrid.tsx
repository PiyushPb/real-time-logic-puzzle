import { useDispatch, useSelector } from "react-redux";
import { optimisticUpdate } from "../store/puzzleSlice";
import { sendUpdate } from "../api/websocket";

export default function PuzzleGrid() {
  const dispatch = useDispatch();
  const { grid, puzzleId, version, role, isUpdating } = useSelector(
    (s) => s.puzzle
  );

  function updateCell(r, c, value) {
    if (role !== "player") return;
    if (isUpdating) return; // ⛔ prevent double-send

    const newGrid = grid.map((row) => [...row]);
    newGrid[r][c] = value;

    dispatch(optimisticUpdate(newGrid));

    sendUpdate({
      puzzleId,
      grid: newGrid.map((r) => r.join(",")).join(";"),
      version, // ✅ always latest confirmed version
    });
  }

  return (
    <div className="grid grid-cols-4 gap-2 w-max">
      {grid.map((row, r) =>
        row.map((cell, c) => (
          <input
            key={`${r}-${c}`}
            className="w-16 h-16 text-center border text-xl"
            value={cell === 0 ? "" : cell}
            disabled={role !== "player"}
            onChange={(e) => updateCell(r, c, Number(e.target.value || 0))}
          />
        ))
      )}
    </div>
  );
}
