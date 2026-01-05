import { useNavigate } from "react-router-dom";
import { useState } from "react";
import { createPuzzle } from "../api/puzzleApi";

export default function LobbyPage() {
  const [roomId, setRoomId] = useState("");
  const navigate = useNavigate();

  async function createRoom() {
    const puzzle = await createPuzzle();
    navigate(`/room/${puzzle.puzzleId}`);
  }

  return (
    <div className="min-h-screen flex items-center justify-center bg-neutral-50">
      <div className="w-full max-w-sm rounded-xl border border-neutral-200 bg-white p-6 space-y-6">
        <div className="space-y-1">
          <h1 className="text-xl font-semibold text-neutral-900">
            Logic Puzzle
          </h1>
          <p className="text-sm text-neutral-500">
            Create a room or join an existing one
          </p>
        </div>

        <div className="space-y-3">
          <input
            placeholder="Room ID"
            className="w-full h-11 rounded-md border border-neutral-300 px-3 text-sm focus:outline-none focus:ring-2 focus:ring-neutral-900"
            value={roomId}
            onChange={(e) => setRoomId(e.target.value)}
          />

          <button
            onClick={() => navigate(`/room/${roomId}`)}
            className="w-full h-11 rounded-md border border-neutral-300 text-sm font-medium text-neutral-900 hover:bg-neutral-100 transition"
          >
            Join room
          </button>

          <div className="w-full h-px bg-gray-200 "></div>

          <button
            onClick={createRoom}
            className="w-full h-11 rounded-md bg-neutral-900 text-white text-sm font-medium hover:bg-neutral-800 transition"
          >
            Create new room
          </button>
        </div>
      </div>
    </div>
  );
}
