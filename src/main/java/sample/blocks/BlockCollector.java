package sample.blocks;

import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.HashMap;

@Getter
@Setter
public class BlockCollector {

    public HashMap<Point, Rectangle> rectangles;

    public BlockCollector() {
        this.rectangles = new HashMap<>();
    }

}
