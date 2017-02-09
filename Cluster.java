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
    
    public ArrayList<Double> getClusterCenter() {
        return this.clusterCenter;
    }
    
    public void clearPoints() {
        this.points = new ArrayList();
    }
    
    public ArrayList<ArrayList<Double>> getPoints() {
        return this.points;
    }
    
    /**
     * average of points in cluster
     * 
     * @return boolean whether center changed
     */
    public boolean computeNewClusterCenter() {
        if (this.points.isEmpty()){
            return false;
        }
        
        boolean centerChanged = false;
        
        // Wrap points as new arraylist to avoid changing by address
        ArrayList<Double> newCenter = new ArrayList(this.points.get(0));
        
        // Sum all indexes of points and store in newCenter arrayList
        for (int i = 1; i < this.points.size(); i ++) {
            ArrayList<Double> point = this.points.get(i);
            
            for (int j = 0; j < point.size(); j ++) {
                // Add index values to newCenter
                newCenter.set(j, newCenter.get(j) + point.get(j));
            }
        }
        
        
        // Divide each index by total number of points, effectively finding average
        //System.out.println("New cluster center: ");
        for (int i = 0; i < newCenter.size(); i ++) {
            //System.out.print(i + ": " + newCenter.get(i) + " / " + this.points.size());
            newCenter.set(i, newCenter.get(i) / this.points.size());
            if (!doublesEqual(newCenter.get(i), this.clusterCenter.get(i))) {
                centerChanged = true;
            }
            //System.out.println(" = " + newCenter.get(i));
        }
        
        this.clusterCenter = newCenter;
        
        return centerChanged;
    }
    
    private boolean doublesEqual(double value1, double value2){
        double threshold = 0.0000000001;
        return value1 >= value2-threshold && value1 <= value2+threshold;
    }
    
    public String printCenter() {
        String center = "";
        for (Double val : this.clusterCenter) {
            center += val + ", ";
        }
        return center;
    }
}
