package org.week2jd14.utils;

public class Timing {
    private long startTime;
    private long startMemory;
    public void start() {
        startTime = System.currentTimeMillis();
        startMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }

    public String stop() {
        long endTime = System.currentTimeMillis();
        long endMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        StringBuilder result = new StringBuilder();
        result.append("==============Timing data==============\n");
        result.append("Memoria utilizada: ");
        result.append(String.valueOf(endMemory-startMemory));
        result.append(" bytes\n");
        result.append("Tiempo de ejecución: ");
        result.append(String.valueOf(endTime-startTime));
        result.append(" milisegundos");
        return result.toString();
    }
}
