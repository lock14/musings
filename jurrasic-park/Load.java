public class Load {
    public static void main(String[] args) {
        if (args.length > 0 && args.length < 4) {
            System.out.print("PERMISSION DENIED");
            if (args.length == 3) {
                System.out.println("...and");
                for (int i = 0; i < 1000; i++) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {}
                    System.out.println("YOU DIDN'T SAY THE MAGIC WORD!");
                }
            } else {
                System.out.println();
            }
        } else {
            System.err.println("unknown command");
        }
    }
}
