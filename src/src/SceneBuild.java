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
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.*;

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
        int globalPosX = 700;
        int globalPosY = 350;

        Stage p1stage = new Stage();

        Label templabel = new Label("this is a test");

        GridPane mygrid = new GridPane();
        mygrid.add(templabel,0,0);
        mygrid.setAlignment(Pos.TOP_LEFT);

        // Setup the tree structure
        Group p1Group = new Group();
        partTree p1Tree = new partTree(2,p1Group,globalPosX,globalPosY);

        partNode nodeArray[] = new partNode[50];

        // Add root node with blue circle and main branch nodes

        nodeArray[0] = p1Tree.addNode("root", null);
        nodeArray[1] = p1Tree.addNode("Left", nodeArray[0]);
        nodeArray[2] = p1Tree.addNode("Right", nodeArray[0]);




        p1Tree.inOrderAddLines(p1Tree.root, p1Group);
        p1Tree.inOrderGetNodeShapes(p1Tree.root, p1Group);












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
