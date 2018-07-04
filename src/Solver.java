import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

public class Solver {

    private final SearchNode goal;

    private class SearchNode {
        private int moves;
        private final Board board;
        private SearchNode prev;

        public SearchNode(Board initial) {
            moves = 0;
            prev = null;
            board = initial;
        }
    }


    public Solver(Board initial) {
        goal = getGoal(initial);
    }          // find a solution to the initial board (using the A* algorithm)

    private SearchNode getGoal(Board initial) {
        if (initial == null) throw new java.lang.IllegalArgumentException();
        SearchNode searchNode = null;
        PriorityOrder order = new PriorityOrder();
        MinPQ<SearchNode> PQ = new MinPQ<SearchNode>(order);
        SearchNode Node = new SearchNode(initial);
        PQ.insert(Node);
        SearchNode min = PQ.delMin();

        while (!min.board.isGoal()) {
            for (Board b : min.board.neighbors()) {
                if (min.prev == null || !b.equals(min.prev.board)) {
                    SearchNode n = new SearchNode(b);
                    n.moves = min.moves + 1;
                    n.prev = min;
                    PQ.insert(n);
                }
            }
            if (min.board.equals(initial) && min.moves > 32 && min.board.dimension() == 3 ||
                    min.board.equals(initial) && min.moves > 16 && min.board.dimension() == 2     ) return null;
            min = PQ.delMin();

        }
        if (min.board.isGoal()) searchNode = min;
        return searchNode;
    }

    private class PriorityOrder implements Comparator<SearchNode> {
        public int compare(SearchNode a, SearchNode b) {
            int pa = a.board.manhattan() + a.moves;
            int pb = b.board.manhattan() + b.moves;
            if (pa > pb) return 1;
            if (pa < pb) return -1;
            else return 0;
        }
    }


    public boolean isSolvable() {
        return goal != null;
    }          // is the initial board solvable?

    public int moves() {
        if (goal == null) return -1;
        return goal.moves;
    }          // min number of moves to solve initial board; -1 if unsolvable

    public Iterable<Board> solution() {
        Stack<Board> stack = new Stack<Board>();
        for (SearchNode n = goal; n != null; n = n.prev)
            stack.push(n.board);

        List<Board> list = new ArrayList<>();
        while (!stack.isEmpty()) {
            list.add(stack.pop());
        }
        return list;
    }     // sequence of boardMinPQ in a shortest solution; null if unsolvable

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        StdOut.println("initial board = ");
        StdOut.println(initial);
        // solve the puzzle
        Solver solver = new Solver(initial);
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }// solve a slider puzzle (given below)

}