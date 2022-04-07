import java.io.*;
import java.util.*;


public class functionality3 {

    public static void func3() throws IOException {
        LinkedList<String> stopTimes = new LinkedList<>();        // the arraylist stores valid lines in file given
        LinkedList<String> results = new LinkedList<>();          // the result arraylist stores the matching result
        // use buffered reader to speed up reading; each line is executed through a filter to filter out lines with invalid time
        try (BufferedReader br = new BufferedReader(new FileReader(new File("stop_times.txt")))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.split(",")[1].trim().matches("([0-9]|[1][0-9]|[2][0-3]):[0-5][0-9]:[0-5][0-9]"))
                    stopTimes.add(line);            // only add in lines with valid
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: file not found");
        }

        Scanner input = new Scanner(System.in);
        System.out.println("enter arrival time in hh:mm:ss (if hh < 10, 0 pre-fix is unnecessary) ");
        String inputTime = input.next();
        if (!((inputTime.charAt(2) == ':' && inputTime.charAt(5) == ':') || (inputTime.charAt(1) == ':' && inputTime.charAt(1) == ':'))) {
            System.out.println("time input is not formatted correctly");        // the input time is in wrong format
        } else if (inputTime.matches("([0-9]|[1][0-9]|[2][0-3]):[0-5][0-9]:[0-5][0-9]")) {      // check time input is reasonable or not
            if (inputTime.length() == 7)
                inputTime = " " + inputTime;        // if there's only digital bit in hour, space needed for searching in the given file

            for (String stopTime : stopTimes) {
                if (stopTime.contains(inputTime)) {
                    results.add(stopTime);
                }
            }
            // print out the file
            if (results.size() > 0) {               // if the result list is not empty, print out results
                Collections.sort(results);
                ArrayList<String> title = new ArrayList<>(Arrays.asList("trip_id", "arrival_time", "departure_time", "stop_id",
                        "stop_sequence", "stop_headsign", "pickup_type", "drop_off_type", "shape_dist_traveled"));
                System.out.println("searching result: \n=============================================");
                for (String line : results) {
                    if (line.endsWith(","))
                        line += " ";
                    String[] lineArray = line.split(",");
                    StringBuilder split = new StringBuilder();
                    for (int i = 0; i < title.size(); i++) {
                        split.append(title.get(i)).append(": ").append(lineArray[i]).append("\n");  // formatting the output
                    }
                    String result = split.toString();
                    System.out.print(result);
                    System.out.println("=============================================");
                }
            } else {
                System.out.println("arrival time not found");
            }
        } else {
            System.out.println("invalid time input");    // the input time is not reasonable
        }

    }

}
