import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  puzzleId: null,
  grid: [],
  version: null,
  role: "spectator",
  players: 0,
  error: null,
  lastStableGrid: null,
  isUpdating: false,
};

const puzzleSlice = createSlice({
  name: "puzzle",
  initialState,
  reducers: {
    setPuzzle(state, action) {
      Object.assign(state, action.payload);
      state.lastStableGrid = action.payload.grid;
      state.error = null;
      state.isUpdating = false;
    },

    optimisticUpdate(state, action) {
      state.lastStableGrid = state.grid;
      state.grid = action.payload;
      state.isUpdating = true;
    },

    confirmedUpdate(state, action) {
      state.grid = action.payload.grid;
      state.version = action.payload.version; // ✅ VERY IMPORTANT
      state.lastStableGrid = action.payload.grid;
      state.error = null;
      state.isUpdating = false;
    },

    rollback(state, action) {
      state.grid = state.lastStableGrid;
      state.error = action.payload;
      state.isUpdating = false;
    },

    setPlayers(state, action) {
      state.players = action.payload;
      state.role = action.payload <= 2 ? "player" : "spectator";
    },
  },
});

export const {
  setPuzzle,
  optimisticUpdate,
  confirmedUpdate,
  rollback,
  setPlayers,
} = puzzleSlice.actions;

export default puzzleSlice.reducer;
