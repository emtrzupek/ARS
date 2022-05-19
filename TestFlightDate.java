// TestFlightDate.java
// by Aryan Karani
// Period 2
// 05/10/2022

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TestFlightDate {
   public static void main(String[] args){
	   // Part A -
      // Create at least two two FlightDate objects.
	  // Examine the constructor for the parameters. 
      // Remember, times are in GMT
	  // You may want to put these in an ArrayList of
	  // type FlightDate to make printing for each easier.
	  System.out.println();
      FlightDate fd1 = new FlightDate(2023, 5, 25, 13, 37); // your code here and next line(s)
      FlightDate fd2 = new FlightDate(2018, 4, 12, 21, 59);
	  ArrayList<FlightDate> flights = new ArrayList<>();
	  flights.add(fd1);
	  flights.add(fd2);
	  // Part B -
	  // Print the toString for each object.
	  System.out.println("Part B\n--------------------");
      for (FlightDate f: flights) {
		  System.out.println("toString test: " + f.toString());
	  }
	  System.out.println("\nPart C\n--------------------");
	  // Part C -
	  // Print formattedDateTime for ISO_DATE_TIME for
	  // each object
      for (FlightDate f: flights) {
		  System.out.println("formattedDateTime test: " + f.formattedDateTime(DateTimeFormatter.ISO_DATE_TIME));
	  }
	  System.out.println("\nPart D\n--------------------");
	  // Part D -
	  // For each FlightDate object print the date in 
	  // pretty format followed by the time in pretty 
	  // format.
      for (FlightDate f: flights) {
		  System.out.println("getDatePretty test: " + f.getDatePretty());
	  }
	  System.out.println("\nPart E\n--------------------");
      // Part E -
	  // Pick two FlightDate objects. For each of these
	  // get the LocalDateTime object.
      LocalDateTime fd1DT = fd1.getLocalDateTimeObject();// your code here, plus a 2nd line
	  LocalDateTime fd2DT = fd2.getLocalDateTimeObject();
   
	  // Part E continued
	  // Print the hours time difference between them 
	  // then the minutes time between them.
	  // Finally, print the total time difference between
	  // them in a pretty format.
      System.out.println("getDifferenceHours test: " + fd1.getDifferenceHours(fd2DT));
	  System.out.println("getDifferenceMinutes test: " + fd1.getDifferenceMinutes(fd2DT));
	  System.out.println("getTimeDifferencePretty test: " + fd1.getTimeDifferencePretty(fd2DT));
	  System.out.println("\nPart F\n--------------------");
	  // Part F
	  // Pick one of the FlightDate objects. For that object
	  // print the day of the week as an int then
	  // print the day of the week as a String
	  System.out.println("getDayOfWeekAsInt test: " + fd1.getDayOfWeekAsInt());
	  System.out.println("getDayOfWeekAsString test: " + fd1.getDayOfWeekAsString());
	  System.out.println();
   }
}
