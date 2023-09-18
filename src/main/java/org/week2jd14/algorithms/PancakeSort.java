package org.week2jd14.algorithms;

import org.week2jd14.utils.Timing;
import org.week2jd14.generators.NumberListGenerator;

import java.util.Arrays;

public class PancakeSort {

    public static void main(String[] args) {
        Timing timer = new Timing();
        NumberListGenerator listGenerator = new NumberListGenerator();
        // int[] arr = {3, 1, 5, 4, 2};
        int[] arr = listGenerator.getListAsIntArray();
        timer.start();
        System.out.println("Original stack: " + Arrays.toString(arr));

        pancakeSort(arr);

        System.out.println("Sorted stack: " + Arrays.toString(arr));
        System.out.println(timer.stop());
    }

    public static void pancakeSort(int[] arr) {
        int n = arr.length;
        for (int i = n; i > 1; i--) {
            // Find the index of the maximum element in the first i elements
            int maxIndex = findMaxIndex(arr, i);

            // If the maximum element is not at the end, flip it to the top
            if (maxIndex != i - 1) {
                // First, flip the stack to get the maximum element to the top
                flip(arr, maxIndex);

                // Then, flip the stack to move the maximum element to its correct position
                flip(arr, i - 1);
            }
        }
    }

    private static int findMaxIndex(int[] arr, int n) {
        int maxIndex = 0;
        for (int i = 0; i < n; i++) {
            if (arr[i] > arr[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    // K representa el índice desde donde hacer el movimiento de voltear
    private static void flip(int[] arr, int k) {
        int i = 0;
        while (i < k) {
            int temp = arr[i];
            // Realiza el intercambio de las posiciones de cada elemento para completar el volteo
            arr[i] = arr[k];
            arr[k] = temp;
            // reducimos k y aumentamos i para preparanos para el siguiente intercambio con otros elementos
            i++;
            k--;
        }
    }
}
