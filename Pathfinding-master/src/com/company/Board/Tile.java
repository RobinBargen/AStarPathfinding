package com.company.Board;

public class Tile {
    private int xPosition;
    private int yPosition;
    private boolean isStart = false;
    private boolean isGoal = false;
    private boolean isOccupied = false;
    private boolean isObstacle = false;
    private boolean isPathTile = false;

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public Tile(int xPosition, int yPosition, boolean isOccupied) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.isOccupied = isOccupied;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    public boolean isGoal() {
        return isGoal;
    }

    public void setGoal(boolean goal) {
        isGoal = goal;
    }

    public boolean isObstacle() {
        return isObstacle;
    }

    public void setObstacle(boolean obstacle) {
        isObstacle = obstacle;
    }

    public boolean isPathTile() {
        return isPathTile;
    }

    public void setPathTile(boolean pathTile) {
        isPathTile = pathTile;
    }
}
