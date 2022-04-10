import java.io.*;
import java.util.*;

public class Front_Interface {

    public static void main(String[] args) {

        HashMap<Integer, Stop> stopsMap = new HashMap<Integer, Stop>();
        readFileStops("stops.txt", stopsMap);

        Scanner input = new Scanner(System.in);
        boolean quit = false;

        while (!quit) {
            System.out.println("╔════════════════════════════════════════════════════════════════════╗ \n" +
                    "║                 WELCOME TO THE BUS MANAGEMENT SYSTEM               ║ \n" +
                    "╠════════════════════════════════════════════════════════════════════╣ \n" +
                    "║     (Enter one of the following options onto the command line)     ║ \n" +
                    "╠════════════════════════════════════════════════════════════════════╣ \n" +
                    "║ 1. Find Shortest Route Between Two Bus Stops                       ║ \n" +
                    "╠════════════════════════════════════════════════════════════════════╣ \n" +
                    "║ 2. Search For A Bus Stop                                           ║ \n" +
                    "╠════════════════════════════════════════════════════════════════════╣ \n" +
                    "║ 3. Search For Trips With An Arrival Time                           ║ \n" +
                    "╚════════════════════════════════════════════════════════════════════╝");
            System.out.print("\nEnter option between 1-3 or type quit: ");
            if (input.hasNextInt()) {
                int usersChoice = input.nextInt();
                switch (usersChoice) {
                    case 1:
                        System.out.println("╔══════════════════════════════════════════════════╗ \n" +
                                "║     FIND A SHORTEST ROUTE BETWEEN TWO STOPS      ║ \n" +
                                "╚══════════════════════════════════════════════════╝ ");
                        shortestRoute(input, stopsMap);
                        break;
                    case 2:
                        System.out.println("╔══════════════════════════════════════════════════╗ \n" +
                                "║               SEARCH FOR A BUS STOP              ║ \n" +
                                "╚══════════════════════════════════════════════════╝ ");
                        busStopSearch(input, stopsMap);
                        break;
                    case 3:
                        System.out.println("╔══════════════════════════════════════════════════╗ \n" +
                                "║            SEARCH WITH ARRIVAL TIME              ║ \n" +
                                "╚══════════════════════════════════════════════════╝ ");
                        arrivalTimeSearch(input);
                        break;
                    default:
                        if (input.next().equalsIgnoreCase("quit")) {
                            quit = true;
                        } else {
                            System.out.println("Error - Enter a number between 1 and 3 or quit.");
                        }
                }
            } else {
                if (input.next().equalsIgnoreCase("quit")) {
                    quit = true;
                } else {
                    System.out.println("Error - Enter a number between 1 and 3 or quit.");
                }
            }
        }
        input.close();
    }

    public static void readFileStops(String filename, HashMap stopsMap){
        try {
            File input = new File(filename);
            Scanner scanFile = new Scanner(input);
            String line = scanFile.nextLine();
            int counter = 0;
            while (scanFile.hasNextLine()) {
                try {
                    String[] split = line.split(",");
                    if (counter == 0){
                        counter++;
                        line = scanFile.nextLine();
                    } else {
                        int stop_id;
                        if (!split[0].equalsIgnoreCase(" ")){
                            stop_id = Integer.parseInt(split[0]);
                        } else {
                            stop_id = 0;
                        }

                        int stop_code;
                        if (!split[1].equalsIgnoreCase(" ")){
                            stop_code = Integer.parseInt(split[1]);
                        } else {
                            stop_code = 0;
                        }

                        String stop_name = split[2];
                        String stop_desc = split[3];

                        double stop_lat;
                        if (!split[4].equalsIgnoreCase(" ")){
                            stop_lat = Double.parseDouble(split[4]);
                        } else {
                            stop_lat = 0;
                        }

                        double stop_lon;
                        if (!split[5].equalsIgnoreCase(" ")){
                            stop_lon = Double.parseDouble(split[5]);
                        } else {
                            stop_lon = 0;
                        }

                        String zone_id = split[6];
                        String stop_url = split[7];
                        String location_type = split[8];

                        int parent_station;
                        if (split.length == 10){
                            if(!split[9].equalsIgnoreCase(" ")){
                                parent_station = Integer.parseInt(split[9]);
                            } else {
                                parent_station = 0;
                            }
                        } else {
                            parent_station = 0;
                        }
                        Stop currentStop = new Stop(stop_id, stop_code, stop_name, stop_desc, stop_lat, stop_lon, zone_id, stop_url, location_type, parent_station);
                        stopsMap.put(currentStop.stop_id, currentStop);
                        line = scanFile.nextLine();
                    }
                } catch (Exception e) {
                    System.out.println("An error has been detected: " + e);
                    e.printStackTrace();
                }
            }
        } catch(FileNotFoundException e) {
            System.out.println("An error has been detected: " + e);
            e.printStackTrace();
        }
    }

    public static void readFileStopTimes(String filename, HashMap<String, ArrayList<Trip>> arrivalTimeToTripMap){
        try {
            File input = new File(filename);
            Scanner scanFile = new Scanner(input);
            String line = scanFile.nextLine();
            int counter = 0;
            while (scanFile.hasNextLine()) {
                try {
                    String[] split = line.split(",");
                    if (counter == 0){
                        counter++;
                        line = scanFile.nextLine();
                    } else {
                        int trip_id;
                        if (!split[0].equalsIgnoreCase("")){
                            trip_id = Integer.parseInt(split[0]);
                        } else {
                            trip_id = 0;
                        }

                        String arrival_time = split[1].replaceAll(" ", "0");
                        String[] splitArrivalTime = split[1].split(":");
                        if (Integer.parseInt(splitArrivalTime[0].replaceAll(" ", "")) > 23 || Integer.parseInt(splitArrivalTime[1]) > 59 || Integer.parseInt(splitArrivalTime[2]) > 59 ) {
                            arrival_time = null;
                        }
                        String departure_time = split[2].replaceAll(" ", "0");
                        String[] splitDepartureTime = split[1].split(":");
                        if (Integer.parseInt(splitDepartureTime[0].replaceAll(" ", "")) > 23 || Integer.parseInt(splitDepartureTime[1]) > 59 || Integer.parseInt(splitDepartureTime[2]) > 59 ) {
                            departure_time = null;
                        }

                        int stop_id;
                        if (!split[3].equalsIgnoreCase("")){
                            stop_id = Integer.parseInt(split[3]);
                        } else {
                            stop_id = 0;
                        }

                        int stop_sequence;
                        if (!split[4].equalsIgnoreCase("")){
                            stop_sequence = Integer.parseInt(split[4]);
                        } else {
                            stop_sequence = 0;
                        }

                        int stop_headsign;
                        if (!split[5].equalsIgnoreCase("")){
                            stop_headsign = Integer.parseInt(split[5]);
                        } else {
                            stop_headsign = 0;
                        }

                        int pickup_type;
                        if (!split[6].equalsIgnoreCase("")){
                            pickup_type = Integer.parseInt(split[6]);
                        } else {
                            pickup_type = 0;
                        }

                        int drop_off_type;
                        if (!split[7].equalsIgnoreCase("")){
                            drop_off_type = Integer.parseInt(split[7]);
                        } else {
                            drop_off_type = 0;
                        }

                        double shape_dist_traveled;
                        if (!split[7].equalsIgnoreCase("")){
                            shape_dist_traveled = Double.parseDouble(split[7]);
                        } else {
                            shape_dist_traveled = 0;
                        }

                        Trip currentTrip = new Trip(trip_id, arrival_time,  departure_time, stop_id, stop_sequence, stop_headsign, pickup_type, drop_off_type, shape_dist_traveled);
                        if(arrivalTimeToTripMap.containsKey(arrival_time)){
                            ArrayList<Trip> currentTripArrayList = arrivalTimeToTripMap.get(arrival_time);
                            currentTripArrayList.add(currentTrip);
                        } else {
                            ArrayList<Trip> newTripArrList= new ArrayList<Trip>();
                            newTripArrList.add(currentTrip);
                            arrivalTimeToTripMap.put(arrival_time, newTripArrList);
                        }
                        line = scanFile.nextLine();
                    }
                } catch (Exception e) {
                    System.out.println("An error has been detected: " + e);
                    e.printStackTrace();
                }
            }
        } catch(FileNotFoundException e) {
            System.out.println("An error has been detected: " + e);
            e.printStackTrace();
        }
    }

    public static void shortestRoute(Scanner userInput, HashMap stopsMap) {
        boolean quit = false;
        while (!quit) {
            System.out.print("This functionality is currently under construction please type quit: ");
            //System.out.print("Enter first bus stop ID (or quit): ");
            if (userInput.hasNextInt()) {
                int firstID = userInput.nextInt();
                System.out.print("Enter second bus stop ID (or quit):");
                if (userInput.hasNextInt()) {
                    int secondID = userInput.nextInt();
                    return;
                } else {
                    if (userInput.next().equalsIgnoreCase("quit")) {
                        quit = true;
                    } else {
                        System.out.println("Error - Enter a valid bus ID.");
                    }
                }
            } else {
                if (userInput.next().equalsIgnoreCase("quit")) {
                    quit = true;
                } else {
                    System.out.println("Error - Enter a valid bus ID.");
                }
            }
        }

        return;
    }

    public static void busStopSearch(Scanner userInput, HashMap<Integer, Stop> stopsMap) {
        TST searchTree = new TST();
        HashMap<String, Integer>  stopNameToIDMap = new HashMap<String, Integer>();
        for (Map.Entry<Integer, Stop> entry : stopsMap.entrySet()) {
            Integer stopID = entry.getKey();
            Stop stop = entry.getValue();
            String [] stopNameArr = (stop.stop_name).split(" ", 2);
            if (stopNameArr[0].equalsIgnoreCase("WB") || stopNameArr[0].equalsIgnoreCase("NB") || stopNameArr[0].equalsIgnoreCase("SB") || stopNameArr[0].equalsIgnoreCase("EB")|| stopNameArr[0].equalsIgnoreCase("FLAGSTOP")) {
                String keyword = stopNameArr[0];
                String startOfName = stopNameArr[1];
                stopNameArr[0] = startOfName;
                stopNameArr[1] = keyword;
            }
            String fullName = stopNameArr[0] + " " + stopNameArr[1];
            searchTree.insert(fullName);
            stopNameToIDMap.put(fullName, stopID);

        }
        boolean quit = false;
        userInput.nextLine();
        while (!quit) {
            System.out.print("Enter bus stop name (or quit): ");
            //TST
            if (userInput.hasNextLine()) {
                String myInput = userInput.nextLine();
                if (myInput.equalsIgnoreCase("quit")) {
                    quit = true;
                } else {
                    String isFound = searchTree.search(myInput.toUpperCase());
                    if (isFound == null) {
                        System.out.println(myInput + " is an invalid input. Enter a valid bus stop name.");
                    } else {
                        Stop result = stopsMap.get(stopNameToIDMap.get(isFound));
                        System.out.println("+----------------------------------------------------+ \n" +
                                "                     STOP" + "  " + result.stop_id + "                     " + " \n" +
                                "+----------------------------------------------------+ \n" +
                                " Stop Code:          |  " + result.stop_code + "             " + " \n" +
                                "+----------------------------------------------------+ \n" +
                                " Stop Name:          |  " + result.stop_name + "             " + " \n" +
                                "+--------------------+-------------------------------+ \n" +
                                "| Stop Description:  |  " + result.stop_desc + "            " + " \n" +
                                "+--------------------+-------------------------------+ \n" +
                                "| Stop Latitude:     |  " + result.stop_lat + "            " + " \n" +
                                "+--------------------+-------------------------------+ \n" +
                                "| Stop Longitude:    |  " + result.stop_lon + "            " + " \n" +
                                "+--------------------+-------------------------------+ \n" +
                                "| Stop URL:          |  " + result.stop_url + "            " + " \n" +
                                "+--------------------+-------------------------------+ \n" +
                                "| Zone ID:           |  " + result.zone_id + "            " + " \n" +
                                "+--------------------+-------------------------------+ \n" +
                                "| Location Type:     |  " + result.location_type + "            " + " \n" +
                                "+--------------------+-------------------------------+ \n" +
                                "| Parent Type:       |  " + result.parent_station + "            " + " \n" +
                                "+--------------------+-------------------------------+");
                    }
                }

            } else {
                System.out.println("Error - Enter a valid bus ID.");
            }
        }
        return;
    }

    public static void arrivalTimeSearch(Scanner userInput) {
        HashMap<String, ArrayList<Trip>> arrivalTimeToTripMap = new HashMap<>();
        System.out.println("Loading...");
        readFileStopTimes("stop_times.txt", arrivalTimeToTripMap);
        System.out.println("Loaded!");

        boolean quit = false;
        while (!quit) {
            System.out.print("Enter desired arrival time in the format (hh:mm:ss): ");
            String desiredArrivalTime = userInput.next();
            if (desiredArrivalTime.contains(":")) {
                if (!arrivalTimeToTripMap.containsKey(desiredArrivalTime)) {
                    System.out.println("Error - Enter a valid arrival time within the 24 hour clock.");
                } else {
                    ArrayList<Trip> tripWithArrivalTime = arrivalTimeToTripMap.get(desiredArrivalTime);
                    for (int i = 0; i < tripWithArrivalTime.size(); i++) {
                        System.out.println("+-----------------------------------------------+ \n" +
                                "                  TRIP" + "  " + tripWithArrivalTime.get(i).trip_id + "                     " + " \n" +
                                "+-----------------------------------------------+ \n" +
                                " Arrival Time:        |  " + tripWithArrivalTime.get(i).arrival_time + "             " + " \n" +
                                "+---------------------+-------------------------+ \n" +
                                " Departure Time:      |  " + tripWithArrivalTime.get(i).departure_time + "             " + " \n" +
                                "+---------------------+-------------------------+ \n" +
                                "| Stop ID:            |  " + tripWithArrivalTime.get(i).stop_id + "            " + " \n" +
                                "+---------------------+-------------------------+ \n" +
                                "| Stop Sequence:      |  " + tripWithArrivalTime.get(i).stop_sequence + "            " + " \n" +
                                "+---------------------+-------------------------+ \n" +
                                "| Stop Headsign:      |  " + tripWithArrivalTime.get(i).stop_headsign + "            " + " \n" +
                                "+---------------------+-------------------------+ \n" +
                                "| Pick Up Type:       |  " + tripWithArrivalTime.get(i).pickup_type + "            " + " \n" +
                                "+---------------------+-------------------------+ \n" +
                                "| Drop Off Type:      |  " + tripWithArrivalTime.get(i).drop_off_type + "            " + " \n" +
                                "+---------------------+-------------------------+ \n" +
                                "| Distance Traveled:  |  " + tripWithArrivalTime.get(i).shape_dist_traveled + "            " + " \n" +
                                "+---------------------+-------------------------+");
                    }
                }
            } else if (desiredArrivalTime.equalsIgnoreCase("quit")){
                quit = true;
            } else {
                System.out.println("Error - Enter a valid arrival time in the format (hh:mm:ss).");
            }
        }
        return;
    }
}



