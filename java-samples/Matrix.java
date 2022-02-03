import java.sql.Array;
import java.util.Arrays;

public class Matrix {
    
    public static void main(String[] args) {
        // int[][] matrix = new int[][]{
        //     {1, 2, 3},
        //     {4, 5, 6},
        //     {7, 8, 9}
        // };
        // diagonalTraversal(matrix);
        // System.out.println();
        // antiDiagonalTraversal(matrix);
        int[][] matrix = new int[][]{
            {0, 2, 4},
            {1, 3, 5},
            {6, 8, 10}
        };
        System.out.println(Arrays.toString(find(matrix, 3)));
    }

    public static void diagonalTraversal(int[][] matrix) {
        // low half
        for (int row = matrix.length - 1; row >= 0; row--) {
            int i = row;
            int j = 0;
            while (i < matrix.length) {
                System.out.println(matrix[i][j]);
                i++;
                j++;
            }
        }

        // upper half
        for (int col = 1; col < matrix[0].length; col++) {
            int i = 0;
            int j = col;
            while (j < matrix[0].length) {
                System.out.println(matrix[i][j]);
                i++;
                j++;
            }
        }
    }

    public static void antiDiagonalTraversal(int[][] matrix) {
        // low half
        for (int col = 0; col < matrix[0].length; col++) {
            int i = 0;
            int j = col;
            while (i < matrix.length && j >= 0) {
                System.out.println(matrix[i][j]);
                i++;
                j--;
            }
        }

        // upper half
        for (int row = 1; row < matrix.length; row++) {
            int i = row;
            int j = matrix[row].length - 1;
            while (i < matrix.length && j >= 0) {
                System.out.println(matrix[i][j]);
                i++;
                j--;
            }
        }
    }

    // matrix is sorted rowize and columnwise
    // e.g.
    // 0 2 4
    // 1 3 5
    // 6 8 10
    public static int[] find(int[][] matrix, int target) {
        int n = matrix.length;
        int m = matrix[0].length;
        int i = 0;
        int j = m - 1;
        while (i < n && j >= 0) {
            if (matrix[i][j] > target) {
                j--;
            } else if (matrix[i][j] < target) {
                i++;
            } else {
                return new int[]{i, j};
            }
        }
        return new int[] {-1, -1};
    }
}
