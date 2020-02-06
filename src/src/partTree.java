import javafx.scene.Group;
import javafx.scene.shape.Line;
import java.lang.Math;

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

}
