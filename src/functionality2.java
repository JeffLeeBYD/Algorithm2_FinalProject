import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class functionality2 {
    public static void func2() {
        TST tie = new TST();
        Scanner input = new Scanner(System.in);
        System.out.println("enter bus stop's name or first few chars: ");
        String stopInput = input.nextLine();
        List<String> result = new LinkedList<>();
        for (String key : tie.keysWithPrefix(stopInput)) {
            result.add(tie.get(key));
        }
        if (result.size() == 0) {
            System.out.println("stop not found");
        } else {
            System.out.println("=============================================");
            for (String s : result) {
                System.out.print(s);
                System.out.println("=============================================");
            }
        }
    }
}
