public class SudokuStdOutDisplayer implements SudokuDisplayer {
    public void display(SudokuGrid puzzle) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < SudokuGrid.BOARD_SIZE; i++) {
			if (i % 3 == 0) {
				System.out.println(separator());
			}
			for (int j = 0; j < SudokuGrid.BOARD_SIZE; j++) {
				if (j % 3 == 0) {
					System.out.print("| ");
				}
				Integer cellValue = puzzle.get(i * SudokuGrid.BOARD_SIZE + j);
				if (cellValue != null) {
					System.out.print(cellValue + " ");
				} else {
					System.out.print("- ");
				}
			}
			System.out.println("|");
			
		}
		System.out.println(separator());
	}

    public void displayMessage(String message) {
        System.out.println(message);
    }
	
	private String separator() {
		StringBuilder sb = new StringBuilder().append("+");
		for (int i = 0; i < SudokuGrid.BOARD_SIZE / 3 ; i++) {
			for (int j = 0; j < SudokuGrid.BOARD_SIZE - 2; j++) {
				sb.append("-");
			}
			sb.append("+");
		}
		return sb.toString();
	}
}
