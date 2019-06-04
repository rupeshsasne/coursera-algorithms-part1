/* *****************************************************************************
 *  Name: Rupesh Sasne
 *  Date: 26th April 2019
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;

    private final double[] fractions;
    private final double mean;
    private final double dev;

    public PercolationStats(int n, int trials) {
        if (n <= 0)
            throw new IllegalArgumentException("n is less than or equal to zero");

        if (trials <= 0)
            throw new IllegalArgumentException("trials are less than or equals to zero");

        fractions = new double[trials];

        Percolation percolation;

        for (int trial = 0; trial < trials; trial++) {
            int openedSites = 0;

            percolation = new Percolation(n);

            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int column = StdRandom.uniform(1, n + 1);

                if (!percolation.isOpen(row, column)) {
                    percolation.open(row, column);
                    openedSites++;
                }
            }

            double fraction = (double) openedSites / (n * n);
            fractions[trial] = fraction;
        }

        mean = StdStats.mean(fractions);
        dev = StdStats.stddev(fractions);
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return dev;
    }

    public double confidenceLo() {
        return mean - ((CONFIDENCE_95 * dev) / Math.sqrt(fractions.length));
    }

    public double confidenceHi() {
        return mean + ((CONFIDENCE_95 * dev) / Math.sqrt(fractions.length));
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, t);

        String confidence = "[" + ps.confidenceLo() + ", " + ps.confidenceHi() + " ]";

        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + confidence);
    }
}