// This class contains the node "memory" vars for handling caller/callee logic, such as caller ID

import javafx.scene.Group;
import javafx.scene.text.Text;

public class partCaller {

    int userCellPos;
    partNode callerNode;
    String callerID;
    Text callerText;

    // Constructor for new caller/callee, parses caller ID from user input.
    partCaller(){
    }
    partCaller(partNode userCell, String callerID,Group callGroup){

        this.userCellPos = userCell.nodeNum;
        this.callerNode = userCell;
        this.callerNode.nodeCaller = this;
        this.callerNode.hasCaller = true;
        this.callerID = callerID;
        callerText = new Text("^\n" + this.callerID);

        updateCallerText();

        callGroup.getChildren().add(callerText);

    }

    public void setUserCellPos(int userCellPos) {
        this.userCellPos = userCellPos;
    }

    public void setCallerNode(partNode callerNode){
        this.callerNode = callerNode;

    }

    // Update position of caller text.
    public void updateCallerText(){

        callerText.setX(callerNode.nodeX - 5);
        callerText.setY(callerNode.nodeY + 20);

    }

    @Override
    public String toString() {
        return callerID;
    }
}
