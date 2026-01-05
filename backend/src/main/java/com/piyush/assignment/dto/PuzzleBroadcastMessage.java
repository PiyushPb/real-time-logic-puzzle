package com.piyush.assignment.dto;

public class PuzzleBroadcastMessage {

    private String puzzleId;
    private String grid;
    private Long version;

    public PuzzleBroadcastMessage(String puzzleId, String grid, Long version) {
        this.puzzleId = puzzleId;
        this.grid = grid;
        this.version = version;
    }

    public String getPuzzleId() {
        return puzzleId;
    }

    public String getGrid() {
        return grid;
    }

    public Long getVersion() {
        return version;
    }
}
