package com.piyush.assignment.dto;

public class PuzzleUpdateMessage {

    private String puzzleId;
    private String grid;
    private Long version;

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
