/**
 * Created by Miner on 1/30/2020.
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.input.*;
public class SceneBuild extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Initialize label
        Label mylabel = new Label();
        mylabel.setText("Select Problem:");
        // Initialize buttons and button events
        Button p1button = new Button();
        Button p2button = new Button();
        Button p3button = new Button();
        p1button.setText("Partition-based Scheme");
        p2button.setText("Working Set Location Scheme");
        p3button.setText("Forwarding Pointers");

        p1button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                p1solution();
            }
        });

        p2button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                p2solution();
            }
        });

        p3button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                p3solution();
            }
        });


        // Setup gridpane with children
        GridPane gridPane = new GridPane();
        gridPane.setMinSize(400,200);
        gridPane.setPadding(new Insets(10,10,10,10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(mylabel,0,0);
        gridPane.add(p1button,0,1);
        gridPane.add(p2button,0,2);
        gridPane.add(p3button,0,3);

        // Setup scene and window parameters
        Scene s = new Scene(gridPane);
        primaryStage.setTitle("Homework #1");
        primaryStage.setScene(s);
        primaryStage.show();
    }
    // BEGIN SECONDARY FUNCTIONS

    public void p1solution(){
        int globalPosX = 900;
        int globalPosY = 250;

        Stage p1stage = new Stage();

        // Create user input gridpane.
        Label inLabel = new Label("User Inputs");
        Label xLabel = new Label("X Position (No overlap)");
        Label callLabel = new Label("Select Caller");
        TextField xPosField = new TextField("3");
        TextField calleeField = new TextField("A");

        GridPane mygrid = new GridPane();

        mygrid.add(inLabel,0,0);
        mygrid.add(xLabel,0,1);
        mygrid.add(xPosField,0,2);
        mygrid.add(callLabel,0,3);
        mygrid.add(calleeField,0,4);
        mygrid.setAlignment(Pos.TOP_LEFT);

        // Setup the tree structure
        Group p1Group = new Group();
        partTree p1Tree = new partTree(2,p1Group,globalPosX,globalPosY);
        partNode nodeArray[] = new partNode[50];

        // Setup tree creation vars.

        int numMain = 3;
        int numSub = 2;
        int numLeaves = 3;
        int index = 1;
        String[] branchNames = {"Left","Right","Middle"};
        nodeArray[0] = p1Tree.addNode("root", null, 0, 0);
        partNode rootNode = nodeArray[0];
        partNode mainParent;
        partNode subParent;

        // Create Tree node hierarchy.

        for(int i = 0; i < numMain; i++){

            nodeArray[index] = p1Tree.addNode(branchNames[i], nodeArray[0], 1, index);
            mainParent = nodeArray[index];
            index++;

            for(int j = 0; j < numSub; j++){

                nodeArray[index] = p1Tree.addNode(branchNames[j],mainParent, 2, index);
                subParent = nodeArray[index];
                index++;

                for(int k = 0; k < numLeaves; k++){

                    nodeArray[index] = p1Tree.addNode(branchNames[k],subParent, 3, index);
                    index++;

                }

            }

        }

        p1Tree.assignLeaves(rootNode);

        // Assign Representatives for Partitions

        int numReps = 4;
        partRep treeReps[] = new partRep[numReps];

        treeReps[0] = new partRep(rootNode.leftChild);
        treeReps[1] = new partRep(rootNode.rightChild);
        treeReps[2] = new partRep(rootNode.middleChild.leftChild);
        treeReps[3] = new partRep(rootNode.middleChild.rightChild);

        // Setup main and sub callers and event handling for user inputs
        int startPos = 3;
        partCaller userCaller = new partCaller(p1Tree.getNodebyNum(rootNode, startPos),"X",p1Group);

        String calleeIDs[] = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        int calleeCells[] = {4, 7, 8, 21, 23, 26, 14, 13, 18, 17};
        int numCallees = calleeIDs.length;
        partCaller Callees[] = new partCaller[numCallees];

        for(int i = 0; i < numCallees; i++){
            Callees[i] = new partCaller(p1Tree.getNodebyNum(rootNode,calleeCells[i]), calleeIDs[i], p1Group);
        }



        // Setup memory for representatives

        for(int i = 0; i< treeReps.length; i++) {
            treeReps[i].getLeafCallees(treeReps[i].repNode);
        }

        xPosField.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER)) {
                   String moveCell = xPosField.getText();

                   partNode newCell = p1Tree.getNodebyNum(rootNode,Integer.parseInt(moveCell));

                   if(newCell.isLeaf && !newCell.hasCaller) {
                       userCaller.callerNode.hasCaller = false;
                       userCaller.callerNode.nodeRep.removeCallee(userCaller);
                       userCaller.setCallerNode(newCell);
                       userCaller.setUserCellPos(userCaller.callerNode.nodeNum);

                       userCaller.updateCallerText();
                       userCaller.callerNode.nodeRep.addCallee(userCaller);
                   }

                }
            }
        });

        calleeField.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER)) {
                    int calleeIdx = 0;
                    String newCalleeID = calleeField.getText();

                    for(int i = 0; i < Callees.length; i++){

                        if(Callees[i].callerID.equals(newCalleeID)){
                            calleeIdx = i;
                            break;
                        }

                    }

                    p1Tree.clearLines(rootNode);
                    p1Tree.resetLeaves(rootNode);
                    p1Tree.searchCallees(userCaller, Callees[calleeIdx]);

                }
            }
        });

        // Get Shapes and text and draw on window.

        p1Tree.inOrderAddLines(p1Tree.root, p1Group);
        p1Tree.inOrderGetNodeShapes(p1Tree.root, p1Group);
        p1Tree.inOrderGetNodeText(p1Tree.root, p1Group);

        p1Group.getChildren().add(mygrid);
        Scene mys = new Scene(p1Group, globalPosX , globalPosY);
        p1stage.setScene(mys);
        p1stage.setTitle("Problem #1 Solution");
        p1stage.show();

    }

    public void p2solution(){
        Stage p2stage = new Stage();



    }

    public void p3solution(){
        Stage p3stage = new Stage();



    }

    // END SECONDARY FUNCTIONS

}
