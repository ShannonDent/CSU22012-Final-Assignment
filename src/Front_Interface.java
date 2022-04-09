import java.io.*;
import java.util.Scanner;
import java.util.HashMap;

public class Front_Interface {

    public static void main(String[] args) {

        HashMap<Integer, Stop> stopsMap = new HashMap<Integer, Stop>();
        readFile("stops.txt", stopsMap);



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
        Scanner input = new Scanner(System.in);
        boolean quit = false;

        while (!quit) {
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

    public static void readFile(String filename, HashMap stopsMap){
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

    public static void shortestRoute(Scanner userInput, HashMap stopsMap) {
        boolean quit = false;
        while (!quit) {
            System.out.print("Enter first bus stop ID (or quit): ");
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

    public static void busStopSearch(Scanner userInput, HashMap stopsMap) {
        boolean quit = false;
        while (!quit) {
            System.out.print("Enter bus stop name (or quit): ");
            //TST
            if (userInput.hasNext() && !userInput.hasNextInt()) {  //Change to hashmap searching later.
                String busStopName = userInput.next();
                System.out.println("Your bus stop is..." + busStopName);

                return;
            } else if (userInput.next().equalsIgnoreCase("quit")) {
                quit = true;
            } else {
                System.out.println("Error - Enter a valid bus ID.");
            }
        }
        return;
    }

    public static void arrivalTimeSearch(Scanner userInput) {
        boolean quit = false;
        while (!quit) {
            System.out.print("Enter desired arrival time: ");
            if (userInput.hasNext() && !userInput.hasNextInt()){ // Use hashmap here to check
                String arrivalTime = userInput.next();
                System.out.println("Your arrival time..." + arrivalTime);
                return;
            } else if (userInput.next().equalsIgnoreCase("quit")) {
                quit = true;
            } else {
                System.out.println("Error - Enter a valid bus ID.");
            }
        }
        return;
    }
}



