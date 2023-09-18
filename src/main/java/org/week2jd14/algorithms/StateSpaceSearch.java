package org.week2jd14.algorithms;

import org.week2jd14.generators.GraphGenerator;
import org.week2jd14.utils.Timing;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StateSpaceSearch {
    private final int[][] graph;
    private final int numNodes;

    public StateSpaceSearch(int[][] adjacencyMatrix) {
        this.graph = adjacencyMatrix;
        this.numNodes = adjacencyMatrix.length;
    }

    public List<Integer> breadthFirstSearch(int startNode, int goalNode) {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[numNodes];
        int[] parent = new int[numNodes];

        queue.add(startNode);
        visited[startNode] = true;
        parent[startNode] = -1;

        while (!queue.isEmpty()) {
            int currentNode = queue.poll();

            if (currentNode == goalNode) {
                // Reconstruct and return the path
                return reconstructPath(parent, goalNode);
            }

            for (int neighbor = 0; neighbor < numNodes; neighbor++) {
                if (graph[currentNode][neighbor] == 1 && !visited[neighbor]) {
                    queue.add(neighbor);
                    visited[neighbor] = true;
                    parent[neighbor] = currentNode;
                }
            }
        }

        // No path found
        return new ArrayList<>();
    }

    private List<Integer> reconstructPath(int[] parent, int goalNode) {
        List<Integer> path = new ArrayList<>();
        int currentNode = goalNode;

        while (currentNode != -1) {
            path.add(currentNode);
            currentNode = parent[currentNode];
        }

        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        Logger logger = Logger.getLogger(StateSpaceSearch.class.getName());
        Timing timer = new Timing();
        GraphGenerator gg = new GraphGenerator();
        int[][] adjacencyMatrix = gg.getAdjacencyMatrix();
        timer.start();

        StateSpaceSearch search = new StateSpaceSearch(adjacencyMatrix);
        int startNode = 518;
        int goalNode = 8925;

        List<Integer> path = search.breadthFirstSearch(startNode, goalNode);

        String finalTiming = timer.stop();

        if (!path.isEmpty()) {
            String pathFound = String.format("Shortest Path from Node %s to Node %s: %s", startNode, goalNode, path);
            logger.log(Level.INFO, pathFound);
        } else {
            String pathNotFound = String.format("No path found from Node %s to Node %s", startNode, goalNode);
            logger.log(Level.INFO, pathNotFound);
        }
        logger.log(Level.INFO, finalTiming);
    }
}
