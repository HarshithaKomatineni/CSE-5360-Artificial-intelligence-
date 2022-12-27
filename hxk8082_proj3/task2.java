public class task2 {
    public static void main(String[] args) {
       if (args.length < 1 || args.length > 6) {
           System.out.println("The number of arguments should be between 1 - 6.");
           return;
       }
        //String[] commands = {"Jf", "Mt", "given", "Et"};
        Bnet bnet = new Bnet(args);
        bnet.calculateBnet();
    }
}
