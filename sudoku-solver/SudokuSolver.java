import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SudokuSolver {
	public static void main(String[] args) {
		if (args.length == 0) {
			System.err.println("no valid file provided. terminating.");
		} else {
			try {
                SudokuGrid puzzle = new SudokuGrid();
				populateBoard(puzzle, new Scanner(new File(args[0])));
				SudokuDisplayer displayer = new SudokuStdOutDisplayer();
				
				displayer.displayMessage("Initial Board Setup:");
				displayer.display(puzzle);
				displayer.displayMessage("");

                SudokuGrid solution = puzzle.solution();
				if (solution == null) {
					displayer.displayMessage("No Solution");
				} else {
					displayer.displayMessage("One Solution is as follows:");
                    displayer.display(solution);
				}
			} catch (FileNotFoundException fne) {
				System.err.println("puzzle file given does not exist. terminating.");
			} catch (Exception e) {
				System.err.println("puzzle file is malformed. terminating.");
			}
		}
	}
	
	private static void populateBoard(SudokuGrid puzzle, Scanner input) throws Exception {
		for (int i = 0; i < 81; i++) {
			if (input.hasNextInt()) {
				puzzle.place(i, input.nextInt());
			} else {
				input.next();
			}
		}
	}
}
