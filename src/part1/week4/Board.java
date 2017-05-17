package part1.week4;

import edu.princeton.cs.algs4.Queue;

/**
 * Immutable Board object with functions for calculating Manhattan and Hamming priority distances.
 * Also has other helper functions like isGoal, twin that helps solve a Board.
 */
public class Board {

    private final short[][] blocks;
    private final byte blankSpace = 0;
    private short dimension;


    public Board(int[][] blocks) {

        dimension = (short) blocks.length;
        this.blocks = new short[dimension][dimension];

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                this.blocks[i][j] = (short) blocks[i][j];
            }
        }
    }

    public int dimension() {
        return this.dimension;
    }

    public int hamming() {
        int numberOfBlocksInWrongPosition = 0;

        for (short i = 0; i < dimension; i++) {
            for (short j = 0; j < dimension; j++) {
                if (blocks[i][j] != blankSpace && blocks[i][j] != (i * dimension + j + 1))
                    numberOfBlocksInWrongPosition++;
            }
        }

        return numberOfBlocksInWrongPosition;
    }

    public int manhattan() {
        int manhattanDistance = 0;

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {

                if (blocks[i][j] != blankSpace && blocks[i][j] != (i * dimension + j + 1)) {

                    int xCord = (blocks[i][j] - 1) / dimension;
                    int yCord = (blocks[i][j] - 1) % dimension;

                    manhattanDistance += Math.abs(xCord - i);
                    manhattanDistance += Math.abs(yCord - j);
                }
            }
        }

        return manhattanDistance;
    }

    public boolean isGoal() {

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] != blankSpace && blocks[i][j] != (i * dimension + j + 1))
                    return  false;
            }
        }

        return true;
    }

    public Board twin() {

        for (int i = 0; i < dimension - 1; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] != blankSpace && blocks[i+1][j] != blankSpace) {
                    return swapBlocksAndReturnBoard(i, j, i+1, j);
                }
            }
        }

        return null;
    }

    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;

        Board that = (Board) y;

        int thisRow = this.blocks.length;
        int thisCol = this.blocks[0].length;

        int thatRow = that.blocks.length;
        int thatCol = that.blocks[0].length;

        if (thisRow != thatRow || thisCol != thatCol) return false;

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (that.blocks[i][j] != this.blocks[i][j])
                    return false;
            }
        }

        return true;
    }

    public Iterable<Board> neighbors() {

        Queue<Board> neighbour = new Queue<>();
        int xLoc = -1, yLoc = -1;

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] == blankSpace) {
                    xLoc = i;
                    yLoc = j;
                }
            }
        }

        if (xLoc - 1 >= 0)             neighbour.enqueue(swapBlocksAndReturnBoard(xLoc, yLoc, xLoc - 1, yLoc));
        if (xLoc + 1 <= dimension - 1) neighbour.enqueue(swapBlocksAndReturnBoard(xLoc, yLoc, xLoc + 1, yLoc));
        if (yLoc - 1 >= 0)             neighbour.enqueue(swapBlocksAndReturnBoard(xLoc, yLoc, xLoc, yLoc - 1));
        if (yLoc + 1 <= dimension - 1) neighbour.enqueue(swapBlocksAndReturnBoard(xLoc, yLoc, xLoc, yLoc + 1));

        return neighbour;
    }

    private Board swapBlocksAndReturnBoard(int x1, int y1, int x2, int y2) {

        int[][] twinBoard = new int[dimension][dimension];

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                twinBoard[i][j] = blocks[i][j];
            }
        }

        int temp = twinBoard[x1][y1];
        twinBoard[x1][y1] = twinBoard[x2][y2];
        twinBoard[x2][y2] = temp;

        return new Board(twinBoard);
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension + "\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
}