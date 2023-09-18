package org.week2jd14.generators;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Logger;

// Author: Chat-GPT
// Modified by: JP
public class GraphGenerator {
    private String fileName = "graph.txt";
    private int[][] adjacencyMatrix;
    private List<List<Integer>> adjacencyList;
    public static void main(String[] args) {
        GraphGenerator gg = new GraphGenerator();
        gg.generateRandomGraph(9000, 0.5);
        gg.saveData();
    }
    public GraphGenerator() {
        readData();
    }

    public void generateRandomGraph(int nodesCount, double edgeProbability) {
        int[][] adjacencyMatrix = new int[nodesCount][nodesCount];
        Random random = new Random();

        for (int i = 0; i < nodesCount; i++) {
            for (int j = i + 1; j < nodesCount; j++) {
                if (random.nextDouble() < edgeProbability) {
                    // If the random number is less than edgeProbability, create an edge
                    adjacencyMatrix[i][j] = 1;
                    adjacencyMatrix[j][i] = 1; // Since the graph is undirected
                }
            }
        }
        this.adjacencyMatrix = adjacencyMatrix;
    }

    private String adjacencyMatrixToString() {
        StringBuilder adjacencyMatrixString = new StringBuilder();
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                int element = adjacencyMatrix[i][j];
                adjacencyMatrixString.append(String.format("%s ", element));
            }
            adjacencyMatrixString.append("\n");
        }
        return adjacencyMatrixString.toString();
    }

    public void printAdjacencyMatrix() {
        System.out.println("Adjacency Matrix:");
        System.out.println(adjacencyMatrixToString());
    }

    public void generateAdjacencyList() {
        int nodesCount = adjacencyMatrix.length;
        List<List<Integer>> adjacencyList = new ArrayList<>();

        for (int i = 0; i < nodesCount; i++) {
            List<Integer> neighbors = new ArrayList<>();
            for (int j = 0; j < nodesCount; j++) {
                if (adjacencyMatrix[i][j] == 1) {
                    neighbors.add(j);
                }
            }
            adjacencyList.add(neighbors);
        }

        this.adjacencyList = adjacencyList;
    }

    public void printAdjacencyList() {
        System.out.println("Adjacency List:");
        for (int i = 0; i < adjacencyList.size(); i++) {
            System.out.print("Node " + i + " is connected to: ");
            List<Integer> neighbors = adjacencyList.get(i);
            for (int j = 0; j < neighbors.size(); j++) {
                System.out.print(neighbors.get(j));
                if (j < neighbors.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
    }

    public void saveData() {
        try {
            File file = new File(fileName);
            if (file.createNewFile()) {
                Logger.getLogger("File created");
            } else {
                Logger.getLogger("File not Created");
            }
            FileWriter writer = new FileWriter(fileName);
            writer.write(adjacencyMatrixToString());
            writer.close();
        } catch (IOException e) {
            System.out.println("An Error Occurred");
            e.printStackTrace();
        }
    }

    public void readData() {
        try {
            File file = new File(fileName);
            List<List<Integer>> matrix = new ArrayList<>();
            if (!file.exists() || !file.canRead()) {
                throw new IOException("File not exist or can't be readable");
            }
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String rowString = reader.nextLine();
                String[] elements = rowString.split(" ");
                List<Integer> row = new ArrayList<>();
                for (String element: elements) {
                    if (element.trim().length() > 0) {
                       row.add(Integer.parseInt(element));
                    }
                }
                matrix.add(row);
            }
            reader.close();
            adjacencyMatrix = new int[matrix.size()][matrix.size()];
            for (int i = 0; i < matrix.size(); i++) {
                for (int j = 0; j < matrix.size(); j++) {
                   adjacencyMatrix[i][j] = matrix.get(i).get(j);
                }
            }
        } catch (IOException e) {
            System.out.println("An Error Occurred");
            e.printStackTrace();
        }
    }

    public int[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    public void setAdjacencyMatrix(int[][] adjacencyMatrix) {
        this.adjacencyMatrix = adjacencyMatrix;
    }

    public List<List<Integer>> getAdjacencyList() {
        return adjacencyList;
    }

    public void setAdjacencyList(List<List<Integer>> adjacencyList) {
        this.adjacencyList = adjacencyList;
    }
}