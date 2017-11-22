import java.util.Collections;

public class ProgressBar {
    public static void main(String[] args) {
        // demo the progress bar
        int n = 1000;
        for (int i = 0; i < n; i++) {
            try {
                Thread.sleep(2);
            } catch (Exception e) {};
            printProgressBar(i, n-1);
        }
    }

    /**
     * Prints a progress to standard out. The total length of the line printed
     * is 80 characters so that the bar will fit on one line with most
     * terminals. Percentage complete displays to two decimal places.
     *
     * Example:
     *  [##################################..........................] 56.67%
     *
     * @param iteration The current iteration
     * @param total the number of iterations
     *
     **/
    public static void printProgressBar(int iteration, int total) {
        printProgressBar(iteration, total, "", "", 2, 69, "#", ".");
    }

    /**
     * Prints a progress to standard out.
     *
     * @param iteration The current iteration
     * @param total the number of iterations
     * @param prefix a string prefix that appears before the percentage marker
     * @param suffix a string suffix that apepars after the percentage marker
     * @param decimals the number of decimal places to display the percentage
     * @param length the length of the progress bar
     * @param fill the character representing filled progress
     * @param unfill the character representing unfilled progress
     *
     **/
    public static void printProgressBar(int iteration, int total, String prefix,
                                   String suffix, int decimals, int length, 
                                   String fill, String unfill) {
        String percent = String.format("%." + decimals + "f", (100.0 * iteration) / total);
        int filledLength = (length * iteration) / total;
        String filled = String.join("", Collections.nCopies(filledLength, fill));
        String unfilled = String.join("", Collections.nCopies(length - filled.length(), unfill));
        String bar = filled + unfilled;
        int pad = 4 + decimals;
        System.out.printf("\r%s [%s] %" + pad + "s%% %s", prefix, bar, percent, suffix);
        if (iteration == total) {
            System.out.println();
        }
    }
}
