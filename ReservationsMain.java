/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ARS;
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
	public static void main(String args[]){
            readFromFile();
            flightList=new ArrayList<Flight>();
            airportList=new ArrayList<Airport>();
            passengerList=new ArrayList<Passenger>();
            mainMenu();
	}
   private static void mainMenu(){
               Scanner KB=new Scanner(System.in);
            continuePoint:
            while(true){
                    System.out.println("what would you like to do\n"
                            + "(P) print things, (R) manage reservations, (A) manage Airports: (e) cancel");
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
      private static void manageAirports(Scanner KB){
            continuePoint:
            while(true){
                    System.out.print("what would you like to do\n"
                            + "The (A) print in Alphabetical order, (B) search for a reservation number, (C) search for a reservation name, (D) create a new reservation,   (E) cancel\n"
                            + "you choose: ");
                    String fullChoice=KB.nextLine();
                    if(fullChoice.length()<1)
                        continue;
                    char choice=fullChoice.toLowerCase().charAt(0);
                    switch (choice){
                        case 'a':            
                           ArrayList<Reservation> list=(ArrayList)reservationList.clone();
                           list.sort((a1,a2)->new Integer(a1.getReservationNumber()).compareTo(new Integer(a2.getReservationNumber())));
                           System.out.println(list);
                            break;
                        case 'b':
                            int choiceN=KB.nextInt();
                            for(Reservation R:reservationList){
                              if(R.getReservationNumber()==choiceN){
                                 System.out.println(R);
                              }
                            }
                            break ;
                        case 'c':
                            break ;
                               
                        case 'd':
                            break ;
                        case 'e':
                            break continuePoint;
                        default:
                            break ;
                    }
                }

   }
   private static void manageReservations(Scanner KB){
            continuePoint:
            while(true){
                    System.out.print("what would you like to do\n"
                            + "The (A) print in Alphabetical order, (B) search for a reservation number, (C) search for a reservation name, (D) create a new reservation,   (E) cancel\n"
                            + "you choose: ");
                    String fullChoice=KB.nextLine();
                    if(fullChoice.length()<1)
                        continue;
                    char choice=fullChoice.toLowerCase().charAt(0);
                    switch (choice){
                        case 'a':            
                           ArrayList<Reservation> list=(ArrayList)reservationList.clone();
                           list.sort((a1,a2)->new Integer(a1.getReservationNumber()).compareTo(new Integer(a2.getReservationNumber())));
                           System.out.println(list);
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
                            break;
                               
                        case 'd':
                            break;
                        case 'e':
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
         readStream.close();
      } catch (Exception e){
         e.printStackTrace();
         airportList=new ArrayList<Airport>();
         flightList=new ArrayList<Flight>();
         reservationList=new ArrayList<Reservation>();
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
                            System.out.println("This is the data for flight"+KB.nextInt());
                            break;
                        case 'e':
                            break continuePoint;
                        default:
                            break;
                    }
                }
	}
}