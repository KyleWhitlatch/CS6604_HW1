import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class partNode {

    // General node vars.
    int nodeNum;
    int treeLevel;
    int nodeX;
    int nodeY;
    Circle nodeCircle;
    Color nodeColor;
    Text nodeText;
    Line nodeLine;
    Group nodeGroup;
    String name;

    // General tree structure vars.
    partNode parent;
    partNode leftChild;
    partNode middleChild;
    partNode rightChild;

    // Representative vars
    boolean isInPart;
    boolean isRep;
    boolean isLeaf;

    partNode( String name, Group nodeGroup, int nodeNum){

        this.name = name;
        this.nodeX = 200;
        this.nodeY = 50;
        this.nodeNum = nodeNum;
        this.nodeText = new Text();
        nodeText.setText("" + this.nodeNum);
        nodeText.setX(this.nodeX - 5);
        nodeText.setY(this.nodeY + 5);
        initCircle(nodeGroup);

    }

    partNode(String name, Group nodeGroup,int nodeX, int nodeY, int nodeNum){

        this.name = name;
        this.nodeX = nodeX;
        this.nodeY = nodeY;
        this.nodeNum = nodeNum;
        this.nodeText = new Text();
        nodeText.setText("" + this.nodeNum);
        nodeText.setX(this.nodeX - 5);
        nodeText.setY(this.nodeY + 5);
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

        nodeText.setX(this.nodeX - 5);
        nodeText.setY(this.nodeY + 5);

    }



}