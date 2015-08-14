public class NbaProb {
    public static void main(String[] args) {
        Sum.Functor prob2nd = new Sum.Functor() {
            public double sum(int... indices) {
                int i = indices[0];
                double team1 = ((12.0 - i) / 66.0);
                double team2 = (11.0 / (66.0 - (12.0 - i)));
                return team1 * team2;
                      
            }
        };
        Sum.Functor prob3rd = new Sum.Functor() {
            public double sum(int... indices) {
                int i = indices[0];
                int j = indices[1];
                if (i != j) {
                    double team1 = ((12.0 - i) / 66.0);
                    double team2 = ((12.0 - j) / (66.0 - (12.0 - i)));
                    double team3 = (11.0 / (66.0 - (24.0 - i - j)));
                    return team1 * team2 * team3;
                } else {
                    return 0.0;
                }
            }

        };
        double prob1stPick = 11.0 / 66.0;
        double prob2ndPick = Sum.sum(2, 11, prob2nd);
        double prob3rdPick = Sum.doubleSum(2, 11, 2, 11, prob3rd);
        double prob4thPick = 1.0 - prob1stPick - prob2ndPick - prob3rdPick;
        System.out.println("prob1stPick = " + prob1stPick);
        System.out.println("prob2ndPick = " + prob2ndPick);
        System.out.println("prob3rdPick = " + prob3rdPick);
        System.out.println("prob4thPick = " + prob4thPick);
    }
}
