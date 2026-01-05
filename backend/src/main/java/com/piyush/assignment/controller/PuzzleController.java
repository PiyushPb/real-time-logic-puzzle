package com.piyush.assignment.controller;

import com.piyush.assignment.dto.CreatePuzzleResponse;
import com.piyush.assignment.dto.UpdatePuzzleRequest;
import com.piyush.assignment.model.Puzzle;
import com.piyush.assignment.service.PuzzleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/puzzles")
public class PuzzleController {

    private final PuzzleService puzzleService;

    public PuzzleController(PuzzleService puzzleService) {
        this.puzzleService = puzzleService;
    }

    @PostMapping
    public CreatePuzzleResponse createPuzzle() {
        Puzzle puzzle = puzzleService.createEmptyPuzzle();
        return new CreatePuzzleResponse(
                puzzle.getId(),
                puzzle.getGrid(),
                puzzle.getVersion()
        );
    }

    @GetMapping("/{id}")
    public CreatePuzzleResponse getPuzzle(@PathVariable String id) {
        Puzzle puzzle = puzzleService.getPuzzle(id);
        return new CreatePuzzleResponse(
                puzzle.getId(),
                puzzle.getGrid(),
                puzzle.getVersion()
        );
    }

    @PutMapping("/{id}")
    public CreatePuzzleResponse updatePuzzle(
            @PathVariable String id,
            @RequestBody UpdatePuzzleRequest request
    ) {
        Puzzle updated = puzzleService.updateGridFromWebSocket(
                id,
                request.getGrid()
        );

        return new CreatePuzzleResponse(
                updated.getId(),
                updated.getGrid(),
                updated.getVersion()
        );
    }
}
