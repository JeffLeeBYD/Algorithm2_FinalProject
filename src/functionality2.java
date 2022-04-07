import java.util.Scanner;

public class functionality2 {
    public static void func2(TST tie) {
        Scanner input = new Scanner(System.in);
        System.out.println("enter bus stop's name or first few chars: ");
        String stopInput = input.nextLine();
        System.out.println("=============================================");
        for (String key : tie.keysWithPrefix(stopInput)) {
            System.out.print(tie.get(key));
            System.out.println("=============================================");
        }
    }
}
