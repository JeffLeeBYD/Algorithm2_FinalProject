import java.util.Scanner;

public class mainProgram {

    public static void main(String[] args) {
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

            if (sc.hasNextInt()) {
                int function = sc.nextInt();
                sc.nextLine();
                if (function < 0 || function > 3) {
                    System.out.println("invalid function select");              // handle invalid int input
                } else {                                                        // normal case
                    try {
                        if (function == 0) {
                            quit = true;
                        } else if (function == 1) {

                        } else if (function == 2) {
                            functionality2.func2();
                        } else {
                            functionality3.func3();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } else {
                System.out.println("please enter a number to select function"); // input is not integer
            }

        }
    }

}