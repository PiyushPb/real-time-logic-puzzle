package com.piyush.assignment.service;

public class GridMapper {

    public static int[][] fromString(String grid) {
        try {
            String[] rows = grid.split(";");
            if (rows.length != 4) {
                throw new IllegalArgumentException("Grid must have 4 rows");
            }

            int[][] result = new int[4][4];

            for (int i = 0; i < 4; i++) {
                String[] cells = rows[i].split(",");
                if (cells.length != 4) {
                    throw new IllegalArgumentException("Each row must have 4 values");
                }

                for (int j = 0; j < 4; j++) {
                    result[i][j] = Integer.parseInt(cells[j]);
                }
            }
            return result;

        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Grid contains non-numeric values");
        }
    }

    public static String toString(int[][] grid) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                sb.append(grid[i][j]);
                if (j < 3) sb.append(",");
            }
            if (i < 3) sb.append(";");
        }
        return sb.toString();
    }
}
