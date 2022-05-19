/*
 * TestAirport.java
 * Alexandra Iurco
 * 5/11/2022
 * Period 2
 */

import java.io.Serializable;
public class TestAirport implements Serializable{
    
       public static void main(String[] args){
           
           Airport seattle = new Airport("SeaTac", "Seattle", "Washington");
           System.out.println(seattle.getName() + ": in " + seattle.getCity() + ", " + seattle.getState());
           System.out.println("Timezone: GMT - " + seattle.setTimeOffset());
           
           Airport florida = new Airport("Orlando International", "Orlando", "Florida");
           System.out.println(florida.getName() + ": in " + florida.getCity() + ", " + florida.getState());
           System.out.println("Timezone: GMT - " + florida.setTimeOffset());
           
           Airport texas = new Airport("Dallas International", "Dallas", "Texas");
           System.out.println(texas.getName() + ": in " + texas.getCity() + ", " + texas.getState());
           System.out.println("Timezone: GMT - " + texas.setTimeOffset());
        
       }
}