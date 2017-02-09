package cp400_a2;

import java.io.IOException;


public class Main {
    
    static int numClusters = 5;
    static String dataSetPath = "C:\\Users\\The Boss\\Desktop\\School\\CP400R\\iris.data.txt";
    
    public static void main(String[] args) {
        
        switch(args.length) {
            case 0:
            case 1:
                System.out.println("Usage: CP400_A2 <path-to-data-set> <k-number-of-clusters>");
                return;
            case 2:
            default:
                dataSetPath = args[1];
                try {
                    numClusters = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    System.out.println("Argument 2 must be an integer");
                    return;
                }
        }
        
        KMeans km;
        try {
            km = new KMeans(dataSetPath, numClusters);
            km.cluster();
        } catch (IOException ex) {
            System.err.println("File not found");
        }
        
    }
    
}
