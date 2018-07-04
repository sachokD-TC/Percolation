import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;
import java.util.List;

public class Board {

    private final int[][] blocks;
    private final int[][] etalonBlocks;
    private final int[] moveUp = new int[]{0, -1};
    private final int[] moveDown = new int[]{0, 1};
    private final int[] moveLeft = new int[]{-1, 0};
    private final int[] moveRight = new int[]{1, 0};
    private final int dimention;
    private int[] hammingArray;
    private int[][] coordArray;
    private int[] manhattenArray;
    private int[] zeroCoord;

    public Board(int[][] blocks) {
        this.blocks = blocks;
        this.dimention = blocks.length;
        etalonBlocks = new int[blocks.length][blocks.length];
        hammingArray = new int[blocks.length * blocks.length + 1];
        manhattenArray = new int[blocks.length * blocks.length + 1];
        coordArray = new int[manhattenArray.length][2];
        fillEtalonBlocks();
    }           // construct a board from an n-by-n array of blocks  (where blocks[i][j] = block in row i, column j)

    private void fillEtalonBlocks() {
        int b = 1;
        for (int i = 0; i != blocks.length; i++) {
            for (int j = 0; j != blocks.length; j++) {
                if (blocks[i][j] == 0) {
                    zeroCoord = new int[]{i, j};
                }
                etalonBlocks[i][j] = b;
                coordArray[b] = new int[]{i + 1, j + 1};
                if (blocks[i][j] != etalonBlocks[i][j]) {
                    hammingArray[b] = 1;
                }
                b++;
            }
        }
        hammingArray[hammingArray.length - 1] = 0;
        b = 1;
        for (int i = 0; i != blocks.length; i++) {
            for (int j = 0; j != blocks.length; j++) {
                int block = blocks[i][j];
                manhattenArray[block] = java.lang.Math.abs((i + 1) - coordArray[block][0]) + java.lang.Math.abs((j + 1) - coordArray[block][1]);
                b++;
            }
        }
        manhattenArray[0] = 0;
        etalonBlocks[blocks.length - 1][blocks.length - 1] = 0;
    }

    public int dimension() {
        return dimention;
    }                // board dimension n

    public int hamming() {
        int h = 0;
        for (int i = 0; i != hammingArray.length; i++) h += hammingArray[i];
        return h;
    }                 // number of blocks out of place

    public int manhattan() {
        int m = 0;
        for (int i = 0; i != manhattenArray.length; i++) m += manhattenArray[i];
        return m;
    }       // sum of Manhattan distances between blocks and goal


    public boolean isGoal() {
        int b = 1;
        for (int i = 0; i != blocks.length; i++) {
            for (int j = 0; j != blocks.length; j++) {
                if (b == dimention * dimension()) b = 0;
                if (blocks[i][j] != b)
                    return false;
                b++;
            }
        }
        return true;
    }

    public Board twin() {
        if (dimension() == 1) return null;
        int[] firstCell = new int[]{0, 0};
        int[] secondCell = new int[]{dimention - 1, dimention - 1};
        if (blocks[0][0] == 0) {
            firstCell = new int[]{0, 1};
        }
        if (blocks[dimention - 1][dimention - 1] == 0) {
            secondCell = new int[]{dimention - 1, dimention - 2};
        }
        return new Board(flipBlocks(firstCell, secondCell, cloneBlocks()));
    }              // a board that is obtained by exchanging any pair of blocks


    private int[][] cloneBlocks() {
        int[][] twinBlocks = new int[blocks.length][blocks.length];
        for (int i = 0; i != blocks.length; i++) {
            for (int j = 0; j != blocks.length; j++) {
                twinBlocks[i][j] = blocks[i][j];
            }
        }
        return twinBlocks;
    }

    private int[][] flipBlocks(int[] firstCell, int[] secondCell, int[][] twinBlocks) {
        int tempCell = twinBlocks[firstCell[0]][firstCell[1]];
        twinBlocks[firstCell[0]][firstCell[1]] = twinBlocks[secondCell[0]][secondCell[1]];
        twinBlocks[secondCell[0]][secondCell[1]] = tempCell;
        return twinBlocks;
    }


    public boolean equals(Object y) {
        if (y == null) return false;
        if (!(y instanceof Board)) return false;
        Board board = (Board) y;
        if (board.dimention != dimention) return false;
        for (int i = 0; i != this.blocks.length; i++) {
            for (int j = 0; j != this.blocks.length; j++) {
                if (board.blocks[i][j] != this.blocks[i][j])
                    return false;
            }
        }
        return true;
    } // does this board equal y?

    public Iterable<Board> neighbors() {
        List<Board> boardHashSet = new LinkedList<>();
        int[][] moves = new int[][]{moveUp, moveDown, moveLeft, moveRight};
        for (int i = 0; i != moves.length; i++) {
            Board neighborBoard = move(moves[i], cloneBlocks());
            if (neighborBoard != null) {
                boardHashSet.add(neighborBoard);
            }
        }
        return boardHashSet;
    }    // all neighboring boards


    private Board move(int[] move, int[][] blocksFromMatrix) {
        if (move[1] != 0 && zeroCoord[1] + move[1] >= 0 && (zeroCoord[1] + move[1]) <= this.blocks.length - 1) {
            blocksFromMatrix = flipBlocks(new int[]{zeroCoord[0], zeroCoord[1] + move[1]}, zeroCoord, blocksFromMatrix);
            return new Board(blocksFromMatrix);
        }
        if (move[0] != 0 && zeroCoord[0] + move[0] >= 0 && (zeroCoord[0] + move[0]) <= this.blocks.length - 1) {
            blocksFromMatrix = flipBlocks(new int[]{zeroCoord[0] + move[0], zeroCoord[1]}, zeroCoord, blocksFromMatrix);
            return new Board(blocksFromMatrix);
        }
        return null;
    }


    public String toString() {
        StringBuffer strMatrix = new StringBuffer();
        strMatrix.append(dimension() + "\n");
        for (int i = 0; i != this.blocks.length; i++) {
            for (int j = 0; j != this.blocks.length; j++) {
                strMatrix.append(String.format("%2d ", this.blocks[i][j]));
            }
            strMatrix.append("\n");
        }
        return strMatrix.toString();
    }           // string representation of this board (in the output format specified below)

    public static void main(String[] args) {
        Board board = new Board(new int[][]{{0, 1, 3}, {4, 2, 5}, {7, 8, 6}});
        StdOut.println("board = ");
        StdOut.println(board);
        StdOut.println("is goal = " + board.isGoal());
        StdOut.println("twin = " + board.twin());

    }// unit tests (not graded)
}