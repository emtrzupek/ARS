// package flight;
import java.io.Serializable;
import java.util.ArrayList;
import java.lang.Exception;
public class Reservation implements Serializable {
    private ArrayList<Passenger> passengers; // ArrayList of Passenger objects
    private Flight flight; 
    private static int resNum = -1; // represents the next reservation number/ID to be assigned
    private int reservationNum; // reservation ID/num for this object

    public Reservation(ArrayList<Passenger> p, Flight f) throws Exception {
        for (int i = 0; i < p.size(); i++) { // Check for duplicate passengers and throw error if duplicate is found
            for (int j = i + 1; j < p.size(); j++) {
                if (p.get(i).getID() == p.get(j).getID()) {
                    throw new Exception("passenger " + p.get(i).getID() + " has been listed more than once");
                }
            }
        }
        p.sort((o1, o2) -> (o1.getFirstName() + " " + o1.getLastName()).compareTo((o2.getFirstName() + ", " + o2.getLastName())));
        passengers = p; // set instance variables
        flight = f;
        resNum++; // increments the next reservation id
        reservationNum = resNum;
    }
    public Reservation() { // Constructor for empty passenger array
        passengers = new ArrayList<Passenger>();
        flight = null;
        resNum++;
        reservationNum = resNum;
    }
    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }
    public Flight getFlight() {
        return flight;
    }
    public int getReservationNumber() {
        return reservationNum;
    }
    public void addPassenger(Passenger newPas) throws Exception {
        for (Passenger p:passengers) { // checks passenger array for duplicate passenger, using ID
            if (p.getID() == newPas.getID()) {
                throw new Exception("passenger " + p.getID() + " is already in the reservation");
            }
        }
        passengers.add(newPas); // adds passenger to arraylist
        passengers.sort((o1, o2) -> (o1.getFirstName() + " " + o1.getLastName()).compareTo((o2.getFirstName() + ", " + o2.getLastName())));
    }
    public void removePassenger(Passenger pas) throws Exception {
        for (int i = 0; i < passengers.size(); i++) { // loops through arraylist looking for matching passenger to remove
            if (passengers.get(i).getID() == pas.getID()) { // checks if passengers match
                passengers.remove(i); // removes passenger from arraylist
                passengers.sort((o1, o2) -> (o1.getFirstName() + " " + o1.getLastName()).compareTo((o2.getFirstName() + ", " + o2.getLastName())));
                return; // end method
            }
        } 
        throw new Exception("passenger " + pas.getID() + " could not be found in the reservation"); // if passenger was not found, throw exception 
    }
    public void changeFlight(Flight newFlight) { // change the flight
        flight = newFlight;
    }
    public void setReservationNumber(int num) {
        reservationNum = num;
        if (resNum < reservationNum) {
            resNum = reservationNum;
        }
    }
    public void changeResNum(int num) {
        resNum = num;
    }
    public String toString() {
        if (passengers.size() == 0) { // if there are no passengers
            return "Empty reservation at flight " + flight.getFlightNumber();
        } else if (passengers.size() == 1) { // if there is one passenger
            return "Reservation for " + passengers.get(0).getFirstName() + " " + passengers.get(0).getLastName() + " at flight " + flight.getFlightNumber();
        } else { // if there are multiple passengers
            String returnVal = passengers.get(0).getFirstName() + " " + passengers.get(0).getLastName();
            for (int i = 1; i < passengers.size() - 1; i++) {
                returnVal += ", " + passengers.get(i).getFirstName() + " " + passengers.get(i).getLastName();
            }

            return "Reservations for " + returnVal + " and " + passengers.get(passengers.size() - 1).getFirstName() + " " + passengers.get(passengers.size() - 1).getLastName() + " at Flight " + flight.getFlightNumber();
        }
    }

    // to string ex. Reservations for [NAME], [NAME], [NAME],... and [NAME] at Flight
}