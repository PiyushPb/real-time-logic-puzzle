package com.piyush.assignment.websocket;

import com.piyush.assignment.dto.PuzzleBroadcastMessage;
import com.piyush.assignment.dto.PuzzleUpdateMessage;
import com.piyush.assignment.dto.WebSocketErrorMessage;
import com.piyush.assignment.model.Puzzle;
import com.piyush.assignment.service.PuzzleService;
import com.piyush.assignment.service.RoomSessionService;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class PuzzleWebSocketController {

    private final PuzzleService puzzleService;
    private final SimpMessagingTemplate messagingTemplate;
    private final RoomSessionService roomSessionService;

    // ✅ SINGLE constructor — all fields initialized
    public PuzzleWebSocketController(
            PuzzleService puzzleService,
            SimpMessagingTemplate messagingTemplate,
            RoomSessionService roomSessionService
    ) {
        this.puzzleService = puzzleService;
        this.messagingTemplate = messagingTemplate;
        this.roomSessionService = roomSessionService;
    }

    // ======================
    // JOIN ROOM
    // ======================
    @MessageMapping("/puzzle/join")
    public void joinPuzzleRoom(
            @Header("simpSessionId") String sessionId,
            @Payload String puzzleId
    ) {
        Puzzle puzzle = puzzleService.getPuzzle(puzzleId);

        if (roomSessionService.isInRoom(sessionId)) {
            return;
        }

        if (!puzzle.canJoin()) {
            messagingTemplate.convertAndSend(
                    "/topic/puzzles/" + puzzleId + "/errors",
                    new WebSocketErrorMessage(puzzleId, "Room is full")
            );
            return;
        }

        puzzle.playerJoined();
        puzzleService.save(puzzle);
        roomSessionService.joinRoom(sessionId, puzzleId);

        messagingTemplate.convertAndSend(
                "/topic/puzzles/" + puzzleId + "/players",
                puzzle.getActivePlayers()
        );

        messagingTemplate.convertAndSend(
                "/topic/puzzles/" + puzzleId,
                new PuzzleBroadcastMessage(
                        puzzle.getId(),
                        puzzle.getGrid(),
                        puzzle.getVersion()
                )
        );
    }


    // ======================
    // UPDATE GRID
    // ======================
    @MessageMapping("/puzzle/update")
    public void handlePuzzleUpdate(
            @Header("simpSessionId") String sessionId,
            @Payload PuzzleUpdateMessage message
    ) {
        try {
            if (!roomSessionService.isInRoom(sessionId)) {
                throw new IllegalStateException("Join the room before playing");
            }

            Puzzle puzzle = puzzleService.getPuzzle(message.getPuzzleId());
            if (puzzle.getActivePlayers() > puzzle.getMaxPlayers()) {
                throw new IllegalStateException("Spectators cannot make moves");
            }

            Puzzle updatedPuzzle = puzzleService.updateGridFromWebSocket(
                    message.getPuzzleId(),
                    message.getGrid()
            );

            messagingTemplate.convertAndSend(
                    "/topic/puzzles/" + updatedPuzzle.getId(),
                    new PuzzleBroadcastMessage(
                            updatedPuzzle.getId(),
                            updatedPuzzle.getGrid(),
                            updatedPuzzle.getVersion()
                    )
            );

        } catch (IllegalArgumentException | IllegalStateException ex) {
            messagingTemplate.convertAndSend(
                    "/topic/puzzles/" + message.getPuzzleId() + "/errors",
                    new WebSocketErrorMessage(
                            message.getPuzzleId(),
                            ex.getMessage()
                    )
            );
        }
    }

}