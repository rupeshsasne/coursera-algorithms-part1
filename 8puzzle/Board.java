import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Board {
    private final int[][] tiles;

    private final int hammingDistance;

    private final int manhattanDistance;

    private final boolean isGoal;

    private int blankRowIndex, blankColIndex;

    private Board twin;

    public Board(int[][] tiles) {
        this.tiles = deepCopy(tiles);
        this.hammingDistance = calculateHammingDistance();
        this.manhattanDistance = calculateManhattanDistance();
        this.isGoal = isBoardAGoalBoard();
        findBlankSpot();
    }

    public int dimension() {
        return tiles.length;
    }

    public int hamming() {
        return hammingDistance;
    }

    public int manhattan() {
        return manhattanDistance;
    }

    public boolean isGoal() {
        return isGoal;
    }

    public Iterable<Board> neighbors() {
        return generateNeighbors(blankRowIndex, blankColIndex, tiles);
    }

    public Board twin() {

        if (twin == null) {
            int[][] copy = copy(tiles);

            int i1, j1, i2, j2;

            do {
                i1 = StdRandom.uniform(0, tiles.length);
                j1 = StdRandom.uniform(0, tiles.length);

                i2 = StdRandom.uniform(0, tiles.length);
                j2 = StdRandom.uniform(0, tiles.length);

            } while (copy[i1][j1] == copy[i2][j2] || copy[i1][j1] == 0 || copy[i2][j2] == 0);

            int temp = copy[i1][j1];
            copy[i1][j1] = copy[i2][j2];
            copy[i2][j2] = temp;

            twin = new Board(copy);
        }

        return twin;
    }

    public boolean equals(Object y) {
        if (y == null) return false;

        if (!Board.class.isAssignableFrom(y.getClass())) return false;

        if (this == y) return true;

        return Arrays.deepEquals(tiles, ((Board) y).tiles);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(tiles.length).append('\n');

        for (int[] tile : tiles) {
            for (int col = 0; col < tiles.length; col++) {
                stringBuilder.append(tile[col]).append(" ");
            }
            stringBuilder.append('\n');
        }

        return stringBuilder.toString();
    }

    private int calculateManhattanDistance() {
        int distance = 0;

        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles.length; col++) {
                if (tiles[row][col] != 0 && tiles[row][col] != (tiles.length * row + col) + 1) {
                    int i = row(tiles[row][col]);
                    int j = col(tiles[row][col]);
                    distance += (Math.abs(row - i) + Math.abs(col - j));
                }
            }
        }

        return distance;
    }

    private int calculateHammingDistance() {
        int distance = 0;

        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles.length; col++) {

                if (tiles[row][col] == 0) {
                    continue;
                }

                if (tiles[row][col] != (tiles.length * row + col) + 1) {
                    distance++;
                }
            }
        }

        return distance;
    }

    private int[][] deepCopy(int[][] arr) {
        int[][] copy = new int[arr.length][arr.length];

        for (int row = 0; row < arr.length; row++) {
            System.arraycopy(arr[row], 0, copy[row], 0, arr.length);
        }

        return copy;
    }

    private Iterable<Board> generateNeighbors(int row, int col, int[][] arr) {
        return generateNeighbors(
                shiftLeft(row, col, arr),
                shiftRight(row, col, arr),
                shiftTop(row, col, arr),
                shiftBotton(row, col, arr)
        );
    }

    private Iterable<Board> generateNeighbors(int[][]... neighbors) {
        List<Board> list = new ArrayList<>();

        for (int[][] neighbor : neighbors) {
            if (neighbor.length != 0) {
                list.add(new Board(neighbor));
            }
        }

        return list;
    }

    private int[][] shiftBotton(int row, int col, int[][] arr) {
        if (row + 1 == arr.length)
            return new int[0][];

        int[][] copy = copy(arr);

        exch(copy, row, col, row + 1, col);

        return copy;
    }

    private int[][] shiftTop(int row, int col, int[][] arr) {
        if (row - 1 == -1)
            return new int[0][];

        int[][] copy = copy(arr);

        exch(copy, row, col, row - 1, col);

        return copy;
    }

    private int[][] shiftRight(int row, int col, int[][] arr) {
        if (col + 1 == arr.length)
            return new int[0][];

        int[][] copy = copy(arr);

        exch(copy, row, col, row, col + 1);

        return copy;
    }

    private int[][] shiftLeft(int row, int col, int[][] arr) {
        if (col - 1 == -1)
            return new int[0][];

        int[][] copy = copy(arr);

        exch(copy, row, col, row, col - 1);

        return copy;
    }

    private void exch(int[][] arr, int x1, int y1, int x2, int y2) {
        int temp = arr[x1][y1];
        arr[x1][y1] = arr[x2][y2];
        arr[x2][y2] = temp;
    }

    private int[][] copy(int[][] arr) {
        int[][] copy = new int[arr.length][arr.length];

        for (int i = 0; i < arr.length; i++) {
            System.arraycopy(arr[i], 0, copy[i], 0, arr.length);
        }

        return copy;
    }

    private int row(int p) {
        int row = p / tiles.length;

        if (p % tiles.length == 0)
            row -= 1;

        return row;
    }

    private int col(int p) {
        int col = (p % tiles.length);

        return col == 0 ? tiles.length - 1 : col - 1;
    }

    private boolean isBoardAGoalBoard() {
        int expectedElement = 1;

        for (int row = 0; row < tiles.length; row++) {

            for (int col = 0; col < tiles.length; col++) {

                if (row == tiles.length - 1 && col == tiles.length - 1) {
                    return tiles[row][col] == 0;
                }

                if (tiles[row][col] != expectedElement) {
                    return false;
                }

                expectedElement++;
            }
        }

        return false;
    }

    private void findBlankSpot() {
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles.length; col++) {
                if (tiles[row][col] == 0) {
                    blankRowIndex = row;
                    blankColIndex = col;
                }
            }
        }
    }
}
