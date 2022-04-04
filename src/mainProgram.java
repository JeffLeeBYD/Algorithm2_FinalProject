import java.util.HashMap;
import java.util.Scanner;

public class mainProgram {

    public static void main(String[] args) throws Exception {
        boolean quit = false;
        while (!quit) {
            Scanner sc = new Scanner(System.in);
            System.out.println(
                            "+------------ select function -----------+ \n" +
                            "| 0 -> quit                              | \n" +
                            "| 1 -> get shortest path between 2 stops | \n" +
                            "| 2 -> search a stop name                | \n" +
                            "| 3 -> search an arrival time            | \n" +
                            "+----------------------------------------+");
            System.out.println("please select function with number 0-3: ");
            if (sc.hasNextInt()) {
                int function = sc.nextInt();                                    // accept user's selection
                switch (function) {
                    case 0 -> quit = true;
                    case 1 -> functionality1.func1();
                    case 2 -> functionality2.func2();
                    case 3 -> functionality3.func3();
                    default -> System.out.println("invalid selection");
                }
            } else {
                System.out.println("please enter a number to select function"); // input is not integer
            }
        }
    }
}