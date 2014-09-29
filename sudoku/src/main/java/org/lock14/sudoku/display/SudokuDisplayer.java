package org.lock14.sudoku.display;

import org.lock14.sudoku.backend.SudokuGrid;

public interface SudokuDisplayer {
    public void display(SudokuGrid puzzle);

    public void displayMessage(String message); 
}
