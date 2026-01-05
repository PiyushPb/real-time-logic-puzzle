package com.piyush.assignment.service;

import com.piyush.assignment.model.Puzzle;
import com.piyush.assignment.repository.PuzzleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PuzzleService {

    private final PuzzleRepository puzzleRepository;

    public PuzzleService(PuzzleRepository puzzleRepository) {
        this.puzzleRepository = puzzleRepository;
    }

    @Transactional
    public Puzzle createEmptyPuzzle() {
        String emptyGrid = "0,0,0,0;0,0,0,0;0,0,0,0;0,0,0,0";
        return puzzleRepository.save(new Puzzle(emptyGrid));
    }

    @Transactional(readOnly = true)
    public Puzzle getPuzzle(String puzzleId) {
        return puzzleRepository.findById(puzzleId)
                .orElseThrow(() -> new IllegalArgumentException("Puzzle not found"));
    }

    @Transactional
    public Puzzle save(Puzzle puzzle) {
        return puzzleRepository.save(puzzle);
    }

    @Transactional
    public Puzzle updateGridFromWebSocket(String puzzleId, String newGrid) {
        Puzzle puzzle = puzzleRepository.findById(puzzleId)
                .orElseThrow(() -> new IllegalArgumentException("Puzzle not found"));

        int[][] gridMatrix = GridMapper.fromString(newGrid);
        PuzzleValidator.validateGrid(gridMatrix);

        puzzle.updateGrid(newGrid);
        return puzzleRepository.save(puzzle);
    }
}
