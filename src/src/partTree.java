import javafx.scene.Group;
import javafx.scene.shape.Line;

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
    public partNode addNode(String name,partNode parentNode) {

        int curLayer = 0;

        if (name == "root") {

            partNode root = new partNode(name, treeGroup, globalX / 2, 50);
            this.root = root;

        } else {

            partNode newNode = new partNode(name, treeGroup);

            if(parentNode == null)
                parentNode = root;

            partNode focusNode = parentNode;
            partNode parent = parentNode;


            if (name == "Left") {

                focusNode = focusNode.leftChild;

                if (focusNode == null) {

                    parent.leftChild = newNode;
                    parent.leftChild.parent = parent;
                    parent.leftChild.setNodeXY(parent.nodeX - 75, parent.nodeY + 40);
                    return parent.leftChild;

                }

            } else if (name == "Middle") {

                focusNode = focusNode.middleChild;

                if (focusNode == null) {

                    parent.middleChild = newNode;
                    parent.middleChild.parent = parent;
                    parent.middleChild.setNodeXY(parent.nodeX, parent.nodeY + 40);
                    return parent.middleChild;

                }

            } else if(name == "Right") {

                focusNode = focusNode.rightChild;

                if (focusNode == null) {

                    parent.rightChild = newNode;
                    parent.rightChild.parent = parent;
                    parent.rightChild.setNodeXY(parent.nodeX + 75, parent.nodeY + 40);
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


}
