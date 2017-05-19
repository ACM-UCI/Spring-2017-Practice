import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        
        ArrayList<ArrayList<Integer>> edges = new ArrayList<ArrayList<Integer>>();
        for(int i = 0; i < n; i++) {
            edges.add(new ArrayList<Integer>());
        }
        
        for(int i = 0; i < n - 1; i++) {
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            edges.get(u).add(v);
            edges.get(v).add(u);
        }
        
        // Construct tree rooted at r
        int r = sc.nextInt() - 1;
        boolean[] found = new boolean[n];
        Node root = new Node(r, edges, found);

        // Find longest paths
        int[] longestPaths = new int[n];
        root.computeDepth();
        root.computeLongestPath();
        root.copyLongestPaths(longestPaths);
        
        int q = sc.nextInt();
        for(int i = 0 ; i < q; i++) {
            int subtree = sc.nextInt() - 1;
            System.out.println(longestPaths[subtree]);
        }        
    }
}

class Node {
    int id;
    ArrayList<Node> children;
    
    int depth;
    int longestPath;
    
    public Node(int id, ArrayList<ArrayList<Integer>> edges, boolean[] found) {
        this.id = id;
        this.children = new ArrayList<Node>();
        found[id] = true;
        
        for(int childId : edges.get(id)) {
            if(!found[childId]) {
                Node child = new Node(childId, edges, found);
                this.children.add(child);
                //System.out.println(childId + " is a child of " + this.id);
            }
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