package org.week2jd14.generators;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Logger;
import java.io.File;

// Author: JP
public class NumberListGenerator {

    public static void main(String[] args) {
        NumberListGenerator numbers = new NumberListGenerator();
        numbers.generateNumberList(100000);
        numbers.saveData();
    }

    private ArrayList<Integer> list;
    private Random randomNumber = new Random();
    private String fileName = "number_list.txt";

    public NumberListGenerator() {
        readData();
    }

    public void generateNumberList(int length) {
       list  = new ArrayList<>(0);
       int max = length*2;
       int min = length*2*-1;
       while (list.size() < length) {
           list.add(randomNumber.nextInt(min, max));
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
            StringBuilder data = new StringBuilder();
            list.forEach(e -> data.append(String.format("%s%n",e)));
            writer.write(data.toString());
            writer.close();
        } catch (IOException e) {
            System.out.println("An Error Occurred");
            e.printStackTrace();
        }
    }

    public void readData() {
        try {
            File file = new File(fileName);
            list = new ArrayList<>(0);
            if (!file.exists() || !file.canRead()) {
                throw new IOException("File not exist or can't be readable");
            }
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                list.add(Integer.parseInt(data));
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("An Error Occurred");
            e.printStackTrace();
        }
    }

    public List<Integer> getList() {
        return list;
    }

    public int[] getListAsIntArray() {
        int[] arr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }

    public void setList(List<Integer> list) {
        this.list = new ArrayList<>(list);
    }
}
