import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Percolation {

    private int[][] percolationMatrix;
    private int[][] rootNumbers;
    private int numberOfSites = 0;
    private int n;
    private WeightedQuickUnionUF quickUnionUF;


    public Percolation(int n) {
        this.n = n;
        quickUnionUF = new WeightedQuickUnionUF(n*n+2);
        rootNumbers = new int[n][n];
        percolationMatrix = new int[n][n];
        int s=1;
        for (int i = 0; i != n; i++) {
            for (int m = 0; m != n; m++) {
                percolationMatrix[i][m] = 1;
                rootNumbers[i][m] = s;
                s++;
            }
        }
        getOpenSitesWhenPercolate(n);
    }

    public void open(int row, int col) {
        validateRowCol(row, col);
        row--;
        col--;
        percolationMatrix[row][col] = 0;
        checkForConnectionAndAdd(row, col);
        numberOfSites++;
    }    // open site (row, col) if it is not open already

    private void checkForConnectionAndAdd(int row, int col){
        if(row == 0){
            quickUnionUF.union(0, rootNumbers[row][col]);
        }
        if(row == this.n-1) {
            quickUnionUF.union(n*n+1, rootNumbers[row][col]);
        }
        if(row-1 >= 0 && percolationMatrix[row-1][col] == 0){
            quickUnionUF.union(rootNumbers[row-1][col], rootNumbers[row][col]);
        }
        if(row+1 < rootNumbers.length && percolationMatrix[row+1][col] == 0){
            quickUnionUF.union(rootNumbers[row+1][col], rootNumbers[row][col]);
        }
        if(col-1 >= 0 && percolationMatrix[row][col-1] == 0){
            quickUnionUF.union(rootNumbers[row][col-1], rootNumbers[row][col]);
        }
        if(col+1 < rootNumbers.length && percolationMatrix[row][col+1] == 0){
            quickUnionUF.union(rootNumbers[row][col+1], rootNumbers[row][col]);
        }
    }

    public boolean isOpen(int row, int col) {
        validateRowCol(row, col);
        row--;
        col--;
        return percolationMatrix[row][col] == 0;
    }  // is site (row, col) open?

    public boolean isFull(int row, int col) {
        validateRowCol(row, col);
        row--;
        col--;
        return percolationMatrix[row][col] == 1;
    }  // is site (row, col) full?

    public int numberOfOpenSites() {
        return numberOfSites;
    } // number of open sites

    public boolean percolates() {
        return quickUnionUF.connected(0, this.n*this.n+1);
    } // does the system percolate?


    public static void main(String[] args) {
        int n = Integer.valueOf(args[0]);
        Percolation percolation = new Percolation(n);
        System.out.println(percolation.getOpenSitesWhenPercolate(n));
        System.out.println(percolation);
    }// test client (optional)

    private int getOpenSitesWhenPercolate(int n){
        while(!percolates()){
            while(!setOpenSite(n));
        }
        return numberOfOpenSites();
    }

    private boolean setOpenSite(int n){
        int row = StdRandom.uniform(1,n+1);
        int col = StdRandom.uniform(1,n+1);
        if(this.isFull(row, col)){
            this.open(row, col);
            return true;
        }
        return false;
    }

    private void validateRowCol(int row, int col) {
        if (row <= 0 || col <= 0) {
            throw new IllegalArgumentException();
        }
    }


    private void readMatrix(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader("matrix.txt"));
            String line = reader.readLine();
            int m=0;
            int n=0;
            while(line!=null){
                for(int i=0; i!=line.length(); i++){
                    if(!(line.charAt(i) == ' ')) {
                        percolationMatrix[n][m] = Integer.valueOf("" +line.charAt(i));
                        if(percolationMatrix[n][m] == 0){
                            open(n,m);
                        }
                        m++;
                    }
                }
                n++;
                m=0;
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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