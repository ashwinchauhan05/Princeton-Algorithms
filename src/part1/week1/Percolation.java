package part1.week1;

/************************************************************************************
 * Name : Ashwin Chauhan
 * Date: 30-Sep-2015
 *
 * This program creates a NxN grid and randomly opens sites till the system
 * can be percolated.
 ************************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] grid;
    private WeightedQuickUnionUF wquf, wqufBw;
    private int numberOfOpenSites;

    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("Value of N cannot be <= 0");

        numberOfOpenSites = 0;
        grid = new boolean[n][n];
        wquf = new WeightedQuickUnionUF(n * n + 2);
        wqufBw = new WeightedQuickUnionUF(n * n + 1); // without virtual bottom

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false;
            }
        }
    }

    public void open(int i, int j) {

        isValid(i, j);

        if (!isOpen(i, j)) {

            int iGrid = i - 1, jGrid = j - 1;
            int ijGridValue = ijToID(iGrid, jGrid);

            grid[iGrid][jGrid] = true;
            numberOfOpenSites++;

            if (iGrid == 0) // if its in top row Connect it to virtual top
            {
                wquf.union(0, ijGridValue);
                wqufBw.union(0, ijGridValue);
            }

            if (iGrid >= 1 && isOpen(i - 1, j)) // top
            {
                wquf.union(ijGridValue, ijToID(iGrid - 1, jGrid));
                wqufBw.union(ijGridValue, ijToID(iGrid - 1, jGrid));
            }

            if (jGrid >= 1 && isOpen(i, j - 1)) // left
            {
                wquf.union(ijGridValue, ijToID(iGrid, jGrid - 1));
                wqufBw.union(ijGridValue, ijToID(iGrid, jGrid - 1));
            }

            if (iGrid < grid.length - 1 && isOpen(i + 1, j)) // bottom
            {
                wquf.union(ijGridValue, ijToID(iGrid + 1, jGrid));
                wqufBw.union(ijGridValue, ijToID(iGrid + 1, jGrid));
            }

            if (jGrid < grid.length - 1 && isOpen(i, j + 1)) // right
            {
                wquf.union(ijGridValue, ijToID(iGrid, jGrid) + 1);
                wqufBw.union(ijGridValue, ijToID(iGrid, jGrid) + 1);
            }

            if (iGrid == grid.length - 1) // if its in the bottom row connect to virtual bottom
                wquf.union(ijGridValue, (grid.length * grid.length) + 1);
        }

    }

    public boolean isOpen(int i, int j) {
        isValid(i, j);
        return grid[i - 1][j - 1];
    }

    public boolean isFull(int i, int j) {
        isValid(i, j);
        return wqufBw.connected(0, ijToID(i - 1, j - 1));
    }

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    public boolean percolates() {
        return wquf.connected(0, grid.length * grid.length + 1);
    }

    private int ijToID(int i, int j) {
        return i * grid.length + j + 1;
    }

    private void isValid(int i, int j) {

        if (i <= 0 || i > grid.length)
            throw new IndexOutOfBoundsException("row index i out of bounds");

        if (j <= 0 || j > grid.length)
            throw new IndexOutOfBoundsException("column index j out of bounds");
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(10);
        p.open(0, 6);
    }
}
