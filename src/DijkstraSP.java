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
        public String name;
        private Vertex previousVertex;
        private double minDist;
        private List<Edge> edgeList;
        private boolean relaxed;

        public Vertex(String name) {
            this.name = name;
            this.edgeList = new ArrayList<>();
            previousVertex = null;
            minDist = Double.MAX_VALUE;
            relaxed = false;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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
            return name;
        }

        @Override
        public int compareTo(Vertex another) {
            return Double.compare(this.minDist, another.minDist);
        }
    }


    public Double cost;
    public HashMap<String, Vertex> VertexesMap = new HashMap<>();

    public DijkstraSP()  {
        Scanner sc1 = null;
        Scanner sc2 = null;
        Scanner sc3 = null;
        try {
            sc1 = new Scanner(new File("stops.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: file not found");
        }
        assert sc1 != null;
        while (sc1.hasNextLine()) {
            String stop = sc1.nextLine();
            String[] stopSplit = stop.split(",");               // add new vertex to graph from file stops.txt
            Vertex v = new Vertex(stopSplit[0]);
            VertexesMap.put(stopSplit[0], v);
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
            String[] transferSplit = transfer.split(",");
            String start = transferSplit[0];
            String end = transferSplit[1];
            double weight = 0;
            if (transferSplit[2].equals("0")) {
                weight = 2.0;
            }
            if (transferSplit[2].equals("2")) {
                weight = Double.parseDouble(transferSplit[3]) / 100;
            }
            Vertex vertex1 = VertexesMap.get(start);
            Vertex vertex2 = VertexesMap.get(end);
            vertex1.addNeighbour(new Edge(vertex1, vertex2, weight));
        }
        sc2.close();


        ArrayList<String> tripInfos = new ArrayList<>();
        try {
            sc3 = new Scanner(new File("stop_times.txt"));      // add new edges from stop_times.txt
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: file not found");
        }
        assert sc3 != null;
        sc3.nextLine();
        while (sc3.hasNextLine()) {
            tripInfos.add(sc3.nextLine());
        }
        sc3.close();


        for (int i = 0; i < tripInfos.size()-1; i++) {
            String trip_id1 = tripInfos.get(i).split(",")[0];
            String stop_id1 = tripInfos.get(i).split(",")[3];
            String trip_id2 = tripInfos.get(i+1).split(",")[0];
            String stop_id2 = tripInfos.get(i+1).split(",")[3];
            Vertex start = VertexesMap.get(stop_id1);
            Vertex end = VertexesMap.get(stop_id2);
            if (trip_id1.equals(trip_id2)) {
                start.addNeighbour(new Edge(start, end, 1.0));      // add edge
                VertexesMap.put(start.name, start);
            }
        }
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
                stops.add(0, vertex.toString());
                stops.add(0, "-" + (vertex.getPreviousVertex() != null ? vertex.getMinDist() - vertex.getPreviousVertex().getMinDist() : 0.0) + "->");
            }
            stops.remove(0);
        }
        return stops;
    }
}
