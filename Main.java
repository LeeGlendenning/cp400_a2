package cp400_a2;

import java.io.IOException;


public class Main {
    
    static int numClusters = 5;
    static String kmeansDataSetPath = "C:\\Users\\The Boss\\Desktop\\School\\CP400R\\A2\\iris.data.txt";
    //static String kmeansDataSetPath = "/Users/theboss/Desktop/School/CP400/A2/km.4000000.txt";
    //static String kmeansDataSetPath = "/Users/theboss/Desktop/School/CP400/A2/iris.data.txt";
    
    static String aprioriDataSetPath = "C:\\Users\\The Boss\\Desktop\\School\\CP400R\\aprioti_test_data\\cti.tra";
    //static String aprioriDataSetPath = "/Users/theboss/Desktop/School/CP400/A2/flare.data1";
    
    public static void main(String[] args) {
        
        doKMeans(args);
        
        //doApriori();
        
    }
    
    private static void doKMeans(String[] args) {
        switch(args.length) {
            case 0:
            case 1:
                System.out.println("Usage: CP400_A2 <path-to-data-set> <k-number-of-clusters>");
                return;
            case 2:
            default:
                kmeansDataSetPath = args[1];
                try {
                    numClusters = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    System.out.println("Argument 2 must be an integer");
                    return;
                }
        }
        
        KMeans km;
        try {
            km = new KMeans(kmeansDataSetPath, numClusters);
            km.cluster();
        } catch (IOException ex) {
            System.err.println("File not found");
        }
    }
    
    
    private static void doApriori() {
        Apriori a;
        try {
            a = new Apriori(aprioriDataSetPath);
            a.associationMine();
        } catch (IOException ex) {
            System.err.println("File not found");
        }
    }
}
