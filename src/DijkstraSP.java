import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DijkstraSP {

    // the helper class for dijkstra
    private class Edge {
        private Vertex start;
        private Vertex end;
        private double weight;

        public Edge(Vertex start, Vertex end, double weight) {
            this.start = start;
            this.end = end;
            this.weight = weight;
        }

        public Vertex getStart() {
            return start;
        }

        public void setStart(Vertex start) {
            this.start = start;
        }

        public Vertex getEnd() {
            return end;
        }

        public void setEnd(Vertex end) {
            this.end = end;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }
    }

    public class Vertex implements Comparable<Vertex> {
        public String stop_id;
        private Vertex previousVertex;
        private double minDist;
        private List<Edge> edgeList;
        private boolean relaxed;

        public Vertex(String id) {
            this.stop_id = id;
            this.edgeList = new ArrayList<>();
            previousVertex = null;
            minDist = Double.MAX_VALUE;
            relaxed = false;
        }

        public String getStop_id() {
            return stop_id;
        }

        public void setStop_id(String stop_id) {
            this.stop_id = stop_id;
        }

        public List<Edge> getEdgeList() {
            return edgeList;
        }

        public void setEdgeList(List<Edge> edgeList) {
            this.edgeList = edgeList;
        }

        public void addNeighbour(Edge edge) {
            this.edgeList.add(edge);
        }

        public boolean isRelaxed() {
            return relaxed;
        }

        public void setRelaxed() {
            this.relaxed = true;
        }

        public Vertex getPreviousVertex() {
            return previousVertex;
        }

        public void setPreviousVertex(Vertex previousVertex) {
            this.previousVertex = previousVertex;
        }

        public double getMinDist() {
            return minDist;
        }

        public void setMinDist(double minDist) {
            this.minDist = minDist;
        }

        @Override
        public String toString() {
            return stop_id;
        }

        @Override
        public int compareTo(Vertex another) {
            return Double.compare(this.minDist, another.minDist);
        }
    }


    public Double cost;
    public HashMap<String, Vertex> VertexesMap = new HashMap<>();
    public HashMap<String, String> stop_id_name = new HashMap<>();

    public DijkstraSP()  {
        Scanner sc1 = null;                                           // initialize all the scanners
        Scanner sc2 = null;
        Scanner sc3 = null;
        Scanner sc4 = null;
        try {
            sc1 = new Scanner(new File("stops.txt"));       // initialize the scanner from file stops.txt
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: file not found");
        }
        assert sc1 != null;
        while (sc1.hasNextLine()) {
            String stop = sc1.nextLine();
            String[] stopSplit = stop.split(",");               // add new vertex to graph from file stops.txt
            Vertex v = new Vertex(stopSplit[0]);                      // this file is used to create all vertices in the map, and create a corresponding between
            VertexesMap.put(stopSplit[0], v);                         // the stop's name and vertex created
            stop_id_name.put(stopSplit[0], stopSplit[2]);
        }
        sc1.close();


        try {
            sc2 = new Scanner(new File("transfers.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: file not found");
        }
        assert sc2 != null;
        sc2.nextLine();                                             // skip the first line in transfer.txt
        while (sc2.hasNextLine()) {
            String transfer = sc2.nextLine();
            String[] transferSplit = transfer.split(",");         // add weight for edges from transfer.txt
            String start = transferSplit[0];
            String end = transferSplit[1];
            double weight = 0;
            if (transferSplit[2].equals("0")) {                         // transfer type 0: weight is 2
                weight = 2.0;
            }
            if (transferSplit[2].equals("2")) {                         // transfer type 2: weight is minimum transfer time divided by 100
                weight = Double.parseDouble(transferSplit[3]) / 100;
            }
            Vertex vertex1 = VertexesMap.get(start);
            Vertex vertex2 = VertexesMap.get(end);
            vertex1.addNeighbour(new Edge(vertex1, vertex2, weight));   // connect the two vertices with the weighted edge just created
        }
        sc2.close();


        try {
            sc3 = new Scanner(new File("stop_times.txt"));      // add new edges from stop_times.txt
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: file not found");
        }
        assert sc3 != null;
        sc3.nextLine();
        try {
            sc4 = new Scanner(new File("stop_times.txt"));      // add new edges from stop_times.txt
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: file not found");
        }
        assert sc4 != null;
        sc4.nextLine();                                                  // use two pointers get each two lines in stop_times and and edges if in need
        sc4.nextLine();
        while (sc4.hasNextLine()) {
            String last = sc3.nextLine();
            String current = sc4.nextLine();
            String last_trip_id = last.split(",")[0];
            String current_trip_id = current.split(",")[0];
            String last_stop_id = last.split(",")[3];
            String current_stop_id = current.split(",")[3];
            if (last_trip_id.equals(current_trip_id)){
                Vertex start = VertexesMap.get(last_stop_id);
                Vertex end = VertexesMap.get(current_stop_id);
                start.addNeighbour(new Edge(start, end, 1.0));      // add edge if the two stops have a same trip_id
            }
        }
        sc3.close();
        sc4.close();
    }


    public ArrayList<String> getSP(Vertex start, Vertex end) {
        start.setMinDist(0);
        PriorityQueue<Vertex> pq = new PriorityQueue<>();
        pq.add(start);

        while (!pq.isEmpty()) {                            // when pq is empty, it means all the vertices has been relaxed
            Vertex vertex = pq.poll();                         // because node will be removed form the list only after it has been relaxed
            vertex.setRelaxed();
            for (Edge edge : vertex.getEdgeList()) {        // check all the edges to find out if the shortest distance to other vertices can get updated or not
                Vertex currentV = edge.getEnd();
                double min = vertex.getMinDist() + edge.getWeight();
                if (!currentV.isRelaxed() && min < currentV.getMinDist()) {
                    pq.remove(currentV);                    // update the current vertex pointed by the current edge
                    currentV.setPreviousVertex(vertex);
                    currentV.setMinDist(min);
                    pq.add(currentV);
                }
            }
        }
        // after updated all vertices' shortest dist to start vertex, ready to return
        ArrayList<String> stops = new ArrayList<>();
        cost = end.getMinDist();
        if (cost != Double.MAX_VALUE) {
            for (Vertex vertex = end; vertex != null; vertex = vertex.getPreviousVertex()) {
                stops.add(0, stop_id_name.get(vertex.toString()));
                stops.add(0, "\n");
                stops.add(0, "\n       |" +
                                           "\n      " + (vertex.getPreviousVertex() != null ? vertex.getMinDist() - vertex.getPreviousVertex().getMinDist() : 0.0) +
                                           "\n       |");
            }
            stops.remove(0);
        }
        return stops;
    }
}
