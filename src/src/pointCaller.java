import javafx.scene.Group;
import javafx.scene.text.Text;

import java.util.Random;

public class pointCaller extends partCaller {

    pointNode pointCallerNode;
    int userCellPos;
    String callerID;
    Text callerText;
    int CMR;
    int CMRmax = 10;

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
        CMR = rand.nextInt(CMRmax);

        callerText = new Text("^\n" + this.callerID + "\n" + CMR + "/" + CMRmax);

        updateCallerText();


        callGroup.getChildren().add(callerText);

    }

    public void updateCallerText(){

        callerText.setX(callerNode.nodeX - 5);
        callerText.setY(callerNode.nodeY + 20);

    }



}
