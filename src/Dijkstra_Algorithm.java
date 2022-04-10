
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Dijkstra_Algorithm {
    int stops;
    int connections;
    int[] previousStop;
    double[][] costGraph;
    double[] distanceArray;
    Boolean[] visitedStops;

    public Dijkstra_Algorithm(String filename) {

        stops = 0;
        connections = 0;

        if (filename == null) {
            stops = 0;
            connections = 0;
            costGraph = new double[0][0];
            visitedStops = new Boolean[0];
            distanceArray = new double[0];
            previousStop = new int[0];
            return;
        }

        try {
            File input = new File(filename);
            Scanner scanFile = new Scanner(input);
            stops = Integer.parseInt(scanFile.nextLine());
            connections = Integer.parseInt(scanFile.nextLine());

            costGraph = new double[stops][stops];

            for (int i = 0; i < stops; i++) {
                for (int j = 0; j < stops; j++) {
                    costGraph[i][j] = Double.POSITIVE_INFINITY;
                }
            }

            while (scanFile.hasNextLine()) {
                try {
                    int from = Integer.parseInt(scanFile.next());
                    int to = Integer.parseInt(scanFile.next());
                    double distance = Double.parseDouble(scanFile.next());
                    Connection myStreet = new Connection(from, to, distance);
                    costGraph[from][to] = distance;
                } catch (Exception e) {
                    stops = 0;
                    connections = 0;
                    costGraph = new double[0][0];
                    return;
                }
            }
        } catch(FileNotFoundException e) {
            System.out.println("An error has been detected");
            e.printStackTrace();
            stops = 0;
            connections = 0;
            costGraph = new double[0][0];
        }

    }

    public int mininumDistance(double distanceArray[], Boolean visitedNode[]){
        double mininumDistance = Double.POSITIVE_INFINITY;
        int currentIntersection = -1;
        for(int i = 0; i < stops; i++){
            if(visitedNode[i] == false && distanceArray[i] <= mininumDistance) {
                mininumDistance = distanceArray[i];
                currentIntersection = i;
            }
        }
        return currentIntersection;
    }

    public double dijkstraAlgorithm(double competitionGraph[][], int startingIntersection) {
        distanceArray = new double[stops];
        visitedStops = new Boolean[stops];
        for(int i = 0; i < stops; i++) {
            distanceArray[i] = Double.POSITIVE_INFINITY;
            visitedStops[i] = false;
        }
        distanceArray[startingIntersection] = 0;
        for (int i = 0; i < stops; i++){
            int minDistance = mininumDistance(distanceArray, visitedStops);
            visitedStops[minDistance] = true;
            for (int j = 0; j < stops; j++){
                if((!visitedStops[j] && competitionGraph[minDistance][j] != Double.POSITIVE_INFINITY) &&
                        (distanceArray[minDistance] != Double.POSITIVE_INFINITY) &&
                        (distanceArray[minDistance] + competitionGraph[minDistance][j] < distanceArray[j])){
                    distanceArray[j] = distanceArray[minDistance] + competitionGraph[minDistance][j];
                }
            }
        }
        double longestShortestPath = distanceArray[0];
        for (int i = 1; i < distanceArray.length; i++) {
            if (longestShortestPath < distanceArray[i] && distanceArray[i] != Double.POSITIVE_INFINITY) {
                longestShortestPath = distanceArray[i];
            }
        }
        return longestShortestPath;
    }
}

class Connection {
    int from;
    int to;
    double distance;

    public Connection(int intersectionOne, int intersectionTwo, double distanceStreet) {
        from = intersectionOne;
        to = intersectionTwo;
        distance = distanceStreet;
    }
}