package com.piyush.assignment.service;

import java.util.HashSet;
import java.util.Set;

public class PuzzleValidator {

    private static final int SIZE = 4;

    /**
     * Validates entire grid
     */
    public static void validateGrid(int[][] grid) {
        validateShape(grid);
        validateValues(grid);
        validateRows(grid);
        validateColumns(grid);
    }

    private static void validateShape(int[][] grid) {
        if (grid.length != SIZE) {
            throw new IllegalArgumentException("Grid must have 4 rows");
        }
        for (int[] row : grid) {
            if (row.length != SIZE) {
                throw new IllegalArgumentException("Each row must have 4 columns");
            }
        }
    }

    private static void validateValues(int[][] grid) {
        for (int[] row : grid) {
            for (int value : row) {
                if (value < 0 || value > 4) {
                    throw new IllegalArgumentException("Cell values must be between 0 and 4");
                }
            }
        }
    }

    private static void validateRows(int[][] grid) {
        for (int row = 0; row < SIZE; row++) {
            Set<Integer> seen = new HashSet<>();
            for (int col = 0; col < SIZE; col++) {
                int value = grid[row][col];
                if (value == 0) continue;
                if (!seen.add(value)) {
                    throw new IllegalArgumentException(
                            "Duplicate value " + value + " found in row " + row
                    );
                }
            }
        }
    }

    private static void validateColumns(int[][] grid) {
        for (int col = 0; col < SIZE; col++) {
            Set<Integer> seen = new HashSet<>();
            for (int row = 0; row < SIZE; row++) {
                int value = grid[row][col];
                if (value == 0) continue;
                if (!seen.add(value)) {
                    throw new IllegalArgumentException(
                            "Duplicate value " + value + " found in column " + col
                    );
                }
            }
        }
    }
}
