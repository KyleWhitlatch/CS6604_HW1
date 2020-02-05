// This class is for the representative of the partitions. This class also holds the
// "memory" about what nodes contain what callers.

import javafx.scene.paint.Color;

public class partRep {

    int numLeaves; // == max Callers in cell
    partNode repNode;


    partRep(partNode newRep){

        this.repNode = newRep;
        repNode.isRep = true;
        numLeaves = 0;

        getNumLeaves(repNode);

        updateRepCircle();

    }

    public void getNumLeaves(partNode Node){

        if((Node.leftChild == null) && (Node.rightChild == null)){
            numLeaves++;
            return;
        }else{
            getNumLeaves(Node.leftChild);
            getNumLeaves(Node.rightChild);

        }

    }

    public void updateRepCircle(){

        repNode.nodeColor = Color.RED;
        repNode.nodeCircle.setFill(repNode.nodeColor);

    };



}
