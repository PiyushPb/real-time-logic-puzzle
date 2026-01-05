package com.piyush.assignment.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "puzzles")
public class Puzzle {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /**
     * 4x4 grid stored as:
     * "0,0,0,0;0,0,0,0;0,0,0,0;0,0,0,0"
     */
    @Column(nullable = false, length = 64)
    private String grid;

    @Column(nullable = false)
    private int activePlayers = 0;

    @Column(nullable = false)
    private int maxPlayers = 2;


    @Version
    private Long version;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    protected Puzzle() {
        // JPA only
    }


    public Puzzle(String grid) {
        this.grid = grid;
        this.createdAt = Instant.now();
    }

    public String getId() {
        return id;
    }

    public String getGrid() {
        return grid;
    }

    public void updateGrid(String newGrid) {
        this.grid = newGrid;
    }

    public Long getVersion() {
        return version;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public boolean canJoin() {
        return activePlayers < maxPlayers;
    }

    public void playerJoined() {
        this.activePlayers++;
    }

    public void playerLeft() {
        if (this.activePlayers > 0) {
            this.activePlayers--;
        }
    }

    public int getActivePlayers() {
        return activePlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }
}
