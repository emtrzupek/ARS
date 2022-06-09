/*
 * Passenger.java
 * by w p osborne
 * 4/4/2022
 */
import java.io.Serializable;
public class Passenger implements Serializable{
   
   // instance variables
   private int passengerID;
   private String lastName;
   private String firstName;
   private boolean affinityMember;  // true means wants to join
   private int affinityNumber; // between 1000 and 9000
   private int mileageBalance;
   
   // constructor - About memberRequest, passengers are not required
   // to be members of the airline affinity program (mileage club). 
   // A "true" value means the passenger wishes to join.
   //
   // A passenger's membership status may be changed by calling 
   // setAffinityMemberStatus().
   public Passenger(String lastName, String firstName, boolean memberRequest){
      this.lastName = lastName;
      this.firstName = firstName;
      affinityMember = memberRequest;
      if(affinityMember){
         // wants to be an affinity member
         affinityNumber = generateRandomNumber(4);
         mileageBalance = 0;
      } else {
         mileageBalance = -1;
         affinityNumber = -1;
      }
      passengerID = generateRandomNumber(3);
   }
   
   // accessors
   
   public String getLastName(){ return lastName; }
   public String getFirstName(){ return firstName; }
   public boolean getAffinityMember(){ return affinityMember; }
   public int getAffinityNumber(){ return affinityNumber; }
   public int getMileage(){ return mileageBalance; }
   
   // mutators
   
   // returns new affinity number after action taken
   public int setAffinityMemberStatus(boolean newStatus){
      if(affinityMember && !newStatus){
         // dropping membership
         mileageBalance = -1;
         affinityNumber = -1;
         affinityMember = false;
      } else if(!affinityMember && newStatus){
         // wants to join
         mileageBalance = 0;
         affinityNumber = generateRandomNumber(4);
         affinityMember = true;
      }
      // if neither of these cases then do nothing.
      return affinityNumber;
   }
   
   public int getID(){
      return passengerID;
   }
   
   // Add mileage balance and return new balance
   public int updateMileage(int deltaMiles){
      mileageBalance += deltaMiles;
      return mileageBalance;
   }
   
   private int generateRandomNumber(int numDigits){
      int min = (int)Math.pow(10, numDigits - 1);
      int max = min * 10 - 1;
      
      return (int)Math.floor(Math.random() * (max - min + 1)) + min;
   }
   public void setID(int id) {
      passengerID = id;
   }
   public void setAffinityNumber(int affinityNumber) {
      this.affinityNumber = affinityNumber;
   }
   public void setMileageBalance(int mileageBalance) {
      this.mileageBalance = mileageBalance;
   }
   // toString
   public String toString(){
      String str = "ID: " + passengerID + ", " + lastName;
      str += ", " + firstName + ", ";
      if(affinityMember){
         str += "Mileage number: " + affinityNumber + ", ";
         str += "Total miles: " + mileageBalance;
      } else {
         str += "Not a mileage member.";
      }
      return str;
   }
}
