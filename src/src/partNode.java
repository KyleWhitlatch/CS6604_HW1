
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class partNode {

    int treeLevel;
    int nodeX;
    int nodeY;
    boolean hasPartition;
    boolean representative;
    Circle nodeCircle;
    Color nodeColor;
    Line nodeLine;
    Group nodeGroup;

    String name;

    partNode parent;
    partNode leftChild;
    partNode middleChild;
    partNode rightChild;

    partNode( String name, Group nodeGroup){

        this.name = name;
        this.nodeX = 200;
        this.nodeY = 50;
        initCircle(nodeGroup);


    }

    partNode(String name, Group nodeGroup,int nodeX, int nodeY){

        this.name = name;
        this.nodeX = nodeX;
        this.nodeY = nodeY;
        initCircle(nodeGroup);

    }

    // Sets nodes level is tree.
    public void setTreeLevel(int treeLevel) {
        this.treeLevel = treeLevel;
    }

    // Initialize node circle shape and color.
    public void initCircle(Group nodeGroup){

        this.nodeCircle = new Circle(nodeX,nodeY,10);
        this.nodeColor = Color.GREEN; // Standard color for standard node.

        // Make root node a special color
        if(this.name == "root"){
            this.nodeColor = Color.BLUE;
        }

        nodeCircle.setFill(nodeColor);

    }

    // Set XY coordinates for a node.
    public void setNodeXY(int nodeX, int nodeY){
        this.nodeX = nodeX;
        this.nodeY = nodeY;

        nodeCircle.setCenterX(this.nodeX);
        nodeCircle.setCenterY(this.nodeY);


    }


}