import flight.Passenger;
import flight.FlightDate;
import flight.Airport;
import flight.Flight;
import flight.Reservation;
import java.lang.Exception;

import java.io.Serializable;
import java.util.ArrayList;
public class TestReservation {
    public static void main(String[] args) throws Exception {
        ArrayList<Passenger> passengers = new ArrayList<Passenger>();
        passengers.add(new Passenger("Walters", "Sarah", true));
        passengers.add(new Passenger("Watson", "Joe", false));
        passengers.add(new Passenger("Robertson", "Arnold", false));
        Reservation reservation = new Reservation(passengers, new Flight(new Airport("KSEA", "Seattle", "Washington"), new Airport("WEHM", "Wyoming", "Florida"), new FlightDate(2001, 3, 21, 14, 3), new FlightDate(2002, 3, 21, 14, 3), 420));
        System.out.println(reservation.getPassengers().toString());
        System.out.println(reservation.getFlight().toString());
        System.out.println(reservation.getReservationNumber());
        Passenger newPerson = new Passenger("Scott", "Tom", true);
        reservation.addPassenger(newPerson);
        System.out.println(reservation.getPassengers().toString());
        System.out.println(reservation.getFlight().toString());
        System.out.println(reservation.getReservationNumber());
        reservation.removePassenger(newPerson);
        System.out.println(reservation.getPassengers().toString());
        System.out.println(reservation.getFlight().toString());
        System.out.println(reservation.getReservationNumber());
        reservation.changeFlight(new Flight(new Airport("KSEA", "Seattle", "Washington"), new Airport("WEHM", "Wyoming", "Florida"), new FlightDate(2030, 3, 21, 14, 3), new FlightDate(2002, 3, 21, 14, 3), 420));
        System.out.println(reservation.getPassengers().toString());
        System.out.println(reservation.getFlight().toString());
        System.out.println(reservation.getReservationNumber());
        System.out.println(reservation.toString());
    }
}