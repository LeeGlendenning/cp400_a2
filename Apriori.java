package cp400_a2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Apriori {
    
    private final File dataFile;
    private final ArrayList<ArrayList<Double>> dataSet;
    private static final int MAX_ITERATIONS = 1000;
    private static final double MINS = 0.9;
    
    public Apriori(String dataFilePath) throws IOException {
        this.dataFile = new File(dataFilePath);
        this.dataSet = DataSetGenerator.genDataSet(dataFile, ", ");
        
    }
    
    public void associationMine() {
        
    }
    
}
