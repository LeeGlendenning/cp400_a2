package cp400_a2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Apriori {
    
    private final File dataFile;
    private final ArrayList<ArrayList<String>> dataSet;
    private static final int MAX_ITERATIONS = 1000;
    private static final double MIN_S = 0.5;
    
    public Apriori(String dataFilePath) throws IOException {
        this.dataFile = new File(dataFilePath);
        this.dataSet = DataSetGenerator.genDataSetString(this.dataFile, ", ");
        
        DataSetGenerator.printDataSetString(this.dataSet);
    }
    
    public void associationMine() {
        
        ArrayList<ArrayList<String>> candidateSet = findInitialCandidateSet();
        
        // Compute support for each item = count(i)/m
        ArrayList<Double> support = calculateSupport(candidateSet);
        
        // Remove all sets from candidateSet having support less than the min support MIN_S
        trimCandidateSetByMinS(candidateSet, support);
        
        
    }
    
    /**
     * Remove all sets from candidateSet having support less than the min support MIN_S
     * 
     * @param candidateSet
     * @param support 
     */
    private void trimCandidateSetByMinS(ArrayList<ArrayList<String>> candidateSet, ArrayList<Double> support) {
        for (int i = 0; i < support.size(); i ++) {
            if (support.get(i) < MIN_S) {
                candidateSet.remove(i);
                support.remove(i);
                i --;
            }
        }
        
        System.out.println("Trimmed candidateSet: " + Arrays.toString(candidateSet.toArray()));
    }
    
    /**
     * Calculate support value for each set in the candidateSet
     * 
     * @param candidateSet
     * @return 
     */
    private ArrayList<Double> calculateSupport(ArrayList<ArrayList<String>> candidateSet) {
        ArrayList<Double> support = new ArrayList();
        
        // Calculate support for each set in the candidate set
        for (ArrayList<String> set : candidateSet) {
            int globalOccurrences = 0; // count number of rows in dataSet containing current set
            
            // Count number of rows in dataSet than current set exists in
            for (ArrayList<String> row : this.dataSet) {
                int localOccurrences = 0; // how many items in set occur in each row of the data set
                for (String item : set) {
                    // An item in the current set exists in the current row of data set
                    if (row.contains(item)) {
                        localOccurrences ++;
                    }
                }
                
                // if all items in set occur in the row, increment count
                if (localOccurrences == set.size()) {
                    globalOccurrences ++;
                }
            }
            
            support.add(new Double(globalOccurrences) / new Double(this.dataSet.size()));
        }
        
        System.out.println(Arrays.toString(support.toArray()));
        return support;
    }
    
    /**
     * Find every 1-item set
     * 
     * @return initial candidate set
     */
    private ArrayList<ArrayList<String>> findInitialCandidateSet() {
        ArrayList<String> items = new ArrayList();
        
        // Find all unique items
        for (ArrayList<String> row : dataSet) {
            for (String item : row) {
                // Add item if it doesn't exist in the initial candidate set
                if (!items.contains(item)) {
                    items.add(item);
                }
            }
        }
        
        // Convert each item into a set of size 1
        ArrayList<ArrayList<String>> c = new ArrayList();
        for (String item : items) {
            c.add(new ArrayList());
            c.get(c.size() - 1).add(item);
        }
        
        printCandidateSet(c);
        
        return c;
    }
    
    private void printCandidateSet(ArrayList<ArrayList<String>> c) {
        System.out.print("\n{");
        for (ArrayList<String> set : c) {
            System.out.print("[");
            for (String item : set) {
                System.out.print(item + ", ");
            }
            System.out.print("]");
        }
        System.out.println("}");
    }
    
}
