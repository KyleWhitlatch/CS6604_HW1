// Child of partNode to handle forwarding pointers and level lines.
import javafx.scene.Group;
import javafx.scene.shape.Line;

import java.util.ArrayList;

public class pointNode extends partNode {

    // General tree structure vars.
    pointNode parent;
    pointNode leftChild;
    pointNode middleChild;
    pointNode rightChild;
    boolean isBranchNode;
    boolean hasCallerX;
    int pointidx = 0;

    Line levelLine;

    // Branch node forward pointer memory

    ArrayList<forPoint> pointers;

    public pointNode(String name, Group nodeGroup, int nodeNum){

        super(name, nodeGroup, nodeNum);

        pointers = new ArrayList<>();
        hasCallerX = false;

    }

    public pointNode(String name, Group nodeGroup, int nodeX, int nodeY, int nodeNum){

        super(name, nodeGroup, nodeX,nodeY,nodeNum);

        pointers = new ArrayList<>();
        hasCallerX = false;

    }

    // Adds caller to branch node memory.
    public void addCaller(int callLoc, String callerID){

        forPoint temp = new forPoint(callLoc,callerID);
        pointers.add(temp);

    }

    // Returns a caller pointer.
    public forPoint getCallerPointer(String callerID){

        int i = pointidx;
        pointidx++;
        return pointers.get(i);

    }

    // Updates a pointer.
    public void updatePointer(String callerID, int newLoc){

        for(int i = 0; i < pointers.size(); i++){

            if(pointers.get(i).callerID.equals(callerID)){
                pointers.get(i).nextLoc = newLoc;
            }

        }



    }

    // Removes a certain pointer.
    public void removeCaller(String callerID){

        for(int i = 0; i < pointers.size(); i++){

            if(pointers.get(i).callerID.equals(callerID)){
                pointers.remove(i);
            }

        }

    }




}
