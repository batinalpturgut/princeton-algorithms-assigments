/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private double[] percThresholds;
    private int nGrid;
    private int totalExperiments;


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {

        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        this.nGrid = n;
        this.totalExperiments = trials;
        percThresholds = new double[trials];

        for (int i = 0; i < totalExperiments; i++) {
            Percolation p = new Percolation(nGrid);
            for (int j = 0; j < nGrid * nGrid; j++) {
                int row = 0;
                int col = 0;
                do {
                    row = StdRandom.uniformInt(1, nGrid + 1);
                    col = StdRandom.uniformInt(1, nGrid + 1);
                } while (p.isOpen(row, col));

                p.open(row, col);
                if (p.percolates()) {
                    double openSites = p.numberOfOpenSites();
                    double totalSites = nGrid * nGrid;
                    percThresholds[i] = openSites / totalSites;
                    break;
                }
            }
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(percThresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {

        return StdStats.stddev(percThresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {

        return mean() - ((CONFIDENCE_95 * stddev()) / Math.sqrt(
                totalExperiments));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {

        return mean() + ((CONFIDENCE_95 * stddev()) / Math.sqrt(
                totalExperiments));
    }

    // test client (see below)
    public static void main(String[] args) {

        int nGrid = Integer.parseInt(args[0]);
        int totalExperiments = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(nGrid, totalExperiments);
        StdOut.println("mean                    = " + percolationStats.mean());
        StdOut.println("stddev                  = " + percolationStats.stddev());
        StdOut.println(
                "95% confidence interval = " + "[" + percolationStats.confidenceLo() + "], [" +
                        percolationStats.confidenceHi()
                        + "]");
    }

}
