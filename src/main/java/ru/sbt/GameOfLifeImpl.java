package ru.sbt;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author Panteleeva Natalia
 */
public class GameOfLifeImpl implements GameOfLife{

    private int divideBy;
    private int n, m;

    public GameOfLifeImpl(int divideByN) {
        this.divideBy = divideByN;
    }

    @Override
    public List<String> play(String inputFile) {
        List<String> list = scanFile(inputFile);

        boolean[][] matrix1 = new boolean[n][n];
        boolean[][] matrix2 = new boolean[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix1[i][j] = list.get(i).charAt(j) == '1';
            }
        }

        boolean[][] lastStep = matrix1;
        Set<Thread> threadSet = new HashSet<>();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < divideBy; j++) {
                for (int k = 0; k < divideBy; k++) {
                    Thread thread = new Thread(new Worker(matrix1, matrix2, n, j, k, divideBy));
                    threadSet.add(thread);
                    thread.start();
                }
            }
            lastStep = matrix2;
            matrix2 = matrix1;
            matrix1 = lastStep;
            joinThreads(threadSet);
        }
        return booleanArrayToStringList(lastStep);
    }

    private void joinThreads(Set<Thread> set) {
        set.stream().forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private List<String> booleanArrayToStringList(boolean[][] array){
        List<String> result = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            char[] str = new char[array.length];
            for (int j = 0; j < array.length; j++) {
                str[j] = array[i][j] ? '1' : '0';
            }
            result.add(String.valueOf(str));
        }
        return result;
    }

    private List<String> scanFile(String inputFile) {
        List<String> list = new ArrayList<>();
        Scanner scanner = null;
        try {
            scanner  = new Scanner(new File(inputFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (scanner.hasNextLine()) {
            String[] nm = scanner.nextLine().split(" ");
            n = Integer.valueOf(nm[0]);
            m = Integer.valueOf(nm[1]);
        }
        while (scanner.hasNextLine()) {
            list.add(scanner.nextLine());
        }
        return list;
    }
}
