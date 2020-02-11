// This class handles the node searching functions and general tree structure for the partition scheme.

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import java.util.Stack;

public class partTree {

    int globalX;
    int globalY;
    Group treeGroup;
    partNode root;

    // Basic constructor for partTree
    public partTree( Group p1group, int globalX, int globalY) {

        this.treeGroup = p1group;
        this.globalX = globalX;
        this.globalY = globalY;

    }

    // Adds nodes to tree.
    public partNode addNode(String name,partNode parentNode, int treeLayer, int nodeNum) {

        int yshift = 80; // Adjust to change distance between levels.

        int initXShift = 250; // Initial shift for first layer.

        // Handle if root exists, if not add new node.
        if (name == "root") {

            partNode root = new partNode(name, treeGroup, globalX / 2, 50, nodeNum);
            this.root = root;
            return root;

        } else {

            partNode newNode = new partNode(name, treeGroup, nodeNum);

            if(parentNode == null)
                parentNode = root;

            partNode focusNode = parentNode;
            partNode parent = parentNode;

            int xshift;

            xshift = initXShift;

            // Change amount to shift nodes by depending on layer of tree in X and Y
            if(treeLayer != 1)
                xshift = (int)(initXShift / (2*treeLayer));

            yshift = yshift/treeLayer;

            // Logic for handling which branch to traverse to add new node.
            if (name == "Left") {

                focusNode = focusNode.leftChild;

                if (focusNode == null) {

                    parent.leftChild = newNode;
                    parent.leftChild.parent = parent;
                    parent.leftChild.setNodeXY(parent.nodeX - xshift, parent.nodeY + yshift);
                    parent.leftChild.treeLevel = treeLayer;
                    return parent.leftChild;

                }

            } else if (name == "Middle") {

                focusNode = focusNode.middleChild;

                if (focusNode == null) {

                    parent.middleChild = newNode;
                    parent.middleChild.parent = parent;
                    parent.middleChild.setNodeXY(parent.nodeX, parent.nodeY + yshift);
                    parent.middleChild.treeLevel = treeLayer;
                    return parent.middleChild;

                }

            } else if(name == "Right") {

                focusNode = focusNode.rightChild;

                if (focusNode == null) {

                    parent.rightChild = newNode;
                    parent.rightChild.parent = parent;
                    parent.rightChild.setNodeXY(parent.nodeX + xshift, parent.nodeY + yshift);
                    parent.rightChild.treeLevel = treeLayer;
                    return parent.rightChild;

                }

            } else{

                // Bad name passed to function.

                return null;

            }


        }

        return null;

    }

    // Generates lines for each node from given parent (apart from root). Lines are assigned to children from each node.
    public void inOrderAddLines(partNode focusNode, Group globalGroup) {

        Line newLine = new Line();

        if (focusNode != null) {

            focusNode.nodeLine = newLine;

            if (focusNode.parent == null) {

                focusNode.nodeLine = null;

            } else {

                newLine.setStartX(focusNode.parent.nodeX);
                newLine.setStartY(focusNode.parent.nodeY);
                newLine.setEndX(focusNode.nodeX);
                newLine.setEndY(focusNode.nodeY);
                globalGroup.getChildren().add(focusNode.nodeLine);

            }

            inOrderAddLines(focusNode.leftChild, globalGroup);
            inOrderAddLines(focusNode.middleChild, globalGroup);
            inOrderAddLines(focusNode.rightChild, globalGroup);

        }

    }

    // Gets the circles for each of the nodes for plotting.
    public void inOrderGetNodeShapes(partNode focusNode, Group globalGroup) {

        if (focusNode != null) {
            globalGroup.getChildren().add(focusNode.nodeCircle);
            inOrderGetNodeShapes(focusNode.leftChild, globalGroup);
            inOrderGetNodeShapes(focusNode.middleChild,globalGroup);
            inOrderGetNodeShapes(focusNode.rightChild, globalGroup);
        }

    }

    // Adds text to global group for each node.
    public void inOrderGetNodeText(partNode focusNode, Group globalGroup) {

        if (focusNode != null) {
            globalGroup.getChildren().add(focusNode.nodeText);
            inOrderGetNodeText(focusNode.leftChild, globalGroup);
            inOrderGetNodeText(focusNode.middleChild,globalGroup);
            inOrderGetNodeText(focusNode.rightChild, globalGroup);
        }

    }

    // Returns the correct node for a given number value.
    public partNode getNodebyNum(partNode focusNode,int keyVal){

        Stack<partNode> myStack = new Stack<>();
        partNode findNode = null;

        myStack.push(focusNode);

        while(!myStack.empty()){

            focusNode = myStack.peek();
            myStack.pop();

            if(focusNode.nodeNum == keyVal){

                while(!myStack.isEmpty()) // Empty stack before exiting.
                    myStack.pop();

                return focusNode;
            }

            if(focusNode.leftChild != null)
                myStack.push(focusNode.leftChild);

            if(focusNode.middleChild != null)
                myStack.push(focusNode.middleChild);

            if(focusNode.rightChild != null)
                myStack.push(focusNode.rightChild);

        }

        return null;

    }

    // Updates the isLeaf variable for each node in the tree if a node is a leaf.
    public void assignLeaves(partNode focusNode){

        Stack<partNode> myStack = new Stack<>();
        partNode findNode = null;

        myStack.push(focusNode);

        while(!myStack.empty()){

            focusNode = myStack.peek();
            myStack.pop();

            if(focusNode.leftChild == null && focusNode.middleChild == null  && focusNode.rightChild == null){
                focusNode.isLeaf = true;
            }

            if(focusNode.leftChild != null)
                myStack.push(focusNode.leftChild);

            if(focusNode.middleChild != null)
                myStack.push(focusNode.middleChild);

            if(focusNode.rightChild != null)
                myStack.push(focusNode.rightChild);

        }

        return;

    }

    // Resets lines to their default thickness for the entire tree.
    public void clearLines(partNode focusNode){

        if (focusNode != null) {
            if(focusNode.nodeLine != null) {
                focusNode.nodeLine.setStrokeWidth(1.0); // Reset to default value.

            }
            clearLines(focusNode.leftChild);
            clearLines(focusNode.middleChild);
            clearLines(focusNode.rightChild);

        }

    }

    // Resets the altered leaf nodes to their default green color.
    public void resetLeaves(partNode focusNode){

        if (focusNode != null) {
            if(focusNode.isLeaf) {
                focusNode.nodeCircle.setFill(Color.GREEN); // Reset to default value.

            }

            resetLeaves(focusNode.leftChild);
            resetLeaves(focusNode.middleChild);
            resetLeaves(focusNode.rightChild);

        }

    }

    //Handles function calls for finding a callee with the appropriate rep.
    public void searchCallees(partCaller userCaller, partCaller userCallee){

        traverseRep(userCaller.callerNode);
        if(userCaller.callerNode.nodeRep.checkCallee(userCallee)){
            repFindCallee(userCaller.callerNode.nodeRep, userCallee);
        }else{
            searchOtherReps(userCaller, userCallee);
        }

    }

    // Handles traversing from a leaf node to a representative and updating line drawings.
    public void traverseRep(partNode focusNode){

        double lineWidth = 5.0;
        focusNode.nodeLine.setStrokeWidth(lineWidth);

        if(!focusNode.parent.isRep)
            traverseRep(focusNode.parent);

    }

    // Handles a rep searching with a stack for a certain callee.
    public void repFindCallee(partRep searchingRep, partCaller userCallee){

        Stack<partNode> repStack = new Stack<>();

        partNode focusNode = searchingRep.repNode;
        String callee = userCallee.callerID;
        repStack.push(focusNode);

        while(!repStack.empty()){

            focusNode = repStack.peek();
            repStack.pop();

            if(focusNode != searchingRep.repNode){
                if(focusNode.nodeLine != null){
                    focusNode.nodeLine.setStrokeWidth(5.0);

                }

            }

            if(focusNode.hasCaller) {
                if (focusNode.nodeCaller.callerID == callee) {

                    focusNode.nodeCircle.setFill(Color.GOLD);
                    while (!repStack.isEmpty()) // Empty stack before exiting.
                        repStack.pop();

                    return;
                }
            }

            if(focusNode.leftChild != null)
                repStack.push(focusNode.leftChild);

            if(focusNode.middleChild != null)
                repStack.push(focusNode.middleChild);

            if(focusNode.rightChild != null)
                repStack.push(focusNode.rightChild);

        }

        return;

    }

    // Handles a single rep searching for other reps on the main branch from the root node.
    public void searchOtherReps(partCaller userCaller, partCaller userCallee){

        partNode callerRepNode = userCaller.callerNode.nodeRep.repNode;
        partNode focusNode;
        Stack<partNode> repSearchStack = new Stack<>();
        focusNode = callerRepNode;

        // Get to root node.
        while(true){

            double lineWidth = 5.0;
            focusNode.nodeLine.setStrokeWidth(lineWidth);

            if(!focusNode.parent.name.equals("root")) {
                focusNode = focusNode.parent;
            }else{
                focusNode = focusNode.parent;
                break;
            }

        }

        // Search for other rep nodes and check for callee.
        repSearchStack.push(focusNode);

        while(true){

            focusNode = repSearchStack.peek();
            repSearchStack.pop();

            if(focusNode.nodeLine != null)
                focusNode.nodeLine.setStrokeWidth(5.0);

            if(focusNode.isRep){
                if(focusNode.repInNode.checkCallee(userCallee)) {
                    repFindCallee(focusNode.repInNode, userCallee);
                    return;
                }
            }else {

                if (focusNode.leftChild != null)
                    repSearchStack.push(focusNode.leftChild);

                if (focusNode.rightChild != null)
                    repSearchStack.push(focusNode.rightChild);

                if (focusNode.middleChild != null)
                    repSearchStack.push(focusNode.middleChild);


            }

        }

    }

}
