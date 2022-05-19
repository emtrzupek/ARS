/*
 * Aiport.java
 * Alexandra Iurco
 * 5/11/2022
 * Period 2
 */

import java.io.Serializable;

public class Airport implements Serializable{
    private String airportName;
    private String city;
    private String state;
    private int timeOffset;
    //ordered by timezone
    private static String[] stateList = {"California", "Washington", "Oregon", "Nevada", "Montana", "Idaho",
                                  "Wyoming", "Utah", "Colorado", "Arizona", "New Mexico",
                                  "North Dakota", "South Dakota", "Nebraska", "Kansas",
                                  "Oklahoma", "Texas", "Minnesota", "Iowa", "Missouri", "Arkansas",
                                  "Louisiana", "Wisconsin", "Illinois", "Mississippi", "Tennessee",
                                  "Michigan", "Indiana", "Kentucky", "Ohio", "West Virginia", "Virginia",
                                  "Pennsylvania", "New York", "New Jersey", "Maryland", "Delaware",
                                  "Connecticut", "Rhode Island", "Massachusetts", "Vermont", "New Hampshire",
                                  "Maine", "North Carolina", "South Carolina", "Gerogia", "Alabama", "Florida",
                                  "Alaska", "Hawaii"};
    
    public Airport(String name, String c, String s){
        airportName = name;
        city = c;
        state = s;
    }
    
    public String getName(){
        return airportName;
    }
    
    public String getCity(){
        return city;
    }
    
    public String getState(){
        return state;
    }
    
    public int setTimeOffset(){
        for(int i = 0; i < stateList.length; i++){
            if(stateList[i].contains(state)){
                if(i <= 3){
                    return 8;
                } else if(i <= 10){
                    return 7;
                } else if(i <= 25){
                    return 6;
                } else if(i<= 47){
                    return 5;
                } else if(i == 48){
                    return 9;
                } else {
                    return 11;
                }
            }
        }
        return timeOffset;
    }

    public String toString() {
        return airportName;
    }
}
