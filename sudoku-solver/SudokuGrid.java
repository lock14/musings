
public class SudokuGrid extends Grid<Integer> {
	public static final int BOARD_SIZE = 9;
	public static final int SQUARE_SIZE = 3;
	
	public SudokuGrid() {
		super(BOARD_SIZE);
	}
	
	public void place(int cell, int value) {
		checkIfValidValue(value);
		super.place(cell, value);
	}

    public SudokuGrid solution() {
        SudokuGrid solution = new SudokuGrid();
        for (int i = 0; i < 81; i++) {
            solution.place(i, super.get(i));
        }
        return explore(solution, solution.getUnassignedCell());
    }
	
	private SudokuGrid explore(SudokuGrid puzzle, int cell) {
		if (cell == -1) {
			return puzzle;
		} else {
			for (int i = 1; i <= 9; i++) {
                if (puzzle.noConflicts(cell, i)) {
                    puzzle.place(cell, i);
                    SudokuGrid solution = explore(puzzle, puzzle.getUnassignedCell());
                    if (solution != null) {
                        return solution;
                    }
                    puzzle.remove(cell);
                }
			}
            return null;
		}
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < BOARD_SIZE; i++) {
			if (i % 3 == 0) {
				sb.append(separator());
			}
			for (int j = 0; j < BOARD_SIZE; j++) {
				if (j % 3 == 0) {
					sb.append("| ");
				}
				Integer cellValue = super.get(i * BOARD_SIZE + j);
				if (cellValue != null) {
					sb.append(cellValue);
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
		for (int i = 0; i < BOARD_SIZE / 3 ; i++) {
			for (int j = 0; j < BOARD_SIZE - 2; j++) {
				sb.append("-");
			}
			sb.append("+");
		}
		return sb.append("\n").toString();
	}
	
    private boolean noConflicts(int cell, int value) {
		checkIfValidValue(value);
		int column = cell % BOARD_SIZE;
		int row = cell / BOARD_SIZE;
		return noRowConflicts(row, value) && noColumnConflicts(column, value) && noBoxConflicts(row, column, value);
	}
	
	
	private boolean noRowConflicts(int row, int value) {
		int startCell = row * BOARD_SIZE;
		int endCell = startCell + (BOARD_SIZE - 1);
		for (int cell = startCell; cell <= endCell; cell++) {
			if (super.get(cell) != null && super.get(cell) == value) {
				return false;
			}
		}
		return true;
	}
	
	private boolean noColumnConflicts(int column, int value) {
		int startCell = column;
		int endCell = startCell + (BOARD_SIZE * 8);
		for (int cell = startCell; cell <= endCell; cell += BOARD_SIZE) {
			if (super.get(cell) != null && super.get(cell) == value) {
				return false;
			}
		}
		return true;
	}
	
	private boolean noBoxConflicts(int row, int column, int value) {
		row -= row % SQUARE_SIZE;
		column -= column % SQUARE_SIZE;
		for (int i = row; i < row + SQUARE_SIZE; i++) {
			for (int j = column; j < column + SQUARE_SIZE; j++) {
				int cell = i * BOARD_SIZE + j;
				if (super.get(cell) != null && super.get(cell) == value) {
					return false;
				}
			}	
		}
		return true;
	}
	
	private void checkIfValidValue(int value) {
		if (value < 1 || value > BOARD_SIZE) {
			throw new IllegalArgumentException("Invalid number: " + value);
		}
	}
}/**/
