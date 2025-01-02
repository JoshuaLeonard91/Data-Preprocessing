/*
 * Author: Joshua Leonard
 * Course: COP3503
 * Project #: 2
 * Title: Project 2 Data Preprocessing
 * Due Date: 07/08/2022
 *
 * Program reads a csv file and loads each column into its own array
 * adds 3 separate columns for section 1 difference, section 2 difference
 * and total average. Writes to a new file with the new sets of data, calculating
 * each rows data.
 */

import java.util.ArrayList;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.io.File;

public class project2 {

    //array list for the different sets of data from csv file
    public static ArrayList<String> header = new ArrayList<String>();
    public static ArrayList<String> dates = new ArrayList<String>();//index 0
    public static ArrayList<String> times = new ArrayList<String>();//index 1
    public static ArrayList<Double> sensor2278 = new ArrayList<Double>();//index 2
    public static ArrayList<Double> sensor3276 = new ArrayList<Double>();//index 3
    public static ArrayList<Double> sensor4689 = new ArrayList<Double>();//index 4
    public static ArrayList<Double> sensor5032 = new ArrayList<Double>();//index 5
    public static ArrayList<Double> section1Diff = new ArrayList<Double>();
    public static ArrayList<Double> section2Diff = new ArrayList<Double>();
    public static ArrayList<Double> totalAvg = new ArrayList<Double>();

    /**
     * main runs the bulk of the program and
     * has the try catch setup for fileNotFoundException
     * ParseException, NumberFormatException and IOException.
     * @param args
     */
    public static void main(String[] args) {

        System.out.println("Project 2 Data Preprocessing\n");

        Scanner in = new Scanner(System.in);
        String inFile;
        boolean flag = false;
        //while loop, if flag is true program will stop, if flag does not trigger
        //it means an exception was found and will loop back, clears array list at parse and
        //number-format exceptions.
        while (!flag) {
            try {
                System.out.print("Enter your files path location: ");
                inFile = in.nextLine();
                fileParse(inFile);
                conversions();
                fileWriter(inFile);
                flag = true;
            } catch (FileNotFoundException e) {
                System.out.println("*File does not exist or path was entered incorrectly.*" + " " + e.getMessage());
                System.out.println("Please try again.");
                in = new Scanner(System.in);
            } catch (ParseException e) {
                System.out.println("*Bad Data in CSV File.*" + " " + e.getMessage());
                System.out.println("Check CSV file data and try again.");
                in = new Scanner(System.in);
                arrayReset();
            } catch (NumberFormatException e) {
                System.out.println("*Bad Number Data in CSV File.*" + " " + e.getMessage());
                System.out.println("Check CSV file data and try again.");
                in = new Scanner(System.in);
                arrayReset();
            } catch (IOException e) {
                System.out.println("*Bad file name.*" + e.getMessage());
            }
        }
    }

    /**
     * arrayReset returns nothing and is called
     * from main method to reconstruct the array lists
     * clearing out any data left over from a throws'
     * exception.
     */
    public static void arrayReset(){
        header = new ArrayList<String>();
        dates = new ArrayList<String>();
        times = new ArrayList<String>();
        sensor2278 = new ArrayList<Double>();
        sensor3276 = new ArrayList<Double>();
        sensor4689 = new ArrayList<Double>();
        sensor5032 = new ArrayList<Double>();
        section1Diff = new ArrayList<Double>();
        section2Diff = new ArrayList<Double>();
        totalAvg = new ArrayList<Double>();
    }

    /**
     * fileParse takes in a string fileName
     * and scans the file using a delimiter for
     * commas and whitespace(space) and scans directly into
     * each array list. Throws FileNotFoundException
     * if there is no file found at the location or a NumberFormatException
     * if there is a invalid number in the file and returns that to main.
     * The method returns nothing.
     * @param inFile
     * @throws FileNotFoundException,NumberFormatException
     */
    public static void fileParse(String inFile) throws FileNotFoundException,NumberFormatException {

        Scanner scan = new Scanner(new File(inFile));
        scan.useDelimiter(",|\n");

        System.out.println("Reading in Data from the file located at " + inFile + "...");

        //takes the first row in the file to temp string->string[]->adds all to array list
        String[] headerTemp = scan.nextLine().split(",");
        for(String s : headerTemp){
            header.add(s);
        }

        //while loop loads each array with the next string that is seperated by
        //the comma or space(whitespace) with the scan.useDelimiter
        while(scan.hasNext()){
            dates.add(scan.next());
            times.add(scan.next());
            sensor2278.add(Double.parseDouble(scan.next()));
            sensor3276.add(Double.parseDouble(scan.next()));
            sensor4689.add(Double.parseDouble(scan.next()));
            sensor5032.add(Double.parseDouble(scan.next()));
        }
        scan.close();
    }
    /**
     * conversions method takes in nothing and accesses array list globally
     * converts date to YYYY/MM/DD also calculates the differences in speed
     * for the various sensors and adds the columns to the header array list.
     * @throws ParseException
     */
    public static void conversions() throws ParseException {

        SimpleDateFormat currentFormat = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat newFormat = new SimpleDateFormat("yyyy/MM/dd");

        System.out.println("Converting Dates from MM/DD/YYYY to YYYY/MM/DD...");
        //loops array list changing dates from MM/DD/YYY TO YYYY/MM/DD
        for(int i = 0;i< dates.size();++i) {
            Date dataObject = currentFormat.parse(dates.get(i));
            dates.set(i, newFormat.format(dataObject));
        }
        System.out.println("Calculating Speed Difference...");
        //loops sensor data and adds the difference from 2278 - 3276 to section1Diff
        for(int i = 0; i < sensor3276.size();++i){
            section1Diff.add(sensor2278.get(i) - sensor3276.get(i));
        }
        header.add("section1_Diff");
        //loops sensor data and adds the difference from 4689 - 5032 to section2Diff
        for(int i = 0; i < sensor5032.size();++i){
            section2Diff.add(sensor4689.get(i) - sensor5032.get(i));
        }
        header.add("section2_Diff");
        System.out.println("Calculating Speed Average...");
        //adds each row of four sensor data and divides by 4 and adds that to the total average for each row
        for(int i = 0; i < dates.size();++i){
            totalAvg.add((sensor2278.get(i)+ sensor3276.get(i)+ sensor4689.get(i)+ sensor5032.get(i))/4);
        }
        header.add("Total Average");
    }

    /**
     * fileWriter method takes in String of the file path name and parses the
     * string until it ends up with only the file name with no extension. Prints out the
     * new file and opens up a new file with that name to write to. Prints one line at a time
     * and adds a comma between each set of data. Header is written first and the actual data.
     * Actual data is only seperated by commas and the last data column does not have a comma but prints
     * newLine.
     * @param path userInput of the filePath name from main method
     * @throws IOException
     */
    public static void fileWriter(String path) throws IOException {

        //gets the file name and removes the extension name
        File newPath = new File(path);
        String newFile = newPath.getName();
        int lastIndex = newFile.lastIndexOf(".");
        String fileName = newFile.substring(0,lastIndex);

        System.out.println("Writing data to file "+fileName+"_Difference.csv...");

        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName+"_Difference.csv", false));

        //loop for header file separate from main loop for the body of the data set
        for(int i = 0; i< header.size();++i) {
            if(i != header.size()-1) {
                writer.write(header.get(i) + ",");
            }else{
                writer.write(header.get(i)+"\n");
            }
        }

        //loop for main body of data, each data set is seperated by a comma and printed as a line
        for(int i = 0; i < dates.size();++i)
        {
            writer.write(
                    dates.get(i)+","+
                            times.get(i)+","+
                            sensor2278.get(i)+","+
                            sensor3276.get(i)+","+
                            sensor4689.get(i)+","+
                            sensor5032.get(i)+","+
                            section1Diff.get(i)+","+
                            section2Diff.get(i)+","+
                            totalAvg.get(i)+"\n");
        }
        writer.close();
    }
}