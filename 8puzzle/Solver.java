/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {
    private boolean isSolvable;
    private Node initialNode;
    private Node twinInitialNode;
    private Stack<Board> puzzle;
    private int numberOfMinMoves;


    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        puzzle = new Stack<>();

        this.initialNode = new Node(initial, null);
        this.twinInitialNode = new Node(initial.twin(), null);
        solvePuzzle();
    }

    private class CompareByPriority implements Comparator<Node> {
        public int compare(Node o1, Node o2) {
            return Integer.compare(o1.priority, o2.priority);
        }
    }

    private class Node {
        private Node previousSearchNode;
        private Board board;
        private int moves;
        private int priority;

        public Node(Board board, Node previousSearchNode) {
            this.board = board;
            this.previousSearchNode = previousSearchNode;
            if (previousSearchNode != null) {
                moves = previousSearchNode.moves + 1;
            }
            else {
                moves = 0;
            }
            priority = moves + board.manhattan();
        }
    }

    public int moves() {
        if (isSolvable) {
            return numberOfMinMoves;
        }
        else {
            return -1;
        }
    }


    public boolean isSolvable() {
        return isSolvable;
    }

    public Iterable<Board> solution() {
        if (!isSolvable()) return null;

        Queue<Board> cloneStack = new Queue<>();
        for (Board temp : puzzle) {
            cloneStack.enqueue(temp);
        }
        return cloneStack;

    }

    private void solvePuzzle() {

        MinPQ<Node> priorityQueue = new MinPQ<>(new CompareByPriority());
        MinPQ<Node> twinPriorityQueue = new MinPQ<>(new CompareByPriority());
        twinPriorityQueue.insert(twinInitialNode);
        priorityQueue.insert(initialNode);
        Node deletedNode;
        Node twinDeletedNode;
        while (true) {
            deletedNode = priorityQueue.delMin();
            twinDeletedNode = twinPriorityQueue.delMin();
            Node previousNode = deletedNode.previousSearchNode;
            Node twinPreviousNode = twinDeletedNode.previousSearchNode;
            if (deletedNode.board.isGoal()) {
                numberOfMinMoves = deletedNode.moves;
                isSolvable = true;
                break;
            }

            if (twinDeletedNode.board.isGoal()) {
                break;
            }

            for (Board temp : deletedNode.board.neighbors()) {
                if (previousNode == null) {
                    priorityQueue.insert(new Node(temp, deletedNode));
                }
                else {
                    if (!previousNode.board.equals(temp)) {
                        priorityQueue.insert(new Node(temp, deletedNode));
                    }
                }
            }

            for (Board temp : twinDeletedNode.board.neighbors()) {
                if (twinPreviousNode == null) {
                    twinPriorityQueue.insert(new Node(temp, twinDeletedNode));
                }
                else {
                    if (!twinPreviousNode.board.equals(temp)) {
                        twinPriorityQueue.insert(new Node(temp, twinDeletedNode));
                    }
                }
            }
        }

        if (isSolvable) {
            while (deletedNode != null) {
                puzzle.push(deletedNode.board);
                deletedNode = deletedNode.previousSearchNode;
            }
        }
    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
                StdOut.println();
            }
        }
    }
}
