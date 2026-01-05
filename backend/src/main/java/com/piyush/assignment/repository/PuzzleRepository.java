package com.piyush.assignment.repository;

import com.piyush.assignment.model.Puzzle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PuzzleRepository extends JpaRepository<Puzzle, String> {
}
