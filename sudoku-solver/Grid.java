import java.util.Arrays;

public abstract class Grid<T> {
	private int size;
	private Object[][] grid;
	
	public Grid(int size) {
		grid = new Object[size][size];
		this.size = size;
	}
	
    @SuppressWarnings("unchecked")
	public T get(int cell) {
		checkIfValidCell(cell);
		return (T) grid[cell / size][cell % size];
	}
	
	public void place(int cell, T value) {
		checkIfValidCell(cell);
		grid[cell / size][cell % size] = value;
	}
	
	public void remove(int cell) {
		checkIfValidCell(cell);
		grid[cell / size][cell % size] = null;
	}
	
	public int getUnassignedCell() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == null) {
					return i * size + j;
				}
			}
		}
		return -1;
	}
	
	public String toString() {
        return Arrays.toString(grid);
	}
	
	private void checkIfValidCell(int cell) {
		int maxCell = (size * size) - 1;
		if (cell < 0 || cell > maxCell) {
			throw new IllegalArgumentException("cell value: " + cell);
		}
	}
}
