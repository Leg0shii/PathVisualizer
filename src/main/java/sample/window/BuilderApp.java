package sample.window;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.util.Timer;

public class BuilderApp extends Application {

    private static final float WIDTH = 1400;
    private static final float HEIGHT = 1000;

    private double anchorX, anchorY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);

    Rotate rotateX = new Rotate(0, new Point3D(1,0,0));
    Rotate rotateY = new Rotate(0, new Point3D(0,1,0));

    public Box box1;
    public Box box2;

    public MovementEngine movementEngine;
    private boolean ignoreMouseEvent = false;

    private Group root;
    private Group debugGroup;
    private Camera camera;

    @Override
    public void start(Stage primaryStage) {
        this.box1 = prepareBox();
        this.box2 = prepareBox();

        box2.setTranslateZ(-10);
        box2.setTranslateX(50);

        this.root = new Group();
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        this.camera = new PerspectiveCamera(true);
        camera.setNearClip(5000);
        camera.setFarClip(0);
        camera.translateZProperty().set(-10);

        TranslateTransition translateTransitionX = new TranslateTransition(Duration.seconds(0.1), camera);
        TranslateTransition translateTransitionZ = new TranslateTransition(Duration.seconds(0.1), camera);
        movementEngine = new MovementEngine(translateTransitionX, translateTransitionZ, camera, angleY);

        Group subGroup = new Group();
        subGroup.getChildren().add(box1);
        subGroup.getChildren().add(box2);

        SubScene subScene = new SubScene(subGroup, WIDTH, HEIGHT);
        subScene.setFill(Color.SILVER);
        subScene.setCamera(camera);
        camera.getTransforms().addAll(rotateX, rotateY);

        root.getChildren().add(subScene);

        subGroup.translateXProperty().set(0);
        subGroup.translateYProperty().set(0);
        subGroup.translateZProperty().set(0);

        initMouseControl(subGroup, scene, primaryStage);
        initKeyboardControl(subScene, scene);
        this.debugGroup = prepareDebugScreen(root);

        primaryStage.setTitle("Path Visualizer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Group prepareDebugScreen(Group group) {

        Group subGroup = new Group();
        SubScene scene = new SubScene(subGroup, 1000, 1000);
        scene.setFill(Color.TRANSPARENT);

        Rectangle rectangle = new Rectangle(550, 400);
        rectangle.setFill(new Color(1, 1, 1, 0.3));

        Text x = new Text(0, 0, "X: ");
        Text y = new Text(0, 0, "Y: ");
        Text z = new Text(0, 0, "Z: ");
        Text facingX = new Text(0, 0, "X-Facing: ");
        Text facingY = new Text(0, 0, "Y-Facing: ");
        VBox vBox = new VBox();
        vBox.getChildren().addAll(x, y, z, facingX, facingY);

        subGroup.translateXProperty().set(1100);

        vBox.translateXProperty().set(10);
        vBox.translateYProperty().set(10);

        subGroup.setVisible(false);

        subGroup.getChildren().addAll(rectangle, vBox);
        group.getChildren().add(subGroup);

        DebugScreen debugScreen = new DebugScreen(vBox, camera, angleX, angleY);
        Timer timerDS = new Timer();
        timerDS.schedule(debugScreen, 100, 50);

        return subGroup;
    }

    private Box prepareBox() {

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.RED);

        Box box = new Box(1, 1, 1);
        box.setMaterial(material);
        return box;
    }

    private void initMouseControl(Group group, Scene scene, Stage stage) {


        scene.setOnMouseMoved(event -> {

            if(!ignoreMouseEvent) {

                ignoreMouseEvent = true;

                // Maybe scene coordinates?

                int oldAnchX = (int) (stage.getX() + WIDTH/2);
                int oldAnchY = (int) (stage.getY() + HEIGHT/2);
                anchorX = event.getScreenX();
                anchorY = event.getScreenY();

                double dX = (anchorX - oldAnchX) / 40;
                double dY = (anchorY - oldAnchY) / 40;

                // rotateX.transform();
                rotateX.setAngle(rotateX.getAngle()-dY);
                rotateY.setAngle(rotateY.getAngle()+dX);

                try {
                    new Robot().mouseMove((int) (stage.getX() + WIDTH/2), (int) (stage.getY() + HEIGHT/2));
                } catch (AWTException e) {
                    e.printStackTrace();
                }
            } else {
                ignoreMouseEvent = false;
            }
            // System.out.println("Facing: " + angleY.get() + " dX: " + dX + " dY: " + dY);
        });

        stage.addEventHandler(ScrollEvent.SCROLL, event -> {
            double delta = event.getDeltaY()/100;
            group.translateZProperty().set(group.getTranslateZ() + delta);
        });
    }

    private void initKeyboardControl(SubScene subScene, Scene scene) {

        Rotate xRotate;
        Rotate yRotate;

        Camera camera = subScene.getCamera();

        Timer timer = new Timer();
        timer.schedule(movementEngine, 0, 50);

        scene.setOnKeyPressed(event -> {

            switch (event.getCode()) {
                case W:
                    movementEngine.w = true;
                    break;
                case A:
                    movementEngine.a = true;
                    break;
                case S:
                    movementEngine.s = true;
                    break;
                case D:
                    movementEngine.d = true;
                    break;
                case F3:
                    debugGroup.setVisible(!debugGroup.isVisible());
                    break;
                case SPACE:
                    camera.translateYProperty().set(camera.translateYProperty().get() - 1);
                    break;
                case SHIFT:
                    camera.translateYProperty().set(camera.translateYProperty().get() + 1);
                    break;
            }

        });

        scene.setOnKeyReleased(event -> {

            switch (event.getCode()) {
                case W:
                    movementEngine.w = false;
                    break;
                case A:
                    movementEngine.a = false;
                    break;
                case S:
                    movementEngine.s = false;
                    break;
                case D:
                    movementEngine.d = false;
                    break;
            }

        });

    }

    public void runWindow(String[] args) {
        launch(args);
    }

}
