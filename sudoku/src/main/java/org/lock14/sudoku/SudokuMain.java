package org.lock14.sudoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.lock14.sudoku.backend.SudokuGrid;
import org.lock14.sudoku.display.SudokuDisplayer;
import org.lock14.sudoku.display.SudokuStdOutDisplayer;

public class SudokuMain {
    public static void main(String[] args) {
        try {
            parseOptions(args);
            Scanner input = getScanner(args);
            // do processing here
            SudokuGrid puzzle = new SudokuGrid();
            populateBoard(puzzle, input); 
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
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (IllegalStateException ex) { // throw this if input is malformed
            System.err.println(ex.getMessage()); // define meaningful message when thrown
        } catch (IllegalArgumentException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    private static void populateBoard(SudokuGrid puzzle, Scanner input) throws IllegalStateException {
        try {
            for (int i = 0; i < 81; i++) {
                if (input.hasNextInt()) {
                    puzzle.place(i, input.nextInt());
                } else {
                    input.next();
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException("puzzle file is malformed.");
        }
    }

    private static Scanner getScanner(String[] args) throws FileNotFoundException {
        if (args.length > 0 && args[args.length - 1].charAt(0) != '-') {
            try {
                return new Scanner(new File(args[args.length - 1]));
            } catch (FileNotFoundException ex) {
                // caught and rethrown for more user friendly message
                throw new FileNotFoundException(args[args.length - 1] + " does not exist");
            }
        } else {
            return new Scanner(System.in);
        }
    }

    private static void parseOptions(String[] args) {
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.charAt(0) == '-') {
                switch (arg) {
                    // put custom options here
                    default: throw new IllegalArgumentException(arg + " is not a valid option");
                }
            }
        }
    }
}

