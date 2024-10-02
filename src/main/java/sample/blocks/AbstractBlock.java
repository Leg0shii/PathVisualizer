package sample.blocks;

import javafx.scene.shape.Rectangle;

import java.awt.*;

public abstract class AbstractBlock {

    public double width;
    public double height;
    public Point position;
    public int blockPosition;
    public int blockSize;

    public boolean blockage;

    public AbstractBlock(double width, double height, boolean blockage, Point point, int blockSize) {
        this.width = width;
        this.height = height;
        this.blockage = blockage;
        this.position = point;
        this.blockSize = blockSize;
    }

    public abstract Rectangle placeRectangle();

}
