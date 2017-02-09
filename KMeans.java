package cp400_a2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class KMeans {
    
    private final File dataFile;
    private final int numClusters;
    private ArrayList<ArrayList<String>> dataSet;
    
    public KMeans(String dataFilePath, int numClusters) throws IOException {
        this.dataFile = new File(dataFilePath);
        this.numClusters = numClusters;
        this.dataSet = DataSetGenerator.genDataSet(dataFile);
        printDataSet();
    }
    
    public void cluster() {
        
    }
    
    private void printDataSet() {
        for (ArrayList<String> row : this.dataSet) {
            for (String i : row) {
                System.out.print(i + ",");
            }
            System.out.println();
        }
    }
    
}
