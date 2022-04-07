import java.util.HashMap;
import java.util.Scanner;

public class functionality1 {

    public static void func1(DijkstraSP dijkstraSP_object){
        Scanner sc = new Scanner(System.in);

        HashMap<String, DijkstraSP.Vertex> vertexes = dijkstraSP_object.VertexesMap;
        String start = "";
        String end = "";

        boolean exit = false;

        while (!exit) {
            System.out.println("enter starting stop's id ");
            String inputStart = sc.nextLine();
            boolean validStart = true;
            boolean validEnd = true;
             // get user input of the starting stop
            if (!(inputStart.matches("[0-9]+"))) {
                System.out.println("please enter a number");
                validStart = false;
            }
            if (vertexes.get(inputStart) == null){
                System.out.println("start stop doesn't exist");
                validStart = false;
            }
            start = inputStart;
            // only accept end stop input if the input stop is correct
            if (validStart) {
                System.out.println("enter end stop's id");
                String inputDestination = sc.nextLine();
                // get user input of the end stop
                if (!(inputDestination.matches("[0-9]+"))){
                    System.out.println("please enter a number");
                    validEnd = false;
                }
                if (vertexes.get(inputDestination) == null) {
                    System.out.println("destination stop doesn't exist");
                    validEnd = false;
                }
                end = inputDestination;
            }
            // only do the calculation after getting valid start and end stops as input
            // after computing the shortest path form start to end, print result
            if (validStart && validEnd){
                DijkstraSP.Vertex source = vertexes.get(start); // creates a vertex object of the source stop
                DijkstraSP.Vertex destination = vertexes.get(end);// creates a vertex object of the
                System.out.println("shortest path from " + source.stop_id + " to " + destination.stop_id + " is \n"
                        + dijkstraSP_object.getSP(source, destination));
                System.out.println("total cost: " + dijkstraSP_object.cost + "\n");
                exit = true;
            }
        }
    }
}
