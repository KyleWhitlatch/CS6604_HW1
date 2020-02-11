// This class inherits from the tree structure in part one and adds some new functions to handle forwarding pointers.

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class pointTree extends partTree {

    pointNode root;
    int index; // Used for recursive creation, starts at 1.

    ArrayList<Line> levelLines;

    pointTree(Group treeGroup, int treeX, int treeY, int index) {

        super(treeGroup, treeX, treeY);
        this.index = index;
        levelLines = new ArrayList<>();

    }

    // Creates the tree and initializes shapes.
    public void createTree(pointNode focusNode, int numLevels, pointNode[] nodeArray) {

        // Check if we have reached the (n-1) tree level, if so, create leaves and return.
        if (focusNode.treeLevel >= (numLevels - 1)) {

            // THREE LEAF NODES PER N-1 BRANCH
            focusNode.leftChild = addNode("" + index, focusNode, numLevels, index);
            focusNode.leftChild.isLeaf = true;
            focusNode.leftChild.setTreeLevel(focusNode.treeLevel+1);
            nodeArray[index] = focusNode.leftChild;
            index++;
            focusNode.middleChild = addNode("" + index, focusNode, numLevels, index);
            focusNode.middleChild.isLeaf = true;
            focusNode.middleChild.setTreeLevel(focusNode.treeLevel+1);
            nodeArray[index] = focusNode.middleChild;
            index++;
            focusNode.rightChild = addNode("" + index, focusNode, numLevels, index);
            focusNode.rightChild.isLeaf = true;
            focusNode.rightChild.setTreeLevel(focusNode.treeLevel+1);
            nodeArray[index] = focusNode.rightChild;
            index++;

            return;

        }

        // TWO BRANCHES PER PARENT BRANCH NODE
        focusNode.leftChild = addNode("" + index, focusNode, numLevels, index);
        focusNode.leftChild.setTreeLevel(focusNode.treeLevel + 1);
        focusNode.leftChild.isBranchNode = true;
        nodeArray[index] = focusNode.leftChild;
        index++;
        focusNode.rightChild = addNode("" + index, focusNode, numLevels, index);
        focusNode.rightChild.setTreeLevel(focusNode.treeLevel + 1);
        focusNode.rightChild.isBranchNode = true;
        nodeArray[index] = focusNode.rightChild;
        index++;

        // Create new nodes recursively until reached leaf level
        createTree(focusNode.leftChild, numLevels, nodeArray);
        createTree(focusNode.rightChild, numLevels, nodeArray);

        return;

    }

    // Adds a node to the in-construction tree.
    public pointNode addNode(String name, pointNode parentNode, int treeLayer, int nodeNum) {

        int yshift = 60; // Adjust to change distance between levels.

        int initXShift = 50; // Initial shift for first layer.

        // Handle if root exists, if not add new node.
        if (name == "root") {

            pointNode root = new pointNode(name, treeGroup, globalX / 2, 50, nodeNum);
            this.root = root;
            return root;

        } else {

            pointNode newNode = new pointNode(name, treeGroup, nodeNum);

            if (parentNode == null)
                parentNode = root;

            pointNode focusNode = parentNode;
            pointNode parent = parentNode;

            int xshift;

            xshift = initXShift;

            // Change amount to shift nodes by depending on layer of tree in X and Y
            if (parentNode.treeLevel != 0){
                xshift = (int) (initXShift / (0.5*parentNode.treeLevel));
            }else{

                xshift = 200;
                yshift = 60;


            }


            // Logic for handling which branch to traverse to add new node.
            if (focusNode.leftChild == null) {

                parent.leftChild = newNode;
                parent.leftChild.parent = parent;
                parent.leftChild.setNodeXY(parent.nodeX - xshift, parent.nodeY + yshift);
                return parent.leftChild;

            } else if (focusNode.middleChild == null && focusNode.treeLevel == (treeLayer - 1)) {

                parent.middleChild = newNode;
                parent.middleChild.parent = parent;
                parent.middleChild.setNodeXY(parent.nodeX, parent.nodeY + yshift);
                return parent.middleChild;

            } else if (focusNode.rightChild == null) {

                parent.rightChild = newNode;
                parent.rightChild.parent = parent;
                parent.rightChild.setNodeXY(parent.nodeX + xshift, parent.nodeY + yshift);
                return parent.rightChild;

            } else {

                // Something bad happened

                return null;

            }


        }

    }

    // Adds lines to the created nodes.
    public void inOrderAddLines(pointNode focusNode, Group globalGroup) {

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
    public void inOrderGetNodeShapes(pointNode focusNode, Group globalGroup) {

        if (focusNode != null) {
            globalGroup.getChildren().add(focusNode.nodeCircle);
            inOrderGetNodeShapes(focusNode.leftChild, globalGroup);
            inOrderGetNodeShapes(focusNode.middleChild,globalGroup);
            inOrderGetNodeShapes(focusNode.rightChild, globalGroup);
        }

    }

    // Adds text to global group for each node.
    public void inOrderGetNodeText(pointNode focusNode, Group globalGroup) {

        if (focusNode != null) {
            globalGroup.getChildren().add(focusNode.nodeText);
            inOrderGetNodeText(focusNode.leftChild, globalGroup);
            inOrderGetNodeText(focusNode.middleChild,globalGroup);
            inOrderGetNodeText(focusNode.rightChild, globalGroup);
        }

    }

    // Returns the correct node for a given number value.
    public pointNode getNodebyNum(pointNode focusNode,int keyVal){

        Stack<pointNode> myStack = new Stack<>();
        pointNode findNode = null;

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

    // Initializes other non-user callers.
    public pointCaller initRandCallees (ArrayList<pointNode> inArray, pointCaller[] callArray, String [] calleeIDs, Group p3group){

        for(int i = 0; i < callArray.length; i++){
            boolean cellfound = false;

            while(!cellfound){

                pointNode focusNode = getRandom(inArray);

                if(!focusNode.hasCaller){
                    callArray[i] = new pointCaller(focusNode, calleeIDs[i], p3group);
                    cellfound = true;
                }

            }

        }

        return callArray[callArray.length-1]; // Return X caller.

    }

    // Gets random index from pointNode array.
    public static pointNode getRandom(ArrayList<pointNode> array) {
        int rnd = new Random().nextInt(array.size());
        return array.get(rnd);
    }

    // Main function to search for caller X. (user)
    public void searchForX(pointCaller newCaller, pointCaller callerX){

        double lineWidth = 5.0;
        traverseRoot(newCaller.pointCallerNode);

        pointNode traverseNode = root;
        pointNode prevNode = traverseNode;

        while(!traverseNode.hasCallerX){

            traverseNode = getNodebyNum(root,traverseNode.getCallerPointer(callerX.callerID).nextLoc);

            if(traverseNode.parent == prevNode){
                // Update basic tree lines
                traverseNode.nodeLine.setStrokeWidth(lineWidth);

            }else{

                // Update level Lines
                traverseNode.levelLine.setStrokeWidth(5);

            }

            prevNode = traverseNode;

        }

        // Found caller X! Rejoice!
        traverseNode.nodeCircle.setFill(Color.GOLD);

        return;

    }

    // Adds a level line to the tree structure.
    public void addLevelLine(pointNode N1, pointNode N2){

        Line newLine = new Line(N1.nodeX,N1.nodeY,N2.nodeX,N2.nodeY);
        newLine.setStrokeWidth(1);
        treeGroup.getChildren().add(newLine);
        levelLines.add(newLine);
        N1.addCaller(N2.nodeNum,"X");
        N2.levelLine = newLine;


    }

    // Clears all lines including level lines
    public void clearAllLines(pointNode focusNode, Group p3Group){

            clearLines(focusNode);

    }

    // Resets node lines to their default thickness for the entire tree.
    public void clearLines(pointNode focusNode){

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
    public void resetLeaves(pointNode focusNode){

        if (focusNode != null) {

            focusNode.pointidx = 0; // Reset multiple pointers counter.

            if(focusNode.isLeaf) {
                focusNode.nodeCircle.setFill(Color.GREEN); // Reset to default value.

            }

            resetLeaves(focusNode.leftChild);
            resetLeaves(focusNode.middleChild);
            resetLeaves(focusNode.rightChild);

        }

    }

    // Traverses to the root node and marks path.
    public void traverseRoot(pointNode focusNode){

        double lineWidth = 5.0;
        focusNode.nodeLine.setStrokeWidth(lineWidth);

        if(!(focusNode.parent == root))
            traverseRoot(focusNode.parent);

    }


}
