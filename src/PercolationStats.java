import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] openSitesPercent;
    private int trials;

    public PercolationStats(int n, int trials) {
        this.trials = trials;
        openSitesPercent = new double[trials];
        for (int i = 0; i != trials; i++) {
            Percolation percolation = new Percolation(n);
            openSitesPercent[i] = percolation.numberOfOpenSites() / Math.pow(n, 2);
        }
    }   // perform trials independent experiments on an n-by-n grid


    public double mean() {
        return StdStats.mean(openSitesPercent);
    }                     // sample mean of percolation threshold

    public double stddev() {
        return StdStats.stddev(openSitesPercent);
    }                    // sample standard deviation of percolation threshold

    public double confidenceLo() {
        return mean() - 1.96 * stddev() / this.trials;
    }                  // low  endpoint of 95% confidence interval

    public double confidenceHi() {
        return mean() - 1.96 * stddev() / this.trials;
    }                 // high endpoint of 95% confidence interval

    public static void main(String[] args) {
        int n = Integer.valueOf(args[0]);
        int trial = Integer.valueOf(args[1]);
        if (n <= 0 || trial <= 0) {
            throw new IllegalArgumentException();
        }
        PercolationStats ps = new PercolationStats(n, trial);
        System.out.println("mean = " + ps.mean());
        System.out.println("stddev = " + ps.stddev());
        System.out.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }      // test client (described below)
}