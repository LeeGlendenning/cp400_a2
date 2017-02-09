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
    
    public static Double SMALLEST_DATA_VALUE = null;
    
    /*private final File dataFile;
    
    public FileReader(File dataFile) {
        this.dataFile = dataFile;
    }*/
    
    public static ArrayList<ArrayList<Double>> genDataSet(File dataFile, String delimiter) throws FileNotFoundException, IOException {
        ArrayList<ArrayList<Double>> dataSet = new ArrayList();
        
        BufferedReader br = new BufferedReader(new FileReader(dataFile));
        
        String line;
        while ((line = br.readLine()) != null) {
            dataSet.add(new ArrayList(Arrays.asList(stringArrayToDouble(line.split(delimiter)))));
            
            for (Double val : dataSet.get(dataSet.size() - 1)) {
                if (SMALLEST_DATA_VALUE == null || val < SMALLEST_DATA_VALUE) {
                    SMALLEST_DATA_VALUE = val;
                }
            }
            
        }
        
        return dataSet;
    }
    
    private static Double[] stringArrayToDouble(String[] arr) {
        return Arrays.stream( Stream.of(arr).mapToDouble(Double::parseDouble).toArray() ).boxed().toArray( Double[]::new );
    }
}
