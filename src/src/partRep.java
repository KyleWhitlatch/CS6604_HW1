// This class is for the representative of the partitions. This class also holds the
// "memory" about what nodes contain what callers.

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class partRep {

    int numLeaves = 0; // == max Callers in cell
    partNode repNode; // The rep's node
    ArrayList<String> repCallees; // The callees that belong to the rep
    int repCalleeIdx = 0;


    partRep(partNode newRep){

        this.repNode = newRep;
        repNode.isRep = true;
        repNode.repInNode = this;
        getNumLeaves(repNode);
        repCallees = new ArrayList<>();
        updateRepCircle();

    }

    public void getLeafCallees(partNode Node){

        if(Node != null) {

            if(Node.isLeaf){
                Node.nodeRep = this;
            }

            if (Node.hasCaller) {
                repCallees.add(Node.nodeCaller.callerID);

                repCalleeIdx++;
                return;
            } else {
                getLeafCallees(Node.leftChild);
                getLeafCallees(Node.middleChild);
                getLeafCallees(Node.rightChild);

            }
        }

    }

    public void addCallee(partCaller addedCallee){

        repCallees.add(addedCallee.callerID);

    }

    public void removeCallee(partCaller removedCallee){

        for(int i = 0; i < repCallees.size(); i++){

            if(repCallees.get(i) == removedCallee.callerID){
                repCallees.remove(i);
                return;

            }

        }

    }

    public boolean checkCallee(partCaller checkCallee){

        for(int i = 0; i < repCallees.size(); i++){

            if(repCallees.get(i) == checkCallee.callerID)
                return true;

        }

        return false;

    }

    public void getNumLeaves(partNode Node){

        if(Node != null) {

            if (Node.isLeaf) {
                numLeaves++;
                return;
            } else {
                getNumLeaves(Node.leftChild);
                getNumLeaves(Node.middleChild);
                getNumLeaves(Node.rightChild);

            }
        }

    }

    public void updateRepCircle(){

        repNode.nodeColor = Color.RED;
        repNode.nodeCircle.setFill(repNode.nodeColor);

    };



}
