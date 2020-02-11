import javafx.scene.Group;

public class pointNode extends partNode {

    // General tree structure vars.
    pointNode parent;
    pointNode leftChild;
    pointNode middleChild;
    pointNode rightChild;
    boolean isBranchNode;

    public pointNode(String name, Group nodeGroup, int nodeNum){

        super(name, nodeGroup, nodeNum);

    }

    public pointNode(String name, Group nodeGroup, int nodeX, int nodeY, int nodeNum){

        super(name, nodeGroup, nodeX,nodeY,nodeNum);

    }

}
