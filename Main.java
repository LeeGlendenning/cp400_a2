package cp400_a2;

import java.io.IOException;


public class Main {
    
    static String IRIS_DATA_PATH = "C:\\Users\\The Boss\\Desktop\\School\\CP400R\\iris.data.txt";
    //static String IRIS_DATA_PATH = "C:\\Users\\The Boss\\Desktop\\School\\CP400R\\test1.txt";
    static int NUM_CLUSTERS = 5;
    
    public static void main(String[] args) {
        
        KMeans km;
        try {
            km = new KMeans(IRIS_DATA_PATH, NUM_CLUSTERS);
            km.cluster();
        } catch (IOException ex) {
            System.err.println("File not found");
        }
        
    }
    
}
