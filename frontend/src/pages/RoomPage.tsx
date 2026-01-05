import { useParams } from "react-router-dom";
import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { connectWS } from "../api/websocket";
import {
  setPuzzle,
  confirmedUpdate,
  rollback,
  setPlayers,
} from "../store/puzzleSlice";
import { getPuzzle } from "../api/puzzleApi";
import PuzzleGrid from "../components/PuzzleGrid";

export default function RoomPage() {
  const { roomId } = useParams();
  const dispatch = useDispatch();
  const puzzle = useSelector((s) => s.puzzle);

  useEffect(() => {
    async function init() {
      const data = await getPuzzle(roomId);

      dispatch(
        setPuzzle({
          puzzleId: data.puzzleId,
          grid: data.grid.split(";").map((r) => r.split(",").map(Number)),
          version: data.version,
        })
      );

      connectWS(
        roomId,
        (msg) =>
          dispatch(
            confirmedUpdate({
              grid: msg.grid.split(";").map((r) => r.split(",").map(Number)),
              version: msg.version,
            })
          ),
        (err) => dispatch(rollback(err)),
        (count) => dispatch(setPlayers(count))
      );
    }

    init();
  }, [roomId, dispatch]);

  return (
    <div className="min-h-screen bg-neutral-50">
      <div className="max-w-5xl mx-auto px-4 py-6 space-y-6">
        <header className="flex items-start justify-between">
          <div className="space-y-0.5">
            <h1 className="text-lg font-semibold text-neutral-900">
              Logic Puzzle Room
            </h1>
            <p className="text-xs text-neutral-500">Room ID · {roomId}</p>
          </div>

          <div className="text-right text-sm text-neutral-600">
            <div>Players: {puzzle.players}</div>
            <div>
              Role:{" "}
              <span className="font-medium text-neutral-900">
                {puzzle.role}
              </span>
            </div>
          </div>
        </header>

        <div className="rounded-lg border border-neutral-200 bg-white p-4">
          <PuzzleGrid />
        </div>

        {puzzle.error && (
          <div className="rounded-md border border-red-200 bg-red-50 px-3 py-2 text-sm text-red-700">
            {puzzle.error}
          </div>
        )}
      </div>
    </div>
  );
}
