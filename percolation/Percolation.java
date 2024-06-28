/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int n;

    private boolean[] sitesOpen;

    private int openSiteCount;

    private WeightedQuickUnionUF algo; // Two virtual site

    private WeightedQuickUnionUF algo2; // One virtual site

    public Percolation(int n) {

        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        algo2 = new WeightedQuickUnionUF((n * n) + 1);

        this.sitesOpen = new boolean[(n * n) + 2];

        this.n = n;

        algo = new WeightedQuickUnionUF((n * n) + 2);
        for (int i = 1; i <= n; i++) {
            algo2.union(0, i);
            algo.union(0, i);
            algo.union((n * n) + 1, (n - 1) * n + i);
        }
    }


    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

        if (row > n || row < 1 || col > n || col < 1) {
            throw new IllegalArgumentException();
        }

        if (!isOpen(row, col)) {
            int index = find1D(row, col);

            sitesOpen[index] = true;
            openSiteCount++;

            if (col != n) {

                if (isOpen(row, col + 1)) {
                    algo.union(index, index + 1);
                    algo2.union(index, index + 1);
                }

            }

            if (col != 1) {
                if (isOpen(row, col - 1)) {
                    algo.union(index, index - 1);
                    algo2.union(index, index - 1);
                }

            }

            if (row != n) {
                if (isOpen(row + 1, col)) {
                    algo.union(index, index + n);
                    algo2.union(index, index + n);
                }
            }

            if (row != 1) {
                if (isOpen(row - 1, col)) {
                    algo.union(index, index - n);
                    algo2.union(index, index - n);
                }
            }
        }
    }

    private int find1D(int row, int col) {
        return ((row - 1) * (n)) + col;
    }


    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {

        if (row > n || row < 1 || col > n || col < 1) {
            throw new IllegalArgumentException();
        }

        int index = ((row - 1) * (n)) + col;

        return sitesOpen[index];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row > n || row < 1 || col > n || col < 1) {
            throw new IllegalArgumentException();
        }

        int index = find1D(row, col);

        return isOpen(row, col) && algo2.find(index) == algo2.find(0);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSiteCount;
    }

    // does the system percolate?
    public boolean percolates() {
        if (openSiteCount != 0) {
            return algo.find(0) == algo.find((n * n) + 1);
        }
        else
            return false;
    }

    // test client (optional)
    public static void main(String[] args) {

    }

}
