// This class handles each of the callers for problem 3.
import javafx.scene.Group;
import javafx.scene.text.Text;

import java.util.Random;

public class pointCaller extends partCaller {

    pointNode pointCallerNode;
    int userCellPos;
    String callerID;
    Text callerText;
    double CMR;
    double CMRmax = 10; // Maximum CMR, can be changed if necessary.
    int maxUpdateLayer;

    pointCaller(pointNode callerNode, String callerID, Group callGroup ){

        super();
        this.pointCallerNode = callerNode;

        this.userCellPos = callerNode.nodeNum;
        this.callerNode = callerNode;
        this.callerNode.nodeCaller = this;
        this.callerNode.hasCaller = true;
        this.callerID = callerID;

        // Set CMR ratio to random num.
        Random rand = new Random();

        callerText = new Text("^\n" + this.callerID);

        updateCallerText();


        callGroup.getChildren().add(callerText);

    }

    // Overwrites partNode func with pointNodes
    public void updateCallerText(){

        callerText.setX(pointCallerNode.nodeX - 5);
        callerText.setY(pointCallerNode.nodeY + 20);

    }

    //Initializes caller pointers to root.
    public void initPointers(){

        pointNode focusNode = this.pointCallerNode;
        pointNode parentNode;

        while(true){

            if(focusNode.parent.name.equals("root")){
                focusNode.parent.addCaller(focusNode.nodeNum, this.callerID);
                return;

            }

            parentNode = focusNode.parent;

            parentNode.addCaller(focusNode.nodeNum, this.callerID);

            focusNode = focusNode.parent;
            parentNode = focusNode.parent;

        }

    }

    // Sets the max layer that will update with the callers current CMR
    public void setMaxUpdateLayer(int numLayers){

        double cmrratio = CMR/CMRmax;
        double numLayersdub = (double) numLayers;

        for(double i = 1; i <= (numLayers-1); i++){

            if(cmrratio <= (i/numLayersdub)){

                maxUpdateLayer = (int)i;
                return;
            }

        }

    }

    // Updates a callers location and database pointers upon moving.
    public void updateLocAndPointers(pointNode newCell, pointTree globalTree){

        int currLevel = this.pointCallerNode.treeLevel;
        pointNode focusNode = this.pointCallerNode;

        // Update old node vars
        focusNode.hasCaller = false;
        focusNode.hasCallerX = false;

        // Get parent at right level of new node
        pointNode newFocusNode = newCell.parent;
        pointNode prevNewNode = newCell;

        while(!(newFocusNode.treeLevel <= maxUpdateLayer)){

            newFocusNode.addCaller(prevNewNode.nodeNum, this.callerID);
            prevNewNode = newFocusNode;
            newFocusNode = newFocusNode.parent;

        }
        newFocusNode.addCaller(prevNewNode.nodeNum, this.callerID);

        // Remove pointers from old parent nodes until transfer layer
        while(!(currLevel <= maxUpdateLayer)){

            focusNode = focusNode.parent;
            focusNode.removeCaller(this.callerID);

            currLevel--;
        }


        if(!(focusNode == newFocusNode)){

            focusNode.updatePointer(this.callerID,newFocusNode.nodeNum);
            globalTree.addLevelLine(focusNode, newFocusNode);

        }

        this.pointCallerNode = newCell;
        this.pointCallerNode.hasCaller = true;
        this.pointCallerNode.hasCallerX = true;
        this.userCellPos = this.pointCallerNode.nodeNum;

    }

}
