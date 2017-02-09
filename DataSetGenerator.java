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
    
    /*private final File dataFile;
    
    public FileReader(File dataFile) {
        this.dataFile = dataFile;
    }*/
    
    public static ArrayList<ArrayList<String>> genDataSet(File dataFile) throws FileNotFoundException, IOException {
        ArrayList<ArrayList<String>> dataSet = new ArrayList();
        
        BufferedReader br = new BufferedReader(new FileReader(dataFile));
        
        String line;
        while ((line = br.readLine()) != null) {
            dataSet.add(new ArrayList(Arrays.asList(line.split(","))));
        }
        
        return dataSet;
    }
}
