# Speed Data Analysis Project

## Overview
This project demonstrates the ability to:
- Use input and output streams for file handling.
- Process data from a delimited text file.
- Convert date formats.
- Perform calculations on sensor data.
- Handle exceptions gracefully to ensure program stability.

The program processes a CSV file containing speed data from multiple sensors, performs various calculations, and outputs the results to a new CSV file.

## Objectives
- Read and process data from a file (`Speed_Data.csv`).
- Convert dates from `MM/DD/YYYY` format to `YYYY/MM/DD`.
- Calculate differences and averages for sensor speed data.
- Output processed data into a new CSV file.
- Demonstrate robust exception handling for common file and data errors.

## Features
1. **File Input Handling**:
   - Prompts the user for the input file name and path.
   - Handles `FileNotFoundException` to recover from incorrect file paths.

2. **Data Storage**:
   - Stores each column of the input file in an `ArrayList`.
   - Handles `NumberFormatException` for invalid numeric data.
   - Handles `ParseException` for invalid date formats.

3. **Data Processing**:
   - Converts date format from `MM/DD/YYYY` to `YYYY/MM/DD`.
   - Computes the following:
     - Difference between `Sensor 3276` and `Sensor 2278` data.
     - Difference between `Sensor 5032` and `Sensor 4689` data.
     - Average speed for each row across all sensors.

4. **File Output**:
   - Outputs all processed data into a new CSV file named `<InputFileName>_Difference.csv`.
   - Includes the following columns:
     - `Date`, `Time`, `Sensor 2278`, `Sensor 3276`, `Sensor 4689`, `Sensor 5032`, `Section1_Diff`, `Section2_Diff`, `Total_Avg`.

5. **Exception Handling**:
   - Uses `try-catch` blocks to recover from exceptions like `FileNotFoundException`, `NumberFormatException`, and `ParseException`.
   - Ensures program stability and data integrity.

## Requirements
- **Java Development Kit (JDK) 8 or higher**
- Input file: `Speed_Data.csv`
  - Must contain 6 columns: `Date`, `Time`, `Sensor 2278`, `Sensor 3276`, `Sensor 4689`, `Sensor 5032`.

## Implementation Details
1. **File Input**:
   - Uses `FileReader` wrapped with `Scanner` to read data from the file.
   - Splits each line on commas to populate the respective `ArrayList`.

2. **Date Conversion**:
   - Uses `SimpleDateFormat` from the Java utility package to convert dates.

3. **Data Calculations**:
   - Calculates differences and averages for sensor data.
   - Stores results in separate `ArrayList`.

4. **File Output**:
   - Uses `FileWriter` wrapped with `PrintWriter` to write processed data to a new CSV file.

5. **Error Handling**:
   - Clears all `ArrayList` data upon encountering exceptions.
   - Allows the user to re-enter file paths or correct input data.
 
