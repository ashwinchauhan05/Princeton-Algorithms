package part1.week4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;

/**
 * Solver class that solves the 8-Puzzle problem using A* search algorithm.
 * Makes use of Board class and prints the entire sequence of steps taken to get to solution.
 */
public class Solver {

    private class SearchNode implements Comparable<SearchNode> {

        private Board current;
        private SearchNode previous;
        private short moves = 0;
        private boolean isTwinBoard = false;

        SearchNode(Board current, SearchNode previous, boolean isTwinBoard) {
            this.current = current;
            this.moves = previous == null ? (short) 0 : (short) (previous.numOfMoves() + 1);
            this.previous = previous;
            this.isTwinBoard = isTwinBoard;
        }

        public int compareTo(SearchNode node) {
            return (this.current.manhattan() - node.current.manhattan() + (this.moves - node.moves));
        }

        short numOfMoves() {
            return moves;
        }
    }

    private boolean solvable = false;
    private SearchNode next;
    private short numOfMoves = 0;

    public Solver(Board initial) {

        if (initial == null) throw new NullPointerException("null argument is passed");

        MinPQ<SearchNode> pq = new MinPQ<>();

        pq.insert(new SearchNode(initial, null, false));
        pq.insert(new SearchNode(initial.twin(), null, true));

        next = pq.delMin();

        while (next != null) {

            if (next.current.isGoal()) {

                if (!next.isTwinBoard) {
                    numOfMoves = next.numOfMoves();
                    solvable = true;
                    return;
                } else {
                    numOfMoves = -1;
                    solvable = false;
                    return;
                }
            }

            for (Board neighbour : next.current.neighbors()) {
                if (next.previous == null || !neighbour.equals(next.previous.current))
                    pq.insert(new SearchNode(neighbour, next, next.isTwinBoard));
            }

            next = pq.delMin();

        }
    }

    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        return numOfMoves;
    }

    public Iterable<Board> solution() {

        if (!isSolvable()) return null;

        LinkedList<Board> solution = new LinkedList<>();
        SearchNode backPointer = next;

        while (backPointer != null) {
            solution.addFirst(backPointer.current);
            backPointer = backPointer.previous;
        }

        return solution;
    }
}

