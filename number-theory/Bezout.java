public class Bezout {
    public static int a;
    public static int b;

    public static void main(String[] args) {
        try {
            a = Integer.parseInt(args[0]);
            b = Integer.parseInt(args[1]);

            int j = -1;
            System.out.println("+---+---------+---------+---------+---------+---------+---------+");
            System.out.println("| j |   r_j   |  r_j+1  |  q_j+1  |  r_j+2  |   s_j   |   t_j   |");
            System.out.println("+---+---------+---------+---------+---------+---------+---------+");
            do {
                j++;
            System.out.println("| " + j + " | " + pad(r(j)) + " | " + pad(r(j + 1)) + " | " + pad(q(j + 1)) + " | "
                               + pad(r(j + 2)) + " | " + pad(s(j)) + " | " + pad(t(j)) + " |");
            } while (r(j + 2) != 0);
            System.out.println("+---+---------+---------+---------+---------+---------+---------+");
            System.out.println("gcd(" + a + ", " + b +"): " + r(j + 1));
            System.out.println("Bezout numbers for (" + a + ", " + b + "): (" + s(j + 1) + ", " + t(j + 1) + ")");

        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("must submit two integers, number submiited: " + args.length);
        } catch (NumberFormatException e) {
            if (args.length < 2) {
                System.err.println("argument must be an integer, argument submitted:  " + args[0]);
            } else {
                System.err.println("arguments must be integers, arguments submitted:  " + args[0] + ", " + args[1]);
            }
        }
    }

    public static String pad(int n) {
        int length = ("" + n).length();
        String pad = "";
        int stop = 7 - length;
        for (int i = 0; i < (stop / 2); i++) {
            pad += " ";
        }
        return (stop % 2 == 0)? pad + n + pad : pad + n + pad + " ";
    }

    public static int r(int j) {
        return (s(j) * a) + (t(j) * b);    
    }

    public static int q(int j) {
        return r(j - 1) / r(j);
    }

    public static int s(int j) {
        if (j == 0) {
            return 1;
        } else if (j == 1) {
            return 0;
        } else {
            return s(j - 2) - (q(j - 1) * s(j - 1));
        }
    }

    public static int t(int j) {
        if (j == 0) {
            return 0;
        } else if (j == 1) {
            return 1;
        } else {
            return t(j - 2) - (q(j - 1) * t(j - 1));
        }
    }
}
