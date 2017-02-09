package cp400_a2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class KMeans {
    
    private final File dataFile;
    private final int numClusters;
    private final ArrayList<ArrayList<Double>> dataSet;
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
        
        //printDataSet();
    }
    
    public void cluster() {
        
        // create initial clusters with first k points in dataSet
        for (int i = 0; i < numClusters; i ++) {
            this.clusters.add(new Cluster(this.dataSet.get(i)));
        }
        
        
        double largestEuclideanDistance; // number of points that have changed clusters
        int iterations = 0;
        
        // Stop when all clusters only move a small amount (euclidean distance less than value of smallest data point)
        do {
            largestEuclideanDistance = 0;
            iterations ++;
            // Clear points in each cluster
            for (Cluster cluster : this.clusters) {
                cluster.clearPoints();
            }

            // Assign each point to the cluster that has the minimum euclidean distance to the point
            for (ArrayList<Double> point : this.dataSet) {
                Double minEuclideanDist = null;
                Cluster clusterWithMinEuclideanDist = null;
                
                // Calculate euclidean distance from point to every cluster center and take min
                for (Cluster cluster : this.clusters) {
                    double euclideanDist = 0;
                    // iterate through each index in the cluster center
                    for (int i = 0; i < cluster.getClusterCenter().size(); i ++) {
                        // Add (pi - ci)^2 to euclidean distance
                        euclideanDist += Math.pow(point.get(i) - cluster.getClusterCenter().get(i), 2);
                    }
                    
                    euclideanDist = Math.sqrt(euclideanDist);
                    
                    if (euclideanDist > largestEuclideanDistance) {
                        largestEuclideanDistance = euclideanDist;
                    }
                    
                    // if appropriate, set minEuclideanDist as the euclidean distance to current cluster
                    if (minEuclideanDist == null || euclideanDist < minEuclideanDist) {
                        minEuclideanDist = euclideanDist;
                        clusterWithMinEuclideanDist = cluster;
                    }
                    
                }
                
                // Add current point to cluster having minEuclideanDist
                clusterWithMinEuclideanDist.addPoint(point);
            }
            
            // Adjust cluster centers
            for (Cluster cluster : this.clusters) {
                cluster.computeNewClusterCenter();
            }
        } while (largestEuclideanDistance > DataSetGenerator.SMALLEST_DATA_VALUE || iterations > 100);
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
