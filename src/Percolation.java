import edu.princeton.cs.algs4.QuickUnionUF;
import edu.princeton.cs.algs4.StdRandom;

public class Percolation {

    private int[][] percolationMatrix;
    private int numberOfSites = 1;
    private int maxnum;
    private QuickUnionUF quickUnionUF;
    private int[][] openSites;

    public Percolation(int n) {
        maxnum = n+2;
        quickUnionUF = new QuickUnionUF(n*n+2);
        for(int i=0; i!=n; i++){
            quickUnionUF.connected(0,i);
            quickUnionUF.connected(maxnum, n-i);
        }
        openSites = new int[n][n];
        percolationMatrix = new int[n][n];
        for (int i = 0; i != n; i++) {
            for (int m = 0; m != n; m++) {
                percolationMatrix[i][m] = 1;
            }
        }
    }

    public void open(int row, int col) {
        percolationMatrix[row][col] = 0;
        openSites[row][col] = numberOfSites;
        if(numberOfSites > 1) {
            checkForConnectionAndAdd(row, col);
        }
        numberOfSites++;
    }    // open site (row, col) if it is not open already

    private void checkForConnectionAndAdd(int row, int col){
        if(row-1 > 0 && openSites[row-1][col] != 0){
            quickUnionUF.union(openSites[row-1][col],openSites[row][col]);
        }
        if(row+1 < openSites.length && openSites[row+1][col] != 0){
            quickUnionUF.union(openSites[row+1][col],openSites[row][col]);
        }
        if(col-1 > 0 && openSites[row][col-1] != 0){
            quickUnionUF.union(openSites[row][col-1],openSites[row][col]);
        }
        if(col+1 < openSites.length && openSites[row][col+1] != 0){
            quickUnionUF.union(openSites[row][col+1],openSites[row][col]);
        }
    }

    public boolean isOpen(int row, int col) {
        return percolationMatrix[row][col] == 0;
    }  // is site (row, col) open?

    public boolean isFull(int row, int col) {
        return percolationMatrix[row][col] == 1;
    }  // is site (row, col) full?

    public int numberOfOpenSites() {
        return numberOfSites-1;
    } // number of open sites

    public boolean percolates() {
        return quickUnionUF.connected(0, maxnum);
    } // does the system percolate?


    public static void main(String[] args) {
        int n = Integer.valueOf(args[0]);
        Percolation percolation = new Percolation(n);
        for(int i=0; i!=50; i++){
            int row = StdRandom.uniform(n);
            int col = StdRandom.uniform(n);
            if(percolation.isFull(row, col)){
                percolation.open(row, col);
            }
        }
        System.out.println(percolation);
        System.out.println(percolation.percolates());
    }// test client (optional)

    @Override
    public String toString() {
        int n = percolationMatrix.length;
        String precolationMatrixString = "";
        for (int i = 0; i != n; i++) {
            for (int m = 0; m != n; m++) {
                precolationMatrixString += "" + percolationMatrix[i][m] + " ";
            }
            precolationMatrixString += "\n";
        }
        return precolationMatrixString;
    }
}