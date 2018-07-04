public class TestPercolation {

    public static void main (String[] args){
        Percolation percolation = new Percolation(6);
        for(int n=1; n!=7; n++) {
            for (int m = 1; m != 7; m++) {
                if (percolation.isOpen(n, m)) {
                    System.out.print(0 + " ");
                } else {
                    System.out.print(1 + " ");
                }
            }
            System.out.println();
        }
        percolation.open(1,6);
        printDataForColumn(percolation, 1,6);
        percolation.open(2,6);
        printDataForColumn(percolation, 2,6);
        percolation.open(3,6);
        printDataForColumn(percolation, 3,6);
        percolation.open(4,6);
        printDataForColumn(percolation, 4,6);
        percolation.open(5,6);
        printDataForColumn(percolation, 5,6);
        percolation.open(5,5);
        printDataForColumn(percolation, 5,5);
        percolation.open(4,4);
        printDataForColumn(percolation, 4,4);
        percolation.open(3,4);
        printDataForColumn(percolation, 3,4);
        percolation.open(2,4);
        printDataForColumn(percolation, 2,6);
        percolation.open(2,3);
        percolation.open(2,2);
        percolation.open(2,1);
        percolation.open(3,1);
        percolation.open(4,1);
        percolation.open(5,1);
        percolation.open(5,2);
        percolation.open(6,2);
        percolation.open(5,4);
        for(int n=1; n!=7; n++) {
            for(int m=1; m!=7; m++) {
                if(percolation.isOpen(n,m)) {
                    System.out.print(0 +" ");
                } else {
                    System.out.print(1 +" ");
                }
            }
            System.out.println();
        }
        printDataForColumn(percolation, 1,1);
    }

    public static void printDataForColumn(Percolation percolation, int row, int col){
        System.out.println("row = " + row + ", col = " + col);
        System.out.println("percolation.isOpen() " + percolation.isOpen(row,col));
        System.out.println("percolation.percolates() " + percolation.percolates());
        System.out.println("percolation.numberOfOpenSites() " + percolation.numberOfOpenSites());
        System.out.println("percolation.isFull() " + percolation.isFull(row, col));
    }
}
