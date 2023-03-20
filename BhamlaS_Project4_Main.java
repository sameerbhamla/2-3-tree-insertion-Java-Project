import java.io.*;
import java.util.*;

    class treeNode {
    public int key1;
    public int key2;
    public int rank;
    public treeNode child1;
    public treeNode child2;
    public treeNode child3;
    public treeNode father;

    public treeNode(){
        this.key1 = -1;
        this.key2 = -1;
        this.rank = -1;
        this.child1 = null;
        this.child2 = null;
        this.child3 = null;
        this.father = null;
    }
    public treeNode(int key1, int key2, int rank, treeNode child1, treeNode child2, treeNode child3, treeNode father) {
        this.key1 = key1;
        this.key2 = key2;
        this.rank = rank;
        this.child1 = child1;
        this.child2 = child2;
        this.child3 = child3;
        this.father = father;
    }

    public static boolean isLeaf(treeNode node) {
        return (node.child1 == null && node.child2 == null && node.child3 == null);
    }

    public static void printNode(treeNode Tnode, PrintWriter outFile) {
        outFile.print("(");
        if (Tnode == null) {
            outFile.println("null");
        } else if (isLeaf(Tnode)) {
            outFile.println(Tnode.key1 + ", " + Tnode.key2 + ", " + Tnode.rank + ", " + "null, null, null, " + (Tnode.father != null ? Tnode.father.key1 : "null")+")");
        } else if (Tnode.child3 == null) {
            outFile.println(Tnode.key1 + ", " + Tnode.key2 + ", " + Tnode.rank + ", " + (Tnode.child1 != null ? Tnode.child1.key1 : "null") + ", " + (Tnode.child2 != null ? Tnode.child2.key1 : "null") + ", " + "null, " + (Tnode.father != null ? Tnode.father.key1 : "null")+")");
        } else {
            outFile.println(Tnode.key1 + ", " + Tnode.key2 + ", " + Tnode.rank + ", " + (Tnode.child1 != null ? Tnode.child1.key1 : "null") + ", " + (Tnode.child2 != null ? Tnode.child2.key1 : "null") + ", " + (Tnode.child3 != null ? Tnode.child3.key1 : "null") + ", " + (Tnode.father != null ? Tnode.father.key1 : "null")+")");
        }
    }
}

class Trees {
    public treeNode Root;

    public Trees() {
        Root = null;
    }

    public treeNode initialTree(Scanner inFile, PrintWriter debugFile) throws Exception {
        try {
            PrintWriter debugWriter = debugFile;
            debugWriter.println("Entering initialTree() method");

            // Step 1
            this.Root = new treeNode(-1, -1, -1, null, null, null, null);

            // Step 2

            int data1 = inFile.nextInt();
            int data2 = inFile.nextInt();
            debugWriter.println("Before swap data1 and data2 are: " + data1 + " " + data2);
            if (data2 < data1) {
                int temp = data1;
                data1 = data2;
                data2 = temp;
                debugWriter.println("After swap data1 and data2 are: " + data1 + " " + data2);
            }

            // Step 3
            treeNode newNode1 = new treeNode(data1, -1, 1, null, null, null, Root);
            treeNode newNode2 = new treeNode(data2, -1, 2, null, null, null, Root);

            // Step 4
            Root.child1 = newNode1;
            Root.child2 = newNode2;

            // Step 5
            Root.key1 = data2;

            // Step 6
            debugWriter.println("The root node is:");
            treeNode.printNode(Root, debugWriter);

            // Step 7
            debugWriter.println("Exiting initialTree() method");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Root;
    }

    public void build23Tree(Scanner inFile, PrintWriter deBugFile) throws Exception {
        //Step 0
        deBugFile.println("Entering build23Tree () method");

        //Step 1
        while (inFile.hasNext()) {
            int data = inFile.nextInt();
            //Step 2
            treeNode Spot = findSpot(this.Root, data, deBugFile);

            //Step 3
            if (Spot != null) {
                deBugFile.println("In build23Tree (), printing Spot info.");
                Spot.printNode(Spot, deBugFile);

                treeNode leafNode = new treeNode(data, -1, 5, null, null, null, null);
                treeInsert(Spot, leafNode, deBugFile);

                deBugFile.println("In build23Tree; printing preOrder () after one treeInsert");
                preOrder(this.Root, deBugFile);
            }
        }
        deBugFile.println("Exiting build23Tree () method");


    }
    public treeNode findSpot(treeNode Spot, int data, PrintWriter deBugFile) {
        deBugFile.println("Entering findSpot() method");
        deBugFile.println("Spot's key1 and key2 and data are " + Spot.key1 + " " + Spot.key2 + " " + data);

        //Step 1
        if(Spot.child1 == null){
            deBugFile.println("in findSpot() You are at a leaf level, you are too far down the tree!");
            return null;
        }
        //Step 2
        if(data == Spot.key1 || data == Spot.key2){
            deBugFile.println("in findSpot() data is already in Spot's keys, no need to search further ");
            return null;
        }


        if (isLeaf(Spot.child1)) {
            if (data == Spot.child1.key1 || data == Spot.child2.key1) {
                deBugFile.println("In findSpot (): data is already in a leaf node");
                return null;
            } else {
                return Spot;
            }
        } else {
              if (data < Spot.key1) {
                return findSpot(Spot.child1, data, deBugFile);

            } else if (Spot.key2 == -1 || data < Spot.key2) {
                return findSpot(Spot.child2, data, deBugFile);

            } else if (Spot.key2 != -1 && data >= Spot.key2) {
                return findSpot(Spot.child3, data, deBugFile);

            } else {
                deBugFile.println("In findSpot (), something is wrong about data: " + data);
                return null;
            }
        }
    }


    public void treeInsert(treeNode Spot, treeNode newNode, PrintWriter deBugFile) {
        deBugFile.println("Entering treeInsert() method");
        if (Spot == null) {
            deBugFile.println("In treeInsert(), Spot is null, something is wrong");
            return;
        } else {
            deBugFile.println("In treeInsert(). Printing Spot and newNode info");
            treeNode.printNode(Spot, deBugFile);
            treeNode.printNode(newNode, deBugFile);
        }

        int count;
        if (Spot.key2 == -1) {
            count = 2;
        }else{
            count = 3;
        }
        deBugFile.println("in treeinsert method Spot kid count is " + count);

        if(count == 2){
            spotHas2kidsCase(Spot, newNode, deBugFile);
        }else if(count == 3){
            spotHas3kidsCase(Spot, newNode, deBugFile);
        }

        deBugFile.println("Leaving treeInsert() method");
    }

    public void spotHas2kidsCase(treeNode Spot, treeNode newNode, PrintWriter deBugFile) {
        //Step 1
        deBugFile.println("In spotHas2kidsCase() method");
        deBugFile.println("In spotHas2KidsCase() method SPots rank is " + Spot.rank);

        // Step 2
        if (newNode.key1 < Spot.child2.key1) {
            Spot.child3 = Spot.child2;
            Spot.child2 = newNode;
        } else {
            Spot.child3 = newNode;
        }
        //Step 3
        if(Spot.child2.key1 < Spot.child1.key1){
            treeNode temp = Spot.child1;
            Spot.child1 = Spot.child2;
            Spot.child2 = temp;
        }
        //Step 4
        Spot.child1.father = Spot;
        Spot.child1.rank = 1;
        Spot.child2.father = Spot;
        Spot.child2.rank = 2;
        Spot.child3.father = Spot;
        Spot.child3.rank = 3;
        //Step 5
        updateKeys(Spot, deBugFile);

        //Step 6
        if(Spot.rank > 1){
            updateKeys(Spot.father, deBugFile);
        }
        //Step 7
        deBugFile.println("Leaving spotHas2kidCase() method");
    }


    public void spotHas3kidsCase(treeNode Spot, treeNode newNode, PrintWriter deBugFile) {
        //Step 0
        deBugFile.println("Entering spotHas3kidsCase() method");
        deBugFile.println("In spotHas3kidsCase() method Spots rank is" + Spot.rank);

        // Step 1
        treeNode sibling = new treeNode(-1,-1,5,null,null,null,null);
        //Step 2
        if(newNode.key1 > Spot.child3.key1){
            sibling.child2 = newNode;
            sibling.child1 = Spot.child3;
            Spot.child3 = null;
        }else if(newNode.key1 < Spot.child3.key1){
            sibling.child2 = Spot.child3;
            Spot.child3 = newNode;
        }

        //Step 3
        if(Spot.child3 != null){
            if(Spot.child3.key1 > Spot.child2.key1){
                sibling.child1 = Spot.child3;
                Spot.child3 = null;
            }else{
                sibling.child1 = Spot.child2;
                Spot.child2 = newNode;
            }
        }else if(Spot.child2.key1 < Spot.child1.key1){
            treeNode temp = Spot.child1;
            Spot.child1 = Spot.child2;
            Spot.child2 = temp;
        }

        //Step 4
        Spot.child1.father = Spot;
        Spot.child1.rank = 1;
        Spot.child2.father = Spot;
        Spot.child2.rank = 2;
        Spot.child3 = null;

        //Step 5
        sibling.child1.father = sibling;
        sibling.child1.rank = 1;
        sibling.child2.father = sibling;
        sibling.child2.rank = 2;
        sibling.child3 = null;

        //Step 6
        updateKeys(Spot, deBugFile);
        updateKeys(sibling, deBugFile);

        //Step 7
        if(Spot.rank == -1 && Spot.father == null){
            this.Root = makeNewRoot(Spot, sibling, deBugFile);
        }else{
            treeInsert(Spot.father, sibling, deBugFile);
        }

        //Step 8
        if(Spot.rank > 1){
            updateKeys(Spot.father, deBugFile);
        }
        //Step 9
        deBugFile.println("Leaving spotHas3KidsCase() method");
    }

    public treeNode makeNewRoot(treeNode Spot, treeNode Sibling, PrintWriter deBugFile) {
        //Step 0
        deBugFile.println("Entering makeNewRoot() method.");

        //Step 1
        treeNode newRoot = new treeNode(-1, -1, -1, null, null, null, null);
        newRoot.child1 = Spot;
        newRoot.child2 = Sibling;
        newRoot.child3 = null;
        newRoot.key1 = findMinLeaf(Sibling);
        newRoot.key2 = -1;
        Spot.father = newRoot;
        Spot.rank = 1;
        Sibling.father = newRoot;
        Sibling.rank = 2;

        //Step 2
        deBugFile.println("Leaving makeNewRoot() method.");
        //Step 3
        return newRoot;
    }

    public int findMinLeaf(treeNode Tnode) {
        if (Tnode == null) {
            return -1;
        }
        if (Tnode.child1 == null) {
            return Tnode.key1;
        } else {
            return findMinLeaf(Tnode.child1);
        }
    }


    public void updateKeys(treeNode Tnode, PrintWriter deBugFile) {
        deBugFile.println("Entering updateKeys() method.");

        if (Tnode == null) {
            return;
        }

        Tnode.key1 = findMinLeaf(Tnode.child2);
        Tnode.key2 = findMinLeaf(Tnode.child3);

        if (Tnode.rank > 1) {
            updateKeys(Tnode.father, deBugFile);
        }

        deBugFile.println("Leaving updateKeys() method.");
    }

    public boolean isLeaf(treeNode Tnode) {

        return treeNode.isLeaf(Tnode);
    }

    public void preOrder(treeNode node, PrintWriter deBugFile) {
        if (node == null) {
            return;
        }
        treeNode.printNode(node, deBugFile);
        preOrder(node.child1, deBugFile);
        preOrder(node.child2, deBugFile);
        preOrder(node.child3, deBugFile);

    }

    public int treeDepth(treeNode root) {
        if (root == null) {
            return 0;
        }

        int leftDepth = treeDepth(root.child1);
        int middleDepth = treeDepth(root.child2);
        int rightDepth = treeDepth(root.child3);

        return 1 + Math.max(leftDepth, Math.max(middleDepth, rightDepth));
    }
}


public class BhamlaS_Project4_Main {
    public static void main(String[] args) {
        try {
            // Step 0: open files
            Scanner inFile = new Scanner(new File(args[0]));
            PrintWriter treeFile = new PrintWriter(new FileWriter(args[1]));
            PrintWriter deBugFile = new PrintWriter(new FileWriter(args[2]));

//            treeNode root = Trees.initialTree(inFile, deBugFile);
//            Trees.build23Tree(inFile, root, deBugFile);
//            Trees.preOrder(root, treeFile);

            Trees tree = new Trees();
            tree.Root = tree.initialTree(inFile, deBugFile);
//          Trees.preOrder(root, deBugFile);
            tree.build23Tree(inFile, deBugFile);
            tree.preOrder(tree.Root, treeFile);

            inFile.close();
            treeFile.close();
            deBugFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}