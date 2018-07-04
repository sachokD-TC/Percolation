import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final double[] openSitesPercent;
    private final int trials;

    public PercolationStats(int n, int trials) {
        this.trials = trials;
        openSitesPercent = new double[trials];
        for (int i = 0; i != trials; i++) {
            Percolation percolation = new Percolation(n);
            openSitesPercent[i] = getOpenSitesWhenPercolate(n, percolation) / Math.pow(n, 2);
        }
    }


    private int getOpenSitesWhenPercolate(int num, Percolation percolation) {
        while (!percolation.percolates()) {
            while (!setOpenSite(num, percolation));
        }
        return percolation.numberOfOpenSites();
    }

    private boolean setOpenSite(int number, Percolation percolation) {
        int row = StdRandom.uniform(1, number + 1);
        int col = StdRandom.uniform(1, number + 1);
        if (!percolation.isOpen(row, col)) {
            percolation.open(row, col);
            return true;
        }
        return false;
    }

    public double mean() {
        return StdStats.mean(openSitesPercent);
    }

    public double stddev() {
        return StdStats.stddev(openSitesPercent);
    }

    public double confidenceLo() {
        return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(this.trials);
    }                  // low  endpoint of 95% confidence interval

    public double confidenceHi() {
        return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(this.trials);
    }                 // high endpoint of 95% confidence interval

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trial = Integer.parseInt(args[1]);
        if (n <= 0 || trial <= 0) {
            throw new IllegalArgumentException();
        }
        PercolationStats ps = new PercolationStats(n, trial);
        System.out.println("mean = " + ps.mean());
        System.out.println("stddev = " + ps.stddev());
        System.out.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }
}