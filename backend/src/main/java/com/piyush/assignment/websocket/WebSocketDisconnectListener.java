package com.piyush.assignment.websocket;

import com.piyush.assignment.model.Puzzle;
import com.piyush.assignment.service.PuzzleService;
import com.piyush.assignment.service.RoomSessionService;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketDisconnectListener {

    private final PuzzleService puzzleService;
    private final RoomSessionService roomSessionService;

    public WebSocketDisconnectListener(
            PuzzleService puzzleService,
            RoomSessionService roomSessionService
    ) {
        this.puzzleService = puzzleService;
        this.roomSessionService = roomSessionService;
    }

    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();

        String puzzleId = roomSessionService.leaveRoom(sessionId);
        if (puzzleId == null) return;

        Puzzle puzzle = puzzleService.getPuzzle(puzzleId);
        puzzle.playerLeft();
        puzzleService.save(puzzle);
    }
}
