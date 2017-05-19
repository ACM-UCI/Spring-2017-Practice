import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static void main(String[] args) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            int tests = Integer.parseInt(in.readLine().trim());
            for(int test = 0; test < tests; test++) {
                int n = Integer.parseInt(in.readLine().trim());
                HashMap<String, Integer> locationsIndex = new HashMap<String, Integer>();
                ArrayList<ArrayList<Integer>> sequences = new ArrayList<ArrayList<Integer>>();
                String[] locations;

                for(int i = 0; i < n; i++) {
                    locations = in.readLine().trim().split(",");

                    ArrayList<Integer> sequence = new ArrayList<Integer>();
                    for(int j = 0; j < locations.length; j++) {
                        if(locationsIndex.containsKey(locations[j])) {
                            sequence.add(locationsIndex.get(locations[j]));
                        }
                        else {
                            int tableSize = locationsIndex.size();
                            locationsIndex.put(locations[j], tableSize);
                            sequence.add(tableSize);
                        }
                    }
                    
                    sequences.add(sequence);
                }

                int numLocations = locationsIndex.size();
                int[] incomingCounts = new int[numLocations];
                int[][] adjacency = new int[numLocations][numLocations];
                for(ArrayList<Integer> sequence : sequences) {
                    for(int i = 1; i < sequence.size(); i++) {
                        incomingCounts[sequence.get(i)]++;
                        adjacency[sequence.get(i - 1)][sequence.get(i)]++;
                    }
                }

                LinkedList<Integer> queue = new LinkedList<Integer>();
                for(int i = 0; i < numLocations; i++) {
                    if(incomingCounts[i] == 0)
                        queue.add(i);
                }

                int ordered = 0;
                HashSet<Integer> found = new HashSet<Integer>();
                
                while(queue.size() > 0) {
                    ordered++;
                    int nextLocation = queue.poll();
                    //System.out.println(nextLocation);
                    if(!found.contains(nextLocation)) {
                        found.add(nextLocation);
                        for(int i = 0; i < numLocations; i++) {
                            incomingCounts[i] -= adjacency[nextLocation][i];
                            if(incomingCounts[i] == 0 && adjacency[nextLocation][i] > 0)
                                queue.add(i);
                        }
                    }
                }

                if(ordered == numLocations)
                    System.out.println("ORDER EXISTS");
                else
                    System.out.println("ORDER VIOLATION");
            }
        }
        catch(IOException e) {

        }
    }
}