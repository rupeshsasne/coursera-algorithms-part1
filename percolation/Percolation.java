/* *****************************************************************************
 *  Name: Rupesh Sasne
 *  Date: 26th April 2019
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF union;
    private final WeightedQuickUnionUF backwash;
    private final int virtualTopIndex;
    private final int virtualBottomIndex;
    private final boolean[] openSites;
    private final int size;
    private int numberOfOpenedSites;

    public Percolation(int size) {
        if (size <= 0)
            throw new IllegalArgumentException();

        this.size = size;
        union = new WeightedQuickUnionUF(this.size * this.size + 2);
        backwash = new WeightedQuickUnionUF(this.size * this.size + 2);
        virtualBottomIndex = size * size;
        virtualTopIndex = size * size + 1;
        openSites = new boolean[size * size];
    }

    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            row = row - 1;
            col = col - 1;

            int index = toIndex(row, col);
            int top = toIndex(row - 1, col);
            int bottom = toIndex(row + 1, col);
            int right = toIndex(row, col + 1);
            int left = toIndex(row, col - 1);

            openSites[index] = true;

            if (row == 0) {
                union.union(index, virtualTopIndex);
                backwash.union(index, virtualTopIndex);
            }

            if (col > 0 && openSites[left]) {
                union.union(index, left);
                backwash.union(index, left);
            }

            if (row < size - 1 && openSites[bottom]) {
                union.union(index, bottom);
                backwash.union(index, bottom);
            }

            if (col < size - 1 && openSites[right]) {
                union.union(index, right);
                backwash.union(index, right);
            }

            if (row > 0 && openSites[top]) {
                union.union(index, top);
                backwash.union(index, top);
            }

            if (row == size - 1) {
                backwash.union(index, virtualBottomIndex);
            }

            numberOfOpenedSites++;
        }
    }

    public boolean isOpen(int row, int col) {
        assertBounds(row, col);
        return openSites[(row - 1) * size + (col - 1)];
    }

    public boolean isFull(int row, int col) {
        assertBounds(row, col);
        return union.connected((row - 1) * size + (col - 1), virtualTopIndex);
    }

    public boolean percolates() {
        return backwash.connected(virtualTopIndex, virtualBottomIndex);
    }

    public int numberOfOpenSites() {
        return numberOfOpenedSites;
    }

    private void assertBounds(int row, int col) {
        if (row <= 0 || row > size)
            throw new IllegalArgumentException();

        if (col <= 0 || col > size)
            throw new IllegalArgumentException();
    }

    private int toIndex(int row, int col) {
        return row * size + col;
    }
}