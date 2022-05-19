package flight;
import java.io.Serializable;
import java.util.ArrayList;
import java.lang.Exception;
public class Reservation implements Serializable {
    private ArrayList<Passenger> passengers;
    private Flight flight;
    private static int resNum = -1;
    private int reservationNum;

    public Reservation(ArrayList<Passenger> p, Flight f) throws Exception {
        for (int i = 0; i < p.size(); i++) {
            for (int j = i + 1; j < p.size(); j++) {
                if (p.get(i).getID() == p.get(j).getID()) {
                    throw new Exception("passenger " + p.get(i).getID() + " has been listed more than once");
                }
            }
        }
        passengers = p;
        flight = f;
        resNum++;
        reservationNum = resNum;
    }
    public Reservation() {
        passengers = new ArrayList<Passenger>();
        flight = null;
        reservationNum = -1;
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
        for (Passenger p:passengers) {
            if (p.getID() == newPas.getID()) {
                throw new Exception("passenger " + p.getID() + " is already in the reservation");
            }
        }
        passengers.add(newPas);
    }
    public void removePassenger(Passenger pas) throws Exception {
        boolean a = true;
        for (int i = 0; i < passengers.size(); i++) {
            if (passengers.get(i).getID() == pas.getID()) {
                a = false;
                passengers.remove(i);
                i--;
            }
        }
        if (a) {
            throw new Exception("passenger " + pas.getID() + " could not be found in the reservation");
        }
    }
    public void changeFlight(Flight newFlight) {
        flight = newFlight;
    }
    public String toString() {
        if (passengers.size() == 0) {
            return "Empty reservation at flight " + flight.getFlightNumber();
        } else if (passengers.size() == 1) {
            return "Reservation for " + passengers.get(0).getFirstName() + " " + passengers.get(0).getLastName() + " at flight " + flight.getFlightNumber();
        } else {
            String returnVal = passengers.get(0).getFirstName() + " " + passengers.get(0).getLastName();
            for (int i = 1; i < passengers.size() - 1; i++) {
                returnVal += ", " + passengers.get(i).getFirstName() + " " + passengers.get(i).getLastName();
            }
            return "Reservations for " + returnVal + " and " + passengers.get(passengers.size() - 1).getFirstName() + " " + passengers.get(passengers.size() - 1).getFirstName() + " at flight " + flight.getFlightNumber();
        }
    }
}