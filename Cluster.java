package cp400_a2;

import java.util.ArrayList;


public class Cluster {
    
    private ArrayList<Integer> clusterCenter;
    private ArrayList<ArrayList<Integer>> points;
    
    public Cluster(ArrayList<Integer> clusterCenter) {
        this.clusterCenter = clusterCenter;
        points = new ArrayList();
    }
    
    public void addPoint(ArrayList<Integer> point) {
        this.points.add(point);
    }
    
    public void updateClusterCenter(ArrayList<Integer> newCenter) {
        this.clusterCenter = newCenter;
    }
    
    public void clearPoints() {
        this.points = new ArrayList();
    }
}
