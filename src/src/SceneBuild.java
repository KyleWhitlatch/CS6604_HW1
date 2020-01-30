/**
 * Created by Miner on 1/30/2020.
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SceneBuild extends Application {
    Button button;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        StackPane layout = new StackPane();
        button = new Button();
        button.setText("Click");
        layout.getChildren().add(button);
        int x = 0;
        button.setOnAction(e ->{
            button.setText("you clicked me");
        });
        Scene s = new Scene(layout, 640,480);
        primaryStage.setScene(s);
        primaryStage.show();
    }
}
