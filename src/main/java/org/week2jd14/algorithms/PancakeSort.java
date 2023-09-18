package org.week2jd14.algorithms;

import org.week2jd14.utils.Timing;
import org.week2jd14.generators.NumberListGenerator;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PancakeSort {

    public static void main(String[] args) {
        Timing timer = new Timing();
        Logger logger = Logger.getLogger(PancakeSort.class.getName());
        NumberListGenerator listGenerator = new NumberListGenerator();
        int[] arr = listGenerator.getListAsIntArray();
        String initialStack = String.format("Original stack: %s",Arrays.toString(arr));
        logger.log(Level.WARNING, initialStack);
        timer.start();

        pancakeSort(arr);

        String finalTiming = timer.stop();
        String finalStack = String.format("Sorted stack: %s", Arrays.toString(arr));
        logger.log(Level.INFO, finalStack);
        logger.log(Level.INFO, finalTiming);
    }

    public static void pancakeSort(int[] arr) {
        int n = arr.length;
        for (int i = n; i > 1; i--) {
            int maxIndex = findMaxIndex(arr, i);
            if (maxIndex != i - 1) {
                flip(arr, maxIndex);
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

    private static void flip(int[] arr, int k) {
        int i = 0;
        while (i < k) {
            int temp = arr[i];
            arr[i] = arr[k];
            arr[k] = temp;
            i++;
            k--;
        }
    }
}
