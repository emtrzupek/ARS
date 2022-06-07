/*
 * TestPassenger.java
 * by w p osborne
 * 4/5/2022
 */

import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;

public class TestPassengerCSV {
   
   // class constant
   private static final String FILENAME = "Passenger.dat";
   
   public static void main(String[] args) {
      ArrayList<Passenger> passengers = new ArrayList<Passenger>();
      
      Scanner kbReader = new Scanner(System.in);

      // show main menu
      // showMainMenu();
      // get user choice
      char choice;
      do{
         showMainMenu();
         choice = getCharacter("ABFDCPSLQ", kbReader);
         System.out.println("You entered " + choice);
         switch (choice) {
            case 'A':
               Passenger newPassenger = getNewPassenger(kbReader);
               if(newPassenger != null){
                  passengers.add(newPassenger);
                  System.out.println("Success adding new passenger.");
               }
               break;
            case 'B': // bulk load from CSV
               int n = bulkLoad(passengers, kbReader);
               System.out.println("Read in " + n + " passengers.");
               break;
            case 'F':
               findPassenger(passengers, kbReader);
               break;
            case 'D':
               break;
            case 'C':
               break;
            case 'P':
               printPassengers(passengers);
               break;
            case 'S':
               boolean result = saveToFile(passengers);
               if(result){
                  System.out.println("Save is successful.");
               } else {
                  System.out.println("Error saving.");
               }
               break;
            case 'L':
               passengers = readFromFile();
               System.out.println("Loaaded List of " + passengers.size() +
                       " passengers.");
               break;
         }
      } while(choice != 'Q');
      
      printPassengers(passengers);
      
      System.out.println("Thanks for testing");
   }
   
   // bulkLoad - reads passenger names and affinity status from a CSV file
   private static int bulkLoad(ArrayList<Passenger> passengers, Scanner kb){
      // steps:
      // 1. get name of file.
      // 2. check if file available and can be read. 
      //    If no, return -1;
      //    else
      // 3. call readFromCSV. Pass file name and passenger list. Returns
      //    number added.
      System.out.print("Enger name of CSV filel, include .csv at end: ");
      String fileName = kb.nextLine();
      System.out.println("You entered: " + fileName);  // debug
      File nameFile = new File(fileName);
      if(!nameFile.exists()){
         System.out.println("File not found.");
      }
      return readFromCSV(fileName, passengers);
   }
   public static int readFromCSV(String fileName, ArrayList<Passenger> passengers){
      int numberAdded = 0;
      // steps:
      // 1. Create CSVReader object.
      // 2. Get fields
      // 3. Get number of records.
      // 4. For each record
      //    Get last name
      //    Get first name
      //    Get mileage status
      //    Create a Passenger object.
      //    if mileage status is "yes" use status of true, else false
      //    Add new object to list
      //    Add one to numberAdded
      // 5. return numberAdded
      CSVReader csvs = new CSVReader(fileName);
      String[] fields = csvs.getFieldNames();
      for(int i = 0; i < fields.length; i++){   // debug
         System.out.println("Field number: " + i + ", name: " + fields[i]);
      }
      // We know that for this database lastName is index 0, 
      // firstName is index 1, and index 2 is "yes" if the person is
      // an affinity club member. So, 
      // 1. Find out how many records
      // 2. For each, create a Passenger object and add it to the list
      
      int numberOfRecords = csvs.getNumberOfRecords();
      for(int i = 0; i < numberOfRecords; i++){
         // read next record fields into an array of Strings
         // Remember, the first record is number 1, not zero
         String[] nextRecord = csvs.getRecord(i + 1);
         System.out.println("lastName: " + nextRecord[0]); // debug
         if(nextRecord[2].length() > 0){
            // check affinity
            boolean affinity = false;
            if(nextRecord[2].equalsIgnoreCase("yes")){
               affinity = true;
            }
            passengers.add(new Passenger(nextRecord[0], nextRecord[1], affinity));
            numberAdded++;
         }
      }
      return numberAdded;
   }
   // Find passengers
   private static void findPassenger(ArrayList<Passenger> passengers, 
           Scanner kb){
      System.out.println();
      System.out.println("Search by\n  A for last name\n  B for ID number");
      int action = getCharacter("AB", kb);
      ArrayList<Passenger> found = new ArrayList<Passenger>();
      if(action == 'A'){
         System.out.print("Enter last name: ");
         String lastName = kb.nextLine();
         for(Passenger p: passengers){
            if(lastName.equalsIgnoreCase(p.getLastName())){
               found.add(p);
            }
         }
      } else {
         System.out.print("Enter passenger ID number: ");
         int number = kb.nextInt();
         for(Passenger p: passengers){
            if(number == p.getID()){
               found.add(p);
            }
         }
      }
      System.out.println();
      if(found.size() == 0){
         System.out.println("Passenger not found.");
      } else {
         for(Passenger p: found){
            System.out.println(p);
         }
      }
   }
   // Read passengers from a file
   private static ArrayList<Passenger> readFromFile(){
      ArrayList<Passenger> myList = null;
      try{
         FileInputStream readData = new FileInputStream(FILENAME);
         ObjectInputStream readStream = new ObjectInputStream(readData);
         
         myList = (ArrayList<Passenger>) readStream.readObject();
         readStream.close();
      } catch (Exception e){
         e.printStackTrace();
      }
      return myList;
   }
   
   // Save passengers to a file
   private static boolean saveToFile(ArrayList<Passenger> list){
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

         writeStream.writeObject(list);
         writeStream.flush();
         writeStream.close();
         success = true;
      } catch (IOException e){
         e.printStackTrace();
      }
      return success;
   }
   
   private static Passenger getNewPassenger(Scanner kb){
      String lastName;
      String firstName;
      boolean joinMileageClub;
      
      System.out.print("Enter last name: ");
      lastName = kb.nextLine();
      System.out.print("Enter first name: ");
      firstName = kb.nextLine();
      System.out.print("Join mileage club? ");
      char choice = getCharacter("YN", kb);
      if(choice == 'Y'){
         joinMileageClub = true;
      } else {
         joinMileageClub = false;
      }
      
      Passenger newPassenger = 
              new Passenger(lastName, firstName, joinMileageClub);
      return newPassenger;
   }
   
   private static void printPassengers(ArrayList<Passenger> a){
      System.out.println();
      for(Passenger p: a){
         System.out.println(p);
      }
      System.out.println();
   }
   
   private static char getCharacter(String prompt, Scanner kb){
      char choice;
      do{
         System.out.print("Enter ");
         for(int i = 0; i < prompt.length() - 1; i++){
            System.out.print(prompt.charAt(i) + ", ");
         }
         System.out.print(prompt.charAt(prompt.length() - 1) + ": ");
         String str = kb.nextLine();
         if(str.length() > 0){
            str = str.toUpperCase();
            choice = str.charAt(0);
         } else {
            choice = ' ';
         }
      } while(!checkChoice(prompt, choice));
      return choice;
   }
   
   private static boolean checkChoice(String prompt, char choice){
      for(int i = 0; i < prompt.length(); i++){
         if(choice == prompt.charAt(i)){
            return true;
         }
      }
      return false;
   }
   
   private static void showMainMenu(){
      System.out.println();
      System.out.println("Please choose from the following:");
      System.out.println("   A = add new passenger");
      System.out.println("   F = find a passenger");
      System.out.println("   D = delete a passenger");
      System.out.println("   C = change passenger affinity status");
      System.out.println();
      System.out.println("   P = Print all passengers");
      System.out.println();
      System.out.println("   S = save passenger list to file");
      System.out.println("   L = load passenger list from file");
      System.out.println("   B = Bulk load from .csv file");
      System.out.println();
      System.out.println("   Q = quit");
      System.out.println();
   }
}
