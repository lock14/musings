
public class Test {
	public static void main(String[] args) {
		SudokuGrid sg = new SudokuGrid();
		sg.place(0, 9);
		System.out.println(sg.noConflicts(1, 9));
		System.out.println(sg);
		
	}
}
