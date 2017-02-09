package cp400_a2;

import java.util.ArrayList;


public class Cluster {
    
    private ArrayList<Double> clusterCenter;
    private ArrayList<ArrayList<Double>> points;
    
    public Cluster(ArrayList<Double> clusterCenter) {
        this.clusterCenter = clusterCenter;
        points = new ArrayList();
    }
    
    public void addPoint(ArrayList<Double> point) {
        this.points.add(point);
    }
    
    private void updateClusterCenter(ArrayList<Double> newCenter) {
        this.clusterCenter = newCenter;
    }
    
    public void clearPoints() {
        this.points = new ArrayList();
    }
    
    /**
     * average of points in cluster
     */
    public void computeNewClusterCenter() {
        if (points.isEmpty()){
            return;
        }
        
        ArrayList<Double> newCenter = this.points.get(0);
        
        // Sum all indexes of points and store in newCenter arrayList
        for (int i = 0; i < points.size(); i ++) {
            ArrayList<Double> point = points.get(i);
            
            for (int j = 0; j < point.size(); j ++) {
                // Add index values to newCenter
                newCenter.set(j, newCenter.get(j) + point.get(j));
            }
        }
        
        
        // Divide each index by total number of points, effectively finding average
        System.out.println("New cluster center: ");
        for (int i = 0; i < newCenter.size(); i ++) {
            System.out.print(i + ": " + newCenter.get(i) + " / " + this.points.size());
            newCenter.set(i, newCenter.get(i) / this.points.size());
            System.out.println(" = " + newCenter.get(i));
        }
        
        this.clusterCenter = newCenter;
    }
}
