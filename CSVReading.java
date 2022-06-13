import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
        
import java.io.FileWriter;
import java.io.IOError;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
public class CSVReading {
    
    public static void main(String[] args) throws IOException{
            
    }

    public static void getFlights(ArrayList<Flight> flights) {
        CSVReader csv = new CSVReader("Flights.csv");
        int records = csv.getNumberOfRecords();
        for (int i = 0; i < records; i++) {
            String[] nextRecord = csv.getRecord(i+1);
            flights.add(new Flight(Integer.parseInt(nextRecord[0]), getAirportFromSymbol(nextRecord[1], airportList), nextRecord[2], new FlightDate(LocalDateTime.parse(nextRecord[3])), new FlightDate(LocalDateTime.parse(nextRecord[4])), Integer.parseInt(nextRecord[5])));
            int flightNumber = Integer.parseInt(nextRecord[0]);
            Airport departure = getAirportFromSymbol(nextRecord[1], airportList);
            Airport arrival = getAirportFromSymbol(nextRecord[2], airportList);
            FlightDate departureDate = new FlightDate(LocalDateTime.parse(nextRecord[3]));
            FlightDate arrivalDate = new FlightDate(LocalDateTime.parse(nextRecord[4]));
            int capacity = Integer.parseInt(nextRecord[5]);
            flights.add(new Flight(flightNumber, departure, arrival, departureDate, arrivalDate, capacity));
        }
    }

    public static void addFlight(Flight flight) throws IOException {
        FileWriter flightWriter = new FileWriter("Flights.csv", true);
        flightWriter.write("\n" + flight.getFlightNumber() + "," + flight.getDepartureAirport().getSymbol() + "," + flight.getArrivalAirport().getSymbol() + "," + flight.getDepartureDate().getLocalDateTimeObject().toString() + "," + flight.getArrivalDate().toString() + "," + flight.getCapacity());
        flightWriter.flush();
        flightWriter.close();
    }

    public static Airport getAirportFromSymbol(String symbol, ArrayList<Airport> airports) {
        for (Airport a: airports) {
            if (a.getSymbol().equalsIgnoreCase(symbol)) {
                return a;
            }
        }
        System.out.println("COULD NOT FIND AIRPORT FROM SYMBOL " + symbol + " CHECK DATABASE"); 
        return null;
    }

}
