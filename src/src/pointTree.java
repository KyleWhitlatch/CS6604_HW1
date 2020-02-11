import javafx.scene.Group;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class pointTree extends partTree {

    pointNode root;
    int index; // Used for recursive creation, starts at 1.

    pointTree(Group treeGroup, int treeX, int treeY, int index) {

        super(treeGroup, treeX, treeY);
        this.index = index;


    }

    public void createTree(pointNode focusNode, int numLevels, pointNode[] nodeArray) {

        // Check if we have reached the (n-1) tree level, if so, create leaves and return.
        if (focusNode.treeLevel >= (numLevels - 1)) {

            // THREE LEAF NODES PER N-1 BRANCH
            focusNode.leftChild = addNode("" + index, focusNode, numLevels, index);
            focusNode.leftChild.isLeaf = true;
            nodeArray[index] = focusNode.leftChild;
            index++;
            focusNode.middleChild = addNode("" + index, focusNode, numLevels, index);
            focusNode.middleChild.isLeaf = true;
            nodeArray[index] = focusNode.middleChild;
            index++;
            focusNode.rightChild = addNode("" + index, focusNode, numLevels, index);
            focusNode.rightChild.isLeaf = true;
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
    public partNode getNodebyNum(pointNode focusNode,int keyVal){

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

    public void initRandCallees (ArrayList<pointNode> inArray, pointCaller[] callArray, String [] calleeIDs, Group p3group){

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

    }

    public static pointNode getRandom(ArrayList<pointNode> array) {
        int rnd = new Random().nextInt(array.size());
        return array.get(rnd);
    }



}
