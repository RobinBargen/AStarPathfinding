package com.company.Problem;

import com.company.Board.Board;
import com.company.Node.Node;

import java.util.ArrayList;

public class ProblemSolvingAgent {
    private Node initialState;
    private Node goalState;
    private Board board;



    private void initStartState() {
        this.initialState = new Node();
        this.initialState.setTile(this.board.getStartTile());
        this.initialState.setParent(null);
        this.initialState.setDepth(0);
        this.initialState.setHeuristics(this.initialState.getTile().getxPosition(), this.initialState.getTile().getyPosition(), this.board.getGoalTile().getxPosition(), this.board.getGoalTile().getyPosition());
    }

    private void initGoalState() {
        this.goalState = new Node();
        this.goalState.setTile(this.board.getGoalTile());
        this.goalState.setParent(null);
        this.goalState.setDepth(0);
        this.goalState.setHeuristics(this.goalState.getTile().getxPosition(), this.goalState.getTile().getyPosition(), this.board.getGoalTile().getxPosition(), this.board.getGoalTile().getyPosition());
    }

    private void initProblem(Board board) {
        this.board = board;
        initStartState();
        initGoalState();
    }

    private void addChildren(Node node) {
        ArrayList<Node> children = new ArrayList<>();
        for(int i = -1; i < 2; i++) {
            for(int j = -1; j < 2; j++) {
                int xPosition = node.getTile().getxPosition() + i;
                int yPosition = node.getTile().getyPosition() + j;

                if(xPosition == node.getTile().getxPosition() && yPosition == node.getTile().getyPosition()) continue;

                if((xPosition >= 0) && (yPosition >= 0) && (xPosition < this.board.getNumberOfRowTiles()) && (yPosition < this.board.getNumberOfColumnTiles())) {
                    Node child = new Node();
                    child.setTile(this.board.getTileAt(xPosition, yPosition));
                    child.setParent(node);
                    child.setDepth(node.getDepth() + 1);
                    child.setHeuristics(this.board.getStartTile().getxPosition(), this.board.getStartTile().getyPosition(), this.board.getGoalTile().getxPosition(), this.board.getGoalTile().getyPosition());
                    children.add(child);
                }
            }
        }
        node.setChildren(children);
    }

    private int getBestNeighbourIndex(ArrayList<Node> openSet) {
        int bestIndex = Integer.MIN_VALUE;
        int bestHeuristicCost = Integer.MIN_VALUE;
        for(Node node : openSet) {
            if(bestHeuristicCost == Integer.MIN_VALUE) {
                bestHeuristicCost = node.getHeuristicValue();
                bestIndex = openSet.indexOf(node);
                continue;
            }

            int currentHeuristicCost = node.getHeuristicValue();
            if(currentHeuristicCost < bestHeuristicCost) {
                bestHeuristicCost = currentHeuristicCost;
                bestIndex = openSet.indexOf(node);
            }
        }
        return bestIndex;
    }

    private void search() {
        ArrayList<Node> openSet = new ArrayList<>();
        ArrayList<Node> closedSet = new ArrayList<>();

        openSet.add(this.initialState);

        while(true) {
            int currentNodeIndex = getBestNeighbourIndex(openSet);
            Node currentNode = openSet.get(currentNodeIndex);
            openSet.remove(currentNodeIndex);
            closedSet.add(currentNode);

            if(currentNode.getTile().getxPosition() == this.goalState.getTile().getxPosition() && currentNode.getTile().getyPosition() == this.goalState.getTile().getyPosition()) {
                this.goalState = currentNode;
                return;
            }

            addChildren(currentNode);

            int bestNeighbouringHeuristic = Integer.MIN_VALUE;
            for(Node child : currentNode.getChildren()) {
                if(child.getTile().isObstacle() || closedSet.contains(child)) {
                    continue;
                }
                if(child.getHeuristicValue() < bestNeighbouringHeuristic || !openSet.contains(child)) {
                    bestNeighbouringHeuristic = child.getHeuristicValue();
                    child.setParent(currentNode);
                    if(!openSet.contains(child)) {
                        openSet.add(child);
                    }
                }
            }
        }
    }

    public void solveProblem(Board board) throws Exception{
        this.initialState = null;
        this.goalState = null;
        this.board = null;

        this.initProblem(board);
        this.search();

        boolean hasParent = true;
        Node currentNode = this.goalState;
        do {
            Node parent = currentNode.getParent();
            if(parent == null) {
                hasParent = false;
            } else {
                parent.getTile().setPathTile(true);
                currentNode = parent;
            }
        }while(hasParent);
    }

    public ProblemSolvingAgent(Board board) throws Exception{
        this.solveProblem(board);
    }


}
