public class NbaProb {
    public static void main(String[] args) {
        Sum.Functor probabilities = new Sum.Functor() {
                                           public double sum(int i) {
                                               return ((12.0 - i) / 66.0) * (11.0 / (66.0 - (12.0 - i)));
                                           }

                                           public double doubleSum(int i, int j) {
                                               if (i != j) {
                                                   return ((12.0 - i) / 66.0) * ((12.0 - j) / (66.0 - (12.0 - i))) * (11.0 / (66.0 - (24.0 - i - j)));
                                               } else {
                                                   return 0.0;
                                               }
                                           }

                                           public double tripleSum(int i, int j, int k) {
                                               throw new UnsupportedOperationException();
                                           }
                         };
        double p1 = 11.0 / 66.0;
        double p2 = Sum.sum(2, 11, probabilities);
        double p3 = Sum.doubleSum(2, 11, 2, 11, probabilities);
        double p4 = 1.0 - p1 - p2 - p3;
        System.out.println("p1 = " + p1);
        System.out.println("p2 = " + p2);
        System.out.println("p3 = " + p3);
        System.out.println("p4 = " + p4);
    }
}
