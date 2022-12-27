public class task1 {
    public static void main(String[] args) {
       if (args.length != 1) {
           System.out.println("Invalid arguments");
           return;
       }
        ComputePosterior computer = new ComputePosterior(args[0]);
        computer.calculate();
    }
}
