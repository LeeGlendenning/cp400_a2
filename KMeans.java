package cp400_a2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class KMeans {
    
    private final File dataFile;
    private final int numClusters;
    private final ArrayList<ArrayList<Double>> dataSet;
    private ArrayList<Cluster> clusters;
    private static final int MAX_ITERATIONS = 1000;
    
    public KMeans(String dataFilePath, int numClusters) throws IOException {
        this.dataFile = new File(dataFilePath);
        this.numClusters = numClusters;
        this.dataSet = DataSetGenerator.genDataSetDouble(dataFile, ",");
        
        if (this.dataSet.size() < numClusters) {
            System.out.println("Not enough data points to create the specified number of clusters");
            System.exit(1);
        }
        
        this.clusters = new ArrayList();
        
        
    }
    
    public void cluster() {
        //System.out.println("Starting clustering...");
        // create initial clusters with first k points in dataSet
        for (int i = 0; i < numClusters; i ++) {
            this.clusters.add(new Cluster(new ArrayList(this.dataSet.get(i))));
            //System.out.println("Creating new cluster center at " + this.clusters.get(clusters.size() - 1).printCenter());
        }
        
        
        double largestEuclideanDistance; // number of points that have changed clusters
        int iterations = 0;
        
        // Stop when all clusters only move a small amount (euclidean distance less than value of smallest data point)
        boolean clusterCenterChange;
        do {
            //System.out.println("\n");
            //printDataSet();
            
            // Used as stopping condition. If no change in clusters, stop.
            clusterCenterChange = false;
            largestEuclideanDistance = 0;
            iterations ++;
            //System.out.println("\nIteration " + iterations + ":");
            // Clear points in each cluster
            for (Cluster cluster : this.clusters) {
                cluster.clearPoints();
            }
           // System.out.println("All points in clusters have been cleared.");

            // Assign each point to the cluster that has the minimum euclidean distance to the point
            int pointIndex = 0; // For debugging
            for (ArrayList<Double> point : this.dataSet) {
                Double minEuclideanDist = null;
                Cluster clusterWithMinEuclideanDist = null;
                
                // Calculate euclidean distance from point to every cluster center and take min
                for (Cluster cluster : this.clusters) {
                    double euclideanDist = 0;
                    //System.out.print("Point " + pointIndex + " has euclidean distance = sqrt[");
                    // iterate through each index in the cluster center
                    for (int i = 0; i < cluster.getClusterCenter().size(); i ++) {
                        // Add (pi - ci)^2 to euclidean distance
                        //System.out.print("(" + point.get(i) + " - " + cluster.getClusterCenter().get(i) + ")^2 + ");
                        euclideanDist += Math.pow(point.get(i) - cluster.getClusterCenter().get(i), 2);
                    }
                    
                    euclideanDist = Math.sqrt(euclideanDist);
                    //System.out.println("] = " + euclideanDist);
                    
                    if (euclideanDist > largestEuclideanDistance) {
                        largestEuclideanDistance = euclideanDist;
                    }
                    
                    // if appropriate, set minEuclideanDist as the euclidean distance to current cluster
                    if (minEuclideanDist == null || euclideanDist < minEuclideanDist) {
                        //System.out.println(euclideanDist + " < " + minEuclideanDist + " => new minEuclideanDistance to cluster: " + cluster.printCenter());
                        minEuclideanDist = euclideanDist;
                        clusterWithMinEuclideanDist = cluster;
                    }
                    
                }
                
                // Add current point to cluster having minEuclideanDist
                //System.out.println("Adding point " + pointIndex + " to cluster: " + clusterWithMinEuclideanDist.printCenter());
                clusterWithMinEuclideanDist.addPoint(point);
                
                pointIndex ++; // For debugging
            }
            
            // Adjust cluster centers
            //System.out.print("Cluster density: ");
            for (Cluster cluster : this.clusters) {
                //System.out.print(cluster.getPoints().size() + ", ");
                if (cluster.computeNewClusterCenter()) {
                    clusterCenterChange = true;
                }
            }
            
            if (!clusterCenterChange) {
                //System.out.println("No change in cluster centers. Stopping.");
            }
            
        } while (clusterCenterChange && iterations < MAX_ITERATIONS);
        
        outputQualityMeasure();
    }
    
    /**
     * Calculate SSE for each cluster and output cluster centers
     * 
     */
    private void outputQualityMeasure() {
        
        for (Cluster cluster : this.clusters) {
            double sseTotal = 0; // Sum of Squared Error quality measure
            
            for (ArrayList<Double> point : cluster.getPoints()) {
                double ssePoint = 0;
                for (int i = 0; i < cluster.getClusterCenter().size(); i ++) {
                    // Distance from point index to cluster index squared
                    ssePoint += Math.pow(point.get(i) - cluster.getClusterCenter().get(i), 2);
                }
                sseTotal += ssePoint;
            }
            System.out.println("Cluster center: " + cluster.printCenter());
            System.out.println("Cluster SSE: " + sseTotal + "\n");
        }
    }
    
}
