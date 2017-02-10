package cp400_a2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Apriori {
    
    private final File dataFile;
    private final ArrayList<ArrayList<String>> dataSet;
    private static final int MAX_ITERATIONS = 10;
    private static final double MIN_S = 0.5;
    
    public Apriori(String dataFilePath) throws IOException {
        this.dataFile = new File(dataFilePath);
        this.dataSet = DataSetGenerator.genDataSetString(this.dataFile, ", ");
        
        DataSetGenerator.printDataSetString(this.dataSet);
    }
    
    public void associationMine() {
        
        ArrayList<ArrayList<String>> candidateSet = findInitialCandidateSet();
        ArrayList<ArrayList<String>> largeItemSet = new ArrayList();
        int iterations = 0;
        
        while (!candidateSet.isEmpty() && iterations < MAX_ITERATIONS) {
            iterations ++;
            System.out.println("Iteration " + iterations);
            
            // Compute support for each item = count(i)/m
            ArrayList<Double> support = calculateSupport(candidateSet);
            
            ArrayList<ArrayList<String>> untrimmedCandidateSet = new ArrayList(candidateSet);
            ArrayList<Double> untrimmedSupport = new ArrayList(support);
            
            // Remove all sets from candidateSet having support less than the min support MIN_S
            trimCandidateSetByMinS(candidateSet, support);
            
            largeItemSet = new ArrayList(candidateSet);
            
            // New sets are size n+1 and are all combinations of old set (size n)
            candidateSet = constructNextCandidateSet(candidateSet, support, untrimmedCandidateSet, untrimmedSupport);
            System.out.println("\nCandidate set: " + Arrays.toString(candidateSet.toArray()));
        }
        
        System.out.println("\nFinal large item sets: " + Arrays.toString(largeItemSet.toArray()));
    }
    
    private  ArrayList<ArrayList<String>> constructNextCandidateSet( ArrayList<ArrayList<String>> candidateSet, ArrayList<Double> support, ArrayList<ArrayList<String>> untrimmedCandidateSet, ArrayList<Double> untrimmedSupport) {
         ArrayList<ArrayList<String>> newCandidateSet = new ArrayList();
         
         // Iterate through each set in candidate set
         for (int i = 0; i < candidateSet.size()-1; i ++) {
             
             // Compare current set with every other set to find new large item sets
             for (int j = i+1; j < candidateSet.size(); j ++) {
                 // Check each item in the "other" item sets to see if adding them to current set yields new large item set
                 for (String item : candidateSet.get(j)) {
                     // if curSet + item is a valid large item set & we haven't already added it to newCandidateSet, add it to newCandidateSet
                     if (!candidateSet.get(i).contains(item) && combosOfSetValid(untrimmedCandidateSet, new ArrayList(candidateSet.get(i)), item, untrimmedSupport) && !newCandidateSetContainsSet(newCandidateSet, new ArrayList(candidateSet.get(i)), item)) {
                         newCandidateSet.add(new ArrayList(candidateSet.get(i)));
                         newCandidateSet.get(newCandidateSet.size()-1).add(item);
                         //System.out.println("Found new large item set: " + Arrays.toString(newCandidateSet.get(newCandidateSet.size()-1).toArray()));
                     }
                }
             }
             
             
         }
         
         return newCandidateSet;
    }
    
    /**
     * return true if all old candidateSet (untrimmed) sets existing as subset in current set have support > MIN_S
     * 
     * @param curCandidateSet
     * @param item
     * @return 
     */
    private boolean combosOfSetValid(ArrayList<ArrayList<String>> candidateSet, ArrayList<String> curCandidate, String item, ArrayList<Double> support) {
        
        curCandidate.add(item);
        
        for (int i = 0; i < candidateSet.size(); i ++) {
            
            int j;
            // Check if curCandidate contains each item in the set then check that support is valid
            for (j = 0; j < candidateSet.get(i).size(); j ++) {
                if (!curCandidate.contains(candidateSet.get(i).get(j))) {
                    break;
                }
            }
            
            //System.out.println("j = " + j + ". Support[" + i + "] = " + support.get(i));
            // curCandidate has the candidate as a subset but its support is invalid => curCandidate is invalid
            if (j == candidateSet.get(i).size() && support.get(i) < MIN_S) {
                //System.out.println("j = " + j + ". Support[" + i + "] = " + support.get(i));
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Used to avoid adding duplicate candidates to the newCandidateSet
     * 
     * @param newCandidateSet
     * @param curCandidate
     * @param item
     * @return 
     */
    private boolean newCandidateSetContainsSet(ArrayList<ArrayList<String>> newCandidateSet, ArrayList<String> curCandidate, String item) {
        
        curCandidate.add(item);
        //System.out.println("Checking curCandidate: " + Arrays.toString(curCandidate.toArray()));
        
        // Check each row for equivelance to curCandidateSet
        for (ArrayList<String> row : newCandidateSet) {
            //System.out.println("Comparing to newCandidateSet row: " + Arrays.toString(newCandidateSet.toArray()));
            int i;
            for (i = 0; i < curCandidate.size(); i ++) {
                if (!row.contains(curCandidate.get(i))) {
                    break;
                }
            }
            
            //System.out.println("i = " + i + " : " + (curCandidate.size()));
            // All items matched => set is already in newCandidateSet
            if (i == curCandidate.size()) {
                return true;
            }
        }
        
        return false;
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
        
        System.out.println(Arrays.toString(c.toArray()));
        
        return c;
    }
    
    /*private void printCandidateSet(ArrayList<ArrayList<String>> c) {
        System.out.print("\n{");
        for (ArrayList<String> set : c) {
            System.out.print("[");
            for (String item : set) {
                System.out.print(item + ", ");
            }
            System.out.print("]");
        }
        System.out.println("}");
    }*/
    
}
