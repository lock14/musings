import java.util.Scanner;

public class SimpleGrid {
	private static final int SIZE = 9;
	private int[][] grid;
	
	public SimpleGrid(Scanner input) {
		grid = new int[9][9];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (input.hasNextInt()) {
					grid[i][j] = input.nextInt();
				} else {
					input.next();
				}
			}
		}
	}
	
	public void place(int cell, int value) {
		grid[cell / SIZE][cell % SIZE] = value;
	}
	
	public void remove(int cell) {
		grid[cell / SIZE][cell % SIZE] = 0;
	}
	
	public int getUnassignedLocation() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == 0) {
					return i * 9 + j;
				}
			}
		}
		return -1;
	}
	
	public boolean noConflicts(int cell, int value) {
		int row = cell / SIZE;
		int column = cell % SIZE;
		if (grid[row][column] != 0) {
			return false;
		}
		for (int i = 0; i < grid[row].length; i++) {
			if (grid[row][i] == value) {
				return false;
			}
		}
		for (int i = 0; i < grid.length; i++) {
			if (grid[i][column] == value) {
				return false;
			}
		}
		row -= row % 3;
		column -= column % 3;
		for (int i = row; i < row + 3; i++) {
			for (int j = column; j < column + 3; j++) {
				if (grid[i][j] == value) {
					return false;
				}
			}	
		}
		return true;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < grid.length; i++) {
			if (i % 3 == 0) {
				sb.append(separator());
			}
			for (int j = 0; j < grid[i].length; j++) {
				if (j % 3 == 0) {
					sb.append("| ");
				}
				if (grid[i][j] != 0) {
					sb.append(grid[i][j]);
					sb.append(" ");
				} else {
					sb.append("- ");
				}
			}
			sb.append("|\n");
			
		}
		return sb.append(separator()).toString();
	}
	
	private String separator() {
		StringBuilder sb = new StringBuilder().append("+");
		for (int i = 0; i < SIZE / 3 ; i++) {
			for (int j = 0; j < SIZE - 2; j++) {
				sb.append("-");
			}
			sb.append("+");
		}
		return sb.append("\n").toString();
	}
}