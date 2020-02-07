import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import java.lang.Math;
import java.util.Stack;

public class partTree {

    int numLayers;
    int globalX;
    int globalY;
    Group treeGroup;
    partNode root;

    // Constructor for partTree
    public partTree(int numLayers, Group p1group, int globalX, int globalY) {

        this.numLayers = numLayers;
        this.treeGroup = p1group;
        this.globalX = globalX;
        this.globalY = globalY;

    }

    // Adds nodes to tree with key and name.
    public partNode addNode(String name,partNode parentNode, int treeLayer, int nodeNum) {

        int yshift = 80; // Adjust to change distance between levels.

        int initXShift = 250; // Initial shift for first layer.

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

            if(treeLayer != 1)
                xshift = (int)(initXShift / (2*treeLayer));

            yshift = yshift/treeLayer;

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

    // Generates lines for each node (apart from root). Lines are assigned to children from each node.
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

    public void inOrderGetNodeText(partNode focusNode, Group globalGroup) {

        if (focusNode != null) {
            globalGroup.getChildren().add(focusNode.nodeText);
            inOrderGetNodeText(focusNode.leftChild, globalGroup);
            inOrderGetNodeText(focusNode.middleChild,globalGroup);
            inOrderGetNodeText(focusNode.rightChild, globalGroup);
        }

    }

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

    public void searchCallees(partCaller userCaller, partCaller userCallee){

        traverseRep(userCaller.callerNode);
        if(userCaller.callerNode.nodeRep.checkCallee(userCallee)){
            repFindCallee(userCaller.callerNode.nodeRep, userCallee);
        }else{
            searchOtherReps(userCaller, userCallee);
        }




    }

    public void traverseRep(partNode focusNode){

        double lineWidth = 5.0;
        focusNode.nodeLine.setStrokeWidth(lineWidth);

        if(!focusNode.parent.isRep)
            traverseRep(focusNode.parent);

    }

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
