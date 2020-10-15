package com.company.Board;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Board extends JPanel {
    private final int MIN_OBSTACLE_PERCENTAGE = 0;
    private final int MAX_OBSTACLE_PERCENTAGE = 50;

    private int numberOfRowTiles;
    private int numberOfColumnTiles;
    private int tileSize;
    private int obstaclePercentage;

    private ArrayList<Tile> tiles = new ArrayList<>();
    private Tile startTile;
    private Tile goalTile;

    public ArrayList<Tile> getTiles() {
        return this.tiles;
    }

    public int getMinObstaclePercentage() {
        return this.MIN_OBSTACLE_PERCENTAGE;
    }
    public int getMaxObstaclePercentage() {
        return this.MAX_OBSTACLE_PERCENTAGE;
    }

    public int getNumberOfRowTiles() {
        return numberOfRowTiles;
    }

    public int getNumberOfColumnTiles() {
        return numberOfColumnTiles;
    }

    public int getObstaclePercentage() {
        return this.obstaclePercentage;
    }

    public void setObstaclePercentage(int obstaclePercentage) {
        this.obstaclePercentage = obstaclePercentage;
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        for(Tile tile : this.tiles) {
            if(tile.isStart()) {
                g2d.setColor(Color.RED);
            } else if(tile.isGoal()) {
                g2d.setColor(Color.GREEN);
            } else if(tile.isObstacle()) {
                g2d.setColor(Color.BLACK);
            }else if(tile.isPathTile()) {
                g2d.setColor(Color.ORANGE);
            } else {
                g2d.setColor(Color.WHITE);
            }
            g2d.fillRect(tile.getxPosition() * tileSize, tile.getyPosition() * tileSize, tileSize, tileSize);
        }
    }

    private void generateStartTile() {
        Random random = new Random();
        int xPosition = random.nextInt(this.numberOfRowTiles);
        int yPosition = random.nextInt(this.numberOfColumnTiles);
        for(Tile tile : tiles) {
            if((!tile.isOccupied()) && (tile.getxPosition() == xPosition) && tile.getyPosition() == yPosition) {
                tile.setStart(true);
                tile.setOccupied(true);
                this.startTile = tile;
            }
        }
    }
    private void generateGoalTile() {
        Random random = new Random();
        int xPosition = random.nextInt(this.numberOfRowTiles);
        int yPosition = random.nextInt(this.numberOfColumnTiles);
        for(Tile tile : tiles) {
            if((!tile.isOccupied()) && (tile.getxPosition() == xPosition) && tile.getyPosition() == yPosition) {
                tile.setGoal(true);
                tile.setOccupied(true);
                this.goalTile = tile;
            }
        }
    }

    public void generateObstacleTiles() {
        Random random = new Random();
        int numberOfObstacles = Math.round((this.numberOfRowTiles * this.getNumberOfColumnTiles() * this.obstaclePercentage) / 100);
        int numberOfObstaclesGenerated = 0;
        do {
            if(numberOfObstacles == 0) {
                break;
            }
            int xPosition = random.nextInt(this.numberOfRowTiles);
            int yPosition = random.nextInt(this.numberOfColumnTiles);

            for(Tile tile : tiles) {
                if((!tile.isOccupied()) && (tile.getxPosition() == xPosition) && tile.getyPosition() == yPosition) {
                    tile.setOccupied(true);
                    tile.setObstacle(true);
                    numberOfObstaclesGenerated++;
                }
            }
        }while(numberOfObstaclesGenerated < numberOfObstacles);
    }

    public void updateBoard() {
        this.tiles.clear();
        for(int i = 0; i < this.numberOfRowTiles; i++) {
            for(int j = 0; j < this.numberOfColumnTiles; j++) {
                this.tiles.add(new Tile(i, j, false));
            }
        }
        generateStartTile();
        generateGoalTile();
        generateObstacleTiles();
    }

    public Board(int height, int width, int tileSize, int obstaclePercentage) {
        this.tileSize = tileSize;
        this.numberOfRowTiles = width / this.tileSize;
        this.numberOfColumnTiles = height / this.tileSize;
        this.obstaclePercentage = obstaclePercentage;
        for(int i = 0; i < this.numberOfRowTiles; i++) {
            for(int j = 0; j < this.numberOfColumnTiles; j++) {
                this.tiles.add(new Tile(i, j, false));
            }
        }
        generateStartTile();
        generateGoalTile();
        generateObstacleTiles();
    }

    public Tile getStartTile() {
        return startTile;
    }

    public void setStartTile(Tile startTile) {
        this.startTile = startTile;
    }

    public Tile getGoalTile() {
        return goalTile;
    }

    public void setGoalTile(Tile goalTile) {
        this.goalTile = goalTile;
    }

    public Tile getTileAt(int xPosition, int yPosition) {
        for(Tile tile : this.tiles) {
            if(tile.getyPosition() == yPosition && tile.getxPosition() == xPosition) {
                return tile;
            }
        }
        return null;
    }
}
