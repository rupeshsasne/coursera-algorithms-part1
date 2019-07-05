import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private final Stack<Board> solution;

    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        solution = solveBoard(initial);
    }

    public boolean isSolvable() {
        return solution != null;
    }

    public int moves() {
        return isSolvable() ? solution.size() - 1 : -1;
    }

    public Iterable<Board> solution() {
        return solution;
    }

    private Stack<Board> solveBoard(Board initial) {
        SearchNode searchNode = new SearchNode(initial, 0, null);
        SearchNode twinSearchNode = new SearchNode(initial.twin(), 0, null);

        MinPQ<SearchNode> minPQ = new MinPQ<>();
        minPQ.insert(searchNode);

        MinPQ<SearchNode> twinMinPQ = new MinPQ<>();
        twinMinPQ.insert(twinSearchNode);

        while (!minPQ.min().board.isGoal() && !twinMinPQ.min().board.isGoal()) {
            processMinBoard(minPQ);
            processMinBoard(twinMinPQ);
        }

        return extractSolution(minPQ);
    }

    private Stack<Board> extractSolution(MinPQ<SearchNode> minPQ) {
        Stack<Board> solutionStack = null;

        if (minPQ.min().board.isGoal()) {
            solutionStack = new Stack<>();

            SearchNode curr = minPQ.min();
            solutionStack.push(curr.board);

            while (curr.prev != null) {
                curr = curr.prev;
                solutionStack.push(curr.board);
            }
        }

        return solutionStack;
    }

    private void processMinBoard(MinPQ<SearchNode> minPQ) {
        SearchNode node = minPQ.delMin();

        for (Board board : node.board.neighbors()) {
            if (node.prev == null || !board.equals(node.prev.board)) {
                SearchNode neighbor = new SearchNode(board, node.moves + 1, node);
                minPQ.insert(neighbor);
            }
        }
    }

    private static class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final int priority;
        private final int moves;
        private final SearchNode prev;

        SearchNode(Board board, int moves, SearchNode prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
            this.priority = board.manhattan() + moves;
        }

        @Override
        public int compareTo(SearchNode other) {
            return Integer.compare(priority, other.priority);
        }
    }
}
