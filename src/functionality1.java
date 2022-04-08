import java.util.HashMap;
import java.util.Scanner;

public class functionality1 {

    public static void func1(DijkstraSP dijkstraSP_object){
        Scanner sc = new Scanner(System.in);

        HashMap<String, DijkstraSP.Vertex> vertexes = dijkstraSP_object.VertexesMap;
        String start = "";
        String end = "";

        boolean validStart = false;
        boolean validEnd = false;

        while (!validStart || !validEnd) {
            if(!validStart){
                System.out.println("enter starting stop's id:");
                String inputStart = sc.nextLine();
                // get user input of the starting stop
                if (!(inputStart.matches("[0-9]+"))) {
                    System.out.println("invalid input, please enter a number");
                }
                else if (vertexes.get(inputStart) == null){
                    System.out.println("start stop doesn't exist");
                }
                else {
                    validStart = true;
                    start = inputStart;
                }
            }

            // only accept end stop input if the input stop is correct
            if (validStart) {
                System.out.println("enter end stop's id:");
                String inputDestination = sc.nextLine();
                // get user input of the end stop
                if (!(inputDestination.matches("[0-9]+"))){
                    System.out.println("invalid input, please enter a number");
                }
                else if (vertexes.get(inputDestination) == null) {
                    System.out.println("destination stop doesn't exist");
                }
                else {
                    validEnd = true;
                    end = inputDestination;
                }
            }
        }
        // only do the calculation after getting valid start and end stops as input
        // after computing the shortest path form start to end, print result
        DijkstraSP.Vertex source = vertexes.get(start); // creates a vertex object of the source stop
        DijkstraSP.Vertex destination = vertexes.get(end);// creates a vertex object of the
        System.out.println("shortest path from [" + dijkstraSP_object.stop_id_name.get(source.stop_id) + "] to [" + dijkstraSP_object.stop_id_name.get(destination.stop_id) + "] is \n"
                + dijkstraSP_object.getSP(source, destination));
        String cost = dijkstraSP_object.cost.toString();        // if the total cost is the largest double, it means no path found between the two stops
        if (dijkstraSP_object.cost == Double.MAX_VALUE){
            cost = "none";
        }
        System.out.println("total cost: " + cost + "\n");
    }
}
