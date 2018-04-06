import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    int[] openSites;
    public PercolationStats(int n, int trials) {
        int m=0;
        openSites = new int[trials];
        for(int i=0; i!= trials; i++) {
            Percolation percolation = new Percolation(n);
            for (int k = 0; k != n; i++) {
                int row = StdRandom.uniform(n);
                int col = StdRandom.uniform(n);
                if (percolation.isFull(row, col)) {
                    percolation.open(row, col);
                }
            }
            if(percolation.percolates()){
                openSites[m] = percolation.numberOfOpenSites();
                m++;
            }
        }
    }   // perform trials independent experiments on an n-by-n grid



    public double mean()     {
        return StdStats.mean(openSites);
    }                     // sample mean of percolation threshold
    public double stddev()    {return 0;}                    // sample standard deviation of percolation threshold
    public double confidenceLo(){return 0;}                  // low  endpoint of 95% confidence interval
    public double confidenceHi() {return 0;}                 // high endpoint of 95% confidence interval

    public static void main(String[] args)  {
        PercolationStats ps = new PercolationStats(200, 100);
        System.out.println("mean = " + ps.mean());
    }      // test client (described below)
}