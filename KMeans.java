package cp400_a2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class KMeans {
    
    private final File dataFile;
    private final int numClusters;
    private ArrayList<ArrayList<Double>> dataSet;
    private ArrayList<Cluster> clusters;
    
    public KMeans(String dataFilePath, int numClusters) throws IOException {
        this.dataFile = new File(dataFilePath);
        this.numClusters = numClusters;
        this.dataSet = DataSetGenerator.genDataSet(dataFile);
        
        if (this.dataSet.size() < numClusters) {
            System.out.println("Not enough data points to create the specified number of clusters");
            System.exit(1);
        }
        
        this.clusters = new ArrayList();
        
        printDataSet();
    }
    
    public void cluster() {
        // create initial clusters
        for (int i = 0; i < numClusters; i ++) {
            this.clusters.add(new Cluster(this.dataSet.get(i)));
        }
        
    }
    
    private void printDataSet() {
        for (ArrayList<Double> row : this.dataSet) {
            for (Double i : row) {
                System.out.print(i + ",");
            }
            System.out.println();
        }
    }
    
}
