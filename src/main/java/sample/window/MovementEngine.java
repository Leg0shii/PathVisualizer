package sample.window;

import javafx.animation.TranslateTransition;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Camera;

import java.util.TimerTask;

public class MovementEngine extends TimerTask {

    private TranslateTransition translateTransitionX;
    private TranslateTransition translateTransitionZ;
    private double xVel;
    private double zVel;
    private double angle;
    private DoubleProperty angleY;

    public boolean w;
    public boolean a;
    public boolean s;
    public boolean d;

    private double m;
    private double direction;

    private Camera camera;

    public MovementEngine(TranslateTransition tX, TranslateTransition tZ, Camera camera, DoubleProperty angleY) {
        this.translateTransitionX = tX;
        this.translateTransitionZ = tZ;
        this.xVel = 0;
        this.zVel = 0;
        this.camera = camera;
        this.angleY = angleY;
        this.w = false;
        this.a = false;
        this.s = false;
        this.d = false;
    }

    public double calcXVel() {
        return (xVel * 0.91 + 0.1 * m * 0.216 * Math.sin(((angle+direction))*Math.PI/180));
    }

    public double calcZVel() {
        return (zVel * 0.91 + 0.1 * m * 0.216 * Math.cos(((angle+direction))*Math.PI/180));
    }

    @Override
    public void run() {

        this.angle = angleY.get();
        translateTransitionX.setFromX(camera.translateXProperty().get());
        translateTransitionZ.setFromZ(camera.translateZProperty().get());

        if (w || a || d || s) m = 1;
        if (a) direction = 270;
        if (s) direction = 180;
        if (d) direction = 90;

        this.xVel = calcXVel();
        this.zVel = calcZVel();

        if (Math.abs(xVel * 1 * 0.91) < 0.005) this.xVel = 0;
        else {
            translateTransitionX.setByX(xVel);
            translateTransitionX.play();
        }

        if (Math.abs(zVel * 1 * 0.91) < 0.005) this.zVel = 0;
        else {
            translateTransitionZ.setByZ(zVel);
            translateTransitionZ.play();
        }

        direction = 0;
        m = 0;

    }

}
