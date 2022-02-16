import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class NQueens {

    public static void main(String[] args) {
        AtomicInteger solution = new AtomicInteger(1);
        solveNQueens(8).forEach(list -> {
            System.out.printf("Solution %d%n", solution.getAndIncrement());
            list.forEach(System.out::println);
        });
    }

    public static List<List<String>> solveNQueens(int n) {
        Board board = new Board(n);
        List<List<String>> list = new ArrayList<>();
        search(board, n, 0, list);
        return list;
    }

    private static void search(Board board, int queens, int row, List<List<String>> list) {
        if (queens == 0) {
            list.add(board.asList());
        } else {
            for (int col = 0; col < board.size(); col++) {
                if (board.placeQueen(row, col)) {
                    search(board, queens - 1, row + 1, list);
                    board.removeQueen(row, col);
                }
            }
        }
    }

    private static class Board {
        private final Map<Integer, Integer> placements;
        private final SightLines sightLines;
        private final int n;

        Board(int n) {
            this.placements = new HashMap<>();
            this.sightLines = new SightLines(n);
            this.n = n;
        }

        int size() {
            return n;
        }

        boolean placeQueen(int row, int col) {
            if (!sightLines.visible(row, col)) {
                sightLines.add(row, col);
                placements.put(row, col);
                return true;
            }
            return false;
        }

        void removeQueen(int row, int col) {
            sightLines.remove(row, col);
            placements.remove(row);
        }

        List<String> asList() {
            List<String> rowStrings = new ArrayList<>(n);
            List<String> list = new ArrayList<>();
            for (int row = 0; row < n; row++) {
                Optional<Integer> rowPlacement = Optional.ofNullable(placements.get(row));
                rowStrings.add(rowPlacement.filter(c -> c == 0).map(c -> "Q").orElse("#"));
                for (int col = 1; col < n; col++) {
                    final int column = col;
                    rowStrings.add(rowPlacement.filter(c -> c == column).map(c -> " Q").orElse(" #"));
                }
                list.add(String.join("", rowStrings));
                rowStrings.clear();
            }
            return list;

        }

        private static class SightLines {
            private final BitSet rows;
            private final BitSet cols;
            private final BitSet upperLeftDiag;
            private final BitSet upperRightDiag;
            private final int n;

            SightLines(int n) {
                this.n = n;
                this.rows = new BitSet(n);
                this.cols = new BitSet(n);
                this.upperLeftDiag = new BitSet(2 * n - 1);
                this.upperRightDiag = new BitSet(2 * n - 1);
            }

            void add(int row, int col) {
                rows.set(rowKey(row, col));
                cols.set(colKey(row, col));
                upperLeftDiag.set(upperLeftDiagonalKey(row, col));
                upperRightDiag.set(upperRightDiagonalKey(row, col));
            }

            void remove(int row, int col) {
                rows.set(rowKey(row, col), false);
                cols.set(colKey(row, col), false);
                upperLeftDiag.set(upperLeftDiagonalKey(row, col), false);
                upperRightDiag.set(upperRightDiagonalKey(row, col), false);
            }

            boolean visible(int row, int col) {
                return rows.get(rowKey(row, col))
                       || cols.get(colKey(row, col))
                       || upperLeftDiag.get(upperLeftDiagonalKey(row, col))
                       || upperRightDiag.get(upperRightDiagonalKey(row, col));
            }

            private int rowKey(int row, int col) {
                return row;
            }

            private int colKey(int row, int col) {
                return col;
            }

            private int upperLeftDiagonalKey(int row, int col) {
                return row + col;
            }

            private int upperRightDiagonalKey(int row, int col) {
                return row - col + n - 1;
            }
        }

    }
}
