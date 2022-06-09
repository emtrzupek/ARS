import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class CSVReading {
    
    public static void main(String[] args) {
        
        
    }

    public static ArrayList<Flight> getFlights() {
        ArrayList<Flight> flights = new ArrayList<>();
        CSVReader csv = new CSVReader("Flights.csv");
        String[] fields = csv.getFieldNames();
        int records = csv.getNumberOfRecords();
        for (int i = 0; i < records; i++) {
            String[] nextRecord = csv.getRecord(i+1);
            flights.add(new Flight(Integer.parseInt(nextRecord[0]), getAirportFromSymbol(nextRecord[1], airports), nextRecord[2], new FlightDate(LocalDateTime.parse(nextRecord[3])), new FlightDate(LocalDateTime.parse(nextRecord[4])), Integer.parseInt(nextRecord[5])));
        }
        return flights;
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
