import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static void main(String[] args) {
        try {
            BufferedReader bi = new BufferedReader(new InputStreamReader(System.in));
            String[] strNums;
            int n = Integer.parseInt(bi.readLine().trim());
            //System.out.println("n = " + n);

            ArrayList<ArrayList<Integer>> subsequences = new ArrayList<ArrayList<Integer>>();
            HashMap<Integer, ArrayList<Integer>> sequenceIndex = new HashMap<Integer, ArrayList<Integer>>();
            int maxNum = 1000000;
            int[] counts = new int[maxNum + 1];

            for(int i = 0; i < n; i++) {
                ArrayList<Integer> newSequence = new ArrayList<Integer>();
                int k = Integer.parseInt(bi.readLine().trim());
                //System.out.println("k = " + k);

                strNums = bi.readLine().split("\\s");
                for(int j = 0; j < strNums.length; j++) {
                    int value = Integer.parseInt(strNums[j]);
                    counts[value]++;
                    newSequence.add(value);
                    
                    if(sequenceIndex.containsKey(value))
                        sequenceIndex.get(value).add(i);
                    else {
                        ArrayList<Integer> indexList = new ArrayList<Integer>();
                        indexList.add(i);
                        sequenceIndex.put(value, indexList);
                    }
                }

                subsequences.add(newSequence);
            }

            PriorityQueue<Integer> queue = new PriorityQueue<Integer>();
            ArrayList<Integer> finalSequence = new ArrayList<Integer>();
            int[] indexes = new int[n];
            int[] countNext = new int[maxNum];
            for(int i = 0; i < n; i++) {
                if(indexes[i] < subsequences.get(i).size()) {
                    int nextVal = subsequences.get(i).get(indexes[i]);
                    countNext[nextVal]++;
                    if(countNext[nextVal] == counts[nextVal])
                        queue.add(nextVal);
                }
            }
            
            while(queue.size() > 0) {
                int nextVal = queue.poll();
                finalSequence.add(nextVal);
                
                for(int seqIndex : sequenceIndex.get(nextVal)) {
                    indexes[seqIndex]++;
                    if(indexes[seqIndex] < subsequences.get(seqIndex).size()) {
                        int newVal = subsequences.get(seqIndex).get(indexes[seqIndex]);
                        countNext[newVal]++;
                        if(countNext[newVal] == counts[newVal])
                            queue.add(newVal);
                    }
                }
            }

            for(int val : finalSequence) {
                System.out.print(val + " ");
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
}