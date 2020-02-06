// This class contains searching logic for caller.

import javafx.scene.Group;
import javafx.scene.text.Text;

public class partCaller {

    int userCellPos;
    partNode callerNode;
    String callerID;
    Text callerText;

    partCaller(partNode userCell, String callerID,Group callGroup){

        this.userCellPos = userCell.nodeNum;
        this.callerNode = userCell;
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

    public void updateCallerText(){

        callerText.setX(callerNode.nodeX - 5);
        callerText.setY(callerNode.nodeY + 20);

    }




}
