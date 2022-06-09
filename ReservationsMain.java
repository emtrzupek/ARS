/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//package ARS;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.*;

import java.io.*;
/**
 *
 * @aut
 * Emily
 */
public class ReservationsMain{
   private static final String FILENAME = "Passenger.dat";
	private static ArrayList<Flight> flightList;
	private static ArrayList<Airport> airportList;
	private static ArrayList<Reservation> reservationList;
	private static ArrayList<Passenger> passengerList;
	public static void main(String args[]) throws Exception, FileNotFoundException, IOException {
            readFromFile();
            //String[][] data = {{"1", "2"}, {"3", "4"}};
            //writeToExcelTMSpreadsheet("Airports.csv", data);
            airportList = airportFromArray(readFromExcelTMSpreadsheet("Airports.csv"));
            flightList = flightsFromArray(readFromExcelTMSpreadsheet("Flights.csv"));
            passengerList = passengersFromArray(readFromExcelTMSpreadsheet("Passengers.csv"));
            reservationList = reservationsFromArray(readFromExcelTMSpreadsheet("Reservation.csv"));
            /*
            flightList=new ArrayList<Flight>();
            airportList=new ArrayList<Airport>();
            passengerList=new ArrayList<Passenger>();
            */
            mainMenu();
	}
   private static void mainMenu() throws Exception {
               Scanner KB=new Scanner(System.in);
            continuePoint:
            while(true){
                    System.out.println("what would you like to do\n"
                            + "(P) print things, (R) manage reservations, (A) manage Flights: (e) cancel");
                    String fullChoice=KB.nextLine();
                    if(fullChoice.length()<1)
                        continue;
                    char choice=fullChoice.toLowerCase().charAt(0);
                    switch (choice){
                        case 'a':            
                            manageAirports(KB);
                            break;
                        case 'r':
                           manageReservations(KB);
                           break;
                        case 'p':
                            printMenu(KB);
                            break;
                        case 'e':
                            break continuePoint;
                        default:
                            break;
                    }
                }
                            
   }
      private static void manageAirports(Scanner KB) throws Exception, FileNotFoundException, IOException {
            Comparator<Flight> flightComparaer = ((Flight f1, Flight f2) -> (int)(f1.toString().compareTo(f2.toString())));
            continuePoint:
            
            while(true){
                    System.out.print("what would you like to do\n"
                            + "The (A) print in Alphabetical order, (B) search for a flight number, (C) search for a flight name, (D) create a new flight, (E) delete flight,   (F) cancel\n"
                            + "you choose: ");
                    String fullChoice=KB.nextLine();
                    if(fullChoice.length()<1)
                        continue;
                    char choice=fullChoice.toLowerCase().charAt(0);
                    switch (choice){
                        case 'a':            
                            
                           ArrayList<Flight> list = (ArrayList)flightList.clone();

                           
                           list.sort(flightComparaer);
                           System.out.println(list.toString().substring(1, list.toString().length() - 1).replace(", F", "\nF"));
                            break;
                        case 'b':
                            int choiceN=KB.nextInt();
                            for(Flight R:flightList){
                              if(R.getFlightNumber()==choiceN){
                                 System.out.println(R);
                              }
                            }
                            break ;
                        case 'c':
                            System.out.print("Departure airport: ");
                            String dep = KB.nextLine().toUpperCase();
                            System.out.print("Arrival airport:");
                            String arr = KB.nextLine().toUpperCase();
                            for (Flight f:flightList) {
                                if (f.getArrivalAirport().getName().toUpperCase().equals(arr) && f.getDepartureAirport().getName().toUpperCase().equals(dep)) {
                                    System.out.println(f);
                                }
                            }
                            break ;
                               
                        case 'd':
                            System.out.print("Departure airport: ");
                            String dep2 = KB.nextLine().toUpperCase();
                            System.out.print("Departure date (Y-M-D): ");
                            String depTime = KB.nextLine();
                            System.out.print("Departure time (H:M): ");
                            String depTime2 = KB.nextLine();
                            FlightDate depp = new FlightDate(Integer.parseInt(depTime.split("-")[0]), Integer.parseInt(depTime.split("-")[1]), Integer.parseInt(depTime.split("-")[2]), Integer.parseInt(depTime2.split(":")[0]), Integer.parseInt(depTime2.split(":")[1]));
                            System.out.print("Arrival airport: ");
                            String arr2 = KB.nextLine().toUpperCase();
                            System.out.print("Arrival date (Y-M-D): ");
                            String arrTime = KB.nextLine();
                            System.out.print("Arrival time (H:M): ");
                            String arrTime2 = KB.nextLine();
                            FlightDate arrr = new FlightDate(Integer.parseInt(arrTime.split("-")[0]), Integer.parseInt(arrTime.split("-")[1]), Integer.parseInt(arrTime.split("-")[2]), Integer.parseInt(arrTime2.split(":")[0]), Integer.parseInt(arrTime2.split(":")[1]));
                            int max = 0;
                            for (Flight f:flightList) {
                                if (f.getFlightNumber() > max) {
                                    max = f.getFlightNumber();
                                }
                            }
                            max++;
                            Airport de = null;
                            for (Airport a:airportList) {
                                if (a.getName().equalsIgnoreCase(dep2)) {
                                    de = a;
                                }
                            }
                            Airport ar = null;
                            for (Airport a:airportList) {
                                if (a.getName().equalsIgnoreCase(arr2)) {
                                    ar = a;
                                }
                            }
                            System.out.print("Capacity: ");
                            int cap = KB.nextInt();
                            flightList.add(new Flight(max, de, ar, depp, arrr, cap));
                            String[] append = {max + "", de.getName(), ar.getName(), depp.toString(), arrr.toString(), cap + ""};
                            addCSVLine("Flights.csv", append);
                            break ;
                        case 'e':
                            System.out.print("Number of flight to delete: ");
                            int num2 = KB.nextInt();
                            for (int i = 0; i < flightList.size(); i++) {
                                if (flightList.get(i).getFlightNumber() == num2) {
                                    flightList.remove(i);
                                    removeCSVLine("Flights.csv", i + 1);
                                    i--;
                                }
                            }
                            ArrayList<String[]> reservationData = readFromExcelTMSpreadsheet("Reservation.csv");
                            for (int i = 0; i < reservationData.size(); i++) {
                                if (Integer.parseInt(reservationData.get(i)[1]) == num2) {
                                    removeCSVLine("Reservation.csv", i);
                                    reservationData = readFromExcelTMSpreadsheet("Reservation.csv");
                                    i--;
                                }
                            }
                            reservationList = reservationsFromArray(reservationData);
                            break;
                        case 'f':
                            break continuePoint;
                        default:
                            break ;
                    }
                }

   }
   private static void manageReservations(Scanner KB) throws Exception {
            continuePoint:
            while(true){
                    System.out.print("what would you like to do\n"
                            + "The (A) print in Alphabetical order, (B) search for a reservation number, (C) search for a reservation name, (D) create a new reservation, (E) delete reservation,   (F) cancel\n"
                            + "you choose: ");
                    String fullChoice=KB.nextLine();
                    if(fullChoice.length()<1)
                        continue;
                    char choice=fullChoice.toLowerCase().charAt(0);
                    switch (choice){
                        case 'a':            
                           ArrayList<Reservation> list=(ArrayList)reservationList.clone();
                           list.sort((a1,a2)->a1.getPassengers().toString().compareTo(a2.getPassengers().toString()));
                           System.out.println(list.toString().substring(1, list.toString().length() - 1).replace(", R", "\nR"));
                            break;
                        case 'b':
                            int choiceN=KB.nextInt();
                            for(Reservation R:reservationList){
                              if(R.getReservationNumber()==choiceN){
                                 System.out.println(R);
                              }
                            }
                            break;
                        case 'c':
                            String name = KB.nextLine();
                            String[] passengers = new String[passengerList.size() * 2];
                            for (int i = 0; i < passengerList.size(); i++) {
                                passengers[i] = passengerList.get(i).getFirstName();
                                passengers[i + passengerList.size()] = passengerList.get(i).getLastName();
                            }
                            name = fuzzyMatch(passengers, name);
                            System.out.println(name);
                            if (name == null) {
                                System.out.println("Reservation not found");
                            } else {
                                for (int i = 0; i < passengerList.size(); i++) {
                                    if (name == passengers[i] || name == passengers[i + passengerList.size()]) {
                                        for (Reservation r:reservationList) {
                                            if (r.getPassengers().indexOf(passengerList.get(i)) != -1) {
                                                System.out.println(r);
                                            }
                                        }
                                    }
                                }
                            }
                            break;
                               
                        case 'd':
                            reservationList.add(new Reservation());
                            System.out.println("Please select your flight:");
                            for (int i = 0; i < flightList.size(); i++) {
                                System.out.println(flightList.get(i));
                            }
                            System.out.print("Flight number: ");
                            int num = KB.nextInt();
                            Flight f = null;
                            for (Flight g:flightList) {
                                if (g.getFlightNumber() == num) {
                                    f = g;
                                }
                            }
                            reservationList.get(reservationList.size() - 1).changeFlight(f);
                            Boolean c = true;
                            top:
                            while (true) {
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
                                        Passenger p2 = new Passenger(fullName.substring(fullName.lastIndexOf(" ") + 1), fullName.substring(0, fullName.indexOf(" ")), KB.nextLine().equalsIgnoreCase("y"));
                                        reservationList.get(reservationList.size() - 1).addPassenger(p2);
                                        String[] temp = {p2.getLastName(), p2.getFirstName(), p2.getAffinityMember() ? "yes" : "no", p2.getID() + "", p2.getAffinityNumber() + "", p2.getMileage() + ""};
                                        addCSVLine("Passengers.csv", temp);
                                        System.out.print("Add another passenger (y/n): ");
                                        String casew = KB.nextLine();
                                        String[] temp2 = {"" + reservationList.get(reservationList.size() - 1).getReservationNumber(), "" + reservationList.get(reservationList.size() - 1).getFlight().getFlightNumber(), p2.getID() + ""};
                                        addCSVLine("Reservation.csv", temp2);
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
                                            passengers2[i] = passengerList.get(i).getFirstName() + " " + passengerList.get(i).getLastName();
                                        }
                                        fullName2 = fuzzyMatch(passengers2, fullName2.substring(0, fullName2.indexOf(" ")) + " " + fullName2.substring(fullName2.lastIndexOf(" ") + 1));
                                        for (Passenger p:passengerList) {
                                            if ((p.getFirstName() + " " + p.getLastName()).equals(fullName2)) {
                                                reservationList.get(reservationList.size() - 1).addPassenger(p);
                                                String[] temp3 = {"" + reservationList.get(reservationList.size() - 1).getReservationNumber(), "" + reservationList.get(reservationList.size() - 1).getFlight().getFlightNumber(), p.getID() + ""};
                                                addCSVLine("Reservation.csv", temp3);
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
                            break;
                        case 'e':
                            System.out.print("ID of reservation to delete: ");
                            int IDD = KB.nextInt();
                            for (int i = 0; i < reservationList.size(); i++) {
                                if (IDD == reservationList.get(i).getReservationNumber()) {
                                    reservationList.remove(i);
                                    i--;
                                }
                                ArrayList<String[]> data4 = readFromExcelTMSpreadsheet("Reservation.csv");
                                for (int j = 0; j < data4.size(); j++) {
                                    if (Integer.parseInt(data4.get(j)[0]) == IDD) {
                                        removeCSVLine("Reservation.csv", j);
                                        data4 = readFromExcelTMSpreadsheet("Reservation.csv");
                                        j--;
                                    }
                                }
                            }
                            break;
                        case 'f':
                            break continuePoint;
                        default:
                            break;
                    }
                }

   }
   private static boolean saveToFile(){
      File oldFile = new File(FILENAME);
      if(oldFile.exists()){
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
      } catch (IOException e){
         e.printStackTrace();
      }
      return success;
   }
      private static void readFromFile(){
      ArrayList<Passenger> myList = null;
      try{
         FileInputStream readData = new FileInputStream(FILENAME);
         ObjectInputStream readStream = new ObjectInputStream(readData);
          
          airportList = (ArrayList<Airport>) readStream.readObject();
          flightList = (ArrayList<Flight>) readStream.readObject();
          reservationList= (ArrayList<Reservation>) readStream.readObject();
          passengerList = (ArrayList<Passenger>) readStream.readObject();
         readStream.close();
      } catch (Exception e){
         e.printStackTrace();
         airportList=new ArrayList<Airport>();
         flightList=new ArrayList<Flight>();
         reservationList=new ArrayList<Reservation>();
         passengerList = new ArrayList<Passenger>();
         saveToFile();
      }
   }
	public static void printMenu(Scanner KB){
            continuePoint:
            while(true){
                    System.out.print("Please select what you want to print?\n"
                            + "The (R)eservations, (F)lights, (P)assengers, (A)irports, passengers in a (G)iven flight, (E)ancel\n"
                            + "you choose: ");
                    String fullChoice=KB.nextLine();
                    if(fullChoice.length()<1)
                        continue;
                    char choice=fullChoice.toLowerCase().charAt(0);
                    switch (choice){
                        case 'r': System.out.println("all of the reservations");
                            break;
                        case 'f':for(Flight f:flightList) System.out.println(f.toString());
                            break;
                        case 'p':for(Passenger p:passengerList) System.out.println(p.toString());
                            break;
                        case 'a':for(Airport a:airportList) System.out.println(a.toString());
                            break;
                        case 'g':
                            System.out.print("Which flight ");
                            int flightNum = KB.nextInt();
                            System.out.println("This is the data for flight "+flightNum);
                            for (Reservation r:reservationList) {
                                if (r.getFlight().getFlightNumber() == flightNum) {
                                    for (Passenger p:r.getPassengers()) {
                                        System.out.println(p);
                                    }
                                }
                            }
                            break;
                        case 'e':
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
            for (int m:matches) {
                mean += m;
            }
            mean /= matches.length;
            for (int m: matches) {
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
    public static void writeToExcelTMSpreadsheet(String name, ArrayList<String[]> data) throws FileNotFoundException, IOException {
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
    public static ArrayList<String[]> readFromExcelTMSpreadsheet(String name) throws FileNotFoundException, IOException {
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
            FlightDate arrivalDate = new FlightDate(Integer.parseInt(array.get(i)[4].split("[-T:]")[0]), Integer.parseInt(array.get(i)[4].split("[-T:]")[1]), Integer.parseInt(array.get(i)[4].split("[-T:]")[2]), Integer.parseInt(array.get(i)[4].split("[-T:]")[3]), Integer.parseInt(array.get(i)[4].split("[-T:]")[4]));
            FlightDate departureDate = new FlightDate(Integer.parseInt(array.get(i)[3].split("[-T:]")[0]), Integer.parseInt(array.get(i)[3].split("[-T:]")[1]), Integer.parseInt(array.get(i)[3].split("[-T:]")[2]), Integer.parseInt(array.get(i)[3].split("[-T:]")[3]), Integer.parseInt(array.get(i)[3].split("[-T:]")[4]));
            list.add(new Flight(Integer.parseInt(array.get(i)[0]), departure, arrival, departureDate, arrivalDate, Integer.parseInt(array.get(i)[5])));
        }
        return list;
    }
    public static Airport findAirportByName(String name) {
        Airport airport = null;
        for (Airport a:airportList) {
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
        for (Flight f:flightList) {
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
        for (Passenger p:passengerList) {
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