package sample.window;

import javafx.beans.property.DoubleProperty;
import javafx.geometry.Point3D;
import javafx.scene.Camera;
import javafx.scene.Node;
import javafx.scene.PointLight;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.TimerTask;

public class DebugScreen extends TimerTask {

    private VBox box;

    private Text x;
    private Text y;
    private Text z;
    private Text xFacing;
    private Text yFacing;
    private Camera camera;
    private DoubleProperty angleX;
    private DoubleProperty angleY;

    public DebugScreen(VBox vBox, Camera camera, DoubleProperty angleX, DoubleProperty angleY) {

        this.box = vBox;
        this.x = (Text) box.getChildren().get(0);
        this.y = (Text) box.getChildren().get(1);
        this.z = (Text) box.getChildren().get(2);
        this.xFacing = (Text) box.getChildren().get(3);
        this.yFacing = (Text) box.getChildren().get(4);
        this.camera = camera;
        this.angleX = angleX;
        this.angleY = angleY;
    }

    @Override
    public void run() {

        x.setText("X: " + camera.getTranslateX());
        y.setText("Y: " + camera.getTranslateY());
        z.setText("Z: " + camera.getTranslateZ());
        xFacing.setText("X-Facing: " + angleX.get());
        yFacing.setText("Y-Facing: " + angleY.get());
    }
}
