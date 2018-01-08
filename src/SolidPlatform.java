package src;

import java.awt.*;

public class SolidPlatform extends Platform{

    public SolidPlatform(int px, int py, int width, int courtWidth, int courtHeight, Color color) {
        super(px, py, width, courtWidth, courtHeight, color);
    }

    public void performAction(Square square){
        super.performAction(square);
    }
}
