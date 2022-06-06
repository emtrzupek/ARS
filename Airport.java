/*
 * Aiport.java
 * Alexandra Iurco
 * 5/11/2022
 * Period 2
 */
// package flight;

import java.io.Serializable;

public class Airport implements Serializable{
    private String symbol;
    private String name; 
    private String city;
    private String state;
    private int timeOffset;
    //list of US states ordered by timezone for setTimeOffset()
    private String[] stateList = {"California", "Washington", "Oregon", "Nevada", "Montana", "Idaho",
                                  "Wyoming", "Utah", "Colorado", "Arizona", "New Mexico",
                                  "North Dakota", "South Dakota", "Nebraska", "Kansas",
                                  "Oklahoma", "Texas", "Minnesota", "Iowa", "Missouri", "Arkansas",
                                  "Louisiana", "Wisconsin", "Illinois", "Mississippi", "Tennessee",
                                  "Michigan", "Indiana", "Kentucky", "Ohio", "West Virginia", "Virginia",
                                  "Pennsylvania", "New York", "New Jersey", "Maryland", "Delaware",
                                  "Connecticut", "Rhode Island", "Massachusetts", "Vermont", "New Hampshire",
                                  "Maine", "North Carolina", "South Carolina", "Gerogia", "Alabama", "Florida",
                                  "Alaska", "Hawaii"};
    // constructs an airport object with the given name, city, and state
    public Airport(String symbol, String name, String c, String s){
        this.name = name;
        this.symbol = symbol;
        city = c;
        state = s;
    }
    // returns the name of the airport 
    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }
    // returns city airport is in
    public String getCity(){
        return city;
    }
    // returns the state airport is in
    public String getState(){
        return state;
    }
    // returns the GMT offset of the airport (the timezone difference between the location 
    // of the airport and GMT)
    public int setTimeOffset(){
        for(int i = 0; i < stateList.length; i++){
            // if state listed is in stateList
            if(stateList[i].contains(state)){
                // depending on where the state is in stateList (since it is
                // ordered by timezone) it will return the offset
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
}
