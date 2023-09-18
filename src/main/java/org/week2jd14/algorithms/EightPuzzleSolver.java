package org.week2jd14.algorithms;

import org.week2jd14.utils.Timing;

import java.util.*;

public class EightPuzzleSolver {
    // Definimos el estado inicial y final, para representar los estados del rompecabezas.
    private static final int[][] GOAL_STATE = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}}; // Goal state
    private static final int[][] INITIAL_STATE = {{1, 2, 3}, {0, 4, 6}, {7, 5, 8}}; // Initial state

    // Estas serán las reglas para realizar las operaciones de movimiento
    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Possible move directions

    // Estrategia de búsqueda: Usamos los principios del algoritmo A* Search
    private static Node solvePuzzle(int[][] initial) {
        // Creamos una cola de prioridad para almacenar los estados (llamados nodos también) que faltan explorar,
        // priorizados por el costo total.
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(Node::getCost));
        // Creamos un set para guardar los nodos explorados, representandolos como Strings
        Set<String> closedSet = new HashSet<>();

        // Creamos el nodo inicial con la información del estado inicial dada
        Node initialNode = new Node(initial, 0, calculateHeuristic(initial), null);
        // Agregamos el nodo inicial a los nodos por explorar
        openSet.add(initialNode);

        // Exploramos nodos hasta que la cola de nodos por explorar, esté vacía
        while (!openSet.isEmpty()) {
            // Extraemos el nodo siguiente de la cola con el menor costo
            Node currentNode = openSet.poll();

            // Verificamos si el nodo actual es equivalente al nodo objetivo
            if (Arrays.deepEquals(currentNode.getState(), GOAL_STATE)) {
                return currentNode; // Solution found
            }

            // Agregamos el nodo actual al set para marcarlo como explorado
            closedSet.add(Arrays.deepToString(currentNode.getState()));

            // Exploramos los posibles movimientos en las 4 dirección definidas: Arriba, abajo, Izquierda y Derecha
            for (int[] dir : DIRECTIONS) {
                // Obtenemos las nuevas coordenadas del espacio en blanco
                int newX = currentNode.getBlankX() + dir[0];
                int newY = currentNode.getBlankY() + dir[1];

                // Validamos si las nuevas coordenadas no se desbordan de la matriz 3x3
                if (isValid(newX, newY)) {
                    // Generamos un nuevo estado, en base al actual, intercambiando de lugar el espacio en blanco
                    int[][] newState = cloneState(currentNode.getState());
                    // Se realiza el intercambio
                    swap(newState, currentNode.getBlankX(), currentNode.getBlankY(), newX, newY);

                    // Validamos si el nuevo estado no ha sido explorado antes
                    if (!closedSet.contains(Arrays.deepToString(newState))) {
                        // Creamos el nuevo nodo para representar el nuevo estado
                        Node newNode = new Node(newState, currentNode.getCost() + 1, calculateHeuristic(newState), currentNode);
                        // Agregamos el nuevo nodo a la cola de nodos por explorar
                        openSet.add(newNode);
                    }
                }
            }
        }

        return null; // No solution found
    }

    // Esta función valida que la matriz sea de 3x3, osea el juego 8-Puzzle
    private static boolean isValid(int x, int y) {
        return x >= 0 && x < 3 && y >= 0 && y < 3;
    }

    // Esta función sirve para crear un clon de un estado
    private static int[][] cloneState(int[][] state) {
        int[][] newState = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                newState[i][j] = state[i][j];
            }
        }
        return newState;
    }

    // Con esta función realizamos la operación de mover la sección en blanco de lugar
    private static void swap(int[][] state, int x1, int y1, int x2, int y2) {
        int temp = state[x1][y1];
        state[x1][y1] = state[x2][y2];
        state[x2][y2] = temp;
    }

    // Calculamos la Heuristica del estado (Complejidad aparente: O(n^2))
    private static int calculateHeuristic(int[][] state) {
        // Iniciamos con un valor 0 de Heuristica
        int h = 0;
        // Iteramos a traves de cada sección (tile) del estado del rompecabeza
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Obtenemos el valor de la sección actual
                int value = state[i][j];
                if (value != 0) {
                    // Calculamos el índice de la fila en el estado final
                    int goalX = (value - 1) / 3;
                    // Calculamos el índice de la columna en el estado final
                    int goalY = (value - 1) % 3;
                    // Calculamos la "Distancia Manhattan" para la sección actual y agregarla al valor de la heurística
                    // Esta distancia representa el número de movimientos horizontales y verticales requeridos para mover
                    // la sección al lugar del estado final
                    h += Math.abs(i - goalX) + Math.abs(j - goalY);
                }
            }
        }
        // Retornamos la "Distancia Manhattan" como valor de heuristica
        return h;
    }

    // Las siguiente dos funciones, son auxiliares para realizar una representación
    // gráfica de la solución
    private static void printSolution(Node solutionNode) {
        List<Node> path = new ArrayList<>();
        Node currentNode = solutionNode;

        while (currentNode != null) {
            path.add(currentNode);
            currentNode = currentNode.getParent();
        }

        Collections.reverse(path);

        for (int i = 0; i < path.size(); i++) {
            System.out.println("Step " + i + ":");
            printState(path.get(i).getState());
        }
    }

    private static void printState(int[][] state) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(state[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Timing timer = new Timing();
        timer.start();
        Node solutionNode = solvePuzzle(INITIAL_STATE);

        if (solutionNode != null) {
            System.out.println("Solution found!");
            printSolution(solutionNode);
        } else {
            System.out.println("No solution found.");
        }
        System.out.println(timer.stop());
    }
}

// Esta clase describe las cualidades de nuestros nodos (estados) para modelar nuestro problema
// Posee las propiedades para calcular su coste respecto a otros nodos
class Node {
    private int[][] state;
    private int cost;
    private int heuristic;
    private Node parent;
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
