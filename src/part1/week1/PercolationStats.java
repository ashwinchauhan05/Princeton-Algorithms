package part1.week1;

/************************************************************************************
 * Name : Ashwin Chauhan
 * Date: 1-Oct-2015
 *
 * This program takes two inputs N(Size of the grid) , T( No. of test cases)
 * from the command line and runs T simulations over a NxN Grid.
 * Later it computes mean, Standard Deviation and the 95% Confidence Value.
 *
 * Execution : java PercolationStat 200 100
 * Dependency : Percolation.java
 *************************************************************************************/

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] x;

    public PercolationStats(int n, int trails) {

        if (n <= 0 || trails <= 0)
            throw new IllegalArgumentException("Values of N and T can't be zero.");

        int i, j;
        Percolation percolation;
        x = new double[trails];

        for (int t = 0; t < trails; t++) {

            percolation = new Percolation(n);

            do {
                i = Integer.parseInt(String.valueOf(StdRandom.uniform(n) + 1));
                j = Integer.parseInt(String.valueOf(StdRandom.uniform(n) + 1));

                percolation.open(i,j);

            } while (!percolation.percolates());

            x[t] = (double) percolation.numberOfOpenSites() / (n * n);
        }
    }

    public static void main(String[] args) {

        int n, trails;

        n = Integer.parseInt(args[0]);
        trails = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(n, trails);

        System.out.println("mean                    = " + percolationStats.mean());
        System.out.println("stddev                  = " + percolationStats.stddev());
        System.out.println("95% confidence interval = " + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi());
    }

    public double mean() {
        return StdStats.mean(x);
    }

    public double stddev() {
        return StdStats.stddev(x);
    }

    public double confidenceLo() {
        return mean() - (1.96 * stddev() / (Math.sqrt(x.length)));
    }

    public double confidenceHi() {
        return mean() + (1.96 * stddev() / (Math.sqrt(x.length)));
    }
}
