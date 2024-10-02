package sample.blocks;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.*;

public class Block extends AbstractBlock {

    public Block(Point point, int blockSize, boolean blockage) {
        super(blockSize, blockSize, blockage, point, blockSize);
    }

    @Override
    public Rectangle placeRectangle() {

        Rectangle rectangle = new Rectangle(position.x*blockSize,position.y*blockSize, width, height);
        rectangle.setStroke(Color.BLACK);

        if(blockage) rectangle.setFill(Color.RED);
        else rectangle.setFill(Color.GRAY);

        return rectangle;
    }
}
