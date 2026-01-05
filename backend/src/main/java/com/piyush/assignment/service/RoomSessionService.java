package com.piyush.assignment.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RoomSessionService {

    // sessionId -> puzzleId
    private final Map<String, String> sessionToPuzzle = new ConcurrentHashMap<>();

    public void joinRoom(String sessionId, String puzzleId) {
        sessionToPuzzle.put(sessionId, puzzleId);
    }

    public String leaveRoom(String sessionId) {
        return sessionToPuzzle.remove(sessionId);
    }

    public boolean isInRoom(String sessionId) {
        return sessionToPuzzle.containsKey(sessionId);
    }
}
