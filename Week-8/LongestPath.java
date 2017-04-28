import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        boolean[] isBlack = new boolean[n];
        for(int i = 0; i < n; i++) {
            int color = sc.nextInt();
            isBlack[i] = (color == 1);
        }
        
        ArrayList<ArrayList<Integer>> edges = new ArrayList<ArrayList<Integer>>();
        boolean[] hasParent = new boolean[n];
        for(int i = 0; i < n; i++) {
            edges.add(new ArrayList<Integer>());
            hasParent[i] = false;
        }        
        
        for(int i = 1; i < n; i++) {
            int parent = sc.nextInt() - 1;
            if(parent >= 0 && isBlack[i] && isBlack[parent]) {
                hasParent[i] = true;
                edges.get(parent).add(i);
                //System.out.println("Adding edge from " + parent + " to " + i);
            }                
        }
        
        int longestPath = 0;
        for(int i = 0; i < n; i++) {
            if(isBlack[i] && !hasParent[i]) {
                Node newRoot = new Node(i, edges);
                newRoot.computeDepth();
                longestPath = Math.max(longestPath, newRoot.computeLongestPath() + 1);
            }
        }
        
        System.out.println(longestPath);
    }
}

class Node {
    int id;
    ArrayList<Node> children;
    
    int depth;
    int longestPath;
    
    public Node(int id, ArrayList<ArrayList<Integer>> edges) {
        this.id = id;
        this.children = new ArrayList<Node>();
        
        for(int childId : edges.get(id)) {
            Node child = new Node(childId, edges);
            this.children.add(child);
            //System.out.println(childId + " is a child of " + this.id);
        }
    }
    
    public int computeDepth() {
        this.depth = 0;
        for(Node child : this.children) {
            this.depth = Math.max(this.depth, child.computeDepth() + 1);
        }
        
        //System.out.println("Depth of vertex " + this.id + " = " + this.depth);
        return this.depth;
    }
    
    public int computeLongestPath() {
        this.longestPath = 0;
        
        // Case 1: Include root
        if(this.children.size() == 1) {
            this.longestPath = this.depth;
        }
        else if(this.children.size() > 1) {
            int maxChildDepth = 0;
            int secondMaxChildDepth = 0;
            for(Node child : this.children) {
                if(child.depth > maxChildDepth) {
                    secondMaxChildDepth = maxChildDepth;
                    maxChildDepth = child.depth;
                }
                else if(child.depth > secondMaxChildDepth) {
                    secondMaxChildDepth = child.depth;
                }
            }
            
            this.longestPath = maxChildDepth + secondMaxChildDepth + 2;
        }
        
        // Case 2: Don't include root
        for(Node child : this.children)
            this.longestPath = Math.max(this.longestPath, child.computeLongestPath());
        
        //System.out.println("Longest path for vertex " + this.id + " = " + this.longestPath);
        return this.longestPath;
    }
    
    public void copyLongestPaths(int[] longestPaths) {
        longestPaths[this.id] = this.longestPath;
        
        for(Node child : this.children)
            child.copyLongestPaths(longestPaths);
    }
}