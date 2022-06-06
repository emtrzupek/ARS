import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

// Flight.java
// by Aryan Karani
// Period 2
// 05/11/2022

public class Flight implements Serializable {
    Airport departureAirport;
    Airport arrivalAirport;
    FlightDate departure; 
    FlightDate arrival;
    int flightNumber; 
    int capacity;

    public Flight(int flightNumber, Airport departureAirport, Airport arrivalAirport, FlightDate departure, FlightDate arrival, int capacity) {
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departure = departure;
        this.arrival = arrival;
        this.capacity = capacity;
    }

    public void delayFlight(int days, int hours, int minutes) { // delays flights by days, hours minutes
        int[] departureTime = addingTime(departure, days, hours, minutes); // returns array of new values for time
        int[] arrivalTime = addingTime(arrival, days, hours, minutes);
        departure = new FlightDate(departureTime[0], departureTime[1], departureTime[2], departureTime[3], departureTime[4]); // Make new flightDate objects using return value array from addingTIme
        arrival = new FlightDate(arrivalTime[0], arrivalTime[1], arrivalTime[2], arrivalTime[3], arrivalTime[4]);
    }

    private int[] addingTime(FlightDate date, int days, int hours, int minutes) { // returns an array of values used to create new FlightDate object in delayFlight
        LocalDateTime dateTime = date.getLocalDateTimeObject(); // get a copy of the LocalDateTimeObject for the passed in date
        dateTime = dateTime.plusDays(days); 
        dateTime = dateTime.plusHours(hours);
        dateTime = dateTime.plusMinutes(minutes);
        return new int[]{dateTime.getYear(), dateTime.getMonthValue(), dateTime.getDayOfMonth(), dateTime.getHour(), dateTime.getMinute()}; // return array of values, year, month, day hour, minutes
    }

    public void changeArrivalAirport(Airport newAirport) {
            arrivalAirport = newAirport;
    }

    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    public FlightDate getDepartureDate() {
        return departure;
    }
    
    public FlightDate getArrivalDate() {
        return arrival;
    }

    public int getFlightNumber() {
        return flightNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public String toString() {
        return "Flight #" + flightNumber + ", Departure: " + departureAirport.getName() + " - " + departure.getDatePretty() + " " + departure.getTimePretty() + ", Arrival: " + arrivalAirport.getName() + " - " + arrival.getDatePretty() + " " + arrival.getTimePretty();   
    }
}
