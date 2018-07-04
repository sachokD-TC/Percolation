import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int[][] rootNumbers;
    private final int n;
    private final WeightedQuickUnionUF quickUnionUF;
    private boolean[][] percolationMatrix;
    private int numberOfSites = 0;


    public Percolation(int n) {
        this.n = n;
        quickUnionUF = new WeightedQuickUnionUF(n * n + 2);
        rootNumbers = new int[n][n];
        percolationMatrix = new boolean[n][n];
        int s = 1;
        for (int i = 0; i != n; i++) {
            for (int m = 0; m != n; m++) {
                percolationMatrix[i][m] = false;
                rootNumbers[i][m] = s;
                s++;
            }
        }
    }

    public void open(int row, int col) {
        validateRowCol(row, col);
        row--;
        col--;
        percolationMatrix[row][col] = true;
        checkForConnectionAndAdd(row, col);
        numberOfSites++;
    }    // open site (row, col) if it is not open already

    private void checkForConnectionAndAdd(int row, int col) {
        if (row == 0) {
            quickUnionUF.union(0, rootNumbers[row][col]);
        }
        if (row == this.n - 1) {
            quickUnionUF.union(n * n + 1, rootNumbers[row][col]);
        }
        if (row - 1 >= 0 && percolationMatrix[row - 1][col]) {
            quickUnionUF.union(rootNumbers[row - 1][col], rootNumbers[row][col]);
        }
        if (row + 1 < rootNumbers.length && percolationMatrix[row + 1][col]) {
            quickUnionUF.union(rootNumbers[row + 1][col], rootNumbers[row][col]);
        }
        if (col - 1 >= 0 && percolationMatrix[row][col - 1]) {
            quickUnionUF.union(rootNumbers[row][col - 1], rootNumbers[row][col]);
        }
        if (col + 1 < rootNumbers.length && percolationMatrix[row][col + 1]) {
            quickUnionUF.union(rootNumbers[row][col + 1], rootNumbers[row][col]);
        }
    }

    public boolean isOpen(int row, int col) {
        validateRowCol(row, col);
        row--;
        col--;
        return percolationMatrix[row][col];
    }  // is site (row, col) open?

    public boolean isFull(int row, int col) {
        validateRowCol(row, col);
        row--;
        col--;
        return quickUnionUF.connected(0, rootNumbers[row][col]);
    }  // is site (row, col) full?

    public int numberOfOpenSites() {
        return numberOfSites;
    } // number of open sites

    public boolean percolates() {
        return quickUnionUF.connected(0, this.n * this.n + 1);
    } // does the system percolate?


    public static void main(String[] args) {
    }


    private void validateRowCol(int row, int col) {
        if (row <= 0 || col <= 0 || row > n || col > n) {
            throw new IllegalArgumentException();
        }
    }
}