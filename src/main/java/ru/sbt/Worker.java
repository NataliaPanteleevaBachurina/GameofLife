package ru.sbt;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Panteleeva Natalia
 */
public class Worker implements Runnable{
    private boolean[][] field;
    private boolean[][] result;
    private int n;
    private int cellX, cellY;
    private int cellNum;

    public Worker(boolean[][] field, boolean[][] result, int n, int cellX, int cellY, int cellNum) {
        this.field = field;
        this.result = result;
        this.n = n;
        this.cellX = cellX;
        this.cellY = cellY;
        this.cellNum = cellNum;
    }

    @Override
    public void run() {
        int xAdd = restElements(cellX);
        int yAdd = restElements(cellY);
        int cellSize = n / cellNum;

        for (int i = cellX * cellSize; i < (cellX + 1) * cellSize + xAdd; i++) {
            for (int j = cellY * cellSize; j < (cellY + 1) * cellSize + yAdd; j++) {

                int aliveNum = calculateLiveNeighbours(i, j);
                result[i][j] = makeDecision(field, i, j, aliveNum);

            }
        }
    }

    private int restElements(int coordinate) {
        return coordinate == cellNum - 1 ? n % cellNum : 0;
    }

    private boolean makeDecision(boolean[][] field, int xCoordianate, int yCoordinate, int aliveNum) {
        return aliveNum == 3 || (field[xCoordianate][yCoordinate] && aliveNum == 2);
    }

    private int calculateLiveNeighbours(int i, int j) {
        int count = 0;
        List<Integer> xIndexes = new ArrayList<>();
        List<Integer> yIndexes = new ArrayList<>();

        xIndexes.add(getPrevious(i));
        xIndexes.add(i);
        xIndexes.add(getNext(i));

        yIndexes.add(getPrevious(j));
        yIndexes.add(j);
        yIndexes.add(getNext(j));

        for (Integer x : xIndexes) {
            for (Integer y : yIndexes) {
                if (field[x][y]) {
                    if (x != i || y != j)
                        count++;
                }
            }
        }
        return count;
    }

    private int getNext(int index) {
        if (index == n - 1) {
            return 0;
        }
        else {
            return index + 1;
        }
    }

    private int getPrevious(int index) {
        if (index == 0) {
            return n - 1;
        }
        else {
            return index - 1;
        }
    }
}
