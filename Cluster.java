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
    
    public void updateClusterCenter(ArrayList<Double> newCenter) {
        this.clusterCenter = newCenter;
    }
    
    public void clearPoints() {
        this.points = new ArrayList();
    }
}
