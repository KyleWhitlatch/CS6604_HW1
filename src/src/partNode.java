
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

public class partNode {

    int Key;
    int treeLevel;
    int nodeX;
    int nodeY;
    boolean hasPartition;
    boolean representative;
    Circle nodeCircle;
    Color nodeColor;
    Group nodeGroup;

    String name;

    partNode leftChild;
    partNode middleChild;
    partNode rightChild;

    partNode(int key, String name, Group nodeGroup){

        this.Key = key;
        this.name = name;
        this.nodeX = 200;
        this.nodeY = 50;
        initCircle(nodeGroup);


    }

    partNode(int key, String name, Group nodeGroup,int nodeX, int nodeY){

        this.Key = key;
        this.name = name;
        this.nodeX = nodeX;
        this.nodeY = nodeY;
        initCircle(nodeGroup);


    }

    public void setTreeLevel(int treeLevel) {
        this.treeLevel = treeLevel;
    }

    public void initCircle(Group nodeGroup){

        this.nodeCircle = new Circle(nodeX,nodeY,10);
        this.nodeColor = Color.GREEN; // Standard color for standard node.

        // Make root node a special color
        if(this.name == "root"){
            this.nodeColor = Color.BLUE;
        }

        nodeCircle.setFill(nodeColor);

    }

    public void setNodeXY(int nodeX, int nodeY){
        this.nodeX = nodeX;
        this.nodeY = nodeY;

        nodeCircle.setCenterX(this.nodeX);
        nodeCircle.setCenterY(this.nodeY);


    }


}