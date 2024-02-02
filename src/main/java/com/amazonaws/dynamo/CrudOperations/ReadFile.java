package com.amazonaws.dynamo.CrudOperations;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class ReadFile {

    private String path ;

    public ReadFile(String path){
        this.path=path;
    }

    public void readTextFile(){
        try {
            int sum= 0;
            int counter=0;
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                sum= sum+Integer.parseInt(data.trim());
                counter++;
            }
            System.out.println("Total time consumed in updating {"+ counter+"} Total records  -> "+(sum/1000) +" sec");
            System.out.println("Average time consumed in updating {"+ counter+"} each records  -> "+(sum/counter) +" milli sec");
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


}
