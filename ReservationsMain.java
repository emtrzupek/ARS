
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//package ARS;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.*;

import java.io.*;
import java.time.LocalDateTime;

/**
 *
 * @aut
 *      Emily
 */
public class ReservationsMain {
    private static final String FILENAME = "Passenger.dat";
    private static ArrayList<Flight> flightList = new ArrayList<>();
    private static ArrayList<Airport> airportList = new ArrayList<>();
    private static ArrayList<Reservation> reservationList = new ArrayList<>();
    private static ArrayList<Passenger> passengerList = new ArrayList<>();

    public static void main(String args[]) throws Exception, FileNotFoundException, IOException {
        // readFromFile();
        // String[][] data = {{"1", "2"}, {"3", "4"}};
        // writeToExcelTMSpreadsheet("Airports.csv", data);
        // airportList = airportFromArray(readFromExcelTMSpreadsheet("Airports.csv"));
        // flightList = flightsFromArray(readFromExcelTMSpreadsheet("Flights.csv"));
        // passengerList =
        // passengersFromArray(readFromExcelTMSpreadsheet("Passengers.csv"));
        // reservationList =
        // reservationsFromArray(readFromExcelTMSpreadsheet("Reservation.csv"));

        getAirports(airportList);
        getFlights(flightList);
        getPassengers(passengerList);
        getReservations(reservationList);

        /*
         * flightList=new ArrayList<Flight>();
         * airportList=new ArrayList<Airport>();
         * passengerList=new ArrayList<Passenger>();
         */
        mainMenu();
    }

    public static void getPassengers(ArrayList<Passenger> p) {
        CSVReader csv = new CSVReader("Passengers.csv");
        int records = csv.getNumberOfRecords();
        p.clear();
        for (int i = 0; i < records; i++) {
            String[] nextRecord = csv.getRecord(i + 1);
            String last = nextRecord[0];
            String first = nextRecord[1];
            String member = nextRecord[2];
            boolean isMember = false;
            if (member.equals("yes")) {
                isMember = true;
            }
            int id = Integer.parseInt(nextRecord[3]);
            Passenger pass = new Passenger(last, first, isMember);
            pass.setID(id);
            pass.setAffinityNumber(Integer.parseInt(nextRecord[4]));
            p.add(pass);
        }
    }

    public static void getAirports(ArrayList<Airport> a) {
        CSVReader csv = new CSVReader("Airports.csv");
        int records = csv.getNumberOfRecords();
        a.clear();
        for (int i = 0; i < records; i++) {
            String[] nextRecord = csv.getRecord(i + 1);
            String name = nextRecord[0];
            String symbol = nextRecord[1];
            String city = nextRecord[2];
            String state = nextRecord[3];
            a.add(new Airport(name, symbol, city, state));
        }
    }

    public static void getReservations(ArrayList<Reservation> reservations) throws Exception {
        CSVReader csv = new CSVReader("Reservation.csv");
        int records = csv.getNumberOfRecords();
        reservations.clear();
        for (int i = 0; i < records; i++) {
            String[] nextRecord = csv.getRecord(i + 1);
            int resNum = Integer.parseInt(nextRecord[0]);
            Flight flight = findFlightByNum(Integer.parseInt(nextRecord[1]));
            ArrayList<Passenger> passengers = new ArrayList<>();
            String pIds = nextRecord[2];
            Scanner scan = new Scanner(pIds);
            while (scan.hasNext()) {
                int id = scan.nextInt();
                Passenger p = findPassengerByID(id);
                passengers.add(p);
            }
            Reservation r = new Reservation(passengers, flight);
            r.setReservationNumber(resNum);
            reservations.add(r);
        }
    }

    public static Airport getAirportFromSymbol(String symbol) {
        for (Airport a : airportList) {
            if (a.getSymbol().equalsIgnoreCase(symbol)) {
                return a;
            }
        }
        System.out.println("COULD NOT FIND AIRPORT FROM SYMBOL " + symbol + " CHECK DATABASE");
        return null;
    }

    public static void getFlights(ArrayList<Flight> flights) {
        CSVReader csv = new CSVReader("Flights.csv");
        int records = csv.getNumberOfRecords();
        flights.clear();
        for (int i = 0; i < records; i++) {
            String[] nextRecord = csv.getRecord(i + 1);
            int flightNumber = Integer.parseInt(nextRecord[0]);
            Airport departure = getAirportFromSymbol(nextRecord[1]);
            Airport arrival = getAirportFromSymbol(nextRecord[2]);
            FlightDate departureDate = new FlightDate(LocalDateTime.parse(nextRecord[3]));
            FlightDate arrivalDate = new FlightDate(LocalDateTime.parse(nextRecord[4]));
            int capacity = Integer.parseInt(nextRecord[5]);
            flights.add(new Flight(flightNumber, departure, arrival, departureDate, arrivalDate, capacity));
        }
    }

    private static void mainMenu() throws Exception {
        Scanner KB = new Scanner(System.in);
        continuePoint: while (true) {
            System.out.println("\nWelcome to Theta Airlines (formerly Boing Airlines)");
            System.out.println("\nWhat would you like to do?\n"
                    + "(P) print things\n(R) Manage reservations\n(S) Manage passengers\n(F) Manage flights\n(A) Manage airports\n(Q) Quit\n");
            System.out.print("Select: ");
            String fullChoice = KB.nextLine();
            if (fullChoice.length() < 1)
                continue;
            char choice = fullChoice.toLowerCase().charAt(0);
            System.out.println();
            switch (choice) {
                case 'f':
                    manageFlights(KB);
                    break;
                case 'r':
                    manageReservations(KB);
                    break;
                case 'p':
                    printMenu(KB);
                    break;
                case 'q':
                    break continuePoint;
                default:
                    break;
            }
        }

    }

    private static void manageFlights(Scanner KB) throws Exception, FileNotFoundException, IOException {
        Comparator<Flight> flightComparaer = ((Flight f1, Flight f2) -> (int) (f1.toString().compareTo(f2.toString())));
        continuePoint:

        while (true) {
        ArrayList<Flight> list;
            System.out.println("Flight Management\n"
                    + "(A) Print in Alphabetical order\n(B) Search for a flight number\n(C) search for a flight name\n(D) create a new flight\n(E) delete flight\n(Q) cancel\n");
            System.out.print("Select: ");
            String fullChoice = KB.nextLine();
            if (fullChoice.length() < 1)
                continue;
            char choice = fullChoice.toLowerCase().charAt(0);
            switch (choice) {
                case 'a':
                    System.out.println();
                     list = (ArrayList) flightList.clone();

                    list.sort(flightComparaer);
                    for (int i = 0; i < list.size(); i++) {
                        System.out.println(list.get(i));
                    }
                    System.out.println();
                    break;
                case 'b':
                    System.out.print("Flight number: ");
                    int choiceN = KB.nextInt();
                    System.out.println();
                    for (Flight R : flightList) {
                        if (R.getFlightNumber() == choiceN) {
                            System.out.println(R);
                        }
                    }
                    System.out.println();
                    KB.nextLine();
                    break;
                case 'c':
                    System.out.print("Departure airport: ");
                    String dep = KB.nextLine().toUpperCase();
                    System.out.print("Arrival airport: ");
                    String arr = KB.nextLine().toUpperCase();
                    System.out.println();
                    for (Flight f : flightList) {
                        if (f.getArrivalAirport().getSymbol().toUpperCase().equals(arr)
                                && f.getDepartureAirport().getSymbol().toUpperCase().equals(dep)) {
                            System.out.println(f);
                        }
                    }
                    System.out.println();
                    break;

                case 'd':
                    try{
                    System.out.println("AIRPORTS");
                    System.out.println("--------");
                    for (Airport a : airportList) {
                        System.out.println(a);
                    }
                    System.out.println();
                    System.out.print("Departure airport: ");
                    String dep2 = KB.nextLine().toUpperCase();
                    System.out.print("Departure date (Y-M-D): ");
                    String depTime = KB.nextLine();
                    System.out.print("Departure time (H:M): ");
                    String depTime2 = KB.nextLine();
                    FlightDate depp = new FlightDate(Integer.parseInt(depTime.split("-")[0]),
                            Integer.parseInt(depTime.split("-")[1]), Integer.parseInt(depTime.split("-")[2]),
                            Integer.parseInt(depTime2.split(":")[0]), Integer.parseInt(depTime2.split(":")[1]));
                    System.out.print("Arrival airport: ");
                    String arr2 = KB.nextLine().toUpperCase();
                    System.out.print("Arrival date (Y-M-D): ");
                    String arrTime = KB.nextLine();
                    System.out.print("Arrival time (H:M): ");
                    String arrTime2 = KB.nextLine();
                    FlightDate arrr = new FlightDate(Integer.parseInt(arrTime.split("-")[0]),
                            Integer.parseInt(arrTime.split("-")[1]), Integer.parseInt(arrTime.split("-")[2]),
                            Integer.parseInt(arrTime2.split(":")[0]), Integer.parseInt(arrTime2.split(":")[1]));
                    int max = 0;
                    for (Flight f : flightList) {
                        if (f.getFlightNumber() > max) {
                            max = f.getFlightNumber();
                        }
                    }
                    max++;
                    Airport de = null;
                    for (Airport a : airportList) {
                        if (a.getSymbol().equalsIgnoreCase(dep2)) {
                            de = a;
                        }
                    }
                    Airport ar = null;
                    for (Airport a : airportList) {
                        if (a.getSymbol().equalsIgnoreCase(arr2)) {
                            ar = a;
                        }
                    }
                    System.out.print("Capacity: ");
                    int cap = KB.nextInt();
                    flightList.add(new Flight(max, de, ar, depp, arrr, cap));
                    String[] append = { max + "", de.getSymbol(), ar.getSymbol(), depp.toString(), arrr.toString(),
                            cap + "" };
                    addCSVLine("Flights.csv", append);
                    }
                    catch(Exception e){
                     System.out.println("\nYou entered invalid data \n");
                     KB.nextLine();
                    }
                    break;
                case 'e':
                
                    System.out.println();
                    list = (ArrayList) flightList.clone();

                    list.sort(flightComparaer);
                    for (int i = 0; i < list.size(); i++) {
                        System.out.println(list.get(i));
                    }
                    System.out.println();

                    System.out.print("Number of flight to delete: ");
                    int num2 = KB.nextInt();
                    ArrayList<Flight> flightData = (ArrayList) flightList.clone();
                    for (int i = 0; i < flightData.size(); i++) {
                        if (flightData.get(i).getFlightNumber() == num2) {
                            flightData.remove(i);
                            removeCSVLine("Flights.csv", i + 1);
                            i--;
                        }
                    }
                    ArrayList<Reservation> reservationData = (ArrayList) reservationList.clone();
                    for (int i = 0; i < reservationData.size(); i++) {
                        if (reservationData.get(i).getFlight().getFlightNumber() == num2) {
                            reservationData.remove(i);
                            removeCSVLine("Reservation.csv", i+1);
                            i--;
                        }
                    }
                    getFlights(flightList);
                    getReservations(reservationList);
                    break;
                case 'q':
                    break continuePoint;
                default:
                    break;
            }
        }

    }

    private static void manageReservations(Scanner KB) throws Exception {
        ArrayList<Reservation> list = (ArrayList) reservationList.clone();
        continuePoint: while (true) {
            System.out.print("What would you like to do?\n"
                    + "(A) Print in Alphabetical order\n(B) Search for a reservation number\n(C) Search for a reservation name\n(D) Create a new reservation\n(E) Delete reservation\n(F) Delete passenger\n(G) Cancel\n"
                    + "Select: ");
            String fullChoice = KB.nextLine();
            if (fullChoice.length() < 1)
                continue;
            char choice = fullChoice.toLowerCase().charAt(0);
            switch (choice) {
                case 'a':
                    list = (ArrayList) reservationList.clone();
                    System.out.println();
                    System.out.println(
                            "| RES_NUM |            PASSENGERS             | FLIGHT NUM |               DEPARTURE                 |                ARRIVAL               |");
                    System.out.println(
                            "----------------------------------------------------------------------------------------------------------------------------------------------");
                    list.sort((a1, a2) -> a1.getPassengers().toString().compareTo(a2.getPassengers().toString()));
                    for (int i = 0; i < list.size(); i++) {
                        System.out.println(list.get(i).resMenuFormat());
                    }
                    break;
                case 'b':
                    try{
                    System.out.print("Enter reservation number: ");
                    int choiceN = KB.nextInt();
                    System.out.println();
                    for (Reservation R : reservationList) {
                        if (R.getReservationNumber() == choiceN) {
                            System.out.println(R);
                        }
                    }
                    System.out.println();
                    }
                    catch(Exception e){
                     System.out.println("\nYou entered invalid data \n");
                     KB.nextLine();
                    }
                    break;
                case 'c':
                    System.out.print("Name in reservation");
                    String name = KB.nextLine();
                    System.out.println();
                    String[] passengers = new String[passengerList.size() * 2];
                    for (int i = 0; i < passengerList.size(); i++) {
                        passengers[i] = passengerList.get(i).getFirstName();
                        passengers[i + passengerList.size()] = passengerList.get(i).getLastName();
                    }
                    name = fuzzyMatch(passengers, name);
                    System.out.println("Autocorrected: " + name);
                    if (name == null) {
                        System.out.println("Reservation not found");
                    } else {
                        for (int i = 0; i < passengerList.size(); i++) {
                            if (name == passengers[i] || name == passengers[i + passengerList.size()]) {
                                for (Reservation r : reservationList) {
                                    if (r.getPassengers().indexOf(passengerList.get(i)) != -1) {
                                        System.out.println(r);
                                        System.out.println();
                                    }
                                }
                            }
                        }
                    }
                    break;

                case 'd':
                    try{
                    reservationList.add(new Reservation());
                    for (Reservation r: reservationList) {

                    }
                    System.out.println("Please select your flight:");
                    for (int i = 0; i < flightList.size(); i++) {
                        System.out.println(flightList.get(i));
                    }
                    System.out.print("Flight number: ");
                    int num = KB.nextInt();
                    Flight f = null;
                    for (Flight g : flightList) {
                        if (g.getFlightNumber() == num) {
                            f = g;
                        }
                    }
                    reservationList.get(reservationList.size() - 1).changeFlight(f);
                    Boolean c = true;
                    ArrayList<Integer> ids = new ArrayList<>();
                    top: while (true) {
                        if (c) {
                            System.out.print("(N)ew passenger or (e)xisting passenger: ");
                        }
                        System.out.print("");
                        String K = KB.nextLine();
                        if (K.length() < 1) {
                            c = false;
                            continue;
                        }
                        char kx = K.toLowerCase().charAt(0);
                        switch (kx) {
                            case 'n':
                                System.out.print("Full name: ");
                                String fullName = KB.nextLine();
                                System.out.print("Do you want a membership (y/n): ");
                                Passenger p2 = new Passenger(fullName.substring(fullName.lastIndexOf(" ") + 1),
                                        fullName.substring(0, fullName.indexOf(" ")),
                                        KB.nextLine().equalsIgnoreCase("y"));
                                reservationList.get(reservationList.size() - 1).addPassenger(p2);
                                String[] temp = { p2.getLastName(), p2.getFirstName(),
                                        p2.getAffinityMember() ? "yes" : "no", p2.getID() + "",
                                        p2.getAffinityNumber() + "", p2.getMileage() + "" };
                                ids.add(p2.getID());
                                addCSVLine("Passengers.csv", temp);
                                System.out.print("Add another passenger (y/n): ");
                                String casew = KB.nextLine();
                                // String[] temp2 = {
                                // "" + reservationList.get(reservationList.size() - 1).getReservationNumber(),
                                // "" + reservationList.get(reservationList.size() - 1).getFlight()
                                // .getFlightNumber(),
                                // p2.getID() + "" };
                                // addCSVLine("Reservation.csv", temp2);
                                c = true;
                                if (!casew.equalsIgnoreCase("y")) {
                                    break top;
                                }
                                break;
                            case 'e':
                                System.out.print("Full name: ");
                                String fullName2 = KB.nextLine();
                                String[] passengers2 = new String[passengerList.size()];
                                for (int i = 0; i < passengerList.size(); i++) {
                                    passengers2[i] = passengerList.get(i).getFirstName() + " "
                                            + passengerList.get(i).getLastName();
                                }
                                fullName2 = fuzzyMatch(passengers2, fullName2.substring(0, fullName2.indexOf(" ")) + " "
                                        + fullName2.substring(fullName2.lastIndexOf(" ") + 1));
                                for (Passenger p : passengerList) {
                                    if ((p.getFirstName() + " " + p.getLastName()).equals(fullName2)) {
                                        reservationList.get(reservationList.size() - 1).addPassenger(p);
                                        // String[] temp3 = {
                                        // "" + reservationList.get(reservationList.size() - 1)
                                        // .getReservationNumber(),
                                        // "" + reservationList.get(reservationList.size() - 1).getFlight()
                                        // .getFlightNumber(),
                                        // p.getID() + "" };
                                        // addCSVLine("Reservation.csv", temp3);
                                        ids.add(p.getID());
                                    }
                                }
                                System.out.print("Add another passenger (y/n): ");
                                String casex = KB.nextLine();
                                c = true;
                                if (!casex.equalsIgnoreCase("y")) {
                                    break top;
                                }
                                break;
                            default:
                                break;
                        }
                    }
                    String ids_in_res = "";
                    for (int i : ids) {
                        ids_in_res += i + " ";
                    }
                    String[] temp3 = {
                            "" + reservationList.get(reservationList.size() - 1)
                                    .getReservationNumber(),
                            "" + reservationList.get(reservationList.size() - 1).getFlight()
                                    .getFlightNumber(),
                            ids_in_res + "" };
                    addCSVLine("Reservation.csv", temp3);
                    System.out.println("UPDATED RESERVATIONS");
                    getPassengers(passengerList);
                    getReservations(reservationList);
                    }
                    catch(Exception e){
                     System.out.println("\nYou entered invalid data \n");
                     KB.nextLine();
                    }
                    break;
                case 'e':
                    list.sort((a1, a2) -> a1.getPassengers().toString().compareTo(a2.getPassengers().toString()));
                    for (int i = 0; i < list.size(); i++) {
                        System.out.println(list.get(i));
                    }
                    System.out.print("ID of reservation to delete: ");
                    int IDD = KB.nextInt();
                    for (int i = 0; i < reservationList.size(); i++) {
                        if (IDD == reservationList.get(i).getReservationNumber()) {
                            reservationList.remove(i);
                            i--;
                        }
                        ArrayList<Reservation> data4 = new ArrayList<>();
                        getReservations(data4);
                        for (int j = 0; j < data4.size(); j++) {
                            if (data4.get(j).getReservationNumber() == IDD) {
                                removeCSVLine("Reservation.csv", j+1);
                                getReservations(data4);
                                j--;
                            }
                        }
                    }
                    break;
                case 'f':

                    ArrayList<Passenger> Alist = (ArrayList) passengerList.clone();
                    Alist.sort((a1, a2) -> a1.toString().compareTo(a2.toString()));
                    String[] PassengerFull = new String[Alist.size()];
                    for (int i = 0; i < Alist.size(); i++) {
                        PassengerFull[i] = (Alist.get(i).getFirstName() + Alist.get(i).getLastName());
                    }
                    for (int i = 0; i < Alist.size(); i++) {
                        System.out.println(Alist.get(i));
                    }
                    System.out.print("name of passenger to delete: ");
                    String STR = KB.next();
                    STR = fuzzyMatch(PassengerFull, STR);
                    for (int i = 0; i < passengerList.size(); i++) {
                        if (STR == passengerList.get(i).toString()) {
                            passengerList.remove(i);
                            i--;
                        }
                    }
                    break;
                case 'g':
                    break continuePoint;
                default:
                    break;
            }

        }

    }

    private static boolean saveToFile() {
        File oldFile = new File(FILENAME);
        if (oldFile.exists()) {
            int index = FILENAME.indexOf('.');
            String backupName = FILENAME.substring(0, index);
            backupName += "_BAKUP.dat";
            oldFile.renameTo(new File(backupName));
        }
        boolean success = false;
        try {
            FileOutputStream writeData = new FileOutputStream(FILENAME);
            ObjectOutputStream writeStream = new ObjectOutputStream(writeData);

            writeStream.writeObject(airportList);
            writeStream.writeObject(flightList);
            writeStream.writeObject(reservationList);
            writeStream.writeObject(passengerList);
            writeStream.flush();
            writeStream.close();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return success;
    }

    private static void readFromFile() {
        ArrayList<Passenger> myList = null;
        try {
            FileInputStream readData = new FileInputStream(FILENAME);
            ObjectInputStream readStream = new ObjectInputStream(readData);

            airportList = (ArrayList<Airport>) readStream.readObject();
            flightList = (ArrayList<Flight>) readStream.readObject();
            reservationList = (ArrayList<Reservation>) readStream.readObject();
            passengerList = (ArrayList<Passenger>) readStream.readObject();
            readStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            airportList = new ArrayList<Airport>();
            flightList = new ArrayList<Flight>();
            reservationList = new ArrayList<Reservation>();
            passengerList = new ArrayList<Passenger>();
            saveToFile();
        }
    }

    public static void printMenu(Scanner KB) {
        continuePoint: while (true) {
            System.out.print("Please select what you want to print?"
                    + "\n(R)eservations\n(F)lights\n(P)assengers\n(A)irports\n(S)pecific flight's passengers\n(Q)uit\n"
                    + "\nSelect: ");
            String fullChoice = KB.nextLine();
            if (fullChoice.length() < 1)
                continue;
            char choice = fullChoice.toLowerCase().charAt(0);
            switch (choice) {
                case 'r':
                    System.out.println();
                    System.out.println(
                            "| RES_NUM |            PASSENGERS             | FLIGHT NUM |               DEPARTURE                 |                ARRIVAL               |");
                    System.out.println(
                            "----------------------------------------------------------------------------------------------------------------------------------------------");
                    for (Reservation r : reservationList)
                        System.out.println(r.resMenuFormat());
                    System.out.println();
                    break;
                case 'f':
                    System.out.println();
                    for (Flight f : flightList)
                        System.out.println(f.toString());
                    System.out.println();
                    break;
                case 'p':
                System.out.println();
                    System.out.println();
                    for (Passenger p : passengerList)
                        System.out.println(p.toString());
                    System.out.println();
                    break;
                case 'a':
                    System.out.println();
                    for (Airport a : airportList)
                        System.out.println(a.toString());
                    System.out.println();
                    break;
                case 'g':
                    System.out.print("Which flight ");
                    int flightNum = KB.nextInt();
                    System.out.println();
                    System.out.println("This is the data for flight " + flightNum);
                    for (Reservation r : reservationList) {
                        if (r.getFlight().getFlightNumber() == flightNum) {
                            for (Passenger p : r.getPassengers()) {
                                System.out.println(p);
                            }
                        }
                    }
                    System.out.println();
                    break;
                case 'q':
                    break continuePoint;
                default:
                    break;
            }
        }
    }

    public static String fuzzyMatch(String[] options, String match) {
        if (match.length() > 0) {
            int[] matches = new int[options.length];
            int closest = -1;
            int closestj = 0;
            for (int j = 0; j < options.length; j++) {
                String R = options[j];
                int[] top = new int[R.toString().length() + 1];
                int[] bottom = new int[R.toString().length() + 1];
                bottom[0] = 1;
                for (int i = 0; i < top.length; i++) {
                    top[i] = i;
                }
                for (; bottom[0] < match.length() + 1; bottom[0]++) {
                    for (int i = 1; i < bottom.length; i++) {
                        if (R.substring(i - 1, i).equals(match.substring(bottom[0] - 1, bottom[0]))) {
                            bottom[i] = Math.min(Math.min(top[i] + 1, bottom[i - 1] + 1), top[i - 1]);
                        } else {
                            bottom[i] = Math.min(Math.min(top[i] + 1, bottom[i - 1] + 1), top[i - 1] + 1);
                        }
                    }
                    top = bottom.clone();
                    bottom = new int[R.toString().length() + 1];
                    bottom[0] = top[0];
                }
                matches[j] = top[top.length - 1];
                if (top[top.length - 1] < closest || closest < 0) {
                    closest = top[top.length - 1];
                    closestj = j;
                }
            }
            double stdev = 0;
            double mean = 0;
            for (int m : matches) {
                mean += m;
            }
            mean /= matches.length;
            for (int m : matches) {
                stdev = (m - mean) * (m - mean);
            }
            stdev /= matches.length;
            stdev = Math.sqrt(stdev);
            if (mean - 3 * stdev > closest) {
                return options[closestj];
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static void writeToExcelTMSpreadsheet(String name, ArrayList<String[]> data)
            throws FileNotFoundException, IOException {
        File spreadsheet = new File(name);
        spreadsheet.createNewFile();
        PrintStream dataStream = new PrintStream(spreadsheet);
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(i).length; j++) {
                dataStream.print(data.get(i)[j]);
                if (j + 1 < data.get(i).length) {
                    dataStream.print(",");
                }
            }
            if (i + 1 < data.size()) {
                dataStream.println();
            }
        }
        dataStream.flush();
        dataStream.close();
    }

    public static ArrayList<String[]> readFromExcelTMSpreadsheet(String name)
            throws FileNotFoundException, IOException {
        File file = new File(name);
        Scanner scanner = new Scanner(file);
        ArrayList<String[]> data = new ArrayList<String[]>();
        while (scanner.hasNextLine()) {
            String[] temp = scanner.nextLine().split(",");
            if (temp.length == 1 && temp[0].equals("")) {
                break;
            } else {
                data.add(temp);
            }
        }
        scanner.close();
        return data;
    }

    public static ArrayList<Airport> airportFromArray(ArrayList<String[]> array) {
        ArrayList<Airport> list = new ArrayList<Airport>();
        for (int i = 0; i < array.size(); i++) {
            list.add(new Airport(array.get(i)[0], array.get(i)[1], array.get(i)[2], array.get(i)[3]));
        }
        return list;
    }

    public static ArrayList<Flight> flightsFromArray(ArrayList<String[]> array) {
        ArrayList<Flight> list = new ArrayList<Flight>();
        for (int i = 1; i < array.size(); i++) {
            Airport arrival = findAirportByName(array.get(i)[2]);
            Airport departure = findAirportByName(array.get(i)[1]);
            FlightDate arrivalDate = new FlightDate(Integer.parseInt(array.get(i)[4].split("[-T:]")[0]),
                    Integer.parseInt(array.get(i)[4].split("[-T:]")[1]),
                    Integer.parseInt(array.get(i)[4].split("[-T:]")[2]),
                    Integer.parseInt(array.get(i)[4].split("[-T:]")[3]),
                    Integer.parseInt(array.get(i)[4].split("[-T:]")[4]));
            FlightDate departureDate = new FlightDate(Integer.parseInt(array.get(i)[3].split("[-T:]")[0]),
                    Integer.parseInt(array.get(i)[3].split("[-T:]")[1]),
                    Integer.parseInt(array.get(i)[3].split("[-T:]")[2]),
                    Integer.parseInt(array.get(i)[3].split("[-T:]")[3]),
                    Integer.parseInt(array.get(i)[3].split("[-T:]")[4]));
            list.add(new Flight(Integer.parseInt(array.get(i)[0]), departure, arrival, departureDate, arrivalDate,
                    Integer.parseInt(array.get(i)[5])));
        }
        return list;
    }

    public static Airport findAirportByName(String name) {
        Airport airport = null;
        for (Airport a : airportList) {
            if (a.getName().equals(name)) {
                airport = a;
            }
        }
        return airport;
    }

    public static ArrayList<Reservation> reservationsFromArray(ArrayList<String[]> array) throws Exception {
        ArrayList<Reservation> list = new ArrayList<Reservation>();
        for (int i = 0; i < array.size(); i++) {
            int resID = Integer.parseInt(array.get(i)[0]);
            Reservation reservation = getReservationByID(resID, list);
            if (reservation == null) {
                reservation = new Reservation();
                reservation.setReservationNumber(resID);
                reservation.changeFlight(findFlightByNum(Integer.parseInt(array.get(i)[1])));
                list.add(reservation);
            }
            Passenger passenger = findPassengerByID(Integer.parseInt(array.get(i)[2]));
            reservation.addPassenger(passenger);
        }
        return list;
    }

    public static ArrayList<Passenger> passengersFromArray(ArrayList<String[]> array) {
        ArrayList<Passenger> list = new ArrayList<Passenger>();
        for (int i = 1; i < array.size(); i++) {
            list.add(new Passenger(array.get(i)[0], array.get(i)[1], array.get(i)[2].equals("yes")));
            list.get(i - 1).setID(Integer.parseInt(array.get(i)[3]));
            list.get(i - 1).setAffinityNumber(Integer.parseInt(array.get(i)[4]));
            list.get(i - 1).setMileageBalance(Integer.parseInt(array.get(i)[5]));
        }
        return list;
    }

    public static Flight findFlightByNum(int num) {
        Flight flight = null;
        for (Flight f : flightList) {
            if (f.getFlightNumber() == num) {
                flight = f;
            }
        }
        return flight;
    }

    public static Reservation getReservationByID(int num, ArrayList<Reservation> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getReservationNumber() == num) {
                return list.get(i);
            }
        }
        return null;
    }

    public static Passenger findPassengerByID(int num) {
        Passenger passenger = null;
        for (Passenger p : passengerList) {
            if (p.getID() == num) {
                passenger = p;
            }
        }
        return passenger;
    }

    public static void addCSVLine(String name, String[] append) throws FileNotFoundException, IOException {
        ArrayList<String[]> data = readFromExcelTMSpreadsheet(name);
        data.add(append);
        writeToExcelTMSpreadsheet(name, data);
    }

    public static void removeCSVLine(String name, int line) throws FileNotFoundException, IOException {
        ArrayList<String[]> data = readFromExcelTMSpreadsheet(name);
        data.remove(line);
        writeToExcelTMSpreadsheet(name, data);
    }
}
