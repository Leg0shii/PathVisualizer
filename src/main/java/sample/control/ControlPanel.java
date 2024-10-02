package sample.control;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ControlPanel {

    private int windowSize;
    private Stage stage;

    public ControlPanel(int windowSize, Stage stage) {
        this.windowSize = windowSize;
        this.stage = stage;
    }

    public void create(Parent root) {

        Scene scene = new Scene(root, windowSize+100, windowSize);

        stage.setTitle("Minecraft Parkour Thing");
        stage.setScene(scene);
        stage.show();

    }

}
