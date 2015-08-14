public class Sum {
    public interface Functor {
        public double sum(int... indices);
    }

    public static double sum(int start, int end, Functor function) {
        double result = 0.0;
        for (int i = start; i <= end; i++) {
            result += function.sum(i);
        }
        return result;
    }

    public static double doubleSum(int outerStart, int outerEnd, 
                                   int innerStart, int innerEnd, 
                                   Functor function) {
        double result = 0.0;
        for (int i = outerStart; i <= outerEnd; i++) {
            for (int j = innerStart; j <= innerEnd; j++) {
                result += function.sum(i, j);
            }
        }
        return result;
    }

    public static double tripleSum(int outerStart, int outerEnd, 
                                   int middleStart, int middleEnd, 
                                   int innerStart, int innerEnd, 
                                   Functor function) {
        double result = 0.0;
        for (int i = outerStart; i <= outerEnd; i++) {
            for (int j = middleStart; j <= middleEnd; j++) {
                for (int k = innerStart; k <= innerEnd; k++) {
                    result += function.sum(i, j, k);
                }
            }
        }
        return result;
    }
}
