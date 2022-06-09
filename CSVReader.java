// CSVReader.java
// by W P Osborne
// 4/12/2021

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class CSVReader {
   // instance variables
   String[] fieldNames = null;
   String fileName;
   FileReader fr;
   BufferedReader br;
   ArrayList<String> lines = new ArrayList<String>();
   
   // Constructor
   public CSVReader(String name){
      fileName = name;  // name of CSV file
      try{
         fr = new FileReader(name);
         br = new BufferedReader(fr);
         String line = br.readLine();
         if(br != null){
            fieldNames = line.split(",");
         }
      } catch (IOException e){
         System.out.println(e.getMessage());
      }
      try{
         String nextLine = br.readLine();
         while(nextLine != null){
            lines.add(nextLine);
            nextLine = br.readLine();
         }
      } catch (IOException e){
         System.out.println(e.getMessage());
      }
   }
   
   // Accessors
   public String getDatabaseFileName(){
      return fileName;
   }
   
   public String[] getFieldNames(){
      return fieldNames;
   }
   
   public int getNumberOfRecords(){
      return lines.size();
   }
   
   // PRECONDITION: A line must first be broken into comma-separated
   // Strings using the split() Sting method. But, sometimes a field
   // within a record will have multiple values, each separated by
   // commas. The .csv standard says such String objects are identified
   // by a leading double quote mark and another double quote mark at the
   // end. An example is cast members of a Netflix title. This has multiple
   // values separated by commas. The entire collection is delimited
   // with quote marks. 
   // Example from the movie Indiana Jones and the Last Crusade:
   // "Harrison Ford, Sean Connery, Alison Doody"
   // But, such a list might have no contents at all, in which case
   // the field would have only a pair of quotation marks, ""
   // PROCESSING: Each String placed in recordRaw is to be processed as
   // follows:
   //   If the leading character is a double-quote call 
   //     getFieldFromQuoteString() and assign the returned String 
   //     to the record element. 
   //   Else, copy the contents of the recordRaw element into
   //     the record element. 
   // POSTCONDITION: Return the created record String array.

   public String[] getRecord(int recordNumber){
      // make sure recordNumber is valid
      if(recordNumber > 0 && recordNumber <= lines.size()){
         String line = lines.get(recordNumber - 1);
         String[] recordRaw = line.split(","); 
         String[] record = new String[fieldNames.length];
         int indexRecord = 0;
         
         // traverse the raw record
         for(int i = 0; i < recordRaw.length; i++){
            if(recordRaw[i] == null || recordRaw[i].length() < 1){
               record[indexRecord] = "<no value>";
            } else if(recordRaw[i].charAt(0) == '"'){
               i = getFieldFromQuoteString(i, recordRaw, indexRecord, record);
            } else {
               record[indexRecord] = recordRaw[i];
            }
            indexRecord++;
         }
         return record;
      }
      return null;
   }
   
   private int getFieldFromQuoteString(int indexRaw, String[] recordRaw,
           int indexRecord, String[] record){
      int newIndexRaw = indexRaw; 
      String str = "";
      
      // Situation 1, nothing between the quote marks
      if(recordRaw[newIndexRaw].length() <= 2){
         record[indexRecord] = "<no values>";
      } else if(recordRaw[newIndexRaw].endsWith("\"")){
         // Situation 2: Only one value, ends with "
         record[indexRecord] = 
              recordRaw[newIndexRaw].substring(1, 
                      recordRaw[newIndexRaw].length() - 2);
      } else {
         // Situation 3: Multiple values before end quote.
         str += recordRaw[newIndexRaw].substring(1);  
         newIndexRaw++;  // go to next String
         while(!recordRaw[newIndexRaw].endsWith("\"")){
            str += ", " + recordRaw[newIndexRaw];
            newIndexRaw++;
         }
         // get the last value
         str += 
            recordRaw[newIndexRaw].substring(0, 
                    recordRaw[newIndexRaw].length() - 2);
         record[indexRecord] = str;
      }
      return newIndexRaw;
   }
   
   public String getFieldValue(int recordNumber, String fieldName){
      // First, validate recordNumber
      if(recordNumber < 1 || recordNumber > lines.size()){
         return "Record number out of bounds.";
      }
      
      // next, validate fieldName
      int index;
      for(index = 0; index < fieldNames.length; index++){
         if(fieldName.compareToIgnoreCase(fieldNames[index]) == 0){
            // field name valid. Get the record and return the value
            String[] record = getRecord(recordNumber);
            return record[index];
         }
      }
      return "Field name not valid.";
   }
   
   public String toString(){
      String str = "Database name: " + fileName + "\n";
      str += "Number of records: " + getNumberOfRecords() + "\n";
      str += "Fields: " + Arrays.toString(fieldNames);
      return str;
   }
}
