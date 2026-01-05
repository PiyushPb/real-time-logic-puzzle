package com.piyush.assignment.dto;

public class CreatePuzzleResponse {

    private String puzzleId;
    private String grid;
    private Long version;

    public CreatePuzzleResponse(String puzzleId, String grid, Long version) {
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
