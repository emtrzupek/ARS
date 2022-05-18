// TestFlight.java
// by Aryan Karani
// Period 2
// 05/13/2022

public class TestFlight {
    public static void main(String[] args) {
        Flight f1 = new Flight(new Airport("SeaTac", "Seattle", "Washington"), new Airport("Orlando International", "Orlando", "Florida"), new FlightDate(2022, 5, 23, 5, 20), new FlightDate(2022, 5, 23, 10, 20), 200);
        System.out.println(f1);
        f1.delayFlight(120, 32, 40);
        System.out.println(f1);
        f1.changeArrivalAirport(new Airport("Shivaji International Airport", "Mumbai", "Maharashtra"));
        f1.changeDepartureAirport(new Airport("Orlando International New", "Orlando", "Florida"));
        System.out.println(f1);
    }
}
