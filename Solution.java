import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


/**
 * Definition for a binary tree node.
 */
class TreeNode {

    // **** class members ****
    int         val;
    TreeNode    left;
    TreeNode    right;

    // **** constructors ****
    TreeNode() {}

    TreeNode(int val) { this.val = val; }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val    = val;
        this.left   = left;
        this.right  = right;
    }

    // **** ****
    @Override
    public String toString() {
        return "" + this.val;
    }
}


/**
 * 104. Maximum Depth of Binary Tree
 * https://leetcode.com/problems/maximum-depth-of-binary-tree/
 */
public class Solution {


    /**
     * Enumerate which child in the node at the head of the queue 
     * (see populateTree function) should be updated.
     */
    enum Child {
        LEFT,
        RIGHT
    }
 
 
    // **** child turn to insert on node at head of queue ****
    static Child  insertChild = Child.LEFT;


    /**
     * Generate the number of nodes in the BT at the specified level.
     */
    static int nodesInLevel(int level) {
        if (level < 1)
            return 0;
        else
            return (int)Math.pow(2.0, level - 1);
    }


    /**
     * Populate a BT using the specified array of integer and null values.
     */
    static TreeNode populateBT(String[] arr) {
    
        // **** auxiliary queue ****
        Queue<TreeNode> q = new LinkedList<TreeNode>();
    
        // **** ****
        TreeNode root = null;
    
        // **** begin and end of substring to process ****
        int b   = 0;
        int e   = 0;
    
        // **** loop once per binary tree level ****
        for (int level = 1; b < arr.length; level++) {
    
            // **** count of nodes at this level ****
            int count = nodesInLevel(level);
    
            // **** compute e ****
            e = b + count;
        
            // **** generate sub array of strings ****
            String[] subArr = Arrays.copyOfRange(arr, b, e);
    
            // **** populate the specified level in the binary tree ****
            root = populateBTLevel(root, level, subArr, q);
    
            // **** update b ****
            b = e;
        }
    
        // **** return the BT ****
        return root;
    }


    /**
     * Populate the specified level in the specified binary tree.
     */
    static TreeNode populateBTLevel(TreeNode root, int level, String[] subArr, Queue<TreeNode> q) {
    
        // **** populate binary tree root (if needed) ****
        if (root == null) {
            root = new TreeNode(Integer.parseInt(subArr[0]));
            q.add(root);
            return root;
        }
    
        // **** ****
        TreeNode child = null;
    
        // **** traverse the sub array of node values ****
        for (int i = 0; (i < subArr.length) && (subArr[i] != null); i++) {
    
            // **** child node ****
            child = null;
    
            // **** create and attach child node (if needed) ****
            if (!subArr[i].equals("null"))
                child = new TreeNode(Integer.parseInt(subArr[i]));
    
            // **** add child to the queue ****
            q.add(child);
    
            // **** attach child node (if NOT null) ****
            if (child != null) {
                if (insertChild == Child.LEFT)
                    q.peek().left = child;
                else
                    q.peek().right = child;
            }
    
            // **** remove node from the queue (if needed) ****
            if (insertChild == Child.RIGHT) {    
                q.remove();
            }
    
            // **** toggle insert for next child ****
            insertChild = (insertChild == Child.LEFT) ? Child.RIGHT : Child.LEFT;
        }
    
        // **** return root of binary tree ****
        return root;
    }


    /**
     * Traverse the specified BST displaying the values in order.
     * This method is used to verify that the BST was properly populated.
     */
    static void inOrder(TreeNode root) {
    
        // **** end condition ****
        if (root == null)
            return;
    
        // **** visit the left sub tree ****
        inOrder(root.left);
    
        // **** display the value of this node ****
        System.out.print(root.val + " ");
    
        // **** visit the right sub tree ****
        inOrder(root.right);
    }


    /**
     * Given a binary tree, find its maximum depth.
     * Recursive function O(log(n)).
     */
    static int maxDepth(TreeNode root) {
        
        // **** base condition ****
        if (root == null)
            return 0;

        // **** initialize the BT max depth ****
        int depth = 0;

        // **** visit left node ****
        depth = Math.max(depth, maxDepth(root.left) + 1);

        // **** visit right node ****
        depth = Math.max(depth, maxDepth(root.right) + 1);

        // **** return the max depth ****
        return depth;
    }


    /**
     * Test scaffolding
     */
    public static void main(String[] args) {
        
        // **** BT root ****
        TreeNode root = null;
 
        // **** open scanner ****
        Scanner sc = new Scanner(System.in);
     
        // **** read data for BT (in level order) ****
        String[] arr = sc.nextLine().split(",");
     
        // **** close scanner ****
        sc.close();
     
        // **** populate the binary tree ****
        root = populateBT(arr);
     
        // ???? display the binary tree ????
        System.out.println("main <<< inOrder: ");
        inOrder(root);
        System.out.println();

        // **** find and display the depth of the BT ****
        System.out.println("main <<< maxDepth: " + maxDepth(root));
    }
}