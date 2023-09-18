package org.week2jd14.algorithms;

import org.week2jd14.utils.Timing;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EightPuzzleSolver {
    private static final int[][] GOAL_STATE = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}}; // Goal state
    private static final int[][] INITIAL_STATE = {{1, 2, 3}, {0, 4, 6}, {7, 5, 8}}; // Initial state
    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Possible move directions

    // A* Search
    private static Node solvePuzzle() {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(Node::getCost));
        Set<String> closedSet = new HashSet<>();

        Node initialNode = new Node(EightPuzzleSolver.INITIAL_STATE, 0, calculateHeuristic(EightPuzzleSolver.INITIAL_STATE), null);
        openSet.add(initialNode);

        while (!openSet.isEmpty()) {
            Node currentNode = openSet.poll();

            if (Arrays.deepEquals(currentNode.getState(), GOAL_STATE)) {
                return currentNode; // Solution found
            }

            closedSet.add(Arrays.deepToString(currentNode.getState()));

            for (int[] dir : DIRECTIONS) {
                int newX = currentNode.getBlankX() + dir[0];
                int newY = currentNode.getBlankY() + dir[1];

                if (isValid(newX, newY)) {
                    int[][] newState = cloneState(currentNode.getState());
                    swap(newState, currentNode.getBlankX(), currentNode.getBlankY(), newX, newY);

                    if (!closedSet.contains(Arrays.deepToString(newState))) {
                        Node newNode = new Node(newState, currentNode.getCost() + 1, calculateHeuristic(newState), currentNode);
                        openSet.add(newNode);
                    }
                }
            }
        }

        return null; // No solution found
    }

    private static boolean isValid(int x, int y) {
        return x >= 0 && x < 3 && y >= 0 && y < 3;
    }

    private static int[][] cloneState(int[][] state) {
        int[][] newState = new int[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(state[i], 0, newState[i], 0, 3);
        }
        return newState;
    }

    private static void swap(int[][] state, int x1, int y1, int x2, int y2) {
        int temp = state[x1][y1];
        state[x1][y1] = state[x2][y2];
        state[x2][y2] = temp;
    }

    private static int calculateHeuristic(int[][] state) {
        int h = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int value = state[i][j];
                if (value != 0) {
                    int goalX = (value - 1) / 3;
                    int goalY = (value - 1) % 3;
                    h += Math.abs(i - goalX) + Math.abs(j - goalY);
                }
            }
        }
        return h;
    }

    private static String printSolution(Node solutionNode) {
        List<Node> path = new ArrayList<>();
        Node currentNode = solutionNode;

        while (currentNode != null) {
            path.add(currentNode);
            currentNode = currentNode.getParent();
        }

        Collections.reverse(path);

        StringBuilder solutionStr = new StringBuilder();
        for (int i = 0; i < path.size(); i++) {
            String state = printState(path.get(i).getState());
            String step = String.format("Step %s:%n%s", i, state);
            solutionStr.append(step);
        }
        return solutionStr.toString();
    }

    private static String printState(int[][] state) {
        StringBuilder stateStr = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String tile = String.format("%s ", state[i][j]);
                stateStr.append(tile);
            }
            stateStr.append("\n");
        }
        stateStr.append("\n");
        return stateStr.toString();
    }

    public static void main(String[] args) {
        Logger logger = Logger.getLogger(PancakeSort.class.getName());
        Timing timer = new Timing();
        timer.start();

        Node solutionNode = solvePuzzle();

        String finalTiming = timer.stop();
        if (solutionNode != null) {
            String solution = String.format("Solution found!%n%s", printSolution(solutionNode));
            logger.log(Level.INFO,solution);
        } else {
            logger.log(Level.INFO, "No solution found.");
        }
        logger.log(Level.INFO, finalTiming);
    }
}

class Node {
    private final int[][] state;
    private final int cost;
    private final int heuristic;
    private final Node parent;
    private int blankX;
    private int blankY;

    public Node(int[][] state, int cost, int heuristic, Node parent) {
        this.state = state;
        this.cost = cost;
        this.heuristic = heuristic;
        this.parent = parent;

        // Find the position of the blank tile
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state[i][j] == 0) {
                    blankX = i;
                    blankY = j;
                    return;
                }
            }
        }
    }

    public int[][] getState() {
        return state;
    }

    public int getCost() {
        return cost;
    }

    public int getHeuristic() {
        return heuristic;
    }

    public int getBlankX() {
        return blankX;
    }

    public int getBlankY() {
        return blankY;
    }

    public Node getParent() {
        return parent;
    }

    public int getTotalCost() {
        return cost + heuristic;
    }
}
