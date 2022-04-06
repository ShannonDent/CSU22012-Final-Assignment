import java.util.Scanner;

public class Front_Interface {

    public static void main(String[] args) {
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
                        shortestRoute(input);
                        break;
                    case 2:
                        System.out.println("╔══════════════════════════════════════════════════╗ \n" +
                                "║               SEARCH FOR A BUS STOP              ║ \n" +
                                "╚══════════════════════════════════════════════════╝ ");
                        busStopSearch(input);
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

    public static void shortestRoute(Scanner userInput) {
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

    public static void busStopSearch(Scanner userInput) {
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



