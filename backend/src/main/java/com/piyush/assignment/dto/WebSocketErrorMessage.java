package com.piyush.assignment.dto;

public class WebSocketErrorMessage {

    private String puzzleId;
    private String error;

    public WebSocketErrorMessage(String puzzleId, String error) {
        this.puzzleId = puzzleId;
        this.error = error;
    }

    public String getPuzzleId() {
        return puzzleId;
    }

    public String getError() {
        return error;
    }
}
