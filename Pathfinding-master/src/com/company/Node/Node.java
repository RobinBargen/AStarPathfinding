package com.company.Node;

import com.company.Board.Tile;

import java.util.ArrayList;
import java.util.Comparator;

public class Node implements Comparable, Comparator<Node> {
    private Node parent;
    private ArrayList<Node> children;
    private int depth;

    private int g;
    private int h;
    private int f;

    private Tile tile;
    private boolean isClosed = false;

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }


    public ArrayList<Node> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Node> children) { this.children = children; }

    public int getDepth() {
        return depth;
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getHeuristicValue() {
        return this.f;
    }

    public void setHeuristics(int startXPos, int startYPos, int goalXPos, int goalYPos) {
        this.g = ((int) Math.sqrt(Math.pow(this.tile.getxPosition() - startXPos, 2) + Math.pow(this.tile.getyPosition() - startYPos, 2)) * 10);
        this.h = ((int) Math.sqrt(Math.pow(goalXPos - this.tile.getxPosition(), 2) + Math.pow(goalYPos - this.tile.getyPosition(), 2)) * 10);
        this.f = this.g + this.h;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof Node) {
            return Integer.compare(this.getHeuristicValue(), ((Node) o).getHeuristicValue());
        }
        return 0;
    }

    @Override
    public int compare(Node o1, Node o2) {
        if((o1.getTile().getxPosition() == o2.getTile().getxPosition()) && (o1.getTile().getyPosition() == o2.getTile().getyPosition())){
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Node){
            Node toCompare = (Node) o;
            return toCompare.getTile().getxPosition() == this.getTile().getxPosition() && toCompare.getTile().getyPosition() == this.getTile().getyPosition();
        }
        return false;
    }

}
