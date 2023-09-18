package org.week2jd14.algorithms;

import org.week2jd14.generators.GraphGenerator;
import org.week2jd14.utils.Timing;

import java.util.*;

public class StateSpaceSearch {
    private int[][] graph;
    private int numNodes;

    // Se crea la representación del Grafo con la Matriz de Adjacencia
    public StateSpaceSearch(int[][] adjacencyMatrix) {
        this.graph = adjacencyMatrix;
        this.numNodes = adjacencyMatrix.length;
    }

    // Estrategia de búsqueda, en este caso BFS
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
                return reconstructPath(parent, startNode, goalNode);
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

    private List<Integer> reconstructPath(int[] parent, int startNode, int goalNode) {
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
        /* int[][] adjacencyMatrix = {
                {0, 1, 0, 0, 1},
                {1, 0, 1, 0, 0},
                {0, 1, 0, 1, 0},
                {0, 0, 1, 0, 1},
                {1, 0, 0, 1, 0}
        };*/
        Timing timer = new Timing();
        GraphGenerator gg = new GraphGenerator();
        int[][] adjacencyMatrix = gg.getAdjacencyMatrix();
        timer.start();

        StateSpaceSearch search = new StateSpaceSearch(adjacencyMatrix);
        int startNode = 518;
        int goalNode = 8925;

        List<Integer> path = search.breadthFirstSearch(startNode, goalNode);

        if (!path.isEmpty()) {
            System.out.println("Shortest Path from Node " + startNode + " to Node " + goalNode + ": " + path);
        } else {
            System.out.println("No path found from Node " + startNode + " to Node " + goalNode);
        }
        System.out.println(timer.stop());
    }
}
