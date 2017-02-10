package cp400_a2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;


public class DataSetGenerator {
    
    //public static Double SMALLEST_DATA_VALUE = null;
    
    public static ArrayList<ArrayList<String>> genDataSetString(File dataFile, String delimiter) throws FileNotFoundException, IOException {
        ArrayList<ArrayList<String>> dataSet = new ArrayList();
        
        BufferedReader br = new BufferedReader(new FileReader(dataFile));
        
        String line;
        while ((line = br.readLine()) != null) {
            dataSet.add(new ArrayList(Arrays.asList(line.split(delimiter))));
            
            
        }
        
        return dataSet;
    }
    
    public static ArrayList<ArrayList<Double>> genDataSetDouble(File dataFile, String delimiter) throws FileNotFoundException, IOException {
        ArrayList<ArrayList<Double>> dataSet = new ArrayList();
        
        BufferedReader br = new BufferedReader(new FileReader(dataFile));
        
        String line;
        while ((line = br.readLine()) != null) {
            dataSet.add(new ArrayList(Arrays.asList(stringArrayToDouble(line.split(delimiter)))));
            
            /*for (Double val : dataSet.get(dataSet.size() - 1)) {
                if (SMALLEST_DATA_VALUE == null || val < SMALLEST_DATA_VALUE) {
                    SMALLEST_DATA_VALUE = val;
                }
            }*/
            
        }
        
        return dataSet;
    }
    
    private static Double[] stringArrayToDouble(String[] arr) {
        return Arrays.stream( Stream.of(arr).mapToDouble(Double::parseDouble).toArray() ).boxed().toArray( Double[]::new );
    }
    
    public static void printDataSetDouble(ArrayList<ArrayList<Double>> dataSet) {
        for (ArrayList<Double> row : dataSet) {
            for (Double i : row) {
                System.out.print(i + ",");
            }
            System.out.println();
        }
        System.out.println("\n");
    }
    
    public static void printDataSetString(ArrayList<ArrayList<String>> dataSet) {
        for (ArrayList<String> row : dataSet) {
            for (String i : row) {
                System.out.print(i + ",");
            }
            System.out.println();
        }
        System.out.println("\n");
    }
}
